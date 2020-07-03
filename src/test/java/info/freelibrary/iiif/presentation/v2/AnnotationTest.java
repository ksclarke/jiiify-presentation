
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;

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

}
