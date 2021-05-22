
package info.freelibrary.iiif.presentation.v3.ids;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

/**
 * A factory that generates Skolem IRIs. If the well-known IRI base is set, the IRIs generated can be mapped back to
 * blank nodes, if needed. It's used by the TextualBody class, but can also be used like:
 * <p>
 * <code>
 * SkolemIriFactory iriFactory = SkolemIriFactory.getFactory().setWellKnownBase("https://freelibrary.info/");<br/>
 * URI skolemIRI = iriFactory.getSkolemIRI();<br/>
 * </code>
 * </p>
 * <p>
 * or to generate a Skolem IRI that cannot be mapped back to a blank node (which is the default):
 * </p>
 * <p>
 * <code>
 * SkolemIriFactory iriFactory = SkolemIriFactory.getFactory();<br/>
 * URI skolemIRI = iriFactory.getSkolemIRI();<br/>
 * </code>
 * </p>
 * <p>
 * What what makes an ID mappable back to a blank node? Consult
 * <a href="https://www.w3.org/TR/rdf11-concepts/#section-skolemization">Replacing Blank Nodes with IRIs</a>.
 * </p>
 */
public final class SkolemIriFactory {

    /**
     * The start of a well-known URL.
     */
    private static final String COMPONENT_START = "/.well-known/genid/";

    /**
     * The internal SkolemIRI factory.
     */
    private static SkolemIriFactory myFactory;

    /**
     * The factory's well-known base.
     */
    private String myWellKnownBase;

    /**
     * Whether the factory creates serializable IDs.
     */
    private boolean hasSerializableIDs;

    /**
     * Creates a new Skolem IRI factory.
     */
    private SkolemIriFactory() {
        // This is intentionally empty
    }

    /**
     * Gets a new Skolem IRI factory.
     *
     * @return A Skolem IRI factory
     */
    public static SkolemIriFactory getFactory() {
        if (myFactory == null) {
            myFactory = new SkolemIriFactory();
        }

        return myFactory;
    }

    /**
     * Sets the well-known Skolem IRI base used by the factory; this should be a host name with the HTTP or HTTPS
     * protocol in front (e.g., "https://example.com/"). To remove a previously set well-known base, set a null.
     *
     * @param aBase A base for the well-known Skolem IRI
     * @return This factory
     */
    public SkolemIriFactory setWellKnownBase(final String aBase) {
        if (aBase == null) {
            myWellKnownBase = aBase;
        } else {
            myWellKnownBase = aBase.endsWith("/") ? aBase.substring(aBase.length() - 1, aBase.length()) : aBase;
        }

        return this;
    }

    /**
     * Gets the well-known Skolem IRI base that the factory uses.
     *
     * @return The well-known Skolem IRI base, if there is one set
     */
    public Optional<String> getWellKnownBase() {
        return Optional.ofNullable(myWellKnownBase);
    }

    /**
     * Gets a new Skolem IRI for use as an ID (most commonly for the ID of a
     * <a href="https://www.w3.org/TR/annotation-model/#embedded-textual-body">TextualBody</a>).
     *
     * @return The next Skolem IRI
     */
    public URI getSkolemIRI() {
        if (myWellKnownBase == null) {
            return URI.create(UUID.randomUUID().toString());
        } else {
            return URI.create(myWellKnownBase + COMPONENT_START + UUID.randomUUID().toString());
        }
    }

    /**
     * Returns whether the IDs created by the factory should be serialized into JSON outputs.
     *
     * @return True if IDs should be serialized; else, false
     */
    public boolean createsSerializableIDs() {
        return hasSerializableIDs;
    }

    /**
     * Sets whether the IDs created by the factory should be serialized into JSON outputs.
     *
     * @param aBoolFlag True if IDs should be serialized; else, false
     * @return This SkolemIriFactory
     */
    public SkolemIriFactory createSerializableIDs(final boolean aBoolFlag) {
        hasSerializableIDs = aBoolFlag;
        return this;
    }
}
