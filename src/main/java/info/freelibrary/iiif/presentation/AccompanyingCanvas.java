
package info.freelibrary.iiif.presentation;

import java.net.URI;

import info.freelibrary.iiif.presentation.properties.Label;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A single Canvas that provides additional content for use while rendering the resource that has the
 * accompanyingCanvas property. Examples include an image to show while a duration-only Canvas is playing audio; or
 * background audio to play while a user is navigating an image-only Manifest.
 */
public class AccompanyingCanvas extends AbstractCanvas<AccompanyingCanvas> {

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID
     */
    public AccompanyingCanvas(final URI aID) {
        super(aID);
    }

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID
     */
    public AccompanyingCanvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID
     * @param aLabel An accompanying canvas label
     */
    public AccompanyingCanvas(final URI aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID in string form
     * @param aLabel A accompanying canvas label in string form
     */
    public AccompanyingCanvas(final String aID, final String aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new accompanying canvas.
     */
    @SuppressWarnings("unused")
    private AccompanyingCanvas() {
        super();
    }

    /**
     * Returns an AccompanyingCanvas from its JSON representation.
     *
     * @param aJsonObject An accompanying canvas in JSON form
     * @return The accompanying canvas
     */
    public static AccompanyingCanvas fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), AccompanyingCanvas.class);
    }

    /**
     * Returns an AccompanyingCanvas from its JSON representation.
     *
     * @param aJsonString An accompanying canvas in string form
     * @return The accompanying canvas
     */
    public static AccompanyingCanvas fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
