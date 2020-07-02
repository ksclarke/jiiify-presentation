
package info.freelibrary.iiif.presentation.v2;

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

import info.freelibrary.iiif.presentation.v2.properties.Type;
import info.freelibrary.iiif.presentation.v2.services.APIComplianceLevel;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * An image resource that is associated with a {@link Canvas}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.MOTIVATION, Constants.ON,
    Constants.RESOURCE, Constants.OA_CHOICE, Constants.ITEM })
public class ImageContent extends Content<ImageContent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageContent.class, MessageCodes.BUNDLE);

    private static final String TYPE = "oa:Annotation";

    private static final String MOTIVATION = "sc:painting";

    private static final String RDF_NIL = "rdf:nil";

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

    @JsonGetter(Constants.MOTIVATION)
    public String getMotivation() {
        return MOTIVATION;
    }

    @JsonSetter(Constants.MOTIVATION)
    private void setMotivation(final String aMotivation) {
        if (!MOTIVATION.equals(aMotivation)) {
            throw new I18nRuntimeException();
        }
    }

    /**
     * Adds a image resource.
     *
     * @param aResource An image resource
     * @return The image content
     */
    @JsonIgnore
    public ImageContent addResource(final ImageResource aResource) {
        Objects.requireNonNull(aResource, LOGGER.getMessage(MessageCodes.JPA_006));
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
        Objects.requireNonNull(aResource, LOGGER.getMessage(MessageCodes.JPA_006));
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
        if (myDefaultResource == null) {
            myDefaultResource = Optional.empty();
        }

        return myDefaultResource;
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
            final Optional<ImageResource> defaultResource = getDefaultResource();

            if (myResources.size() > 1 || defaultResource.isPresent()) {
                final List<Object> itemList = new ArrayList<>();

                map.put(Constants.TYPE, Constants.OA_CHOICE);

                if (defaultResource.isPresent()) {
                    map.put(Constants.DEFAULT, myDefaultResource);
                }

                for (final ImageResource resource : myResources) {
                    if (resource == null) {
                        itemList.add(RDF_NIL);
                    } else {
                        itemList.add(resource);
                    }
                }

                map.put(Constants.ITEM, itemList);
            } else if (myResources.size() == 1) {
                final ImageResource resource = myResources.get(0);
                final Optional<ImageInfoService> service = resource.getService();
                final int height = resource.getHeight();
                final int width = resource.getWidth();
                final String format = resource.getFormat();

                map.put(Constants.ID, resource.getID());
                map.put(Constants.TYPE, resource.getType());

                if (height != 0) {
                    map.put(Constants.HEIGHT, height);
                }

                if (width != 0) {
                    map.put(Constants.WIDTH, width);
                }

                if (format != null) {
                    map.put(Constants.FORMAT, format);
                }

                if (resource.getLabel() != null) {
                    map.put(Constants.LABEL, resource.getLabel());
                }

                if (service.isPresent()) {
                    map.put(Constants.SERVICE, resource.getService());
                }
            }
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
        LOGGER.trace(aResourceMap.toString());

        if (!aResourceMap.isEmpty()) {
            final Map<String, Object> defaultItem = (Map<String, Object>) aResourceMap.get(Constants.DEFAULT);
            final List<Map<String, Object>> items = (List<Map<String, Object>>) aResourceMap.get(Constants.ITEM);

            if (defaultItem != null) {
                myDefaultResource = Optional.of(buildImageResource(defaultItem));
            }

            if (items != null) {
                for (final Object object : items) {
                    if (object instanceof String && RDF_NIL.equals(object.toString())) {
                        myResources.add(null);
                    } else {
                        myResources.add(buildImageResource((Map<String, Object>) object));
                    }
                }
            }

            if (defaultItem == null && items == null && aResourceMap.get(Constants.ID) != null) {
                myResources.add(buildImageResource(aResourceMap));
            }
        }
    }

    private ImageResource buildImageResource(final Map<String, Object> aImageResourceMap) {
        final ImageResource resource = new ImageResource(URI.create((String) aImageResourceMap.get(Constants.ID)));
        final String label = (String) aImageResourceMap.get(Constants.LABEL);
        final int width = (int) aImageResourceMap.getOrDefault(Constants.WIDTH, 0);
        final int height = (int) aImageResourceMap.getOrDefault(Constants.HEIGHT, 0);
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
