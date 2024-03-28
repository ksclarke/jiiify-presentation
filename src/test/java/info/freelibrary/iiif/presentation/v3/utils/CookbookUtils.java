
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.util.Constants.EOL;
import static info.freelibrary.util.Constants.SLASH;
import static info.freelibrary.util.Constants.SPACE;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.json.Json;
import info.freelibrary.json.JsonObject;
import info.freelibrary.json.JsonOptions;
import info.freelibrary.json.JsonReader;

/**
 * Utilities related to the cookbook recipes.
 */
public final class CookbookUtils {

    /** The cookbook utilities logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookUtils.class, MessageCodes.BUNDLE);

    /** The cookbook fixtures directory. */
    private static final String RECIPE_TEST_DIR = "src/test/resources/cookbook/";

    /** The base URL for IIIF cookbooks. */
    private static final String BASE_COOKBOOK_URL = "https://iiif.io";

    /** The URL for the IIIF cookbooks. */
    private static final String COOKBOOK_URL = BASE_COOKBOOK_URL + "/api/cookbook/";

    /** The base cookbook recipe URL. */
    private static final String COOKBOOK_RECIPE = COOKBOOK_URL + "recipe/";

    /** A regular expression for a cookbook recipe page. */
    private static final String RECIPE_PAGE_RE = ".*\\/cookbook\\/recipe\\/\\d{4}.*";

    /** A regular expression for the cookbook recipe. */
    private static final String RECIPE_RE = "^(?!https?:\\/\\/).*\\.json";

    /** A constant for the HREF attribute. */
    private static final String HREF = "href";

    /** A constant for the link tag. */
    private static final String LINK = "a";

    /**
     * Creates a new cookbook utilities instance.
     */
    private CookbookUtils() {
        // This is intentionally left empty.
    }

    /**
     * A utility to check the IIIF cookbooks for changes.
     *
     * @throws CookbookRecipeException If there is trouble checking the cookbook fixtures
     */
    public static void checkCookbooks() {
        try {
            final Stream<Element> links = Jsoup.connect(COOKBOOK_URL).get().select(LINK).stream();
            final JsonOptions options = new JsonOptions().ignoreOrder(true);
            final SortedSet<String> urlSet = new TreeSet<>(); // another de-duplication and final sort

            links.distinct().map(link -> link.attr(HREF)).filter(url -> url.matches(RECIPE_PAGE_RE)).forEach(url -> {
                urlSet.addAll(getJsonURLs(url.replaceFirst("^/", BASE_COOKBOOK_URL + SLASH)));
            });

            compareLocalFiles(urlSet, options);
        } catch (final IOException details) {
            throw new CookbookRecipeException(details);
        }
    }

    /**
     * Gets a list of URLs to compare against against the local files..
     *
     * @param aHref An href from a recipe page link
     * @return A list of related JSON URLs
     * @throws CookbookRecipeException If a JSON URL cannot be scraped from the cookbook site
     */
    private static List<String> getJsonURLs(final String aHref) {
        final String baseURL = !aHref.endsWith(SLASH) ? aHref + SLASH : aHref;
        final List<String> urlList = new ArrayList<>();

        try {
            final Stream<Element> links = Jsoup.connect(aHref).get().select(LINK).stream();

            links.distinct().map(link -> link.attr(HREF)).filter(url -> url.matches(RECIPE_RE)).forEach(path -> {
                urlList.add(baseURL + path);
            });
        } catch (final IOException details) {
            throw new CookbookRecipeException(details);
        }

        return urlList;
    }

    /**
     * Compare the local recipe files against the downloaded files.
     *
     * @param aLinkSet A set of recipe links
     * @param aConfig A comparison configuration
     * @throws CookbookRecipeException If the cookbook fixture files cannot be compared
     */
    private static void compareLocalFiles(final Set<String> aLinkSet, final JsonOptions aConfig) {
        aLinkSet.stream().distinct().forEach(url -> {
            final int index = url.indexOf(COOKBOOK_RECIPE) + COOKBOOK_RECIPE.length();
            final File file = new File(RECIPE_TEST_DIR, url.substring(index));

            try {
                if (file.exists()) {
                    final JsonObject local = (JsonObject) Json.parse(new JsonReader(file));
                    final JsonObject remote = (JsonObject) Json.parse(new JsonReader(new URL(url)));

                    if (!remote.equals(local, aConfig)) {
                        LOGGER.warn(MessageCodes.JPA_134, EOL, String.join(EOL + EOL, file.getAbsolutePath(),
                                local.toString(), SPACE + url, remote.toString()) + EOL);
                    }
                } else {
                    LOGGER.warn(MessageCodes.JPA_135, EOL, url + EOL);
                }
            } catch (final IOException details) {
                throw new CookbookRecipeException(details);
            }
        });
    }
}
