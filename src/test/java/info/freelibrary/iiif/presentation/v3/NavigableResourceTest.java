
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;

import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;

/**
 * Tests of {@link NavigableResource}.
 */
public class NavigableResourceTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final NavigableResource<TestClass> test1 = new TestClass(HTTPS + id);
        final NavigableResource<TestClass> test2 = new TestClass(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsHashCodeNot() {
        final NavigableResource<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final NavigableResource<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsNull() {
        final NavigableResource<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertFalse(test.equals(null));
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final NavigableResource<TestClass> test1 = new TestClass(HTTPS + id);
        final NavigableResource<TestClass> test2 = new TestClass(HTTPS + id);

        assertTrue(test1.equals(test2));
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSameNot() {
        final NavigableResource<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final NavigableResource<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertFalse(test1.equals(test2));
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSameObject() {
        final NavigableResource<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertTrue(test.equals(test));
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testNavigableResourceEqualsString() {
        final NavigableResource<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertFalse(test.equals(new String(Constants.EMPTY)));
    }

    /**
     * A test class.
     */
    private static class TestClass extends NavigableResource<TestClass> {

        /**
         * Creates a new test object.
         * 
         * @param aID An ID to use in testing
         */
        public TestClass(final String aID) {
            super(ResourceTypes.CANVAS, CanvasBehavior.class);
            super.setID(aID);
        }
    }
}
