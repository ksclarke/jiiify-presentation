
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * Tests video content.
 */
public class VideoContentTest {

    /** The test ID. */
    private String myID;

    /**
     * Sets up testing environment.
     */
    @Before
    public final void setup() {
        myID = "https://" + UUID.randomUUID().toString() + ".mp4";
    }

    /**
     * Tests video content test fixture.
     *
     * @throws IOException If there is trouble reading the test fixture.
     */
    @Test
    public final void testFixture0003() throws IOException {
        final String expected = format(StringUtils.read(new File("src/test/resources/fixtures/0003-mvm-video.json")));
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(expected, format(found));
    }

    /**
     * Tests {@link VideoContent#setBehaviors(Behavior...)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorArray() {
        final List<Behavior> behaviors = new VideoContent(myID).setBehaviors(ResourceBehavior.HIDDEN).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link VideoContent#setBehaviors(Behavior...)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorArrayInvalid() {
        new VideoContent(myID).setBehaviors(CanvasBehavior.NON_PAGED);
    }

    /**
     * Tests {@link VideoContent#setBehaviors(List)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorList() {
        final VideoContent content = new VideoContent(myID);
        final List<Behavior> behaviors = content.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link VideoContent#setBehaviors(List)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorListInvalid() {
        new VideoContent(myID).setBehaviors(List.of(CanvasBehavior.NON_PAGED));
    }

    /**
     * Tests {@link VideoContent#setBehaviors(List)} with an explicit {@code BehaviorList}.
     */
    @Test
    public final void testSetBehaviorsRealBehaviorList() {
        final List<Behavior> behaviors = new VideoContent(myID)
                .setBehaviors(new BehaviorList(ResourceBehavior.class, ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests setting and getting video content duration.
     */
    @Test
    public final void testSetGetDuration() {
        assertEquals(2f, new VideoContent(myID).setDuration(2f).getDuration(), 0);
    }

    /**
     * Tests setting and getting video content width and height.
     */
    @Test
    public final void testSetGetWidthHeight() {
        final VideoContent video = new VideoContent(myID).setWidthHeight(480, 360);
        assertEquals(480, video.getWidth());
        assertEquals(360, video.getHeight());
    }

    /**
     * Tests constructing new video content with an ID.
     */
    @Test
    public final void testVideoContentString() {
        assertEquals(myID, new VideoContent(myID).getID());
    }
}
