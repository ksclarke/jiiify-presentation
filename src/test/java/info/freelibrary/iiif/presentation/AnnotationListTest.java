
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

/**
 * An annotation list for testing.
 */
public class AnnotationListTest {

    private static final URI ID = URI.create("http://example.org/id");

    /**
     * Tests constructing an annotation list.
     */
    @Test
    public void testAnnotationListString() {
        assertEquals(ID.toString(), new AnnotationList(ID.toString()).getID().toString());
    }

    /**
     * Tests constructing an annotation list.
     */
    @Test
    public void testAnnotationListURI() {
        assertEquals(ID, new AnnotationList(ID).getID());
    }

}
