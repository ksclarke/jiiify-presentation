
package info.freelibrary.iiif.presentation.v3.content;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Image content that can be associated with an annotation or set as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.HEIGHT,
    JsonKeys.WIDTH, JsonKeys.LANGUAGE, JsonKeys.SERVICE })
public class ImageContent extends AbstractContentResource<ImageContent>
        implements SpatialContentResource<ImageContent>, AnnotatedContentResource<ImageContent> {

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
        super(ResourceTypes.IMAGE, aURI, false, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Copy constructor for image content.
     *
     * @param aImageContent The image content to copy
     */
    public ImageContent(final ImageContent aImageContent) {
        this(aImageContent.getID());

        myHeight = aImageContent.getHeight();
        myWidth = aImageContent.getWidth();
        myLanguages = new ArrayList<>(aImageContent.getLanguages());
        aImageContent.getFormat().ifPresent(format -> myFormat = format);
        setBehaviors(aImageContent.getBehaviors());

        super.copyTo(aImageContent);
    }

    /**
     * Constructs an image content resource for Jackson's deserialization process.
     */
    private ImageContent() {
        super(ResourceTypes.IMAGE, ResourceBehavior.class);
    }

    @Override
    public ImageContent copy() {
        return new ImageContent(this);
    }

    @Override
    public boolean equals(final Object aObject) {
        final ImageContent other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (ImageContent) aObject;

        return Objects.equals(myHeight, other.myHeight) && Objects.equals(myWidth, other.myWidth) &&
                super.equals(other);
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
    public int hashCode() {
        return Objects.hash(super.hashCode(), myHeight, myWidth);
    }

    @Override
    @JsonIgnore
    public final ImageContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public final ImageContent setBehaviors(final List<Behavior> aBehaviorList) {
        final ImageContent imageContent;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            imageContent = super.setBehaviors(behaviorList);
        } else {
            imageContent = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return imageContent;
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
}
