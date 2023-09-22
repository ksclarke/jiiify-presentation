
package info.freelibrary.iiif.presentation.v3.utils.json; // NOPMD - ExcessiveImports

import static com.pivovarit.function.ThrowingBiFunction.sneaky;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.pivovarit.function.ThrowingBiFunction;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.CanvasContent;
import info.freelibrary.iiif.presentation.v3.ContentResource;
import info.freelibrary.iiif.presentation.v3.DatasetContent;
import info.freelibrary.iiif.presentation.v3.ImageContent;
import info.freelibrary.iiif.presentation.v3.ModelContent;
import info.freelibrary.iiif.presentation.v3.OtherContent;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.SoundContent;
import info.freelibrary.iiif.presentation.v3.SpecificResource;
import info.freelibrary.iiif.presentation.v3.TextContent;
import info.freelibrary.iiif.presentation.v3.TextualBody;
import info.freelibrary.iiif.presentation.v3.VideoContent;
import info.freelibrary.iiif.presentation.v3.annotations.AssessingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ClassifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.CommentingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.DescribingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.EditingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.HighlightingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.IdentifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.LinkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ModeratingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.Motivation;
import info.freelibrary.iiif.presentation.v3.annotations.Purpose;
import info.freelibrary.iiif.presentation.v3.annotations.QuestioningAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ReplyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.TaggingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.Target;
import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A deserializer for {@code WebAnnotation}(s).
 */
@SuppressWarnings({ PMD.GOD_CLASS, "PMD.GodClass" })
public class WebAnnotationDeserializer extends StdDeserializer<WebAnnotation> {

    /** The deserializer's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAnnotationDeserializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for the deserializer. */
    private static final long serialVersionUID = -6905362570704679943L;

    /**
     * Creates a new <code>WebAnnotationDeserializer</code>.
     */
    public WebAnnotationDeserializer() {
        super(WebAnnotation.class);
    }

    /**
     * Creates a new <code>WebAnnotationDeserializer</code>.
     *
     * @param aClass A class to deserialize
     */
    public WebAnnotationDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public WebAnnotation deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JacksonException {
        final ThrowingBiFunction<String, JsonNode, String, InputCoercionException> check = (aKey, aNode) -> {
            // Check that required value exists in the WebAnnotation's JSON serialization and fail if it doesn't
            if (aNode == null) {
                throw error(aParser, LOGGER.getMessage(MessageCodes.JPA_012, aKey));
            }

            // Check that the required value is a leaf node and fail if it isn't (otherwise `asText()` returns "")
            if (!aNode.isValueNode()) {
                throw error(aParser, LOGGER.getMessage(MessageCodes.JPA_129, aKey));
            }

            return aNode.asText();
        };

        final JsonNode node = aParser.getCodec().readTree(aParser);
        final String id = sneaky(check).apply(JsonKeys.ID, node.get(JsonKeys.ID));
        final String motivation = sneaky(check).apply(JsonKeys.MOTIVATION, node.get(JsonKeys.MOTIVATION));
        final WebAnnotation annotation = getAnnotation(id, motivation, node, aParser);
        final Optional<TimeMode> timeMode = getTimeMode(node.get(JsonKeys.TIMEMODE));
        final Optional<Label> label = getLabel(node.get(JsonKeys.LABEL));
        final JsonNode bodyNode = node.get(JsonKeys.BODY);

        annotation.setBody(getBody(bodyNode, sneaky(check)));
        annotation.setChoice(getChoice(bodyNode));

        if (timeMode.isPresent()) {
            annotation.setTimeMode(timeMode.get());
        }

        if (label.isPresent()) {
            annotation.setLabel(label.get());
        }

        return annotation;
    }

    /**
     * Gets an optional IIIF-style <code>Label</code> from the incoming JSOM.
     *
     * @param aLabel A label node
     * @return An optional label
     */
    private Optional<Label> getLabel(final JsonNode aLabel) {
        return aLabel == null ? Optional.empty() : Optional.of(JSON.convertValue(aLabel, Label.class));
    }

