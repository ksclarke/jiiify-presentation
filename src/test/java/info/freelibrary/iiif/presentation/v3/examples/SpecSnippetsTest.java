
package info.freelibrary.iiif.presentation.v3.examples;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.cookbooks.AbstractCookbookTest;

/**
 * Tests built around examples/snippets in the v3 presentation specification.
 */
public class SpecSnippetsTest extends AbstractCookbookTest {

    /**
     * A pattern from which to pull manifest from the test resources directory.
     */
    private static final String MANIFEST_PATTERN = "src/test/resources/examples/{}.json";

    /**
     * Tests the example manifest from the specification (cf.
     * https://iiif.io/api/presentation/3.0/#b-example-manifest-response)
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void testSpecExample() throws IOException {
        final String expected = getExpected("spec-example");
        final String found = Manifest.fromJSON(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Gets a cookbook manifest as a string.
     *
     * @param aManifestName A manifest file name
     * @return A JSON serialization of a manifest
     * @throws IOException If there is trouble reading the manifest file
     */
    @Override
    protected String getExpected(final String aManifestName) throws IOException {
        return updateDuration(StringUtils.read(new File(StringUtils.format(MANIFEST_PATTERN, aManifestName))));
    }

}
