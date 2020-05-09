
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.properties.selectors.Selector;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * Tests a SpecificResource.
 */
public class SpecificResourceTest extends AbstractTest {

    private static final Selector SELECTOR = new AudioContentSelector();

    private static final String JSON;

    static {
        try {
            JSON = StringUtils.read(new File("src/test/resources/json/specificresource.json"));
        } catch (final IOException details) {
            throw new I18nRuntimeException(details);
        }
    }

    private String myID;

    private String myOtherID;

    @Before
    public void setUp() {
        myID = LOREM_IPSUM.getUrl();
        myOtherID = LOREM_IPSUM.getUrl();
    }

    /**
     * Tests String constructor.
     */
    @Test
    public final void testSpecificResourceIdStringSourceStringSelector() {
        new SpecificResource(myID, myOtherID, SELECTOR);
    }

    /**
     * Tests URI constructor.
     */
    @Test
    public final void testSpecificResourceIdUriSourceUriSelector() {
        new SpecificResource(URI.create(myID), URI.create(myOtherID), SELECTOR);
    }

    /**
     * Tests {@link SpecificResource#setID(String) setID} method.
     */
    @Test
    public final void testSetIDString() {
        assertEquals(URI.create(myOtherID), new SpecificResource(myID, myID, SELECTOR).setID(myOtherID).getID());
    }

    /**
     * Tests {@link SpecificResource#setID(URI) setID} method.
     */
    @Test
    public final void testSetIDURI() {
        final URI uri = URI.create(myOtherID);
        assertEquals(uri, new SpecificResource(myID, myID, SELECTOR).setID(uri).getID());
    }

    /**
     * Tests {@link SpecificResource#getType() getType} method.
     */
    @Test
    public final void testGetType() {
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, new SpecificResource(myID, myOtherID, SELECTOR).getType());
    }

    /**
     * Tests {@link SpecificResource#setSelector(Selector) setSelector} method.
     */
    @Test
    public final void testSetSelector() {
        assertEquals(SELECTOR, new SpecificResource(myID, myOtherID, SELECTOR).getSelector());
    }

    /**
     * Tests {@link SpecificResource#setSource(String) setSource} method.
     */
    @Test
    public final void testSetSourceString() {
        final URI source = URI.create(myOtherID);
        assertEquals(source, new SpecificResource(myID, myID, SELECTOR).setSource(myOtherID).getSource());
    }

    /**
     * Tests {@link SpecificResource#setSource(URI) setSource} method.
     */
    @Test
    public final void testSetSourceURI() {
        final URI source = URI.create(myOtherID);
        assertEquals(source, new SpecificResource(myID, myID, SELECTOR).setSource(source).getSource());
    }

    /**
     * Tests {@link SpecificResource#toJSON() toJSON} method.
     */
    @Test
    public final void testToJSON() {
        final String json = StringUtils.format(JSON, myID, myOtherID);
        assertEquals(new JsonObject(json), new SpecificResource(myID, myOtherID, SELECTOR).toJSON());
    }

    /**
     * Tests {@link SpecificResource#toString() toString} method.
     */
    @Test
    public final void testToString() {
        final String json = StringUtils.format(JSON, myID, myOtherID);
        assertEquals(new JsonObject(json).encode(), new SpecificResource(myID, myOtherID, SELECTOR).toString());
    }

    /**
     * Tests {@link SpecificResource#fromJSON(JsonObject) fromJSON} method.
     */
    @Test
    public final void testFromJSON() {
        final String json = StringUtils.format(JSON, myID, myOtherID);
        assertEquals(new JsonObject(json), SpecificResource.fromJSON(new JsonObject(json)).toJSON());
    }

    /**
     * Tests {@link SpecificResource#fromString(String) fromString} method.
     */
    @Test
    public final void testFromString() {
        final String json = StringUtils.format(JSON, myID, myOtherID);
        assertEquals(new JsonObject(json), SpecificResource.fromString(json).toJSON());
    }

}
