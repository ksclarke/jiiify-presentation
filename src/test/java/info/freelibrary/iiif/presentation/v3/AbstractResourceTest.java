
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * An abstract testing class for resources.
 */
public class AbstractResourceTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests {@link AbstractResource#equals(Object) AbstractResource}.
     */
    @Test
    public final void testAbstractResourceEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final AbstractResource<TestClass> test1 = new TestClass(HTTPS + id);
        final AbstractResource<TestClass> test2 = new TestClass(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AbstractResource#equals(Object) AbstractResource}.
     */
    @Test
    public final void testAbstractResourceEqualsHashCodeNot() {
        final AbstractResource<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final AbstractResource<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AbstractResource#equals(Object) AbstractResource}.
     */
    @Test
    public final void testAbstractResourceEqualsNull() {
        assertNotEquals(new TestClass(HTTPS + UUID.randomUUID().toString()), null);
    }

    /**
     * Tests {@link AbstractResource#equals(Object) AbstractResource}.
     */
    @Test
    public final void testAbstractResourceEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final AbstractResource<TestClass> test1 = new TestClass(HTTPS + id);
        final AbstractResource<TestClass> test2 = new TestClass(HTTPS + id);

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link AbstractResource#equals(Object) AbstractResource}.
     */
    @Test
    public final void testAbstractResourceEqualsSameNot() {
        final AbstractResource<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final AbstractResource<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link AbstractResource#equals(Object) AbstractResource}.
     */
    @Test
    public final void testAbstractResourceEqualsSameObject() {
        final AbstractResource<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link AbstractResource#equals(Object) AbstractResource}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testAbstractResourceEqualsString() {
        assertNotEquals(new TestClass(HTTPS + UUID.randomUUID().toString()), EMPTY);
    }

    /**
     * Tests {@link AbstractResource#setBehavior(Behavior)}.
     */
    @Test
    public final void testSetBehavior() {
        final AbstractResource<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(ResourceBehavior.HIDDEN,
                test.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors().get(0));
    }

    /**
     * A test class.
     */
    private static final class TestClass extends AbstractResource<TestClass> {

        /**
         * Creates a new test object.
         *
         * @param aID An ID to use in testing
         */
        private TestClass(final String aID) {
            super("TestType", ResourceBehavior.class);
            super.setID(aID);
        }

        /**
         * Creates a copy of this test object.
         *
         * @return A copy of this test object
         */
        public TestClass copy() {
            return new TestClass(getID());
        }
    }
}
