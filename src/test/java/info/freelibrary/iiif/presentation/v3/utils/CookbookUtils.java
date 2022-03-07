
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.util.Constants.SLASH;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Assert;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.json.InvalidPointerException;
import info.freelibrary.json.Json;
import info.freelibrary.json.JsonObject;
import info.freelibrary.json.JsonOptions;
import info.freelibrary.json.JsonPointer;
import info.freelibrary.json.JsonReader;

/**
 * Utilities related to the cookbook recipes.
 */
public class CookbookUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookUtils.class, MessageCodes.BUNDLE);

    private static final String RECIPE_TEST_DIR = "src/test/resources/cookbook/";

    private static final String COOKBOOK = "https://iiif.io/api/cookbook/";

    private static final String COOKBOOK_RECIPE = COOKBOOK + "recipe/";

    private static final String RECIPE_PAGE_RE = ".*\\/cookbook\\/recipe\\/\\d{4}.*";

    private static final String RECIPE_RE = "^(?!https?:\\/\\/).*\\.json";

    private static final String EOL = System.lineSeparator();

    private static final String HREF = "href";

    private static final String LINK = "a";

    public static void checkCookbooks() {
        try {
            final Set<JsonPointer> collapsibleArrays = Set.of(new JsonPointer("/@context"));
            final JsonOptions options = new JsonOptions().ignoreOrder(true).setCollapsibleArrays(collapsibleArrays);
            final Elements links = Jsoup.connect(COOKBOOK).get().select(LINK);

            links.stream().map(link -> link.attr(HREF)).filter(url -> url.matches(RECIPE_PAGE_RE)).forEach(url -> {
                compareLocalFiles(getJsonURLs(url), options);
            });
        } catch (final IOException | InvalidPointerException details) {
            throw new CookbookRecipeException(details);
        }
    }

    public static void assertEquals(final String aJson1, final String aJson2) {
        try {
            final JsonPointer bodyPointer = new JsonPointer("/items/0/items/0/items/0/body");
            final Set<JsonPointer> collapsibleArrays = Set.of(new JsonPointer("/@context"), bodyPointer);
            final JsonOptions options = new JsonOptions().ignoreOrder(true).setCollapsibleArrays(collapsibleArrays);
            final JsonObject json1 = (JsonObject) Json.parse(aJson1);
            final JsonObject json2 = (JsonObject) Json.parse(aJson2);

            // Do the comparison in a smart way, but produce output that's more useful
            if (!json1.equals(json2, options)) {
                Assert.assertEquals(aJson1, aJson2);
            }
        } catch (final InvalidPointerException details) {
            throw new CookbookRecipeException(details);
        }
    }

    private static void compareLocalFiles(final List<String> aLinkList, final JsonOptions aConfig) {
        aLinkList.forEach(url -> {
            final int index = url.indexOf(COOKBOOK_RECIPE) + COOKBOOK_RECIPE.length();
            final File file = new File(RECIPE_TEST_DIR, url.substring(index));

            try {
                if (file.exists()) {
                    final JsonObject fileJson = (JsonObject) Json.parse(new JsonReader(file));
                    final JsonObject urlJson = (JsonObject) Json.parse(new JsonReader(new URL(url)));

                    if (!urlJson.equals(fileJson, aConfig)) {
                        LOGGER.warn(MessageCodes.JPA_127, EOL, file, EOL, fileJson, EOL, url, EOL, urlJson);
                    }
                } else {
                    LOGGER.warn(MessageCodes.JPA_128, EOL, url);
                }
            } catch (final IOException details) {
                throw new CookbookRecipeException(details);
            }
        });
    }

    private static List<String> getJsonURLs(final String aHref) {
        final String baseURL = !aHref.endsWith(SLASH) ? aHref + SLASH : aHref;
        final List<String> list = new ArrayList<>();

        try {
            final Elements links = Jsoup.connect(aHref).get().select(LINK);

            links.stream().map(link -> link.attr(HREF)).filter(url -> url.matches(RECIPE_RE)).forEach(path -> {
                list.add(baseURL + path);
            });
        } catch (final IOException details) {
            throw new CookbookRecipeException(details);
        }

        return list;
    }
}
