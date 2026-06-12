
package info.freelibrary.iiif.presentation.v3.services.image;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An Image API size.
 */
@JsonPropertyOrder({ JsonKeys.HEIGHT, JsonKeys.WIDTH })
public class Size {

    /**
     * My size height.
     */
    private int myHeight;

    /**
     * My size width.
     */
    private int myWidth;

    /**
     * Creates a new Image API size.
     */
    public Size() {
        // This is intentionally left empty
    }

    /**
     * Creates a new Image API size.
     *
     * @param aSize A size to copy
     */
    public Size(final Size aSize) {
        myHeight = aSize.myHeight;
        myWidth = aSize.myWidth;
    }

    /**
     * Creates a new Image API size from the supplied width and height.
     *
     * @param aWidth A size width
     * @param aHeight A size height
     */
    public Size(final int aWidth, final int aHeight) {
        myWidth = aWidth;
        myHeight = aHeight;
    }

    /**
     * Copies this size.
     *
     * @return A copy of this size
     */
    public Size copy() {
        return new Size(this);
    }

    /**
     * Gets the size height.
     *
     * @return The size height
     */
    @JsonGetter(JsonKeys.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the size width.
     *
     * @return The size width
     */
    @JsonGetter(JsonKeys.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Sets the size height.
     *
     * @param aHeight A size height
     * @return This size
     */
    @JsonSetter(JsonKeys.HEIGHT)
    public Size setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Sets a new size width.
     *
     * @param aWidth A size width
     * @return This size
     */
    @JsonSetter(JsonKeys.WIDTH)
    public Size setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

}
