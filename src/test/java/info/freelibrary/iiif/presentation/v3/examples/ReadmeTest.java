
package info.freelibrary.iiif.presentation.v3.examples;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.utils.Manifestor;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Examples from documentation.
 */
public class ReadmeTest {

    /**
     * Example from from the README.md
     *
     * @throws IOException If there is trouble reading or writing a file.
     */
    @Test
    public final void testReadmeExample() throws IOException {
        final Manifestor manifestor = new Manifestor();
        final Manifest manifest = manifestor.readManifest(new File(TestUtils.TEST_DIR, "z1960050.json"));

        manifest.getMetadata().add(new Metadata("Contributor", "Your Name Here"));
        manifestor.write(manifest, File.createTempFile("z1960050", ".json"));
    }

}
