
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Optional;

import org.junit.Test;

import com.google.common.net.MediaType;

/**
 * A seeAlso test.
 */
public class SeeAlsoTest {

    /** A test URI. */
    private static final URI URI_ONE = URI.create("http://www.example.org/one");

    /** A test URI. */
    private static final URI URI_TWO = URI.create("http://www.example.org/two");

    /** A test media type. */
    private static final MediaType MIME_TYPE = MediaType.parse("image/jpeg");

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoStringArray() {
        assertEquals(2, new SeeAlso(URI_ONE.toString(), URI_TWO.toString()).getValues().size());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoURIArray() {
        assertEquals(2, new SeeAlso(URI_ONE, URI_TWO).getValues().size());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoStringMediaType() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE).getID());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoStringString() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE.toString()).getID());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoURIMediaType() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE, MIME_TYPE).getID());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoStringStringString() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE.toString(), URI_TWO.toString()).getID());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoStringMediaTypeString() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE, URI_TWO.toString()).getID());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testGetValues() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(1, seeAlso.getValues().size());
    }

    /**
     * Tests getting a seeAlso profile.
     */
    @Test
    public void testGetProfileEmpty() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE);
        assertEquals(Optional.empty(), seeAlso.getProfile());
    }

    /**
     * Tests getting a seeAlso profile.
     */
    @Test
    public void testGetProfile() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(URI_TWO, seeAlso.getProfile().get());
    }

    /**
     * Tests getting an empty format.
     */
    @Test
    public void testGetFormatEmpty() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE);
        assertEquals(Optional.empty(), seeAlso.getFormat());
    }

    /**
     * Tests getting a format.
     */
    @Test
    public void testGetFormat() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(MIME_TYPE.toString(), seeAlso.getFormat().get());
    }

    /**
     * Tests getting a format media-type.
     */
    @Test
    public void testGetFormatMediaTypeEmpty() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE);
        assertEquals(Optional.empty(), seeAlso.getFormatMediaType());
    }

    /**
     * Tests getting a format media-type.
     */
    @Test
    public void testGetFormatMediaType() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(MIME_TYPE, seeAlso.getFormatMediaType().get());
    }

    /**
     * Tests counting the number of seeAlso(s).
     */
    @Test
    public void testCount() {
        assertEquals(1, new SeeAlso(URI_ONE).count());
    }

}
