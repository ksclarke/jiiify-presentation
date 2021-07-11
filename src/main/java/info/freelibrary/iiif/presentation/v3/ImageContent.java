
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Image content that can be associated with an annotation or set as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.HEIGHT,
    JsonKeys.WIDTH, JsonKeys.LANGUAGE, JsonKeys.SERVICE })
public class ImageContent extends AbstractContentResource<ImageContent> implements AnnotationBody<ImageContent>,
        ContentResource<ImageContent>, SpatialContentResource<ImageContent>, Resource<ImageContent> {

    /**
     * The class of media type this content represents.
     */
    private static final String MEDIA_TYPE_CLASS = "image";

    /**
     * The image content's width.
     */
    private int myWidth;

    /**
     * The image content's height.
     */
    private int myHeight;

    /**
     * Creates image content with the supplied ID.
     *
     * @param aURI An image content ID in string form
     */
    public ImageContent(final String aURI) {
        super(ResourceTypes.IMAGE, aURI);
        setFormatFromMediaType(MediaType.parse(URI.create(aURI), MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Creates image content with the supplied ID.
     *
     * @param aURI An image content ID
     */
    public ImageContent(final URI aURI) {
        super(ResourceTypes.IMAGE, aURI);
        setFormatFromMediaType(MediaType.parse(aURI, MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Constructs an image content resource for Jackson's deserialization process.
     */
    private ImageContent() {
        super(ResourceTypes.IMAGE);
    }

    @Override
    @JsonIgnore
    public ImageContent setFormat(final MediaType aMediaType) {
        return (ImageContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public ImageContent setFormat(final String aMediaType) {
        return (ImageContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public ImageContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public ImageContent setProviders(final List<Provider> aProviderList) {
        return (ImageContent) super.setProviders(aProviderList);
    }

    @Override
    public ImageContent clearBehaviors() {
        return (ImageContent) super.clearBehaviors();
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public ImageContent setBehaviors(final Behavior... aBehaviorArray) {
        return (ImageContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public ImageContent setBehaviors(final List<Behavior> aBehaviorList) {
        return (ImageContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public ImageContent addBehaviors(final Behavior... aBehaviorArray) {
        return (ImageContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public ImageContent addBehaviors(final List<Behavior> aBehaviorList) {
        return (ImageContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    @Override
    public ImageContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (ImageContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public ImageContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (ImageContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public ImageContent setServices(final Service<?>... aServiceArray) {
        return (ImageContent) super.setServices(aServiceArray);
    }

    @Override
    public ImageContent setServices(final List<Service<?>> aServiceList) {
        return (ImageContent) super.setServices(aServiceList);
    }

    @Override
    public ImageContent setPartOfs(final PartOf... aPartOfArray) {
        return (ImageContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    public ImageContent setPartOfs(final List<PartOf> aPartOfList) {
        return (ImageContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public ImageContent setRenderings(final Rendering... aRenderingArray) {
        return (ImageContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public ImageContent setRenderings(final List<Rendering> aRenderingList) {
        return (ImageContent) super.setRenderings(aRenderingList);
    }

    @Override
    public ImageContent setHomepages(final Homepage... aHomepageArray) {
        return (ImageContent) super.setHomepages(aHomepageArray);
    }

    @Override
    public ImageContent setHomepages(final List<Homepage> aHomepageList) {
        return (ImageContent) super.setHomepages(aHomepageList);
    }

    @Override
    public ImageContent setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (ImageContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public ImageContent setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (ImageContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public ImageContent setID(final String aID) {
        return (ImageContent) super.setID(aID);
    }

    @Override
    public ImageContent setID(final URI aID) {
        return (ImageContent) super.setID(aID);
    }

    @Override
    public ImageContent setRights(final String aRights) {
        return (ImageContent) super.setRights(aRights);
    }

    @Override
    public ImageContent setRights(final URI aRights) {
        return (ImageContent) super.setRights(aRights);
    }

    @Override
    public ImageContent setRequiredStatement(final RequiredStatement aStatement) {
        return (ImageContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public ImageContent setSummary(final String aSummary) {
        return (ImageContent) super.setSummary(aSummary);
    }

    @Override
    public ImageContent setSummary(final Summary aSummary) {
        return (ImageContent) super.setSummary(aSummary);
    }

    @Override
    public ImageContent setMetadata(final Metadata... aMetadataArray) {
        return (ImageContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public ImageContent setMetadata(final List<Metadata> aMetadataList) {
        return (ImageContent) super.setMetadata(aMetadataList);
    }

    @Override
    public ImageContent setLabel(final String aLabel) {
        return (ImageContent) super.setLabel(aLabel);
    }

    @Override
    public ImageContent setLabel(final Label aLabel) {
        return (ImageContent) super.setLabel(aLabel);
    }

    /**
     * Sets the width and height of the image.
     *
     * @param aWidth An image width
     * @param aHeight An image height
     * @return This image content
     */
    @Override
    @JsonIgnore
    public ImageContent setWidthHeight(final int aWidth, final int aHeight) {
        setWidth(aWidth);
        setHeight(aHeight);

        return this;
    }

    /**
     * Gets the image's width.
     *
     * @return The image's width
     */
    @Override
    @JsonGetter(JsonKeys.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the image's height.
     *
     * @return The image's height
     */
    @Override
    @JsonGetter(JsonKeys.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Returns image content from its JSON representation.
     *
     * @param aJsonString A image content resource in string form
     * @return The image content
     */
    public static ImageContent from(final String aJsonString) {
        try {
            return JSON.getReader(ImageContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Sets the image width.
     *
     * @param aWidth The image's width
     * @return The image
     */
    @JsonSetter(JsonKeys.WIDTH)
    private ImageContent setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Sets the image height.
     *
     * @param aHeight The image's height
     * @return The image
     */
    @JsonSetter(JsonKeys.HEIGHT)
    private ImageContent setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

}
