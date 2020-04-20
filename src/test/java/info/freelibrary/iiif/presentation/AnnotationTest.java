
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.TimeMode;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;
import io.vertx.core.json.JsonObject;

/**
 * Tests an annotation.
 */
public class AnnotationTest {

    private static final URI ID = URI.create("http://example.org/id");

    private static final File ANNOTATION_TIMEMODE = new File(TestUtils.TEST_DIR, "annotation-timemode.json");

    /**
     * Tests constructing an annotation.
     */
    @Test
    public void testAnnotationURI() {
        assertEquals(ID.toString(), new Annotation(ID.toString()).getID().toString());
    }

    /**
     * Tests constructing an annotation.
     */
    @Test
    public void testAnnotationString() {
        assertEquals(ID, new Annotation(ID).getID());
    }

    /**
     * Test setting annotation behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Annotation annotation = new Annotation(ID);

        assertEquals(1, annotation.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed annotation behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Annotation annotation = new Annotation(ID);

        annotation.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding annotation behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Annotation annotation = new Annotation(ID);

        assertEquals(1, annotation.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed annotation behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Annotation annotation = new Annotation(ID);

        annotation.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

    /**
     * Tests setting and getting a time mode, and serializing an annotation with a time mode.
     *
     * @throws IOException If there is trouble reading the annotation file or serializing the constructed annotation
     */
    @Test
    public final void testTimeMode() throws IOException {
        final Annotation annotation = new Annotation(ID).setTimeMode(TimeMode.TRIM);
        final JsonObject expected = new JsonObject(StringUtils.read(ANNOTATION_TIMEMODE));
        final JsonObject found = new JsonObject(TestUtils.toJson(annotation));

        assertEquals(TimeMode.TRIM, annotation.getTimeMode());
        assertEquals(expected, found);
    }
}
