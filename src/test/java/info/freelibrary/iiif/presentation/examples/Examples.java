
package info.freelibrary.iiif.presentation.examples;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.Manifestor;

public class Examples {

    @Test
    public final void testReadmeExample() throws IOException {
        final Manifestor manifestor = new Manifestor();
        final Manifest manifest = manifestor.read(new File("src/test/resources/json/z1960050.json"));

        manifest.getMetadata().add("Contributor", "Your Name Here");
        manifestor.write(manifest, File.createTempFile("z1960050", ".json"));
    }

}
