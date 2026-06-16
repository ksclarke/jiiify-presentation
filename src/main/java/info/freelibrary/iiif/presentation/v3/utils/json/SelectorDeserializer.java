
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.EMPTY;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import info.freelibrary.iiif.presentation.v3.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.ImageApiSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.PointSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.SvgSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.VisualContentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * A deserializer for classes that implement the Selector interface.
 */
public class SelectorDeserializer extends StdDeserializer<Selector> {

    /** The SelectorDeserializer's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectorDeserializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for SelectorDeserializer. */
    @Serial
    private static final long serialVersionUID = 505267696639975498L;

    /**
     * Creates a new deserializer.
     */
    public SelectorDeserializer() {
        this(null);
    }

    /**
     * Creates a new deserializer.
     *
     * @param aClass A class
     */
    public SelectorDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Selector deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
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
                    return deserializeImageApiSelector(node);
                case "PointSelector":
                    return deserializePointSelector(node, aParser);
                case "SvgSelector":
                    return deserializeSvgSelector(node);
                default:
                    // Checkstyle wants us to have a default; this default falls through to the final `return null`
            }
        }

        return null; // Return nothing (which will be ignored)
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
        if (conformsToNode == null) {
            return new MediaFragmentSelector(aNode.get(JsonKeys.VALUE).asText());
        }

        try {
            final URI conformsTo = new URI(conformsToNode.asText());

            if (MediaFragmentSelector.MEDIA_FRAGMENT_SPECIFICATION_URI.equals(conformsTo)) {
                return new MediaFragmentSelector(aNode.get(JsonKeys.VALUE).asText());
            }

            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_061, conformsTo),
                    aParser.currentLocation());
        } catch (final URISyntaxException details) {
            throw new JsonMappingException(aParser, details.getMessage(), details);
        }
    }

    /**
     * Deserializes a JSON node that represents an ImageApiSelector.
     *
     * @param aNode A JSON node representing an ImageApiSelector
     * @return A new <code>ImageApiSelector</code>
     */
    private ImageApiSelector deserializeImageApiSelector(final JsonNode aNode) {
        final ImageApiSelector selector = new ImageApiSelector();

        getText(aNode, ImageApiSelector.SIZE).ifPresent(selector::setSize);
        getText(aNode, ImageApiSelector.REGION).ifPresent(selector::setRegion);
        getText(aNode, ImageApiSelector.FORMAT).ifPresent(selector::setFormat);
        getText(aNode, ImageApiSelector.QUALITY).ifPresent(selector::setQuality);
        getText(aNode, ImageApiSelector.ROTATION).ifPresent(selector::setRotation);

        return selector;
    }

    /**
     * Deserializes a PointSelector from the supplied JsonNode.
     *
     * @param aNode A JSON node
     * @param aParser A JSON parser
     * @return A media fragment selector
     * @throws JsonMappingException If there is trouble deserializing the fragment selector
     */
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY })
    private PointSelector deserializePointSelector(final JsonNode aNode, final JsonParser aParser)
            throws JsonMappingException {
        final int pointX = getInt(aNode, PointSelector.X_COORDINATE, -1);
        final int pointY = getInt(aNode, PointSelector.Y_COORDINATE, -1);
        final float pointT = getFloat(aNode, PointSelector.T_COORDINATE, -1F);
        final PointSelector pointSelector;

        // We could play this looser but let's be explicit with what we expect
        if (pointT == -1F && (pointX == -1 || pointY == -1)) {
            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_117), aParser.currentLocation());
        }

        if (pointT != -1F) {
            pointSelector = pointX != -1 && pointY != -1 ? new PointSelector(pointX, pointY, pointT)
                    : new PointSelector(pointT);
        } else if (pointX != -1 && pointY != -1) {
            pointSelector = new PointSelector(pointX, pointY);
        } else {
            pointSelector = null;
        }

        return pointSelector;
    }

    /**
     * Deserializes a JSON node that represents an SvgSelector.
     *
     * @param aNode A JSON node representing an SvgSelector
     * @return A new <code>SvgSelector</code>
     */
    private SvgSelector deserializeSvgSelector(final JsonNode aNode) {
        final Optional<String> value = getText(aNode, JsonKeys.VALUE);
        return value.map(node -> new SvgSelector(Jsoup.parse(node, EMPTY, Parser.xmlParser()))).orElse(null);
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
        }

        return node.floatValue();
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
        }

        return node.asInt(aDefaultValue);
    }

    /**
     * Gets the text from a node that may or may not exist in the JSON.
     *
     * @param aNode A parent node
     * @param aNodeName The name of an optional child node
     * @return The text from the node or the default value
     */
    private Optional<String> getText(final JsonNode aNode, final String aNodeName) {
        final JsonNode node = aNode.get(aNodeName);
        return node == null ? Optional.empty() : Optional.ofNullable(node.asText());
    }
}
