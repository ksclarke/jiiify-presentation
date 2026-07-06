
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import info.freelibrary.iiif.presentation.v3.annotation.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.util.Constants;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Tests {@link AbstractCanvas}.
 */
public class AbstractCanvasTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests {@link AbstractCanvas#equals(Object) AbstractCanvas}.
     */
    @Test
    public final void testAbstractCanvasEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final AbstractCanvas<TestClass> test1 = new TestClass(HTTPS + id);
        final AbstractCanvas<TestClass> test2 = new TestClass(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AbstractCanvas#equals(Object) AbstractCanvas}.
     */
    @Test
    public final void testAbstractCanvasEqualsHashCodeNot() {
        final AbstractCanvas<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final AbstractCanvas<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AbstractCanvas#equals(Object) AbstractCanvas}.
     */
    @Test
    public final void testAbstractCanvasEqualsNull() {
        assertNotEquals(new TestClass(HTTPS + UUID.randomUUID().toString()), null);
    }

    /**
     * Tests {@link AbstractCanvas#equals(Object) AbstractCanvas}.
     */
    @Test
    public final void testAbstractCanvasEqualsSame() {
        final String id = UUID.randomUUID().toString();
        assertEquals(new TestClass(HTTPS + id), new TestClass(HTTPS + id));
    }

    /**
     * Tests {@link AbstractCanvas#equals(Object) AbstractCanvas}.
     */
    @Test
    public final void testAbstractCanvasEqualsSameNot() {
        final AbstractCanvas<TestClass> test1 = new TestClass(HTTPS + UUID.randomUUID().toString());
        final AbstractCanvas<TestClass> test2 = new TestClass(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link AbstractCanvas#equals(Object) AbstractCanvas}.
     */
    @Test
    public final void testAbstractCanvasEqualsSameObject() {
        final AbstractCanvas<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link AbstractCanvas#equals(Object) AbstractCanvas}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testAbstractCanvasEqualsString() {
        final AbstractCanvas<TestClass> test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, Constants.EMPTY);
    }

    /**
     * Tests {@link AbstractCanvas#getMinter()}.
     */
    @Test
    public final void testGetMinter() {
        assertTrue(new TestClass(HTTPS + UUID.randomUUID().toString()).getMinter().isEmpty());
    }

    /**
     * Tests {@link AbstractCanvas#getMinter()}.
     */
    @Test
    public final void testGetMinterNull() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        assertTrue(new TestClass(minter).getMinter().isPresent());
    }

    /**
     * Tests {@link AbstractCanvas#setBehaviors(List)}.
     */
    @Test
    public final void testSetBehaviorsList() {
        final TestClass test = new TestClass(HTTPS + UUID.randomUUID().toString());
        assertEquals(1, test.setBehaviors(List.of(CanvasBehavior.FACING_PAGES)).getBehaviors().size());
    }

    /**
     * Tests {@link AbstractCanvas#setMinter(Minter)}.
     */
    @Test
    public final void testSetMinter() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);

        assertTrue(new TestClass(id).setMinter(minter).getMinter().isPresent());
    }

    /**
     * Tests {@link AbstractCanvas#setPaintingPages(List)}.
     */
    @Test
    public final void testSetPaintingPages() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<PaintingAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        assertEquals(1, new TestClass(minter).setPaintingPages(page).setPaintingPages(page).getPaintingPages().size());
    }

    /**
     * Tests {@link AbstractCanvas#setPaintingPages(List)}.
     */
    @Test
    public final void testSetPaintingPagesList() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<PaintingAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        assertEquals(1,
                new TestClass(minter).setPaintingPages(page).setPaintingPages(List.of(page)).getPaintingPages().size());
    }

    /**
     * Tests {@link AbstractCanvas#setSupplementingPages(List)}.
     */
    @Test
    public final void testSetSupplementingPages() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<SupplementingAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        assertEquals(1, new TestClass(minter).setSupplementingPages(page).setSupplementingPages(page)
                .getSupplementingPages().size());
    }

    /**
     * Tests {@link AbstractCanvas#setSupplementingPages(List)}.
     */
    @Test
    public final void testSetSupplementingPagesList() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<SupplementingAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        assertEquals(1, new TestClass(minter).setSupplementingPages(page).setSupplementingPages(List.of(page))
                .getSupplementingPages().size());
    }

    /**
     * Tests {@link AbstractCanvas#setWebAnnotations(List)}.
     */
    @Test
    public final void testSetWebAnnotations() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        page.setAnnotations(new BookmarkingAnnotation(minter));
        assertEquals(1, new TestClass(minter).setWebAnnotations(page).getWebAnnotations().size());
    }

    /**
     * Tests {@link AbstractCanvas#setWebAnnotations(List)}.
     */
    @Test
    public final void testSetWebAnnotationsList() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        page.setAnnotations(new BookmarkingAnnotation(minter));
        assertEquals(1, new TestClass(minter).setWebAnnotations(List.of(page)).getWebAnnotations().size());
    }

    /**
     * A test class.
     */
    private static final class TestClass extends AbstractCanvas<TestClass> {

        /**
         * Creates a new test object.
         *
         * @param aMinter A minter to use in testing
         */
        private TestClass(final Minter aMinter) {
            super(aMinter);
        }

        /**
         * Creates a new test object.
         *
         * @param aID An ID to use in testing
         */
        private TestClass(final String aID) {
            super(aID);
        }
    }

}
