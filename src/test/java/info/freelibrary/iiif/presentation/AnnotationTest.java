
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

public class AnnotationTest {

    private static final URI ID = URI.create("http://example.org/id");

    @Test
    public void testAnnotationURI() {
        assertEquals(ID.toString(), new Annotation(ID.toString()).getID().toString());
    }

    @Test
    public void testAnnotationString() {
        assertEquals(ID, new Annotation(ID).getID());
    }

}
