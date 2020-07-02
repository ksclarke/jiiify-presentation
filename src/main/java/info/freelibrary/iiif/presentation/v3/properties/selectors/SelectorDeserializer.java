
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A deserializer for classes that implement the Selector interface.
 */
class SelectorDeserializer extends StdDeserializer<Selector> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectorDeserializer.class, MessageCodes.BUNDLE);

    /**
     * The <code>serialVersionUID</code> for SelectorDeserializer.
     */
    private static final long serialVersionUID = 505267696639975498L;

    /**
     * Creates a new deserializer.
     */
    SelectorDeserializer() {
        this(null);
    }

    /**
     * Creates a new deserializer.
     *
     * @param aClass A class
     */
    SelectorDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Selector deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final JsonNode typeNode = node.get(Constants.TYPE);

        if (typeNode != null) {
            switch (typeNode.asText()) {
                case "AudioContentSelector":
                    return new AudioContentSelector();
                case "VisualContentSelector":
                    return new VisualContentSelector();
                case "FragmentSelector":
                    final String fragment = node.get(Constants.VALUE).asText();
                    final String conformsToString = node.get(Constants.CONFORMS_TO).asText();
                    final URI conformsTo;

                    try {
                        conformsTo = new URI(conformsToString);
                    } catch (final URISyntaxException details) {
                        throw new JsonMappingException(aParser, details.getMessage(), details);
                    }

                    if (Constants.MEDIA_FRAGMENT_SPECIFICATION_URI.equals(conformsTo)) {
                        return new MediaFragmentSelector(fragment);
                    } else {
                        throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_061, conformsTo),
                                aParser.getCurrentLocation());
                    }
                case "ImageApiSelector":
                    final String size = getText(node, ImageApiSelector.SIZE, ImageApiSelector.DEFAULT_SIZE);
                    final String region = getText(node, ImageApiSelector.REGION, ImageApiSelector.DEFAULT_REGION);
                    final String format = getText(node, ImageApiSelector.FORMAT, ImageApiSelector.DEFAULT_FORMAT);
                    final String quality = getText(node, ImageApiSelector.QUALITY, ImageApiSelector.DEFAULT_QUALITY);
                    final String rotation = getText(node, ImageApiSelector.ROTATION, ImageApiSelector.DEFAULT_ROTATION);

                    return new ImageApiSelector(region, size, rotation, quality, format);
                case "PointSelector":
                    final int pointX = getInt(node, PointSelector.X_COORDINATE, -1);
                    final int pointY = getInt(node, PointSelector.Y_COORDINATE, -1);
                    final float pointT = getFloat(node, PointSelector.T_COORDINATE, -1F);

                    // We could play this looser and rely on the class, but let's be explicit w/r/t what we expect
                    if (pointX == -1 && pointY == -1 && pointT != -1F) {
                        return new PointSelector(pointT);
                    } else if (pointX != -1 && pointY != -1 && pointT == -1F) {
                        return new PointSelector(pointX, pointY);
                    } else if (pointX != -1 && pointY != -1 && pointT != -1F) {
                        return new PointSelector(pointX, pointY, pointT);
                    } // else, return null (no break needed)
                default:
                    // Checkstyle wants us to have a default; it falls through to return null
            }
        }

        return null; // Return nothing (which will be ignored) versus throwing an exception?
    }

    /**
     * Gets an integer from a node that may or may not exist in the JSON.
     *
     * @param aNode A parent node
     * @param aNodeName The name of an optional child node
     * @param aDefaultValue The default string value for missing nodes
     * @return The text from the node or the default value
     */
    private int getInt(final JsonNode aNode, final String aNodeName, final int aDefaultValue) {
        final JsonNode node = aNode.get(aNodeName);

        if (node == null) {
            return aDefaultValue;
        } else {
            return node.asInt(aDefaultValue);
        }
    }

    /**
     * Gets a float from a node that may or may not exist in the JSON.
     *
     * @param aNode A parent node
     * @param aNodeName The name of an optional child node
     * @param aDefaultValue The default string value for missing nodes
     * @return The text from the node or the default value
     */
    private float getFloat(final JsonNode aNode, final String aNodeName, final float aDefaultValue) {
        final JsonNode node = aNode.get(aNodeName);

        if (node == null) {
            return aDefaultValue;
        } else {
            return node.floatValue();
        }
    }

    /**
     * Gets the text from a node that may or may not exist in the JSON.
     *
     * @param aNode A parent node
     * @param aNodeName The name of an optional child node
     * @param aDefaultValue The default string value for missing nodes
     * @return The text from the node or the default value
     */
    private String getText(final JsonNode aNode, final String aNodeName, final String aDefaultValue) {
        final JsonNode node = aNode.get(aNodeName);

        if (node == null) {
            return aDefaultValue;
        } else {
            return node.asText(aDefaultValue);
        }
    }
}
