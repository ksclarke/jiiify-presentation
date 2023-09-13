
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;

/**
 * An annotation list for testing.
 */
public class AnnotationListTest {

    /** A test value. */
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

    /**
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final AnnotationList annoList = new AnnotationList(ID.toString());

        annoList.setAttribution(UUID.randomUUID().toString()).clearAttribution();
        assertTrue(annoList.getAttribution() == null);
    }

    /**
     * Test the clearViewingHint() method.
     */
    @Test
    public void testClearViewingHint() {
        final AnnotationList annoList = new AnnotationList(ID.toString());

        annoList.setViewingHint(ViewingHint.Option.INDIVIDUALS).clearViewingHint();
        assertTrue(annoList.getViewingHint() == null);
    }
}
