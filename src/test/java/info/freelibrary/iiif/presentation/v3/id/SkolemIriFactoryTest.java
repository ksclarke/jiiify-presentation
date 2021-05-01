
package info.freelibrary.iiif.presentation.v3.id;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.TextualBody;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Tests of the SkolemIriFactory.
 */
public class SkolemIriFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkolemIriFactoryTest.class, MessageCodes.BUNDLE);

    private static final String WELL_KNOWN_BASE = "https://freelibrary.info";

    private static final String SKOLEM_IRI_START = WELL_KNOWN_BASE + "/.well-known/genid/";

    /**
     * Cleans up after the tests.
     */
    @After
    public final void cleanUp() {
        SkolemIriFactory.reset();
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
     * Tests resetting the factory.
     */
    @Test
    public final void testReset() {
        final SkolemIriFactory factory1 = SkolemIriFactory.getFactory();
        final SkolemIriFactory factory2;

        SkolemIriFactory.reset();
        factory2 = SkolemIriFactory.getFactory();

        assertNotEquals(factory1, factory2);
    }

    /**
     * Tests setting the well-known Skolem IRI base.
     */
    @Test
    public final void testSetWellKnownBase() {
        final SkolemIriFactory factory = SkolemIriFactory.getFactory();

        assertEquals(WELL_KNOWN_BASE, factory.setWellKnownBase(WELL_KNOWN_BASE).getWellKnownBase().get());
    }

    /**
     * Tests getting a SkolemIRI that has a well-known base set.
     */
    @Test
    public final void testGetBlankNodeSkolemIRI() {
        final SkolemIriFactory factory = SkolemIriFactory.getFactory();
        final String skolemIRI = factory.setWellKnownBase(WELL_KNOWN_BASE).getSkolemIRI().toString();

        LOGGER.debug(MessageCodes.JPA_112, skolemIRI);
        assertTrue(skolemIRI.startsWith(SKOLEM_IRI_START));
    }

    /**
     * Tests getting a SkolemIRI that doesn't have a well-known base set.
     */
    @Test
    public final void testGetSkolemIRI() {
        final SkolemIriFactory factory = SkolemIriFactory.getFactory();
        final String skolemIRI = factory.getSkolemIRI().toString();

        LOGGER.debug(MessageCodes.JPA_112, skolemIRI);
        assertFalse(skolemIRI.startsWith(SKOLEM_IRI_START));
    }

    /**
     * Tests <code>hasSerializableIDs()</code>.
     */
    @Test
    public final void testHasSerializableIDs() {
        final TextualBody textualBody;

        SkolemIriFactory.getFactory().hasSerializableIDs(true);
        textualBody = new TextualBody();

        assertNotEquals(null, textualBody.getID());
    }

    /**
     * Tests <code>hasSerializableIDs()</code>.
     */
    @Test
    public final void testHasNonSerializableIDsDefault() {
        final TextualBody textualBody;

        SkolemIriFactory.getFactory();
        textualBody = new TextualBody();

        assertEquals(null, textualBody.getID());
    }

    /**
     * Tests <code>hasSerializableIDs()</code>.
     */
    @Test
    public final void testHasNonSerializableIDs() {
        final TextualBody textualBody;

        SkolemIriFactory.getFactory().hasSerializableIDs(false);
        textualBody = new TextualBody();

        assertEquals(null, textualBody.getID());
    }
}
