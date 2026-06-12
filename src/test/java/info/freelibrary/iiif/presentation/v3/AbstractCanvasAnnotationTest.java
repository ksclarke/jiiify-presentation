
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import info.freelibrary.iiif.presentation.v3.annotation.AbstractCanvasAnnotation;
import info.freelibrary.iiif.presentation.v3.content.DatasetContent;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.util.Constants;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Tests of {@link AbstractCanvasAnnotation}.
 */
public class AbstractCanvasAnnotationTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests {@link AbstractCanvasAnnotation#equals(Object) AbstractCanvasAnnotation}.
     */
    @Test
    public final void testAbstractCanvasAnnotationEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final AbstractCanvasAnnotation<TestClass> test1 = new TestClass(HTTPS + id);
        final AbstractCanvasAnnotation<TestClass> test2 = new TestClass(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#equals(Object) AbstractCanvasAnnotation}.
     */
    @Test
    public final void testAbstractCanvasAnnotationEqualsHashCodeNot() {
        final AbstractCanvasAnnotation<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final AbstractCanvasAnnotation<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#equals(Object) AbstractCanvasAnnotation}.
     */
    @Test
    public final void testAbstractCanvasAnnotationEqualsNull() {
        final AbstractCanvasAnnotation<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#equals(Object) AbstractCanvasAnnotation}.
     */
    @Test
    public final void testAbstractCanvasAnnotationEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final AbstractCanvasAnnotation<TestClass> test1 = new TestClass(HTTPS + id);
        final AbstractCanvasAnnotation<TestClass> test2 = new TestClass(HTTPS + id);

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#equals(Object) AbstractCanvasAnnotation}.
     */
    @Test
    public final void testAbstractCanvasAnnotationEqualsSameNot() {
        final AbstractCanvasAnnotation<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final AbstractCanvasAnnotation<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#equals(Object) AbstractCanvasAnnotation}.
     */
    @Test
    public final void testAbstractCanvasAnnotationEqualsSameObject() {
        final AbstractCanvasAnnotation<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#equals(Object) AbstractCanvasAnnotation}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testAbstractCanvasAnnotationEqualsString() {
        final AbstractCanvasAnnotation<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, new String(Constants.EMPTY));
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#setBehavior(Behavior)}.
     */
    @Test
    public final void testSetBehaviorList() {
        final AbstractCanvasAnnotation<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(1, test.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors().size());
    }

    /**
     * Tests {@link AbstractCanvasAnnotation#setBehavior(Behavior)}.
     */
    @Test
    public final void testSetBodyDatasetContent() {
        final AbstractCanvasAnnotation<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(1, test.setBody(new DatasetContent(HTTPS + UUID.randomUUID().toString())).getBody().size());
    }

    /**
     * A test class.
     */
    private static final class TestClass extends AbstractCanvasAnnotation<TestClass> {

        /**
         * Creates a new test object.
         *
         * @param aID An ID to use in testing
         */
        private TestClass(final String aID) {
            super();
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
