
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

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

    /** The audio content selector. */
    private static final Selector SELECTOR = new AudioContentSelector();

    /** A JSON test fixture. */
    private static final String JSON;

    static {
        try {
            JSON = StringUtils.read(new File("src/test/resources/json/specificresource.json"));
        } catch (final IOException details) {
            throw new I18nRuntimeException(details);
        }
    }

    /** The test ID. */
    private String myID;

    /** The test other ID. */
    private String myOtherID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        final String http = "http";
        final String https = "https";

        myID = myLoremIpsum.getUrl();
        myOtherID = myLoremIpsum.getUrl();

        // Temporary fixes until the tests are rewritten to use JEasy.
        if (!myID.startsWith(https)) {
            myID = myID.replace(http, https);
        }

        if (!myOtherID.startsWith(https)) {
            myOtherID = myOtherID.replace(http, https);
        }
    }

    /**
     * Tests String constructor.
     */
    @Test
    public final void testSpecificResourceIdStringSourceStringSelector() {
        new SpecificResource(myID, myOtherID, SELECTOR);
    }

    /**
     * Tests {@link SpecificResource#setID(String) setID} method.
     */
    @Test
    public final void testSetIDString() {
        assertEquals(myID, new SpecificResource(myID, myOtherID, SELECTOR).getID());
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
     * Tests {@link SpecificResource#from(String) fromString} method.
     */
    @Test
    public final void testFromString() {
        final String json = TestUtils.format(StringUtils.format(JSON, myID, myOtherID));
        assertEquals(json, SpecificResource.from(json).toString());
    }

}
