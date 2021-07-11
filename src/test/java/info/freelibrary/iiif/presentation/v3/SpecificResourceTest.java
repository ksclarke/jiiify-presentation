
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

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

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = myLoremIpsum.getUrl();
        myOtherID = myLoremIpsum.getUrl();
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
        assertEquals(URI.create(myID), new SpecificResource(myID, myOtherID, SELECTOR).getID());
    }

    /**
     * Tests {@link SpecificResource#setID(URI) setID} method.
     */
    @Test
    public final void testSetIDURI() {
        final URI uri = URI.create(myID);
        assertEquals(uri, new SpecificResource(uri, URI.create(myOtherID), SELECTOR).getID());
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
        assertEquals(source, new SpecificResource(myID, myOtherID, SELECTOR).getSource());
    }

    /**
     * Tests {@link SpecificResource#setSource(URI) setSource} method.
     */
    @Test
    public final void testSetSourceURI() {
        final URI source = URI.create(myOtherID);
        assertEquals(source, new SpecificResource(URI.create(myID), source, SELECTOR).getSource());
    }

    /**
     * Tests {@link SpecificResource#from(String) fromString} method.
     */
    @Test
    public final void testFromString() {
        final String json = TestUtils.format(StringUtils.format(JSON, myID, myOtherID));
        assertEquals(json, SpecificResource.from(json).toString());
    }

}
