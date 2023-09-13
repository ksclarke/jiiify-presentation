
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.utils.TestUtils;

/**
 * A thumbnail test.
 */
public class ThumbnailTest {

    /** A test URI. */
    public static final URI THUMBNAIL_ID_1 =
            URI.create("http://example.org/images/book1-page1/full/80,100/0/default.jpg");

    /** A test URI. */
    public static final URI PAGE_ID_1 = URI.create("http://example.org/images/book1-page1");

    /** A test URI. */
    public static final URI THUMBNAIL_ID_2 =
            URI.create("http://example.org/images/book1-page2/full/80,100/0/default.jpg");

    /** A test URI. */
    public static final URI PAGE_ID_2 = URI.create("http://example.org/images/book1-page2");

    /** A test file name. */
    private static final String TN_FULL_SINGLE = "thumbnail-full-single.json";

    /** The test resources directory. */
    private static final File TEST_DIR = new File("src/test/resources/json");

    /**
     * Tests constructing a new thumbnail.
     *
     * @throws JsonProcessingException If there is trouble processing the test JSON
     * @throws IOException If there is trouble reading the test file
     */
    @Test
    public void testFullSingleURI() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, TN_FULL_SINGLE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, true));
    }

    /**
     * Tests constructing a new thumbnail.
     *
     * @throws JsonProcessingException If there is trouble processing the test JSON
     * @throws IOException If there is trouble reading the test file
     */
    @Test
    public void testFullSingleString() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1.toString(), 1000, 1000);
        final File expected = new File(TEST_DIR, TN_FULL_SINGLE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, true));
    }

    /**
     * Tests constructing a new thumbnail.
     *
     * @throws JsonProcessingException If there is trouble processing the test JSON
     * @throws IOException If there is trouble reading the test file
     */
    @Test
    public void testFullSingleService() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, new ImageInfoService(PAGE_ID_1));
        final File expected = new File(TEST_DIR, "thumbnail-full-single-service.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, true));
    }

    /**
     * Tests constructing a new thumbnail.
     *
     * @throws JsonProcessingException If there is trouble processing the test JSON
     * @throws IOException If there is trouble reading the test file
     */
    @Test
    public void testSimpleTwo() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, THUMBNAIL_ID_2);
        final File expected = new File(TEST_DIR, "thumbnail-simple-two.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, false));
    }

    /**
     * Tests constructing a new thumbnail.
     *
     * @throws JsonProcessingException If there is trouble processing the test JSON
     * @throws IOException If there is trouble reading the test file
     */
    @Test
    public void testSimpleOne() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1);
        final File expected = new File(TEST_DIR, "thumbnail-simple-one.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, false));
    }

}
