
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * A thumbnail test.
 */
public class ThumbnailTest {

    public static final URI THUMBNAIL_ID_1 = URI.create(
            "http://example.org/images/book1-page1/full/80,100/0/default.jpg");

    public static final URI PAGE_ID_1 = URI.create("http://example.org/images/book1-page1");

    public static final URI THUMBNAIL_ID_2 = URI.create(
            "http://example.org/images/book1-page2/full/80,100/0/default.jpg");

    public static final URI PAGE_ID_2 = URI.create("http://example.org/images/book1-page2");

    private static final String TN_FULL_SINGLE = "thumbnail-full-single.json";

    /**
     * Tests constructing a new thumbnail.
     *
     * @throws JsonProcessingException If there is trouble processing the test JSON
     * @throws IOException If there is trouble reading the test file
     */
    @Test
    public void testFullSingleURI() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, 1000, 1000);
        final File testFile = new File(TestUtils.TEST_DIR, TN_FULL_SINGLE);
        final JsonObject expected = new JsonObject(StringUtils.read(testFile));
        final JsonObject found = new JsonObject(TestUtils.toJson(Constants.THUMBNAIL, thumbnail));

        assertEquals(expected, found);
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
        final File testFile = new File(TestUtils.TEST_DIR, TN_FULL_SINGLE);
        final JsonObject expected = new JsonObject(StringUtils.read(testFile));
        final JsonObject found = new JsonObject(TestUtils.toJson(Constants.THUMBNAIL, thumbnail));

        assertEquals(expected, found);
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
        final File testFile = new File(TestUtils.TEST_DIR, "thumbnail-full-single-service.json");
        final JsonObject expected = new JsonObject(StringUtils.read(testFile));
        final JsonObject found = new JsonObject(TestUtils.toJson(Constants.THUMBNAIL, thumbnail));

        assertEquals(expected, found);
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
        final File testFile = new File(TestUtils.TEST_DIR, "thumbnail-simple-two.json");
        final JsonObject expected = new JsonObject(StringUtils.read(testFile));
        final JsonObject found = new JsonObject(TestUtils.toJson(Constants.THUMBNAIL, thumbnail));

        assertEquals(expected, found);
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
        final File testFile = new File(TestUtils.TEST_DIR, "thumbnail-simple-one.json");
        final JsonObject expected = new JsonObject(StringUtils.read(testFile));
        final JsonObject found = new JsonObject(TestUtils.toJson(Constants.THUMBNAIL, thumbnail));

        assertEquals(expected, found);
    }

}
