
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertOptEquals;
import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;

/**
 * Image content test.
 */
public class ImageContentTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** A sample image format. */
    private static final String IMAGE_PNG = "image/png";

    /** A sample image ID. */
    private static final String IMAGE_URI = "https://example.org/image/001.jpg";

    /** A test image service. */
    private static final ImageService3 SERVICE = new ImageService3("https://example.org/service");

    /** A test ID. */
    private String myID;

    /**
     * Sets up testing environment.
     */
    @Before
    public final void setup() {
        myID = HTTPS + UUID.randomUUID().toString();
    }

    /**
     * Tests {@link ImageContent#equals(Object) ImageContent}.
     */
    @Test
    public final void testImageContentEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final ImageContent test1 = new ImageContent(HTTPS + id);
        final ImageContent test2 = new ImageContent(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link ImageContent#equals(Object) ImageContent}.
     */
    @Test
    public final void testImageContentEqualsHashCodeNot() {
        final ImageContent test1 = new ImageContent(HTTPS + UUID.randomUUID().toString());
        final ImageContent test2 = new ImageContent(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link ImageContent#equals(Object) ImageContent}.
     */
    @Test
    public final void testImageContentEqualsNull() {
        final ImageContent test = new ImageContent(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link ImageContent#equals(Object) ImageContent}.
     */
    @Test
    public final void testImageContentEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final ImageContent test1 = new ImageContent(HTTPS + id);
        final ImageContent test2 = new ImageContent(HTTPS + id);

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link ImageContent#equals(Object) ImageContent}.
     */
    @Test
    public final void testImageContentEqualsSameNot() {
        final ImageContent test1 = new ImageContent(HTTPS + UUID.randomUUID().toString());
        final ImageContent test2 = new ImageContent(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link ImageContent#equals(Object) ImageContent}.
     */
    @Test
    public final void testImageContentEqualsSameObject() {
        final ImageContent test = new ImageContent(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link ImageContent#equals(Object) ImageContent}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testImageContentEqualsString() {
        final ImageContent test = new ImageContent(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, EMPTY);
    }

    /**
     * Tests image content constructor.
     */
    @Test
    public void testImageContentString() {
        assertEquals(IMAGE_URI, new ImageContent(IMAGE_URI).getID());
    }

    /**
     * Tests {@link ImageContent#setBehaviors(Behavior...)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorArray() {
        final List<Behavior> behaviors = new ImageContent(myID).setBehaviors(ResourceBehavior.HIDDEN).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link ImageContent#setBehaviors(Behavior...)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorArrayInvalid() {
        new ImageContent(myID).setBehaviors(CanvasBehavior.NON_PAGED);
    }

    /**
     * Tests {@link ImageContent#setBehaviors(List)}.
     */
    @Test
    public final void testSetBehaviorsBehaviorList() {
        final ImageContent content = new ImageContent(myID);
        final List<Behavior> behaviors = content.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests {@link ImageContent#setBehaviors(List)} with a bad behavior.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetBehaviorsBehaviorListInvalid() {
        new ImageContent(myID).setBehaviors(List.of(CanvasBehavior.NON_PAGED));
    }

    /**
     * Tests {@link ImageContent#setBehaviors(List)} with an explicit {@code BehaviorList}.
     */
    @Test
    public final void testSetBehaviorsRealBehaviorList() {
        final List<Behavior> behaviors = new ImageContent(myID)
                .setBehaviors(new BehaviorList(ResourceBehavior.class, ResourceBehavior.HIDDEN)).getBehaviors();

        assertEquals(1, behaviors.size());
        assertEquals(ResourceBehavior.HIDDEN, behaviors.get(0));
        assertTrue(behaviors instanceof BehaviorList);
    }

    /**
     * Tests setting media type.
     */
    @Test
    public void testSetFormat() {
        // This will have image/jpeg set as format by default
        assertEquals(MediaType.IMAGE_PNG,
                new ImageContent(IMAGE_URI).setFormat(MediaType.fromString(IMAGE_PNG).get()).getFormat().get());
    }

    /**
     * Tests setting and getting the image service.
     */
    @Test
    public void testSetGetService() {
        assertEquals(SERVICE, new ImageContent(IMAGE_URI).setServices(SERVICE).getServices().get(0));
    }

    /**
     * Tests setting height.
     */
    @Test
    public void testSetHeightInt() {
        assertEquals(100, new ImageContent(IMAGE_URI).setWidthHeight(200, 100).getHeight());
    }

    /**
     * Tests setting label.
     */
    @Test
    public void testSetLabel() {
        final Label label = new Label("MY LABEL");
        assertOptEquals(label, new ImageContent(IMAGE_URI).setLabel(label).getLabel());
    }

    /**
     * Tests setting width.
     */
    @Test
    public void testSetWidthInt() {
        assertEquals(100, new ImageContent(IMAGE_URI).setWidthHeight(100, 200).getWidth());
    }
}
