
package info.freelibrary.iiif.presentation.v3.services.image;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * An Image API size.
 */
@JsonPropertyOrder({ Constants.HEIGHT, Constants.WIDTH })
public class Size {

    /**
     * My size width.
     */
    private int myWidth;

    /**
     * My size height.
     */
    private int myHeight;

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
     * Creates a new Image API size.
     */
    public Size() {
        // This is intentionally left empty
    }

    /**
     * Sets a new size width.
     *
     * @param aWidth A size width
     * @return This size
     */
    @JsonSetter(Constants.WIDTH)
    public Size setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Gets the size width.
     *
     * @return The size width
     */
    @JsonGetter(Constants.WIDTH)
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
    @JsonSetter(Constants.HEIGHT)
    public Size setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Gets the size height.
     *
     * @return The size height
     */
    @JsonGetter(Constants.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

}
