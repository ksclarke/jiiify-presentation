
package info.freelibrary.iiif.presentation.v3.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.content.OtherContent;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * Tests of {@code ContentStateAnnotation}.
 */
public class ContentStateAnnotationTest {

    /** A test ID. */
    private static final String ID = "https://example.org/anno/1";

    /** A test canvas. */
    private Canvas myCanvas;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myCanvas = new Canvas("https://example.org/canvas/1");
    }

    /**
     * Tests creating a ContentStateAnnotation with a canvas.
     */
    @Test
    public void testConstructorWithCanvas() {
        final ContentStateAnnotation annotation = new ContentStateAnnotation(ID, myCanvas);
        assertEquals(ID, annotation.getID());
        assertFalse(annotation.getTargets().isEmpty());
    }

    /**
     * Tests creating a ContentStateAnnotation with a canvas and media fragment selector.
     */
    @Test
    public void testConstructorWithCanvasAndSelector() {
        final MediaFragmentSelector selector = new MediaFragmentSelector("xywh=10,20,30,40");
        final ContentStateAnnotation annotation = new ContentStateAnnotation(ID, myCanvas, selector);
        assertEquals(ID, annotation.getID());
        assertFalse(annotation.getTargets().isEmpty());
    }

    /**
     * Tests setting and getting body.
     */
    @Test
    public void testSetAndGetBody() {
        final ObjectNode json = JsonNodeFactory.instance.objectNode();
        final ContentResource resource = new OtherContent(json);
        final ContentStateAnnotation annotation = new ContentStateAnnotation(ID, myCanvas);

        annotation.setBody(resource);
        assertEquals(1, annotation.getBody().size());
    }

    /**
     * Tests setting and getting contexts.
     */
    @Test
    public void testSetAndGetContexts() {
        final URI context = URI.create("https://example.org/context");
        final ContentStateAnnotation annotation = new ContentStateAnnotation(ID, myCanvas);
        final List<URI> contexts = Arrays.asList(context);

        annotation.setContexts(contexts);
        assertTrue(annotation.getContexts().contains(context));
    }

    /**
     * Tests setting and getting label.
     */
    @Test
    public void testSetAndGetLabel() {
        final ContentStateAnnotation annotation = new ContentStateAnnotation(ID, myCanvas);
        final Label label = new Label("en", "Test Label");

        annotation.setLabel(label);
        assertTrue(annotation.getLabel().isPresent());
        assertEquals(label, annotation.getLabel().get());
    }

    /**
     * Tests setting and getting time mode.
     */
    @Test
    public void testSetAndGetTimeMode() {
        final ContentStateAnnotation annotation = new ContentStateAnnotation(ID, myCanvas);
        final TimeMode mode = TimeMode.LOOP;

        annotation.setTimeMode(mode);
        assertTrue(annotation.getTimeMode().isPresent());
        assertEquals(mode, annotation.getTimeMode().get());
    }

    /**
     * Tests setting a non-CONTENT_STATE motivation throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidMotivationThrowsException() {
        final ContentStateAnnotation annotation = new ContentStateAnnotation(ID, myCanvas);
        annotation.setMotivation(Motivation.fromLabel(Purpose.TAGGING));
    }
}
