
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.TimeMode;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;
import io.vertx.core.json.JsonObject;

/**
 * Tests {@link SupplementingAnnotation}.
 */
public class SupplementingAnnotationTest extends AbstractTest {

    private static final File ANNOTATION = new File(TestUtils.TEST_DIR, "annotation-supplementing-full.json");

    private final Label myCanvasLabel = new Label(LOREM_IPSUM.getWords(4));

    private final URI myCanvasID = URI.create("cf6da69c-7d60-4dbe-965b-e40be626f2eb");

    private final Canvas myCanvas = new Canvas(myCanvasID, myCanvasLabel);

    private final URI myAnnoID = URI.create("1408c0a9-7402-4a44-8091-fa11d32172f9");

    private final URI myTextContentID = URI.create("e03f1662-2a33-48a4-82ba-3a726cee15c9" + ".html");

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvas() {
        assertEquals(myAnnoID, new SupplementingAnnotation(myAnnoID, myCanvas).getID());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvas() {
        assertEquals(myAnnoID, new SupplementingAnnotation(myAnnoID.toString(), myCanvas).getID());
    }

    /**
     * Tests setting behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(1, anno.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Tests setting disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        anno.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Tests adding behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(1, anno.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Tests adding disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        anno.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

    /**
     * Tests setting a time mode.
     */
    @Test
    public final void testSetTimeMode() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(TimeMode.LOOP, anno.setTimeMode(TimeMode.LOOP).getTimeMode());
    }

    /**
     * Tests serializing and deserializing an annotation.
     *
     * @throws IOException If there is trouble reading the annotation file or serializing the constructed annotation
     */
    @Test
    public final void testSerialization() throws IOException {
        final TextContent content = new TextContent(myTextContentID);
        final SupplementingAnnotation annotation = new SupplementingAnnotation(myAnnoID, myCanvas).setBody(content)
                .setTarget(myCanvasID);
        final JsonObject expected = new JsonObject(StringUtils.read(ANNOTATION));
        final JsonObject found = new JsonObject(TestUtils.toJson(annotation));

        assertEquals(expected, found);
    }
}
