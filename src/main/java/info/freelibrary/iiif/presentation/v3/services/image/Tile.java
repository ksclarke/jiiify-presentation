
package info.freelibrary.iiif.presentation.v3.services.image;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An Image API tile.
 */
@JsonPropertyOrder({ JsonKeys.HEIGHT, JsonKeys.WIDTH, ImageAPI.SCALE_FACTORS })
public class Tile {

    /**
     * My tile height.
     */
    private int myHeight;

    /**
     * My tile width.
     */
    private int myWidth;

    /**
     * My scale factors.
     */
    private int[] myScaleFactors;

    /**
     * Creates a new Image API tile.
     *
     * @param aWidth A tile width
     * @param aHeight A tile height
     * @param aScaleFactorsArray A tile's scale factors
     */
    public Tile(final int aWidth, final int aHeight, final int... aScaleFactorsArray) {
        myScaleFactors = Arrays.copyOf(aScaleFactorsArray, aScaleFactorsArray.length);
        myHeight = aHeight;
        myWidth = aWidth;
    }

    /**
     * Creates a new Image API tile.
     */
    public Tile() {
        // This constructor is intentionally empty
    }

    /**
     * Sets the tile's height.
     *
     * @param aHeight A tile height
     * @return This tile
     */
    @JsonSetter(JsonKeys.HEIGHT)
    public Tile setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Gets the tile's height.
     *
     * @return The tile height
     */
    @JsonGetter(JsonKeys.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the tile's width.
     *
     * @param aWidth A tile width
     * @return This tile
     */
    @JsonSetter(JsonKeys.WIDTH)
    public Tile setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Gets the tile's width.
     *
     * @return A tile width
     */
    @JsonGetter(JsonKeys.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Sets the tile's scale factors.
     *
     * @param aScaleFactorsArray A tile's scale factors
     * @return This tile
     */
    @JsonSetter(ImageAPI.SCALE_FACTORS)
    public Tile setScaleFactors(final int... aScaleFactorsArray) {
        myScaleFactors = Arrays.copyOf(aScaleFactorsArray, aScaleFactorsArray.length);
        return this;
    }

    /**
     * Gets the tile's scale factors.
     *
     * @return The tile's scale factors
     */
    @JsonGetter(ImageAPI.SCALE_FACTORS)
    @JsonInclude(Include.NON_EMPTY)
    public int[] getScaleFactors() {
        return Arrays.copyOf(myScaleFactors, myScaleFactors.length);
    }
}
