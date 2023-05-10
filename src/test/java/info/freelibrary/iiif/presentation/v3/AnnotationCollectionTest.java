
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;

/**
 * Tests of {@link AnnotationCollection}.
 */
public class AnnotationCollectionTest {

    /** A test ID. */
    private static final String ID = "https://example.org/id";

    /** A test label. */
    private static final Label LABEL = new Label("My great label");

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
     * Tests viewing direction on a {@link AnnotationCollection}.
     */
    @Test
    public void testGetSetViewingDirection() {
        final AnnotationCollection annotationCollection =
                new AnnotationCollection(ID, LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, annotationCollection.getViewingDirection());
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
