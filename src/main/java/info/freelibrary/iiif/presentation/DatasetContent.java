
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Dataset content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.THUMBNAIL, Constants.FORMAT, Constants.LANGUAGE })
public class DatasetContent extends AbstractContentResource<DatasetContent> implements Thumbnail {

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
