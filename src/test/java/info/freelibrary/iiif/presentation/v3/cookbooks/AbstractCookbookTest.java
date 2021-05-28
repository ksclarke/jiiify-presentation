
package info.freelibrary.iiif.presentation.v3.cookbooks;

import java.io.File;
import java.io.IOException;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * An abstract class that other tests running the cookbooks as test fixtures can extend.
 */
public abstract class AbstractCookbookTest {

    /**
     * A method to retrieve a test fixture JSON document.
     *
     * @param aName The name of the file to use as a test fixture
     * @return The string representation of the JSON test fixture
     * @throws IOException If there is trouble reading the test fixture
     */
    protected abstract String getExpected(String aName) throws IOException;

    /**
     * A method to retrieve a test fixture JSON document.
     *
     * @param aFile The file to use as a test fixture
     * @return The string representation of the JSON test fixture
     * @throws IOException If there is trouble reading the test fixture
     */
    protected String getExpected(final File aFile) throws IOException {
        return getExpected(aFile.getName());
    }

    /**
     * Since the minter's randomly generated IDs will differ from the ones in the expected JSON file, we normalize IDs.
     *
     * @param aJsonObject A JsonObject whose IDs should be normalized
     * @return The JsonObject with its IDs normalized
     */
    protected String normalizeIDs(final JsonObject aJsonObject) {
        aJsonObject.forEach(entry -> {
            final Object valueObj = entry.getValue();
            final String valueClassName = valueObj.getClass().getSimpleName();
            final String key = entry.getKey();
            final String value = valueObj.toString();

            // We ignore IDs... they're tested in other places
            if (JsonKeys.ID.equals(key) ||
                    (JsonKeys.TARGET.equals(key) || JsonKeys.SOURCE.equals(key)) && value.startsWith("http")) {
                entry.setValue("ZEROED_OUT_ID");
            }

            // We want to check inside JsonObject(s) and JsonArray(s) too
            if (valueClassName.equals(JsonObject.class.getSimpleName())) {
                normalizeIDs((JsonObject) valueObj);
            } else if (valueClassName.equals(JsonArray.class.getSimpleName())) {
                ((JsonArray) valueObj).forEach(object -> {
                    if (object instanceof JsonObject) {
                        normalizeIDs((JsonObject) object);
                    }
                });
            }
        });

        // We're modifying the same object, this is just a convenience
        return aJsonObject.encodePrettily();
    }

}
