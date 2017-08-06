
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * An annotation list resource.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class AnnotationList extends Resource<AnnotationList> {

    static final String TYPE = "sc:AnnotationList";

    private static final int REQ_ARG_COUNT = 2;

    /**
     * Creates an annotation list resource.
     */
    public AnnotationList(final String aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates an annotation list resource.
     */
    public AnnotationList(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

}
