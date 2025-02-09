
package info.freelibrary.iiif.presentation.v3.content;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * Tests for {@link SoundContent}.
 */
public class SoundContentTest extends AbstractTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** The test ID. */
    private String myID;

    /**
     * Sets up test environment.
     */
    @Before
    public final void setUp() {
        myID = HTTPS + UUID.randomUUID().toString() + ".mp3";
    }

    /**
     * Tests sound content test fixture.
     *
     * @throws IOException If there is trouble reading the test fixture.
     */
    @Test
    public final void testFixture0003() throws IOException {
        final String expected = format(StringUtils.read(new File("src/test/resources/fixtures/0002-mvm-audio.json")));
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(expected, format(found));
    }

    /**
     * Tests {@link SoundContent#setBehaviors(Behavior...)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorArray() {
        final List<Behavior> behaviors = new SoundContent(myID).setBehaviors(ResourceBehavior.HIDDEN).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link SoundContent#setBehaviors(Behavior...)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorArrayInvalid() {
        new SoundContent(myID).setBehaviors(CanvasBehavior.NON_PAGED);
    }

    /**
     * Tests {@link SoundContent#setBehaviors(List)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorList() {
        final SoundContent content = new SoundContent(myID);
        final List<Behavior> behaviors = content.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link SoundContent#setBehaviors(List)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorListInvalid() {
        new SoundContent(myID).setBehaviors(List.of(CanvasBehavior.NON_PAGED));
    }

    /**
     * Tests {@link SoundContent#setBehaviors(List)} with an explicit {@code BehaviorList}.
     */
    @Test
    public final void testSetBehaviorsRealBehaviorList() {
        final List<Behavior> behaviors = new SoundContent(myID)
                .setBehaviors(new BehaviorList(ResourceBehavior.class, ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Test method for {@link SoundContent#getDuration()}.
     */
    @Test
    public final void testSetGetDuration() {
        assertEquals(1.5f, new SoundContent(myID).setDuration(1.5f).getDuration(), 0);
    }

    /**
     * Tests {@link SoundContent#equals(Object) SoundContent}.
     */
    @Test
    public final void testSoundContentEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final SoundContent test1 = new SoundContent(HTTPS + id);
        final SoundContent test2 = new SoundContent(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link SoundContent#equals(Object) SoundContent}.
     */
    @Test
    public final void testSoundContentEqualsHashCodeNot() {
        final SoundContent test1 = new SoundContent(HTTPS + UUID.randomUUID().toString());
        final SoundContent test2 = new SoundContent(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link SoundContent#equals(Object) SoundContent}.
     */
    @Test
    public final void testSoundContentEqualsNull() {
        assertNotEquals(new SoundContent(HTTPS + UUID.randomUUID().toString()), null);
    }

    /**
     * Tests {@link SoundContent#equals(Object) SoundContent}.
     */
    @Test
    public final void testSoundContentEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final SoundContent test1 = new SoundContent(HTTPS + id);
        final SoundContent test2 = new SoundContent(HTTPS + id);

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link SoundContent#equals(Object) SoundContent}.
     */
    @Test
    public final void testSoundContentEqualsSameNot() {
        final SoundContent test1 = new SoundContent(HTTPS + UUID.randomUUID().toString());
        final SoundContent test2 = new SoundContent(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link SoundContent#equals(Object) SoundContent}.
     */
    @Test
    public final void testSoundContentEqualsSameObject() {
        final SoundContent test = new SoundContent(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link SoundContent#equals(Object) SoundContent}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testSoundContentEqualsString() {
        assertNotEquals(new SoundContent(HTTPS + UUID.randomUUID().toString()), EMPTY);
    }
}
