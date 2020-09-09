
package info.freelibrary.iiif.presentation.v2;

import java.net.MalformedURLException;
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

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v2.properties.Attribution;
import info.freelibrary.iiif.presentation.v2.properties.Description;
import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.License;
import info.freelibrary.iiif.presentation.v2.properties.Logo;
import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v2.properties.Thumbnail;
import info.freelibrary.iiif.presentation.v2.properties.Type;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.v2.services.APIComplianceLevel;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.services.Service;
import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;

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

    /**
     * Gets the motivation for the image content.
     *
     * @return The image content's motivation
     */
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

    @Override
    public ImageContent setLabel(final String aLabel) {
        return (ImageContent) super.setLabel(aLabel);
    }

    @Override
    public ImageContent setLabel(final Label aLabel) {
        return (ImageContent) super.setLabel(aLabel);
    }

    @Override
    public ImageContent setService(final Service<?> aService) {
        return (ImageContent) super.setService(aService);
    }

    @Override
    public ImageContent setMetadata(final Metadata aMetadata) {
        return (ImageContent) super.setMetadata(aMetadata);
    }

    @Override
    public ImageContent setDescription(final String aDescription) {
        return (ImageContent) super.setDescription(aDescription);
    }

    @Override
    public ImageContent setDescription(final Description aDescription) {
        return (ImageContent) super.setDescription(aDescription);
    }

    @Override
    public ImageContent setThumbnail(final Thumbnail aThumbnail) {
        return (ImageContent) super.setThumbnail(aThumbnail);
    }

    @Override
    public ImageContent setThumbnail(final String aURI) {
        return (ImageContent) super.setThumbnail(aURI);
    }

    @Override
    public ImageContent setAttribution(final String aAttribution) {
        return (ImageContent) super.setAttribution(aAttribution);
    }

    @Override
    public ImageContent setAttribution(final Attribution aAttribution) {
        return (ImageContent) super.setAttribution(aAttribution);
    }

    @Override
    public ImageContent setLicense(final License aLicense) {
        return (ImageContent) super.setLicense(aLicense);
    }

    @Override
    public ImageContent setLicense(final String aURL) throws MalformedURLException {
        return (ImageContent) super.setLicense(aURL);
    }

    @Override
    public ImageContent setLogo(final Logo aLogo) {
        return (ImageContent) super.setLogo(aLogo);
    }

    @Override
    public ImageContent setLogo(final String aURI) {
        return (ImageContent) super.setLogo(aURI);
    }

    @Override
    public ImageContent setID(final String aURI) {
        return (ImageContent) super.setID(aURI);
    }

    @Override
    public ImageContent setID(final URI aID) {
        return (ImageContent) super.setID(aID);
    }

    @Override
    public ImageContent setWithin(final String aWithin) {
        return (ImageContent) super.setWithin(aWithin);
    }

    @Override
    public ImageContent setWithin(final URI aWithin) {
        return (ImageContent) super.setWithin(aWithin);
    }

    @Override
    public ImageContent setViewingHint(final ViewingHint aViewingHint) {
        return (ImageContent) super.setViewingHint(aViewingHint);
    }

    @Override
    public ImageContent setViewingHint(final String aViewingHint) {
        return (ImageContent) super.setViewingHint(aViewingHint);
    }

    @Override
    public ImageContent setViewingHint(final Option aViewingHint) {
        return (ImageContent) super.setViewingHint(aViewingHint);
    }

    @Override
    public ImageContent setSeeAlso(final SeeAlso aSeeAlso) {
        return (ImageContent) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public ImageContent setSeeAlso(final String aSeeAlso) {
        return (ImageContent) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public ImageContent setOn(final URI aURI) {
        return (ImageContent) super.setOn(aURI);
    }

    @Override
    public ImageContent setOn(final String aURI) {
        return (ImageContent) super.setOn(aURI);
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
            final Object defaultItem = aResourceMap.get(Constants.DEFAULT);
            final List<?> items = (List<?>) aResourceMap.get(Constants.ITEM);

            if (defaultItem != null) {
                myDefaultResource = Optional.of(buildImageResource(defaultItem));
            }

            if (items != null && items instanceof List) {
                for (final Object object : items) {
                    if (object instanceof String && RDF_NIL.equals(object.toString())) {
                        myResources.add(null);
                    } else {
                        myResources.add(buildImageResource(object));
                    }
                }
            }

            if (defaultItem == null && items == null && aResourceMap.get(Constants.ID) != null) {
                myResources.add(buildImageResource(aResourceMap));
            }
        }
    }

    private ImageResource buildImageResource(final Object aImageResourceMap) {
        if (aImageResourceMap instanceof Map) {
            final Map<?, ?> map = (Map<?, ?>) aImageResourceMap;
            final ImageResource resource = new ImageResource(URI.create((String) map.get(Constants.ID)));
            final String label = (String) map.get(Constants.LABEL);
            final Map<?, ?> service = (Map<?, ?>) map.get(Constants.SERVICE);
            final Object widthObj = map.get(Constants.WIDTH);
            final Object heightObj = map.get(Constants.HEIGHT);
            final int width;
            final int height;

            if (widthObj == null) {
                width = 0;
            } else {
                width = Integer.parseInt(widthObj.toString());
            }

            if (heightObj == null) {
                height = 0;
            } else {
                height = Integer.parseInt(heightObj.toString());
            }

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
        } else {
            return null;
        }
    }
}
