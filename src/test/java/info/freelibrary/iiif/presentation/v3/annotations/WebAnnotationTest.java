
package info.freelibrary.iiif.presentation.v3.annotations;

import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SINGLE_INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.Canvas;

/**
 * Tests of {@link WebAnnotation}.
 */
public class WebAnnotationTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** An ID used in testing. */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = HTTPS + UUID.randomUUID().toString();
    }

    /**
     * Tests {@link WebAnnotation#equals(Object) WebAnnotation}.
     */
    @Test
    public final void testWebAnnotationEqualsHashCode() {
        final WebAnnotation test1 = new WebAnnotation(myID, new Canvas(myID));
        final WebAnnotation test2 = new WebAnnotation(myID, new Canvas(myID));

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link WebAnnotation#equals(Object) WebAnnotation}.
     */
    @Test
    public final void testWebAnnotationEqualsHashCodeNot() {
        final WebAnnotation test1 = new WebAnnotation(myID, new Canvas(myID));
        final WebAnnotation test2 = new WebAnnotation(myID + SINGLE_INSTANCE, new Canvas(myID));

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link WebAnnotation#equals(Object) WebAnnotation}.
     */
    @Test
    public final void testWebAnnotationEqualsNull() {
        assertNotEquals(new WebAnnotation(myID, new Canvas(myID)), null);
    }

    /**
     * Tests {@link WebAnnotation#equals(Object) WebAnnotation}.
     */
    @Test
    public final void testWebAnnotationEqualsSame() {
        final WebAnnotation test1 = new WebAnnotation(myID, new Canvas(myID));
        final WebAnnotation test2 = new WebAnnotation(myID, new Canvas(myID));

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link WebAnnotation#equals(Object) WebAnnotation}.
     */
    @Test
    public final void testWebAnnotationEqualsSameNot() {
        final WebAnnotation test1 = new WebAnnotation(myID, new Canvas(myID));
        final WebAnnotation test2 = new WebAnnotation(myID + "0", new Canvas(myID));

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link WebAnnotation#equals(Object) WebAnnotation}.
     */
    @Test
    public final void testWebAnnotationEqualsSameObject() {
        final WebAnnotation test = new WebAnnotation(myID, new Canvas(myID));
        assertEquals(test, test);
    }

    /**
     * Tests {@link WebAnnotation#equals(Object) WebAnnotation}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testWebAnnotationEqualsString() {
        assertNotEquals(new WebAnnotation(myID, new Canvas(myID)), EMPTY);
    }
}
