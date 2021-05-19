
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

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.URIs;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A deserializer for classes that implement the Selector interface.
 */
class SelectorDeserializer extends StdDeserializer<Selector> {

    /**
     * The SelectorDeserializer's logger.
     */
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
    public Selector deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final JsonNode typeNode = node.get(JsonKeys.TYPE);

        if (typeNode != null) {
            switch (typeNode.asText()) {
                case "AudioContentSelector":
                    return new AudioContentSelector();
                case "VisualContentSelector":
                    return new VisualContentSelector();
                case "FragmentSelector":
                    return deserializeFragmentSelector(node, aParser);
                case "ImageApiSelector":
                    final String size = getText(node, ImageApiSelector.SIZE, ImageApiSelector.DEFAULT_SIZE);
                    final String region = getText(node, ImageApiSelector.REGION, ImageApiSelector.DEFAULT_REGION);
                    final String format = getText(node, ImageApiSelector.FORMAT, ImageApiSelector.DEFAULT_FORMAT);
                    final String quality = getText(node, ImageApiSelector.QUALITY, ImageApiSelector.DEFAULT_QUALITY);
                    final String rotation = getText(node, ImageApiSelector.ROTATION, ImageApiSelector.DEFAULT_ROTATION);

                    return new ImageApiSelector(region, size, rotation, quality, format);
                case "PointSelector":
                    return deserializePointSelector(node, aParser);
                default:
                    // Checkstyle wants us to have a default; this default falls through to the final `return null`
            }
        }

        return null; // Return nothing (which will be ignored)
    }

    /**
     * Deserializes a PointSelector from the supplied JsonNode.
     *
     * @param aNode A JSON node
     * @param aParser A JSON parser
     * @return A media fragment selector
     * @throws JsonMappingException If there is trouble deserializing the fragment selector
     */
    private PointSelector deserializePointSelector(final JsonNode aNode, final JsonParser aParser)
            throws JsonMappingException {
        final int pointX = getInt(aNode, PointSelector.X_COORDINATE, -1);
        final int pointY = getInt(aNode, PointSelector.Y_COORDINATE, -1);
        final float pointT = getFloat(aNode, PointSelector.T_COORDINATE, -1F);
        final PointSelector pointSelector;

        // We could play this looser but let's be explicit with what we expect
        if (pointT == -1F && (pointX == -1 || pointY == -1)) {
            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_117),
                    aParser.getCurrentLocation());
        }

        // If we have everything we're expecting, let's go ahead and build it
        pointSelector = new PointSelector();

        if (pointT != -1F) {
            pointSelector.setSeconds(pointT);
        }

        if (pointX != -1 && pointY != -1) {
            pointSelector.setX(pointX);
            pointSelector.setY(pointY);
        }

        return pointSelector;
    }

    /**
     * Deserializes a MediaFragmentSelector from the supplied JsonNode.
     *
     * @param aNode A JSON node
     * @param aParser A JSON parser
     * @return A media fragment selector
     * @throws JsonMappingException If there is trouble deserializing the fragment selector
     */
    private MediaFragmentSelector deserializeFragmentSelector(final JsonNode aNode, final JsonParser aParser)
            throws JsonMappingException {
        final JsonNode conformsToNode = aNode.get(JsonKeys.CONFORMS_TO);

        // Fragment selectors SHOULD have a conformsTo but aren't required to have one
        if (conformsToNode != null) {
            final String conformsToString = conformsToNode.asText();

            try {
                final URI conformsTo = new URI(conformsToString);

                if (URIs.MEDIA_FRAGMENT_SPECIFICATION_URI.equals(conformsTo)) {
                    return new MediaFragmentSelector(aNode.get(JsonKeys.VALUE).asText());
                } else {
                    throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_061, conformsTo),
                            aParser.getCurrentLocation());
                }
            } catch (final URISyntaxException details) {
                throw new JsonMappingException(aParser, details.getMessage(), details);
            }
        } else {
            return new MediaFragmentSelector(aNode.get(JsonKeys.VALUE).asText());
        }
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
    private float getFloat(final JsonNode aNode, final String aNodeName, final Number aDefaultValue) {
        final JsonNode node = aNode.get(aNodeName);

        if (node == null) {
            return aDefaultValue.floatValue();
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
