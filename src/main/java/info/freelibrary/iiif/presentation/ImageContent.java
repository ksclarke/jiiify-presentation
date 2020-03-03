
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.I18n;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.services.APIComplianceLevel;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * An image content that is associated with a {@link Canvas}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.MOTIVATION, Constants.ON,
    Constants.RESOURCE, Constants.OA_CHOICE, Constants.ITEM })
public class ImageContent extends Content<ImageContent> {

    private static final String TYPE = "oa:Annotation";

    private static final String MOTIVATION = "sc:painting";

    private static final String RDF_NIL = "rdf:nil";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageContent.class, Constants.BUNDLE_NAME);

    private final List<ImageResource> myResources = new ArrayList<>();

    private Optional<ImageResource> myDefaultResource;

    /**
     * Creates image content.
     *
     * @param aIdString An image content ID in string form
     * @param aCanvas A canvas for the image content
     */
    public ImageContent(final String aIdString, final Canvas aCanvas) {
        super(TYPE, aIdString, aCanvas);
    }

    /**
     * Creates image content.
     *
     * @param aID An image content ID
     * @param aCanvas A canvas for the image content
     */
    public ImageContent(final URI aID, final Canvas aCanvas) {
        super(TYPE, aID, aCanvas);
    }

    /**
     * Creates image content.
     */
    private ImageContent() {
        super(new Type(TYPE));
    }

    /**
     * Gets the motivation of the image content.
     *
     * @return The motivation
     */
    @JsonGetter(Constants.MOTIVATION)
    public String getMotivation() {
        return MOTIVATION;
    }

    /**
     * Sets the motivation of the image content.
     *
     * @param aMotivationString A motivation in string form
     */
    @JsonSetter(Constants.MOTIVATION)
    private void setMotivation(final String aMotivationString) {
        if (!MOTIVATION.equals(aMotivationString)) {
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
        Objects.requireNonNull(aResource, MessageCodes.JPA_006);
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
        Objects.requireNonNull(aResource, MessageCodes.JPA_006);
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
    private Map<String, Object> getResourceMap() {
        // Since we're supplying the resource map, we need to supply the order too
        @SuppressWarnings("checkstyle:BooleanExpressionComplexity")
        final Map<String, Object> map = new TreeMap<>((a1stKey, a2ndKey) -> {
            if (Constants.TYPE.equals(a1stKey) && !Constants.ID.equals(a2ndKey)) {
                return -1;
            } else if (Constants.ID.equals(a1stKey)) {
                return -1;
            } else if (Constants.DEFAULT.equals(a1stKey) && !Constants.TYPE.equals(a2ndKey) && !Constants.ID.equals(
                    a2ndKey)) {
                return -1;
            } else if (Constants.ITEM.equals(a1stKey) && !Constants.ID.equals(a2ndKey) && !Constants.TYPE.equals(
                    a2ndKey) && !Constants.DEFAULT.equals(a2ndKey)) {
                return -1;
            } else if (Constants.WIDTH.equals(a1stKey) && !Constants.TYPE.equals(a2ndKey) && !Constants.ID.equals(
                    a2ndKey)) {
                return -1;
            } else if (Constants.HEIGHT.equals(a1stKey) && !Constants.TYPE.equals(a2ndKey) && !Constants.ID.equals(
                    a2ndKey) && !Constants.WIDTH.equals(a2ndKey)) {
                return -1;
            } else if (Constants.FORMAT.equals(a1stKey) && !Constants.TYPE.equals(a2ndKey) && !Constants.ID.equals(
                    a2ndKey) && !Constants.WIDTH.equals(a2ndKey) && !Constants.HEIGHT.equals(a2ndKey)) {
                return -1;
            } else if (Constants.LABEL.equals(a1stKey) && !Constants.TYPE.equals(a2ndKey) && !Constants.ID.equals(
                    a2ndKey) && !Constants.WIDTH.equals(a2ndKey) && !Constants.HEIGHT.equals(a2ndKey) &&
                    !Constants.FORMAT.equals(a2ndKey)) {
                return -1;
            } else if (Constants.SERVICE.equals(a1stKey) && !Constants.TYPE.equals(a2ndKey) && !Constants.ID.equals(
                    a2ndKey) && !Constants.WIDTH.equals(a2ndKey) && !Constants.HEIGHT.equals(a2ndKey) &&
                    !Constants.FORMAT.equals(a2ndKey) && !Constants.LABEL.equals(a2ndKey)) {
                return -1;
            } else {
                return 1;
            }
        });

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
     * @param aMap A JSON representation of the resources map
     */
    @JsonSetter(Constants.RESOURCE)
    private void setResourceMap(final Map<String, Object> aMap) {
        LOGGER.trace(aMap.toString());

        if (!aMap.isEmpty()) {
            final Map<String, Object> defaultItem = (Map<String, Object>) aMap.get(Constants.DEFAULT);
            final List<Map<String, Object>> items = (List<Map<String, Object>>) aMap.get(Constants.ITEM);

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

            if (defaultItem == null && items == null && aMap.get(Constants.ID) != null) {
                myResources.add(buildImageResource(aMap));
            }
        }
    }

    /**
     * Builds an image resource from the Map that Jackson creates
     *
     * @param aMap A map of the image resources
     * @return The newly built image resource
     */
    private ImageResource buildImageResource(final Map<String, Object> aMap) {
        final ImageResource resource = new ImageResource(URI.create((String) aMap.get(Constants.ID)));
        final LinkedHashMap labelMap = (LinkedHashMap) aMap.get(Constants.LABEL);
        final int width = (int) aMap.getOrDefault(Constants.WIDTH, 0);
        final int height = (int) aMap.getOrDefault(Constants.HEIGHT, 0);
        final Map<String, Object> service = (Map<String, Object>) aMap.get(Constants.SERVICE);

        if (labelMap != null) {
            final Iterator<String> iterator = labelMap.keySet().iterator();

            if (iterator.hasNext()) {
                final String langTag = iterator.next();
                final List<String> langStrings = (List<String>) labelMap.get(langTag);

                resource.setLabel(new Label(new I18n(langTag, langStrings)));
            } else {
                throw new RuntimeException(); // FIXME
            }
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
