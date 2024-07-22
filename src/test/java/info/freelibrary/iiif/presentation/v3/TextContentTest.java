
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;

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
 * Tests of {@code TextContent}.
 */
public class TextContentTest {

    /** A text content to test. */
    private TextContent myText;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myText = new TextContent("https://" + UUID.randomUUID().toString());
    }

    /**
     * Test method for {@link TextContent#setBehaviors(Behavior[])}.
     */
    @Test
    public void testSetBehaviorsBehaviorArray() {
        assertEquals(1, myText.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test method for {@link TextContent#setBehaviors(Behavior[])}.
     */
    @Test(expected = InvalidBehaviorException.class)
    public void testSetBehaviorsBehaviorArrayInvalid() {
        myText.setBehaviors(CanvasBehavior.AUTO_ADVANCE);
    }

    /**
     * Test method for {@link TextContent#setBehaviors(List)}.
     */
    @Test
    public void testSetBehaviorsListOfBehavior() {
        assertEquals(1, myText.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors().size());
    }

    /**
     * Test method for {@link TextContent#setBehaviors(Behavior[])}.
     */
    @Test(expected = InvalidBehaviorException.class)
    public void testSetBehaviorsListOfBehaviorInvalid() {
        myText.setBehaviors(new BehaviorList(ResourceBehavior.class, CanvasBehavior.AUTO_ADVANCE));
    }
}
