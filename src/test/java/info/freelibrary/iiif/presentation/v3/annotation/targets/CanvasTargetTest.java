
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;

/**
 * Tests of {@link _Target}.
 */
public class CanvasTargetTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests {@link _CanvasTarget#equals(Object) CanvasTarget}.
     */
    @Test
    public final void testTargetEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final Target test1 = new CanvasTarget(HTTPS + id);
        final Target test2 = new CanvasTarget(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link _CanvasTarget#equals(Object) CanvasTarget}.
     */
    @Test
    public final void testTargetEqualsHashCodeNot() {
        final CanvasTarget test1 = new CanvasTarget(HTTPS + UUID.randomUUID().toString());
        final CanvasTarget test2 = new CanvasTarget(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link _CanvasTarget#equals(Object) CanvasTarget}.
     */
    @Test
    public final void testTargetEqualsNull() {
        final CanvasTarget test = new CanvasTarget(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link _CanvasTarget#equals(Object) CanvasTarget}.
     */
    @Test
    public final void testTargetEqualsSame() {
        final String id = UUID.randomUUID().toString();
        assertEquals(new CanvasTarget(HTTPS + id), new CanvasTarget(HTTPS + id));
    }

    /**
     * Tests {@link _CanvasTarget#equals(Object) CanvasTarget}.
     */
    @Test
    public final void testTargetEqualsSameNot() {
        final CanvasTarget test1 = new CanvasTarget(HTTPS + UUID.randomUUID().toString());
        final CanvasTarget test2 = new CanvasTarget(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link _CanvasTarget#equals(Object) CanvasTarget}.
     */
    @Test
    public final void testTargetEqualsSameObject() {
        final CanvasTarget test = new CanvasTarget(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link _CanvasTarget#equals(Object) CanvasTarget}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testTargetEqualsString() {
        final CanvasTarget test = new CanvasTarget(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, Constants.EMPTY);
    }

    /**
     * Tests {@link _CanvasTarget#getType() CanvasTarget}.
     */
    @Test
    public final void testTargetGetType() {
        final Optional<String> type = new CanvasTarget(HTTPS + UUID.randomUUID().toString()).getType();

        assertTrue(type.isPresent());
        assertEquals(ResourceTypes.CANVAS, type.get());
    }

    /**
     * Tests {@link _CanvasTarget#setID(String) CanvasTarget}.
     */
    @Test
    public final void testTargetSetID() {
        final String id = UUID.randomUUID().toString();
        final CanvasTarget test = new CanvasTarget(HTTPS + UUID.randomUUID().toString());

        assertEquals(id, test.setID(id).getID());
    }

    /**
     * Tests {@link _CanvasTarget#setType(String) CanvasTarget}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testTargetSetTypeBad() {
        final CanvasTarget target = new CanvasTarget(HTTPS + UUID.randomUUID().toString());
        target.setType("asdfasdf");
    }

    /**
     * Tests {@link _CanvasTarget#setType(String) CanvasTarget}.
     */
    @Test
    public final void testTargetSetTypeGood() {
        final CanvasTarget target = new CanvasTarget(HTTPS + UUID.randomUUID().toString());
        final Optional<String> type = target.setType(ResourceTypes.CANVAS).getType();

        if (type.isPresent()) {
            assertEquals(ResourceTypes.CANVAS, type.get());
        } else {
            fail();
        }
    }
}
