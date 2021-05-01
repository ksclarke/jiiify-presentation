
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.After;
import org.junit.Test;

import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.v3.id.SkolemIriFactory;

/**
 * Tests of TextualBody.
 */
public class TextualBodyTest {

    /**
     * Cleans up after the tests.
     */
    @After
    public final void cleanUp() {
        SkolemIriFactory.reset();
    }

    /**
     * Tests the default TextualBody constructor.
     */
    @Test
    public final void testTextualBody() {
        assertEquals(null, new TextualBody().getID());
    }

    /**
     * Tests the TextualBody constructor after IDs have been set as serializable in <code>SkolemiIriFactory</code>.
     */
    @Test
    public final void testTextualBodyNotSerializingID() {
        SkolemIriFactory.getFactory().hasSerializableIDs(true);
        assertNotEquals(null, new TextualBody().getID());
    }

    /**
     * Tests setting and getting IDs in string form.
     */
    @Test
    public final void testGetSetID() {
        final String id = UUID.randomUUID().toString();
        assertEquals(id, new TextualBody().setID(id).getID().toString());
    }

    /**
     * Tests setting and getting IDs in URI form.
     */
    @Test
    public final void testSetIDURI() {
        final URI id = URI.create(UUID.randomUUID().toString());
        assertEquals(id, new TextualBody().setID(id).getID());
    }

    /**
     * Tests an ID that has been serialized only on this TextualBody.
     */
    @Test
    public final void testSerializedID() {
        final TextualBody textualBody = new TextualBody().serializeID(true);
        assertNotEquals(null, textualBody.getID());
    }

    /**
     * Tests that IDs are not serializable unless they've been set that way.
     */
    @Test
    public final void testUnserializeID() {
        final TextualBody textualBody = new TextualBody().serializeID(false);
        assertEquals(null, textualBody.getID());
    }

    /**
     * Tests setting and getting the TextualBody's text value.
     */
    @Test
    public final void testSetGetValue() {
        final String value = "this is my value";
        assertEquals(value, new TextualBody().setValue(value).getValue());
    }

    /**
     * Tests setting and getting the TextualBody's language.
     */
    @Test
    public final void testSetGetLanguage() {
        final String language = "en";
        assertEquals(language, new TextualBody().setLanguage(language).getLanguage());
    }

    /**
     * Tests setting and getting the TextualBody's MediaType.
     */
    @Test
    public final void testSetGetFormatMediaType() {
        assertEquals(MediaType.HTML_UTF_8,
                new TextualBody().setFormat(MediaType.HTML_UTF_8).getFormatMediaType().get());
    }

    /**
     * Tests setting and getting the TextualBody's MediaType in string form.
     */
    @Test
    public final void testGetFormat() {
        assertEquals(MediaType.HTML_UTF_8.toString(),
                new TextualBody().setFormat(MediaType.HTML_UTF_8.toString()).getFormat().get());
    }

    /**
     * Tests getting the type of the TextualBody.
     */
    @Test
    public final void testGetType() {
        assertEquals(ResourceTypes.TEXTUAL_BODY, new TextualBody().getType());
    }

}
