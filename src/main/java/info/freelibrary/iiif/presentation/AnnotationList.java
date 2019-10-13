
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * An annotation list resource.
 */
public class AnnotationList extends Resource<AnnotationList> {

    private static final String TYPE = "sc:AnnotationList";

    private static final int REQ_ARG_COUNT = 2;

    /**
     * Creates an annotation list resource.
     *
     * @param aID An annotation list ID
     */
    public AnnotationList(final String aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates an annotation list resource.
     *
     * @param aID An annotation list ID
     */
    public AnnotationList(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

}
