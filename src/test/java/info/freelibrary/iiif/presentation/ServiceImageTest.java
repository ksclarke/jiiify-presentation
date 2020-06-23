/**
 *
 */

package info.freelibrary.iiif.presentation;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.util.StringUtils;

/**
 * Tests the service image by way of a logo.
 */
public class ServiceImageTest {

    private static final String TEST_FORMAT = "image/jpeg";

    private String myID;

    /**
     * Set up the tests.
     */
    @Before
    public final void setUp() {
        myID = UUID.randomUUID().toString() + ".jpg";
    }

    /**
     * Test method for {@link ServiceImage#ServiceImage(String)}.
     */
    @Test
    public final void testServiceImageString() {
        assertEquals(URI.create(myID), new ServiceImage<Logo>(myID).getID());
    }

    /**
     * Test method for {@link ServiceImage#ServiceImage(URI)}.
     */
    @Test
    public final void testServiceImageURI() {
        final URI id = URI.create(myID);
        assertEquals(id, new ServiceImage<Logo>(id).getID());
    }

    /**
     * Test method for {@link ServiceImage#ServiceImage(String, ImageInfoService)}.
     */
    @Test
    public final void testServiceImageStringImageInfoService() {
        final String infoServiceID = UUID.randomUUID().toString();
        final ImageInfoService infoService = new ImageInfoService(infoServiceID);

        assertEquals(URI.create(myID), new ServiceImage<Logo>(myID, infoService).getID());
    }

    /**
     * Test method for {@link ServiceImage#ServiceImage(URI, ImageInfoService)}.
     */
    @Test
    public final void testServiceImageURIImageInfoService() {
        final String infoServiceID = UUID.randomUUID().toString();
        final ImageInfoService infoService = new ImageInfoService(infoServiceID);
        final URI id = URI.create(myID);

        assertEquals(id, new ServiceImage<Logo>(id, infoService).getID());
    }

    /**
     * Test method for {@link ServiceImage#ServiceImage(String, int, int)}.
     */
    @Test
    public final void testServiceImageStringIntInt() {
        assertEquals(URI.create(myID), new ServiceImage<Logo>(myID, 100, 100).getID());
    }

    /**
     * Test method for {@link ServiceImage#ServiceImage(URI, int, int)}.
     */
    @Test
    public final void testServiceImageURIIntInt() {
        final URI id = URI.create(myID);
        assertEquals(id, new ServiceImage<Logo>(id, 100, 100).getID());
    }

    /**
     * Test method for {@link ServiceImage#getType()}.
     */
    @Test
    public final void testGetType() {
        assertEquals(ResourceTypes.IMAGE, new ServiceImage<Logo>().getType());
    }

    /**
     * Test method for {@link ServiceImage#setFormat(String)}.
     */
    @Test
    public final void testSetGetFormat() {
        assertEquals(TEST_FORMAT, new ServiceImage<Logo>().setFormat(TEST_FORMAT).getFormat().get());
    }

    /**
     * Test method for {@link ServiceImage#setFormatMediaType(MediaType)}.
     */
    @Test
    public final void testSetFormatMediaType() {
        assertEquals(TEST_FORMAT, new ServiceImage<Logo>().setFormatMediaType(MediaType.JPEG).getFormat().get());
    }

    /**
     * Test method for {@link ServiceImage#getFormatMediaType()}.
     */
    @Test
    public final void testGetFormatMediaType() {
        assertEquals(MediaType.JPEG, new ServiceImage<Logo>().setFormatMediaType(MediaType.JPEG).getFormatMediaType()
                .get());
    }

    /**
     * Test method for {@link ServiceImage#setWidthHeight(int, int)}.
     */
    @Test
    public final void testSetGetWidthHeight() {
        assertEquals(100, new ServiceImage<Logo>().setWidthHeight(100, 200).getWidth());
    }

    /**
     * Test method for {@link ServiceImage#getHeight()}.
     */
    @Test
    public final void testGetHeight() {
        assertEquals(200, new ServiceImage<Logo>().setWidthHeight(100, 200).getHeight());
    }

    /**
     * Test method for {@link ServiceImage#setID(java.lang.String)}.
     */
    @Test
    public final void testSetIDString() {
        assertEquals(URI.create(myID), new ServiceImage<Logo>().setID(myID).getID());
    }

    /**
     * Test method for {@link ServiceImage#setID(java.net.URI)}.
     */
    @Test
    public final void testSetIDURI() {
        final URI id = URI.create(myID);
        assertEquals(id, new ServiceImage<Logo>().setID(id).getID());
    }

    /**
     * Test method for {@link ServiceImage#getService()}.
     */
    @Test
    public final void testGetService() {
        final String infoServiceID = UUID.randomUUID().toString();
        final ImageInfoService infoService = new ImageInfoService(infoServiceID);

        assertEquals(URI.create(infoServiceID), new ServiceImage<Logo>(myID, infoService).getService().get().getID());
    }

    /**
     * Test method for {@link ServiceImage#setService(ImageInfoService)}.
     */
    @Test
    public final void testSetService() {
        final String infoServiceID = UUID.randomUUID().toString();
        final ImageInfoService infoService = new ImageInfoService(infoServiceID);

        assertEquals(URI.create(infoServiceID), new ServiceImage<Logo>(myID).setService(infoService).getService()
                .get().getID());
    }

    /**
     * Test method for {@link ServiceImage#toString()}.
     */
    @Test
    public final void testToString() {
        final ServiceImage<Logo> serviceImage = new ServiceImage<>(myID);
        final String expectedString = StringUtils.format("ServiceImage:{}", myID);

        assertEquals(expectedString, serviceImage.toString());
    }

}
