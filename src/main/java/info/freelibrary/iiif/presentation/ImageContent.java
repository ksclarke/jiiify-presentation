
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
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.services.APIComplianceLevel;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A content resource.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.MOTIVATION, Constants.ON,
    Constants.RESOURCE, Constants.OA_CHOICE, Constants.ITEM })
public class ImageContent extends Content<ImageContent> {

    private static final String TYPE = "oa:Annotation";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageContent.class, Constants.BUNDLE_NAME);

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
     * Creates a IIIF presentation content resource.
     */
    private ImageContent() {
        super(new Type(TYPE));
    }

    /**
     * Adds a image resource.
     *
     * @param aResource An image resource
     * @return The image content
     */
    @JsonIgnore
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
        return myDefaultResource.isEmpty() ? Optional.empty() : myDefaultResource;
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
    private Map<String, Object> getResourcesMap() {
        final Map<String, Object> map = new TreeMap<>();

        if (!myResources.isEmpty()) {
            map.put(Constants.TYPE, Constants.OA_CHOICE);

            if (getDefaultResource().isPresent()) {
                map.put(Constants.DEFAULT, myDefaultResource);
            }

            map.put(Constants.ITEM, myResources);
        }

        return map;
    }

    /**
     * Builds the ImageContent's ImageResoures from the JSON resources map.
     *
     * @param aResourceMap A JSON representation of the resources map
     */
    @JsonSetter(Constants.RESOURCE)
    private void setResourcesMap(final Map<String, Object> aResourceMap) {
        if (!aResourceMap.isEmpty()) {
            final Map<String, Object> defaultItem = (Map<String, Object>) aResourceMap.get(Constants.DEFAULT);
            final List<Map<String, Object>> items = (List<Map<String, Object>>) aResourceMap.get(Constants.ITEM);

            if (defaultItem != null) {
                myDefaultResource = Optional.of(buildImageResource(defaultItem));
            }

            if (items != null) {
                for (final Map<String, Object> map : items) {
                    myResources.add(buildImageResource(map));
                }
            }
        }
    }

    private ImageResource buildImageResource(final Map<String, Object> aImageResourceMap) {
        final ImageResource resource = new ImageResource(URI.create((String) aImageResourceMap.get(Constants.ID)));
        final String label = (String) aImageResourceMap.get(Constants.LABEL);
        final int width = (int) aImageResourceMap.get(Constants.WIDTH);
        final int height = (int) aImageResourceMap.get(Constants.HEIGHT);
        final Map<String, Object> service = (Map<String, Object>) aImageResourceMap.get(Constants.SERVICE);

        if (StringUtils.trimToNull(label) != null) {
            resource.setLabel(label);
        }

        if (width != 0) {
            try {
                resource.setWidth(width);
            } catch (final NumberFormatException details) {
                LOGGER.error(details, details.getMessage());
                resource.setWidth(0);
            }
        }

        if (height != 0) {
            try {
                resource.setHeight(height);
            } catch (final NumberFormatException details) {
                LOGGER.error(details, details.getMessage());
                resource.setHeight(0);
            }
        }

        if (service != null) {
            final String profile = StringUtils.trimToNull((String) service.get(Constants.PROFILE));
            final String id = StringUtils.trimToNull((String) service.get(Constants.ID));

            if (profile != null && id != null) {
                resource.setService(new ImageInfoService(APIComplianceLevel.fromProfile(profile), id));
            } else if (id != null) {
                resource.setService(new ImageInfoService(id));
            }
        }

        return resource;
    }
}
