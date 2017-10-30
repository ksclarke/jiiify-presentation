
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

public class AnnotationListTest {

    private static final URI ID = URI.create("http://example.org/id");

    @Test
    public void testAnnotationListString() {
        assertEquals(ID.toString(), new AnnotationList(ID.toString()).getID().toString());
    }

    @Test
    public void testAnnotationListURI() {
        assertEquals(ID, new AnnotationList(ID).getID());
    }

}
