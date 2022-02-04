
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.util.Constants.SLASH;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.json.Json;
import info.freelibrary.json.JsonObject;
import info.freelibrary.json.JsonReader;

public class CookbookUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookUtils.class, MessageCodes.BUNDLE);

    private static final String RECIPE_TEST_DIR = "src/test/resources/cookbook/";

    private static final String COOKBOOK = "https://iiif.io/api/cookbook/";

    private static final String COOKBOOK_RECIPE = COOKBOOK + "recipe/";

    private static final String RECIPE_PAGE_RE = ".*\\/cookbook\\/recipe\\/\\d{4}.*";

    private static final String RECIPE_RE = "^(?!https?:\\/\\/).*\\.json";

    private static final String HREF = "href";

    private static final String LINK = "a";

    @Test
    public void checkCookbooks() throws MalformedURLException, IOException {
        try {
            final Elements links = Jsoup.connect(COOKBOOK).get().select(LINK);

            links.stream().map(link -> link.attr(HREF)).filter(url -> url.matches(RECIPE_PAGE_RE)).forEach(url -> {
                compareLocalFiles(getJsonURLs(url));
            });
        } catch (final HttpStatusException details) {
            LOGGER.error(details.getMessage(), details);
        }
    }

    //
    // TODO: magicfree-json
    // * Option to collapse arrays with single items, by property name
    // * Option to compare values with regex similarity, by property name
    // * Option to ignore order, totally (done) or by property name (not done)
    //
    private void compareLocalFiles(final List<String> aLinkList) {
        aLinkList.forEach(url -> {
            final int index = url.indexOf(COOKBOOK_RECIPE) + COOKBOOK_RECIPE.length();
            final File file = new File(RECIPE_TEST_DIR, url.substring(index));

            try {
                if (file.exists()) {
                    final JsonObject json = (JsonObject) Json.parse(new JsonReader(new URL(url)));

                    if (!json.equalsIgnoreOrder(Json.parse(new JsonReader(file)))) {
                        System.out.println("Needs updating: " + file + " " + url);
                        System.out.println(json);
                        System.out.println(Json.parse(new JsonReader(file)));
                        System.out.println();
                    }
                } else {
                    // System.out.println("Does not exist: " + file);
                }
            } catch (final IOException details) {

            }
        });
    }

    private List<String> getJsonURLs(final String aHref) {
        final String baseURL = !aHref.endsWith(SLASH) ? aHref + SLASH : aHref;
        final List<String> list = new ArrayList<>();

        try {
            final Elements links = Jsoup.connect(aHref).get().select(LINK);

            links.stream().map(link -> link.attr(HREF)).filter(url -> url.matches(RECIPE_RE)).forEach(path -> {
                list.add(baseURL + path);
            });

            return list;
        } catch (final IOException details) {
            LOGGER.error(details.getMessage(), details);
        }

        return list;
    }
}
