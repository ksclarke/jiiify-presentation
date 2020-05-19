
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * Model content that can be associated with a {@link ContentAnnotation}.
 */
public class ModelContent extends AbstractContentResource<ModelContent> implements ContentResource {

    /**
     * Creates a model content resource.
     *
     * @param aID An model content resource ID in string form
     */
    public ModelContent(final String aID) {
        super(ResourceTypes.MODEL, aID);
    }

    /**
     * Creates a model content resource.
     *
     * @param aID An model content ID
     */
    public ModelContent(final URI aID) {
        super(ResourceTypes.MODEL, aID);
    }

    /**
     * Creates a model content annotation.
     */
    private ModelContent() {
        super(ResourceTypes.MODEL);
    }

}
