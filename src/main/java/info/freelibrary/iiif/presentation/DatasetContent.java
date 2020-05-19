
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * Dataset content that can be associated with a {@link ContentAnnotation}.
 */
public class DatasetContent extends AbstractContentResource<DatasetContent> implements ContentResource {

    /**
     * Creates a dataset content resource.
     *
     * @param aID An dataset content resource ID in string form
     */
    public DatasetContent(final String aID) {
        super(ResourceTypes.DATASET, aID);
    }

    /**
     * Creates a dataset content resource.
     *
     * @param aID An dataset content resource ID
     */
    public DatasetContent(final URI aID) {
        super(ResourceTypes.DATASET, aID);
    }

    /**
     * Creates a dataset content resource.
     */
    private DatasetContent() {
        super(ResourceTypes.DATASET);
    }

}
