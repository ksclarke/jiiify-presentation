
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.util.Constants;
import info.freelibrary.iiif.presentation.util.MessageCodes;

/**
 * A content resource.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.MOTIVATION, Constants.ON,
    Constants.RESOURCE, "oa:Choice", Constants.ITEM })
public class ImageContent extends Content<ImageContent> {

    static final String TYPE = "oa:Annotation";

    static final String CHOICE = "oa:Choice";

    private final List<ImageResource> myResources = new ArrayList<>();

    private Optional<ImageResource> myDefaultResource;

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID An image content ID
     * @param aCanvas A canvas for the image content
     */
    public ImageContent(final String aID, final Canvas aCanvas) {
        super(TYPE, aID, aCanvas);
    }

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID An image content ID
     * @param aCanvas A canvas for the image content
     */
    public ImageContent(final URI aID, final Canvas aCanvas) {
        super(TYPE, aID, aCanvas);
    }

    /**
     * Adds a image resource.
     *
     * @param aResource An image resource
     * @return The image content
     */
    public ImageContent addResource(final ImageResource aResource) {
        Objects.requireNonNull(aResource, MessageCodes.EXC_006);
        myResources.add(aResource);
        return this;
    }

    /**
     * Sets the default image resource.
     *
     * @param aResource An image resource
     * @return The image content
     */
    @JsonIgnore
    public ImageContent setDefaultResource(final ImageResource aResource) {
        Objects.requireNonNull(aResource, MessageCodes.EXC_006);
        myDefaultResource = Optional.of(aResource);
        return this;
    }

    /**
     * Gets the default image resource, if there is one.
     *
     * @return The default image resource
     */
    @JsonIgnore
    public Optional<ImageResource> getDefaultResource() {
        return myDefaultResource == null ? Optional.empty() : myDefaultResource;
    }

    /**
     * Gets the image resources.
     *
     * @return The image resources
     */
    @JsonIgnore
    public List<ImageResource> getResources() {
        return myResources;
    }

    /**
     * Gets the resources map.
     *
     * @return The resources map
     */
    @JsonGetter(Constants.RESOURCE)
    Map<String, Object> getResourcesMap() {
        final Map<String, Object> map = new TreeMap<>();

        if (myResources.size() > 1) {
            map.put(Constants.TYPE, CHOICE);

            if (getDefaultResource().isPresent()) {
                map.put(Constants.DEFAULT, myDefaultResource);
            }

            map.put(Constants.ITEM, myResources);
        }

        return map;
    }
}
