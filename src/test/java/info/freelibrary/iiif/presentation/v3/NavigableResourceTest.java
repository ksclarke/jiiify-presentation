
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

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
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final NavigableResource<TestClass> test1 = new TestClass(HTTPS + id);
        final NavigableResource<TestClass> test2 = new TestClass(HTTPS + id);

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSameNot() {
        final NavigableResource<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final NavigableResource<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSameObject() {
        final NavigableResource<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testNavigableResourceEqualsString() {
        assertNotEquals(new TestClass(HTTPS + UUID.randomUUID().toString()), EMPTY);
    }

    /**
     * Tests {@link NavigableResource#getContextList() NavigableResource}.
     */
    @Test
    public final void testNavigableResourceGetContextList() {
        final String id = UUID.randomUUID().toString();
        final NavigableResource<TestClass> test = new TestClass(HTTPS + id);

        assertEquals(1, test.getContextList().size()); // Uninitialized
        assertEquals(1, test.getContextList().size()); // Should now be initialized
    }

    /**
     * Tests {@link NavigableResource#getContexts() NavigableResource}.
     */
    @Test
    public final void testNavigableResourceGetContexts() {
        final String id = UUID.randomUUID().toString();
        final NavigableResource<TestClass> test = new TestClass(HTTPS + id);

        assertEquals(1, test.getContexts().size());
    }

    /**
     * Tests {@link NavigableResource#setContexts(List) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceSetContextsWithContextList() {
        final String id = UUID.randomUUID().toString();
        final NavigableResource<TestClass> test = new TestClass(HTTPS + id);
        final ContextList contexts = new ContextList();

        assertEquals(contexts, test.setContexts(contexts).getContexts());
    }

    /**
     * Tests {@link NavigableResource#setContexts(List) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceSetContextsWithURIList() {
        final String id = UUID.randomUUID().toString();
        final NavigableResource<TestClass> test = new TestClass(HTTPS + id);
        final List<URI> contexts = new ArrayList<>();

        contexts.add(URI.create(id));
        assertEquals(2, test.setContexts(contexts).getContexts().size());
    }

    /**
     * A test class.
     */
    private static final class TestClass extends NavigableResource<TestClass> {

        /**
         * Creates a new test object.
         *
         * @param aID An ID to use in testing
         */
        private TestClass(final String aID) {
            super(ResourceTypes.CANVAS, CanvasBehavior.class);
            super.setID(aID);
        }
    }
}
