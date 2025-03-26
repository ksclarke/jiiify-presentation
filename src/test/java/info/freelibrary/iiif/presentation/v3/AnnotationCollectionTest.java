
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertOptEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;

import info.freelibrary.iiif.presentation.v3.annotation.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;

/**
 * Tests of {@link AnnotationCollection}.
 */
public class AnnotationCollectionTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** A test ID. */
    private static final String ID = "https://example.org/id";

    /** A test label. */
    private static final Label LABEL = new Label("My great label");

    /**
     * Tests {@link AnnotationCollection#equals(Object) AnnotationCollection}.
     */
    @Test
    public final void testAnnotationCollectionEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final AnnotationCollection test1 = new AnnotationCollection(HTTPS + id, LABEL);
        final AnnotationCollection test2 = new AnnotationCollection(HTTPS + id, LABEL);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AnnotationCollection#equals(Object) AnnotationCollection}.
     */
    @Test
    public final void testAnnotationCollectionEqualsHashCodeNot() {
        final AnnotationCollection test1 = new AnnotationCollection(HTTPS + UUID.randomUUID().toString(), LABEL);
        final AnnotationCollection test2 = new AnnotationCollection(HTTPS + UUID.randomUUID().toString(), LABEL);

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AnnotationCollection#equals(Object) AnnotationCollection}.
     */
    @Test
    public final void testAnnotationCollectionEqualsNull() {
        assertNotEquals(new AnnotationCollection(HTTPS + UUID.randomUUID().toString(), LABEL), null);
    }

    /**
     * Tests {@link AnnotationCollection#equals(Object) AnnotationCollection}.
     */
    @Test
    public final void testAnnotationCollectionEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final AnnotationCollection test1 = new AnnotationCollection(HTTPS + id, LABEL);
        final AnnotationCollection test2 = new AnnotationCollection(HTTPS + id, LABEL);

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link AnnotationCollection#equals(Object) AnnotationCollection}.
     */
    @Test
    public final void testAnnotationCollectionEqualsSameNot() {
        final AnnotationCollection test1 = new AnnotationCollection(HTTPS + UUID.randomUUID().toString(), LABEL);
        final AnnotationCollection test2 = new AnnotationCollection(HTTPS + UUID.randomUUID().toString(), LABEL);

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link AnnotationCollection#equals(Object) AnnotationCollection}.
     */
    @Test
    public final void testAnnotationCollectionEqualsSameObject() {
        final AnnotationCollection test = new AnnotationCollection(HTTPS + UUID.randomUUID().toString(), LABEL);
        assertEquals(test, test);
    }

    /**
     * Tests {@link AnnotationCollection#equals(Object) AnnotationCollection}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testAnnotationCollectionEqualsString() {
        assertNotEquals(new AnnotationCollection(HTTPS + UUID.randomUUID().toString(), LABEL),
                new String(Constants.EMPTY));
    }

    /**
     * Tests an {@link AnnotationCollection} constructor.
     */
    @Test
    public void testConstructorStringIdStringLabel() {
        assertEquals(ID, new AnnotationCollection(ID, LABEL).getID());
    }

    /**
     * Tests an {@link AnnotationCollection} constructor.
     */
    @Test
    public void testConstructorUriIdLabel() {
        assertEquals(ID, new AnnotationCollection(ID, LABEL).getID());
    }

    /**
     * Tests {@link AnnotationCollection#setFirstPage(AnnotationPage)}.
     */
    @Test
    public final void testGetSetFirstPage() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final BookmarkingAnnotation annotation = new BookmarkingAnnotation(minter);
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        page.setAnnotations(annotation);
        assertTrue(new AnnotationCollection(ID, LABEL).setFirstPage(page).getFirstPage().isPresent());
    }

    /**
     * Tests {@link AnnotationCollection#setLastPage(AnnotationPage)}.
     */
    @Test
    public final void testGetSetLastPage() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final BookmarkingAnnotation annotation = new BookmarkingAnnotation(minter);
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(minter, new Canvas(minter));

        page.setAnnotations(annotation);
        assertTrue(new AnnotationCollection(ID, LABEL).setLastPage(page).getLastPage().isPresent());
    }

    /**
     * Tests viewing direction on a {@link AnnotationCollection}.
     */
    @Test
    public void testGetSetViewingDirection() {
        final AnnotationCollection annotationCollection =
                new AnnotationCollection(ID, LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertOptEquals(ViewingDirection.LEFT_TO_RIGHT, annotationCollection.getViewingDirection());
    }

    /**
     * Tests {@link AnnotationCollection#setBehavior(Behavior)}.
     */
    @Test
    public final void testSetBehaviorList() {
        assertEquals(1, new AnnotationCollection(ID, LABEL).setBehaviors(List.of(ResourceBehavior.HIDDEN))
                .getBehaviors().size());
    }

    /**
     * Test setting {@link AnnotationCollection} behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final AnnotationCollection annotationCollection = new AnnotationCollection(ID, LABEL);
        assertEquals(1, annotationCollection.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed {@link AnnotationCollection} behaviors.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        final AnnotationCollection annotationCollection = new AnnotationCollection(ID, LABEL);
        annotationCollection.setBehaviors(ManifestBehavior.AUTO_ADVANCE);
    }

}