    /**
     * Gets an optional <code>TimeMode</code> from the incoming JSON.
     *
     * @param aTimeModeNode A JSON node representing a TimeMode
     * @return An optional <code>TimeMode</code> if found; else, an empty <code>Optional</code>
     */
    private Optional<TimeMode> getTimeMode(final JsonNode aTimeModeNode) {
        if (aTimeModeNode != null && aTimeModeNode.isValueNode()) {
            final Optional<TimeMode> timeMode = TimeMode.forLabel(aTimeModeNode.asText());

            if (timeMode.isEmpty()) {
                LOGGER.warn(MessageCodes.JPA_130, aTimeModeNode.asText());
            }

            return timeMode;
        }

        if (aTimeModeNode != null) {
            LOGGER.warn(MessageCodes.JPA_130, aTimeModeNode.toString());
        }

        return Optional.empty();
    }

    /**
     * Gets whether the incoming body node contains a choice between resources.
     *
     * @param aBodyNode A <code>JsonNode</code> representing an annotation body
     * @return True if the body contains a choice; else, false
     */
    private boolean getChoice(final JsonNode aBodyNode) {
        if (aBodyNode != null) {
            final JsonNode choiceNode = aBodyNode.get(JsonKeys.TYPE);

            if (choiceNode != null && choiceNode.isValueNode() && ResourceTypes.CHOICE.equals(choiceNode.asText())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets an annotation body from the supplied incoming JSON.
     *
     * @param aNode A <code>JsonNode</code> that represents an annotation body
     * @param aTypeCheck A function that checks that the required value exists
     * @return A list of annotation resources
     */
    private List<ContentResource<?>> getBody(final JsonNode aNode,
            final BiFunction<String, JsonNode, String> aTypeCheck) {
        final List<ContentResource<?>> resources = new ArrayList<>();

        if (aNode != null) {
            final JsonNode itemsNode = aNode.get(JsonKeys.ITEMS);

            // If the items node is empty, we expect to have to parse a single object or a string value
            if (itemsNode == null) {
                if (aNode.isObject()) {
                    resources.add(getResource(aTypeCheck.apply(JsonKeys.TYPE, aNode.get(JsonKeys.TYPE)), aNode));
                } else if (aNode.isValueNode() && ResourceTypes.RDF_NIL.equals(aNode.asText())) {
                    resources.add(null);
                } else if (aNode.isArray()) { // below added
                    aNode.elements().forEachRemaining(node -> {
                        resources.add(getResource(aTypeCheck.apply(JsonKeys.TYPE, node.get(JsonKeys.TYPE)), node));
                    });
                } // else warning?
            } else {
                itemsNode.forEach(node -> {
                    resources.add(getResource(aTypeCheck.apply(JsonKeys.TYPE, node.get(JsonKeys.TYPE)), node));
                });
            }
        }

        return resources;
    }

    /**
     * Gets an annotation from the Jackson parser.
     *
     * @param aID An ID for the annotation
     * @param aMotivation The annotation's motivation
     * @param aNode A current JSON node
     * @param aParser The JSON parser
     * @return A new <code>WebAnnotation</code>
     * @throws InputCoercionException If there is trouble parsing the incoming JSON
     * @throws IllegalArgumentException If the found motivation is not one of the expected ones
     */
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY, "PMD.CyclomaticComplexity" })
    private WebAnnotation getAnnotation(final String aID, final String aMotivation, final JsonNode aNode,
            final JsonParser aParser) throws InputCoercionException {
        final Optional<Purpose> purpose = Purpose.fromLabel(aMotivation);
        final JsonNode targetNode = aNode.get(JsonKeys.TARGET);

        if (purpose.isPresent()) {
            return switch (purpose.get()) {
                case ASSESSING -> new AssessingAnnotation(aID, getTarget(targetNode, aParser));
                case BOOKMARKING -> new BookmarkingAnnotation(aID, getTarget(targetNode, aParser));
                case CLASSIFYING -> new ClassifyingAnnotation(aID, getTarget(targetNode, aParser));
                case COMMENTING -> new CommentingAnnotation(aID, getTarget(targetNode, aParser));
                case DESCRIBING -> new DescribingAnnotation(aID, getTarget(targetNode, aParser));
                case EDITING -> new EditingAnnotation(aID, getTarget(targetNode, aParser));
                case HIGHLIGHTING -> new HighlightingAnnotation(aID, getTarget(targetNode, aParser));
                case IDENTIFYING -> new IdentifyingAnnotation(aID, getTarget(targetNode, aParser));
                case LINKING -> new LinkingAnnotation(aID, getTarget(targetNode, aParser));
                case MODERATING -> new ModeratingAnnotation(aID, getTarget(targetNode, aParser));
                case QUESTIONING -> new QuestioningAnnotation(aID, getTarget(targetNode, aParser));
                case REPLYING -> new ReplyingAnnotation(aID, getTarget(targetNode, aParser));
                case TAGGING -> new TaggingAnnotation(aID, getTarget(targetNode, aParser));
                default -> throw new IllegalArgumentException(aMotivation);
            };
        }

        return new WebAnnotation(aID, getTarget(targetNode, aParser)).setMotivation(Motivation.fromLabel(aMotivation));
    }

    /**
     * Gets a resource to add to the annotation's body.
     *
     * @param aType A type of content resource
     * @param aNode A JSON node representing the resource
     * @return A new content resource to add to the annotation's body
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    private ContentResource<?> getResource(final String aType, final JsonNode aNode) {
        return switch (aType) {
            case ResourceTypes.SOUND -> JSON.convertValue(aNode, SoundContent.class);
            case ResourceTypes.VIDEO -> JSON.convertValue(aNode, VideoContent.class);
            case ResourceTypes.IMAGE -> JSON.convertValue(aNode, ImageContent.class);
            case ResourceTypes.TEXT -> JSON.convertValue(aNode, TextContent.class);
            case ResourceTypes.DATASET -> JSON.convertValue(aNode, DatasetContent.class);
            case ResourceTypes.MODEL -> JSON.convertValue(aNode, ModelContent.class);
            case ResourceTypes.CANVAS -> JSON.convertValue(aNode, CanvasContent.class);
            case ResourceTypes.TEXTUAL_BODY -> JSON.convertValue(aNode, TextualBody.class);
            case ResourceTypes.SPECIFIC_RESOURCE -> JSON.convertValue(aNode, SpecificResource.class);
            default -> new OtherContent(JSON.valueToTree(aNode));
        };
    }

    /**
     * Gets the target for the current annotation.
     *
     * @param aNode A JSON node
     * @param aParser The JSON parser
     * @return The annotation's target
     * @throws InputCoercionException If there is trouble parsing the incoming JSON
     */
    private Target getTarget(final JsonNode aNode, final JsonParser aParser) throws InputCoercionException {
        if (aNode == null) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_132);
            throw new InputCoercionException(aParser, message, aParser.getCurrentToken(), WebAnnotation.class);
        }

        // If our target is a value node, it should be a URI
        if (aNode.isValueNode()) {
            return new Target(aNode.asText());
        }

        // If our target is not a value node, it should be a specific resource
        try {
            return new Target((SpecificResource) JSON.getReader(SpecificResource.class).readTree(aParser));
        } catch (final IOException details) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_131, aNode.toString());
            throw (InputCoercionException) new InputCoercionException(aParser, message, aParser.getCurrentToken(),
                    WebAnnotation.class).fillInStackTrace();
        }
    }

    /**
     * Assists with keeping error throwing code in the main flow of things concise.
     *
     * @param aParser A JSON parser
     * @param aMessage An error message
     * @return A new {@code InputCoercianException} with the supplied details
     */
    private InputCoercionException error(final JsonParser aParser, final String aMessage) {
        return new InputCoercionException(aParser, aMessage, aParser.getCurrentToken(), WebAnnotation.class);
    }
}
