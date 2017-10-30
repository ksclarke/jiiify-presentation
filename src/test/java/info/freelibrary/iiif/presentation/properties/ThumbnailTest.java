
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.util.TestUtils;
import info.freelibrary.util.StringUtils;

public class ThumbnailTest {

    public final static URI THUMBNAIL_ID_1 = URI.create(
            "http://example.org/images/book1-page1/full/80,100/0/default.jpg");

    public final static URI PAGE_ID_1 = URI.create("http://example.org/images/book1-page1");

    public final static URI THUMBNAIL_ID_2 = URI.create(
            "http://example.org/images/book1-page2/full/80,100/0/default.jpg");

    public final static URI PAGE_ID_2 = URI.create("http://example.org/images/book1-page2");

    private final static File TEST_DIR = new File("src/test/resources/json");

    @Test
    public void testFullSingleURI() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, "thumbnail-full-single.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, true));
    }

    @Test
    public void testFullSingleString() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1.toString(), 1000, 1000);
        final File expected = new File(TEST_DIR, "thumbnail-full-single.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, true));
    }

    @Test
    public void testFullSingleService() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, new ImageInfoService(PAGE_ID_1));
        final File expected = new File(TEST_DIR, "thumbnail-full-single-service.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, true));
    }

    @Test
    public void testSimpleTwo() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, THUMBNAIL_ID_2);
        final File expected = new File(TEST_DIR, "thumbnail-simple-two.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, false));
    }

    @Test
    public void testSimpleOne() throws JsonProcessingException, IOException {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1);
        final File expected = new File(TEST_DIR, "thumbnail-simple-one.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(thumbnail, false));
    }

}
