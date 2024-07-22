
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;

/**
 * Tests of {@link ModelContent}.
 */
public class ModelContentTest {

    /** A test ID. */
    private String myID;

    /**
     * Sets up testing environment.
     */
    @Before
    public final void setup() {
        myID = "https://" + UUID.randomUUID().toString();
    }

    /**
     * Tests {@link ModelContent#setBehaviors(Behavior...)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorArray() {
        final List<Behavior> behaviors = new ModelContent(myID).setBehaviors(ResourceBehavior.HIDDEN).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link ModelContent#setBehaviors(Behavior...)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorArrayInvalid() {
        new ModelContent(myID).setBehaviors(CanvasBehavior.NON_PAGED);
    }

    /**
     * Tests {@link ModelContent#setBehaviors(List)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorList() {
        final ModelContent content = new ModelContent(myID);
        final List<Behavior> behaviors = content.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link ModelContent#setBehaviors(List)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorListInvalid() {
        new ModelContent(myID).setBehaviors(List.of(CanvasBehavior.NON_PAGED));
    }

    /**
     * Tests {@link ModelContent#setBehaviors(List)} with an explicit {@code BehaviorList}.
     */
    @Test
    public final void testSetBehaviorsRealBehaviorList() {
        final List<Behavior> behaviors = new ModelContent(myID)
                .setBehaviors(new BehaviorList(ResourceBehavior.class, ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }
}
