
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Optional;

import org.junit.Test;

import com.google.common.net.MediaType;

public class SeeAlsoTest {

    private final static URI URI_ONE = URI.create("http://www.example.org/one");

    private final static URI URI_TWO = URI.create("http://www.example.org/two");

    private final static MediaType MIME_TYPE = MediaType.parse("image/jpeg");

    @Test
    public void testSeeAlsoStringArray() {
        assertEquals(2, new SeeAlso(URI_ONE.toString(), URI_TWO.toString()).getValues().size());
    }

    @Test
    public void testSeeAlsoURIArray() {
        assertEquals(2, new SeeAlso(URI_ONE, URI_TWO).getValues().size());
    }

    @Test
    public void testSeeAlsoStringMediaType() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE).getID());
    }

    @Test
    public void testSeeAlsoStringString() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE.toString()).getID());
    }

    @Test
    public void testSeeAlsoURIMediaType() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE, MIME_TYPE).getID());
    }

    @Test
    public void testSeeAlsoStringStringString() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE.toString(), URI_TWO.toString()).getID());
    }

    @Test
    public void testSeeAlsoStringMediaTypeString() {
        assertEquals(URI_ONE, new SeeAlso(URI_ONE.toString(), MIME_TYPE, URI_TWO.toString()).getID());
    }

    @Test
    public void testGetValues() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(1, seeAlso.getValues().size());
    }

    @Test
    public void testGetProfileEmpty() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE);
        assertEquals(Optional.empty(), seeAlso.getProfile());
    }

    @Test
    public void testGetProfile() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(URI_TWO, seeAlso.getProfile().get());
    }

    @Test
    public void testGetFormatEmpty() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE);
        assertEquals(Optional.empty(), seeAlso.getFormat());
    }

    @Test
    public void testGetFormat() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(MIME_TYPE.toString(), seeAlso.getFormat().get());
    }

    @Test
    public void testGetFormatMediaTypeEmpty() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE);
        assertEquals(Optional.empty(), seeAlso.getFormatMediaType());
    }

    @Test
    public void testGetFormatMediaType() {
        final SeeAlso seeAlso = new SeeAlso(URI_ONE, MIME_TYPE, URI_TWO);
        assertEquals(MIME_TYPE, seeAlso.getFormatMediaType().get());
    }

    @Test
    public void testCount() {
        assertEquals(1, new SeeAlso(URI_ONE).count());
    }

}
