
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.services.ServiceImage;

public class ThumbnailTest {

    public final static URI THUMBNAIL_ID_1 = URI.create(
            "http://example.org/images/book1-page1/full/80,100/0/default.jpg");

    public final static URI PAGE_ID_1 = URI.create("http://example.org/images/book1-page1");

    public final static URI THUMBNAIL_ID_2 = URI.create(
            "http://example.org/images/book1-page2/full/80,100/0/default.jpg");

    public final static URI PAGE_ID_2 = URI.create("http://example.org/images/book1-page2");

    @Test
    public void test() {
        final Thumbnail thumbnail = new Thumbnail(THUMBNAIL_ID_1, new ImageInfoService(PAGE_ID_1));

        assertEquals(1, thumbnail.getImages().size());

        final ServiceImage image = new ServiceImage("asdf", new ImageInfoService(URI.create("aaaa")));

        thumbnail.addImage(image);

        for (final ServiceImage i : thumbnail.getImages()) {
            System.out.println(i.getID());
        }
    }

}
