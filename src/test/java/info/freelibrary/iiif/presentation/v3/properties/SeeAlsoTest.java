
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;

/**
 * A seeAlso test.
 */
public class SeeAlsoTest extends AbstractTest {

    /** A test format. */
    private static final String JPEG_FORMAT = "image/jpeg";

    /** A test mime-type. */
    private static final MediaType MIME_TYPE = MediaType.fromString(JPEG_FORMAT).get();

    /** A test ID. */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = myLoremIpsum.getUrl();
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoStringConstructor() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);

        assertEquals(myID, seeAlso.getID());
        assertEquals(ResourceTypes.DATASET, seeAlso.getType());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoURIConstructor() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.TEXT);

        assertEquals(myID, seeAlso.getID());
        assertEquals(ResourceTypes.TEXT, seeAlso.getType());
    }

    /**
     * Tests getting a seeAlso profile.
     */
    @Test
    public void testSetGetProfile() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.TEXT);
        final String url = myLoremIpsum.getUrl();

        assertEquals(url, seeAlso.setProfile(url).getProfile().get());
    }

    /**
     * Tests getting a seeAlso profile with URI.
     */
    @Test
    public void testSetGetProfileURI() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.TEXT);
        final String uri = myLoremIpsum.getUrl();

        assertEquals(uri, seeAlso.setProfile(uri).getProfile().get());
    }

    /**
     * Tests getting a seeAlso profile.
     */
    @Test
    public void testGetProfileEmpty() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);
        assertEquals(Optional.empty(), seeAlso.getProfile());
    }

    /**
     * Tests setting and getting a format.
     */
    @Test
    public void testSetGetFormat() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);
        assertEquals(MediaType.IMAGE_JPEG, seeAlso.setFormat(MIME_TYPE).getFormat().get());
    }

    /**
     * Tests setting and getting a format as a MediaType.
     */
    @Test
    public void testSetGetFormatMediaType() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);
        final MediaType mediaType = MediaType.fromString(JPEG_FORMAT).get();

        assertEquals(MediaType.IMAGE_JPEG, seeAlso.setFormat(mediaType).getFormat().get());
    }

    /**
     * Tests getting an empty format.
     */
    @Test
    public void testGetFormatEmpty() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);
        assertEquals(Optional.empty(), seeAlso.getFormat());
    }

    /**
     * Tests setting and getting a label.
     */
    @Test
    public void testSetGetLabel() {
        final String text = myLoremIpsum.getWords(4);
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);

        assertEquals(Optional.of(new Label(text)), seeAlso.setLabel(new Label(text)).getLabel());
    }

    /**
     * Tests getting an empty label.
     */
    @Test
    public void testGetLabelEmpty() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);
        assertEquals(Optional.empty(), seeAlso.getFormat());
    }

}
