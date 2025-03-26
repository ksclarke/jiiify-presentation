
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Tests a SpecificResource.
 */
public class SpecificResourceTest extends AbstractTest {

    /** A JSON test fixture. */
    private static final String EXPECTED;

    /** The audio content selector. */
    private static final Selector SELECTOR = new AudioContentSelector();

    static {
        try {
            EXPECTED = StringUtils.read(new File("src/test/resources/json/specificresource.json"));
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
     * Tests the SpecificResource equals method.
     */
    @Test
    public final void testEquals() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        final SpecificResource specificResource = JSON.readValue(json, SpecificResource.class);

        assertEquals(specificResource, specificResource);
    }

    /**
     * Tests the SpecificResource equals method.
     */
    @Test
    public final void testEqualsFirstNull() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        final SpecificResource specificResource = JSON.readValue(json, SpecificResource.class);

        assertNotEquals(null, specificResource);
    }

    /**
     * Tests the SpecificResource equals method.
     */
    @Test
    public final void testEqualsSecondNull() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        final SpecificResource specificResource = JSON.readValue(json, SpecificResource.class);

        assertNotEquals(specificResource, null);
    }

    /**
     * Tests the SpecificResource equals method.
     */
    @Test
    public final void testEqualsString() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        final SpecificResource specificResource = JSON.readValue(json, SpecificResource.class);

        assertNotEquals(specificResource, json);
    }

    /**
     * Tests reading using {@link JSON}.
     */
    @Test
    public final void testFromString() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        assertEquals(json, JSON.readValue(json, SpecificResource.class).toString());
    }

    /**
     * Tests the SpecificResource getFormat() method.
     */
    @Test
    public final void testGetFormatEmpty() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        final SpecificResource specificResource = JSON.readValue(json, SpecificResource.class);
        final Optional<MediaType> format = specificResource.getFormat();

        assertTrue(format.isEmpty());
    }

    /**
     * Tests getting the selector.
     */
    @Test
    public final void testGetSelector() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        final SpecificResource specificResource = JSON.readValue(json, SpecificResource.class);

        assertTrue(specificResource.getSelector().isPresent());
        assertEquals(SELECTOR, specificResource.getSelector().get());
    }

    /**
     * Tests {@link SpecificResource#getType() getType} method.
     */
    @Test
    public final void testGetType() {
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, new SpecificResource(myID, myOtherID, SELECTOR).getType().get());
    }

    /**
     * Test protected constructor.
     */
    @Test
    public void testProtectedConstructorViaSubclass() {
        final SpecificResource instance;

        /**
         * A subclass that can test the protected constructor.
         */
        class SpecificResourceTestSubclass extends SpecificResource {

            /**
             * Creates a new instance of the subclass.
             */
            SpecificResourceTestSubclass() {
                super();
            }
        }

        instance = new SpecificResourceTestSubclass();
        assertNotNull(instance);
    }

    /**
     * Tests setting the ID.
     */
    @Test
    public final void testSetID() {
        final String json = TestUtils.format(StringUtils.format(EXPECTED, myID, myOtherID));
        final SpecificResource specificResource = JSON.readValue(json, SpecificResource.class);

        specificResource.setID(myID);
        assertEquals(myID, specificResource.getID());
    }

    /**
     * Tests {@link SpecificResource#setID(String) setID} method.
     */
    @Test
    public final void testSetIDString() {
        assertEquals(myID, new SpecificResource(myID, myOtherID, SELECTOR).getID());
    }

    /**
     * Tests {@link SpecificResource#setSelector(Selector) setSelector} method.
     */
    @Test
    public final void testSetSelector() {
        assertEquals(SELECTOR, new SpecificResource(myID, myOtherID, SELECTOR).getSelector().get());
    }

}
