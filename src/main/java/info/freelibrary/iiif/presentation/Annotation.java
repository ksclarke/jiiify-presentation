
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * An annotation resource.
 */
public class Annotation extends Resource<Annotation> {

    private static final String TYPE = "sc:Annotation";

    private static final int REQ_ARG_COUNT = 1;

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An annotation ID
     */
    public Annotation(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An annotation ID
     */
    public Annotation(final String aID) {
        super(TYPE, URI.create(aID), REQ_ARG_COUNT);
    }

}
