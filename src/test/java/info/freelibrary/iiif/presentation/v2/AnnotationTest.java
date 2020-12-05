
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;

/**
 * Tests an annotation.
 */
public class AnnotationTest {

    private static final URI ID = URI.create("http://example.org/id");

    /**
     * Tests constructing an annotation.
     */
    @Test
    public void testAnnotationURI() {
        assertEquals(ID.toString(), new Annotation(ID.toString()).getID().toString());
    }

    /**
     * Tests constructing an annotation.
     */
    @Test
    public void testAnnotationString() {
        assertEquals(ID, new Annotation(ID).getID());
    }

    /**
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final Annotation anno = new Annotation(ID.toString()).setAttribution(UUID.randomUUID().toString());

        anno.clearAttribution();
        assertTrue(anno.getAttribution() == null);
    }

    /**
     * Test the clearViewingHint() method.
     */
    @Test
    public void testClearViewingHint() {
        final Annotation anno = new Annotation(ID.toString()).setViewingHint(ViewingHint.Option.INDIVIDUALS);

        anno.clearViewingHint();
        assertTrue(anno.getViewingHint() == null);
    }
}
