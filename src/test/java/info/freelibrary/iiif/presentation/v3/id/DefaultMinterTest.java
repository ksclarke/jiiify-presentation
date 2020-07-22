
package info.freelibrary.iiif.presentation.v3.id;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.Stopwatch;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Tests the default minter implementation.
 */
public class DefaultMinterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMinterTest.class, MessageCodes.BUNDLE);

    private static final String NOID_PATTERN = "[0-9a-z]{4}";

    private static final int MAX_NOID_COUNT = 1500625;

    private Minter myMinter;

    private URI myManifestID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        final String id = UUID.randomUUID().toString();
        final int index = id.indexOf('-');

        myManifestID = URI.create("https://example.org/iiif/" + id.substring(0, index));
        myMinter = MinterFactory.getMinter(myManifestID);
    }

    /**
     * Tests the constructor that takes a manifest.
     */
    @Test
    public final void testManifestConstructor() {
        final String id = myManifestID + "/canvas-kfb9";
        final Manifest manifest = new Manifest(myManifestID, new Label("Label"));
        final Minter minter = MinterFactory.getMinter(manifest.addCanvas(new Canvas(id)));

        int counter = 0;

        while (minter.hasNext()) {
            minter.getCanvasID();
            counter++;
        }

        // Should have one less than the max because our manifest has one ID already in it
        assertEquals(MAX_NOID_COUNT - 1, counter);
        assertEquals(0, minter.remaining());
    }

    /**
     * Tests {@link DefaultMinter#getManifestID() getManifestID}.
     */
    @Test
    public final void testGetManifestID() {
        assertEquals(myManifestID, myMinter.getManifestID());
    }

    /**
     * Tests {@link DefaultMinter#getCanvasID() getCanvasID}.
     */
    @Test
    public final void testGetCanvasID() {
        final String pattern = myManifestID.toString() + "/canvas-" + NOID_PATTERN;
        final String id = myMinter.getCanvasID().toString();

        assertTrue(Pattern.compile(pattern).matcher(id).matches());
    }

    /**
     * Tests {@link DefaultMinter#getAnnotationID() getAnnotationID}.
     */
    @Test
    public final void testGetAnnotationID() {
        final String pattern = myManifestID.toString() + "/annotations/anno-" + NOID_PATTERN;
        final String id = myMinter.getAnnotationID().toString();

        assertTrue(Pattern.compile(pattern).matcher(id).matches());
    }

    /**
     * Tests {@link DefaultMinter#getAnnotationPageID(CanvasResource) getAnnotationPageID}.
     */
    @Test
    public final void testGetAnnotationPageID() {
        final Canvas canvas = new Canvas(myManifestID + "/canvas-x1x0");
        final String pattern = myManifestID.toString() + "/canvas-x1x0/anno-page-" + NOID_PATTERN;
        final String id = myMinter.getAnnotationPageID(canvas).toString();

        assertTrue(Pattern.compile(pattern).matcher(id).matches());
    }

    /**
     * Tests {@link DefaultMinter#getRangeID() getRangeID}.
     */
    @Test
    public final void testGetRangeID() {
        final String pattern = myManifestID.toString() + "/range-" + NOID_PATTERN;
        final String id = myMinter.getRangeID().toString();

        assertTrue(Pattern.compile(pattern).matcher(id).matches());
    }

    /**
     * Tests that the default minter's iterator can iterate.
     */
    @Test
    public final void testIteratorHasNext() {
        final Stopwatch stopwatch = new Stopwatch().start();
        final Set<URI> ids = new HashSet<>();

        while (myMinter.hasNext()) {
            final URI id = myMinter.getCanvasID();

            if (!ids.add(id)) {
                fail(LOGGER.getMessage(MessageCodes.JPA_108, id, ids.size()));
            }
        }

        assertEquals(MAX_NOID_COUNT, ids.size());
        LOGGER.debug(MessageCodes.JPA_104, stopwatch.stop().getSeconds());
    }
}
