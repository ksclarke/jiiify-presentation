
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * An annotation page for testing.
 */
public class AnnotationPageTest<T extends Annotation<T>> extends AbstractTest {

    /**
     * An annotation page serialization.
     */
    private static final File ANNOTATION_PAGE = new File(TestUtils.TEST_DIR, "annotation-page-with-context.json");

    /**
     * A unique ID for use in the tests.
     */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
    }

    /**
     * Tests that the deserialization and serialization of a stand-alone annotation page doesn't include a context URI
     * if there isn't one in the incoming JSON.
     *
     * @throws IOException If the test fixtures cannot be read
     */
    @Test
    public void testAnnotationPageToString() throws IOException {
        final ObjectReader reader = JSON.getReader();
        final ObjectNode node = (ObjectNode) reader.readTree(new FileReader(ANNOTATION_PAGE));
        final AnnotationPage<T> page;

        node.remove(JsonKeys.CONTEXT); // Confirm this isn't added if it isn't present
        page = AnnotationPage.from(node.toString());

        assertEquals(node, reader.readTree(page.toString()));
    }

    /**
     * Tests the deserialization and serialization of a stand-alone annotation page.
     *
     * @throws IOException If the test fixtures cannot be read
     */
    @Test
    public void testExternalAnnotationPageToString() throws IOException {
        final String json = StringUtils.read(ANNOTATION_PAGE);
        final AnnotationPage<T> page = AnnotationPage.from(json);
        final ObjectReader reader = JSON.getReader();

        assertEquals(reader.readTree(json), reader.readTree(page.toString()));
    }

    /**
     * Tests constructing an annotation page.
     */
    @Test
    public void testAnnotationPageStringId() {
        assertEquals(myID, new AnnotationPage<PaintingAnnotation>(myID).getID().toString());
    }

    /**
     * Tests constructing an annotation page.
     */
    @Test
    public void testAnnotationPageUriId() {
        assertEquals(URI.create(myID), new AnnotationPage<PaintingAnnotation>(URI.create(myID)).getID());
    }

    /**
     * Test setting annotation page behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        assertEquals(1, new AnnotationPage<PaintingAnnotation>(myID).setBehaviors(ResourceBehavior.HIDDEN)
                .getBehaviors().size());
    }

    /**
     * Test setting disallowed annotation page behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        new AnnotationPage<PaintingAnnotation>(myID).setBehaviors(ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Test adding annotation page behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, new AnnotationPage<PaintingAnnotation>(myID).addBehaviors(ResourceBehavior.HIDDEN)
                .getBehaviors().size());
    }

    /**
     * Test adding disallowed annotation page behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        new AnnotationPage<PaintingAnnotation>(myID).addBehaviors(ManifestBehavior.CONTINUOUS,
                CanvasBehavior.AUTO_ADVANCE);
    }

}
