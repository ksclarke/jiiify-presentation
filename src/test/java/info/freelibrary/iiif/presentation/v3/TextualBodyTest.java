
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.ids.SkolemIriFactory;

/**
 * Tests of TextualBody.
 */
public class TextualBodyTest {

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
        final SkolemIriFactory idFactory = SkolemIriFactory.getFactory().createSerializableIDs(true);
        assertNotEquals(null, new TextualBody(idFactory).getID());
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
        final TextualBody textualBody = new TextualBody(SkolemIriFactory.getFactory()).serializeID(true);
        assertNotEquals(null, textualBody.getID());
    }

    /**
     * Tests that IDs are not serializable unless they've been set that way.
     */
    @Test
    public final void testUnserializeID() {
        final TextualBody textualBody = new TextualBody(SkolemIriFactory.getFactory()).serializeID(false);
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
     * Tests setting and getting the TextualBody's MediaType in string form.
     */
    @Test
    public final void testGetFormat() {
        assertEquals(MediaType.TEXT_HTML,
                new TextualBody().setFormat(MediaType.TEXT_HTML.toString()).getFormat().get());
    }

    /**
     * Tests getting the type of the TextualBody.
     */
    @Test
    public final void testGetType() {
        assertEquals(ResourceTypes.TEXTUAL_BODY, new TextualBody().getType());
    }

}
