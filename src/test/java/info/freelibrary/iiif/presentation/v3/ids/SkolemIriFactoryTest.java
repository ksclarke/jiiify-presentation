
package info.freelibrary.iiif.presentation.v3.ids;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.TextualBody;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Tests of the SkolemIriFactory.
 */
public class SkolemIriFactoryTest {

    /** A well-known base URL. */
    private static final String BASE_URL = "http://freelibrary.info";

    /** The test's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SkolemIriFactoryTest.class, MessageCodes.BUNDLE);

    /** A Skolem IRI start for testing. */
    private static final String SKOLEM_IRI_START = BASE_URL + "/.well-known/genid/";

    /**
     * Tests getting a SkolemIRI that has a well-known base set.
     */
    @Test
    public final void testGetBlankNodeSkolemIRI() {
        final SkolemIriFactory factory = SkolemIriFactory.getFactory();
        final String skolemIRI = factory.setWellKnownBase(BASE_URL).getSkolemIRI().toString();

        LOGGER.debug(MessageCodes.JPA_112, skolemIRI);
        assertTrue(skolemIRI.startsWith(SKOLEM_IRI_START));
    }

    /**
     * Tests that <code>getFactory()</code> return the same factory.
     */
    @Test
    public final void testGetFactory() {
        final SkolemIriFactory factory1 = SkolemIriFactory.getFactory();
        final SkolemIriFactory factory2 = SkolemIriFactory.getFactory();

        assertEquals(factory1, factory2);
    }

    /**
     * Tests getting a SkolemIRI that doesn't have a well-known base set.
     */
    @Test
    public final void testGetSkolemIRI() {
        final SkolemIriFactory factory = SkolemIriFactory.getFactory().setWellKnownBase(null);
        final String skolemIRI = factory.getSkolemIRI().toString();

        LOGGER.debug(MessageCodes.JPA_112, skolemIRI);
        assertFalse(skolemIRI.startsWith(SKOLEM_IRI_START));
    }

    /**
     * Tests <code>hasSerializableIDs()</code>.
     */
    @Test
    public final void testHasNonSerializableIDs() {
        assertEquals(EMPTY, new TextualBody(SkolemIriFactory.getFactory().createSerializableIDs(false)).getID());
    }

    /**
     * Tests <code>hasSerializableIDs()</code>.
     */
    @Test
    public final void testHasNonSerializableIDsDefault() {
        assertEquals(EMPTY,
                new TextualBody(SkolemIriFactory.getFactory().createSerializableIDs(false).setWellKnownBase(null))
                        .getID());
    }

    /**
     * Tests <code>hasSerializableIDs()</code>.
     */
    @Test
    public final void testHasSerializableIDs() {
        assertNotEquals(EMPTY, new TextualBody(SkolemIriFactory.getFactory().createSerializableIDs(true)).getID());
    }

    /**
     * Tests setting the well-known Skolem IRI base.
     */
    @Test
    public final void testSetWellKnownBase() {
        final SkolemIriFactory factory = SkolemIriFactory.getFactory();

        assertEquals(BASE_URL, factory.setWellKnownBase(BASE_URL).getWellKnownBase().get());
    }
}
