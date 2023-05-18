
package info.freelibrary.iiif.presentation.v3;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * Image content that can be associated with an annotation or set as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.HEIGHT,
    JsonKeys.WIDTH, JsonKeys.LANGUAGE, JsonKeys.SERVICE })
public class ImageContent extends AbstractContentResource<ImageContent>
        implements ContentResource<ImageContent>, SpatialContentResource<ImageContent>, Resource<ImageContent> {

    /** The class of media type this content represents. */
    private static final String MEDIA_TYPE_CLASS = "image";

    /** The image content's height. */
    private int myHeight;

    /** The image content's width. */
    private int myWidth;

    /**
     * Creates image content with the supplied ID.
     *
     * @param aURI An image content ID
     */
    public ImageContent(final String aURI) {
        super(ResourceTypes.IMAGE, aURI, ResourceBehavior.class);
        setFormat(MediaType.parse(aURI, MEDIA_TYPE_CLASS).orElse(null));
    }

    /**
     * Constructs an image content resource for Jackson's deserialization process.
     */
    private ImageContent() {
        super(ResourceTypes.IMAGE, ResourceBehavior.class);
    }

    /**
     * Gets the media type format of the content resource.
     *
     * @return The media type format of the content resource
     */
    @Override
    public Optional<MediaType> getFormat() {
        return super.getFormat();
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

    @Override
    @JsonIgnore
    public ImageContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public ImageContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ResourceBehavior.class, this.getClass());
        }

        return (ImageContent) super.setBehaviors(aBehaviorList);
    }

    @Override
    public final ImageContent setFormat(final MediaType aMediaType) {
        return (ImageContent) super.setFormat(aMediaType);
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
    public ImageContent setID(final String aID) {
        return (ImageContent) super.setID(aID);
    }

    @Override
    public ImageContent setLabel(final Label aLabel) {
        return (ImageContent) super.setLabel(aLabel);
    }

    @Override
    public ImageContent setLanguages(final String... aLangArray) {
        return (ImageContent) super.setLanguages(aLangArray);
    }

    @Override
    public ImageContent setMetadata(final List<Metadata> aMetadataList) {
        return (ImageContent) super.setMetadata(aMetadataList);
    }

    @Override
    public ImageContent setMetadata(final Metadata... aMetadataArray) {
        return (ImageContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public ImageContent setPartOfs(final List<PartOf> aPartOfList) {
        return (ImageContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public ImageContent setPartOfs(final PartOf... aPartOfArray) {
        return (ImageContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    @JsonIgnore
    public ImageContent setProviders(final List<Provider> aProviderList) {
        return (ImageContent) super.setProviders(aProviderList);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public ImageContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    public ImageContent setRenderings(final List<Rendering> aRenderingList) {
        return (ImageContent) super.setRenderings(aRenderingList);
    }

    @Override
    public ImageContent setRenderings(final Rendering... aRenderingArray) {
        return (ImageContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public ImageContent setRequiredStatement(final RequiredStatement aStatement) {
        return (ImageContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public ImageContent setRights(final String aRights) {
        return (ImageContent) super.setRights(aRights);
    }

    @Override
    public ImageContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (ImageContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public ImageContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (ImageContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public ImageContent setServices(final List<Service<?>> aServiceList) {
        return (ImageContent) super.setServices(aServiceList);
    }

    @Override
    @SafeVarargs
    public final ImageContent setServices(final Service<?>... aServiceArray) {
        return (ImageContent) super.setServices(aServiceArray);
    }

    @Override
    public ImageContent setSummary(final Summary aSummary) {
        return (ImageContent) super.setSummary(aSummary);
    }

    @Override
    public ImageContent setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (ImageContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public ImageContent setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (ImageContent) super.setThumbnails(aThumbnailList);
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
     * Returns image content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of an image content resource
     * @return The image content
     * @throws JsonParsingException If the image content cannot be deserialized from the supplied JSON
     */
    static ImageContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(ImageContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
