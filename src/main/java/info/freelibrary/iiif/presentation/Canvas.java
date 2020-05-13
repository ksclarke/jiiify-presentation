
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Label;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of
 * it. The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed
 * from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a
 * blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.WIDTH, Constants.HEIGHT,
    Constants.DURATION, Constants.THUMBNAIL, Constants.ITEMS })
public class Canvas extends AbstractCanvas<Canvas> {

    private Optional<AccompanyingCanvas> myAccompanyingCanvas;

    private Optional<PlaceholderCanvas> myPlaceholderCanvas;

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     */
    public Canvas(final URI aID) {
        super(aID);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     */
    public Canvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     */
    public Canvas(final URI aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID in string form
     * @param aLabel A canvas label in string form
     */
    public Canvas(final String aID, final String aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a blank new canvas.
     */
    Canvas() {
        super();
    }

    /**
     * Gets the placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(Constants.PLACEHOLDER_CANVAS)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return myPlaceholderCanvas;
    }

    /**
     * Sets the placeholder canvas
     *
     * @param aCanvas A placeholder canvas
     * @return This canvas
     */
    @JsonSetter(Constants.PLACEHOLDER_CANVAS)
    public Canvas setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = Optional.ofNullable(aCanvas);
        return this;
    }

    /**
     * Gets the accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(Constants.ACCOMPANYING_CANVAS)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return myAccompanyingCanvas;
    }

    /**
     * Sets the accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This canvas
     */
    @JsonSetter(Constants.ACCOMPANYING_CANVAS)
    public Canvas setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = Optional.ofNullable(aCanvas);
        return this;
    }

    /**
     * Returns a Canvas from its JSON representation.
     *
     * @param aJsonObject A canvas in JSON form
     * @return The canvas
     */
    public static Canvas fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Canvas.class);
    }

    /**
     * Returns a Canvas from its JSON representation.
     *
     * @param aJsonString A canvas in string form
     * @return The canvas
     */
    public static Canvas fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
