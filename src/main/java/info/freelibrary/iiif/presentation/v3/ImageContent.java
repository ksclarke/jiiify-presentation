
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
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
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class ImageContent extends AbstractContentResource<ImageContent>
        implements SpatialContentResource, AnnotatedContentResource<ImageContent>, Resource<ImageContent> {

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
        super(ResourceTypes.IMAGE, aURI, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Constructs an image content resource for Jackson's deserialization process.
     */
    private ImageContent() {
        super(ResourceTypes.IMAGE, ResourceBehavior.class);
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
        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
        }

        return super.setBehaviors(aBehaviorList);
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
