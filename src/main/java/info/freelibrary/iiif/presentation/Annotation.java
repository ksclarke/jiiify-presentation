
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * An annotation resource.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Annotation extends Resource<Annotation> {

    static final String TYPE = "sc:Annotation";

    private static final int REQ_ARG_COUNT = 1;

    /**
     * Creates a IIIF presentation annotation resource.
     */
    public Annotation(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation annotation resource.
     */
    public Annotation(final String aID) {
        super(TYPE, URI.create(aID), REQ_ARG_COUNT);
    }

}
