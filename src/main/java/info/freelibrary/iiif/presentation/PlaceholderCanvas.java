
package info.freelibrary.iiif.presentation;

import java.net.URI;

import info.freelibrary.iiif.presentation.properties.Label;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A single Canvas that provides additional content for use before the main content of the resource that has the
 * placeholderCanvas property is rendered, or as an advertisement or stand-in for that content. Examples include
 * images, text and sound standing in for video content before the user initiates playback; or a film poster to
 * attract user attention. The content provided by placeholderCanvas differs from a thumbnail: a client might use
 * thumbnail to summarize and navigate multiple resources, then show content from placeholderCanvas as part of the
 * initial presentation of a single resource. A placeholder Canvas is likely to have different dimensions to those of
 * the Canvas(es) of the resource that has the placeholderCanvas property.
 */
public class PlaceholderCanvas extends AbstractCanvas<PlaceholderCanvas> {

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A placeholder canvas ID
     */
    public PlaceholderCanvas(final URI aID) {
        super(aID);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A placeholder canvas ID
     */
    public PlaceholderCanvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A placeholder canvas ID
     * @param aLabel A placeholder canvas label
     */
    public PlaceholderCanvas(final URI aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A placeholder canvas ID in string form
     * @param aLabel A placeholder canvas label in string form
     */
    public PlaceholderCanvas(final String aID, final String aLabel) {
        super(aID, aLabel);
    }

    @SuppressWarnings("unused")
    private PlaceholderCanvas() {
        super();
    }

    /**
     * Returns a PlaceholderCanvas from its JSON representation.
     *
     * @param aJsonObject A placeholder canvas in JSON form
     * @return The placeholder canvas
     */
    public static PlaceholderCanvas fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), PlaceholderCanvas.class);
    }

    /**
     * Returns a PlaceholderCanvas from its JSON representation.
     *
     * @param aJsonString A placeholder canvas in string form
     * @return The placeholder canvas
     */
    public static PlaceholderCanvas fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
