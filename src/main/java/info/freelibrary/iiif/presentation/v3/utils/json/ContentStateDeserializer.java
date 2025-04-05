
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;
import static info.freelibrary.util.ThrowingBiFunction.unwrap;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.ThrowingBiFunction;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.ContentStateAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.Motivation;
import info.freelibrary.iiif.presentation.v3.annotation.targets.CanvasTarget;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.content.CanvasContent;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.content.DatasetContent;
import info.freelibrary.iiif.presentation.v3.content.ImageContent;
import info.freelibrary.iiif.presentation.v3.content.ModelContent;
import info.freelibrary.iiif.presentation.v3.content.OtherContent;
import info.freelibrary.iiif.presentation.v3.content.SoundContent;
import info.freelibrary.iiif.presentation.v3.content.TextContent;
import info.freelibrary.iiif.presentation.v3.content.TextualBody;
import info.freelibrary.iiif.presentation.v3.content.VideoContent;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A deserializer for {@code ContentState}(s).
 */
@SuppressWarnings({ PMD.GOD_CLASS, PMD.EXCESSIVE_IMPORTS })
public class ContentStateDeserializer extends StdDeserializer<ContentStateAnnotation> {

    /** The deserializer's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentStateDeserializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for the deserializer. */
    private static final long serialVersionUID = -6905362570704679943L;

    /**
     * Creates a new <code>ContentStateDeserializer</code>.
     */
    public ContentStateDeserializer() {
        super(ContentStateAnnotation.class);
    }

    /**
     * Creates a new <code>ContentStateDeserializer</code>.
     *
     * @param aClass A class to deserialize
     */
    public ContentStateDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, PMD.N_PATH_COMPLEXITY })
    public ContentStateAnnotation deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException {
        final ThrowingBiFunction<String, JsonNode, String, JsonMappingException> check = (aKey, aNode) -> {
            // Check that required value exists in the ContentState's JSON serialization and fail if it doesn't
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
        final JsonNode contexts = node.get(JsonKeys.CONTEXT);
        final JsonNode motivationNode = node.get(JsonKeys.MOTIVATION);
        final String id = unwrap(check).applyThrows(JsonKeys.ID, node.get(JsonKeys.ID));
        final Optional<TimeMode> timeMode = getTimeMode(node.get(JsonKeys.TIMEMODE));
        final Optional<Label> label = getLabel(node.get(JsonKeys.LABEL));
        final JsonNode bodyNode = node.get(JsonKeys.BODY);
        final ContentStateAnnotation annotation;
        final String motivation;

        // We can read an array of motivations, but just take the first one.
        if (motivationNode != null && motivationNode.isArray()) {
            motivation = motivationNode.get(0).asText();
        } else if (motivationNode != null && motivationNode.isTextual()) {
            motivation = motivationNode.asText();
        } else {
            motivation = null;
        }

        annotation = getAnnotation(id, motivation, node, aParser);
        annotation.setBody(getBody(bodyNode, unwrap(check)));
        annotation.setChoice(getChoice(bodyNode));

        if (contexts != null) {
            if (contexts.size() == SINGLE_INSTANCE) {
                annotation.getContexts().add(URI.create(node.get(0).asText()));
            } else {
                contexts.forEach(context -> annotation.getContexts().add(URI.create(context.asText())));
            }
        }

        if (timeMode.isPresent()) {
            annotation.setTimeMode(timeMode.get());
        }

        if (label.isPresent()) {
            annotation.setLabel(label.get());
        }

        return annotation;
    }

    /**
     * Assists with keeping error throwing code in the main flow of things concise.
     *
     * @param aParser A JSON parser
     * @param aMessage An error message
     * @return A new {@code JsonMappingException} with the supplied details
     */
    private JsonMappingException error(final JsonParser aParser, final String aMessage) {
        return new JsonMappingException(aParser, aMessage, aParser.currentTokenLocation());
    }

    /**
     * Gets an annotation from the Jackson parser.
     *
     * @param aID An ID for the annotation
     * @param aMotivation The annotation's motivation
     * @param aNode A current JSON node
     * @param aParser The JSON parser
     * @return A new <code>ContentState</code>
     * @throws JsonMappingException If there is trouble mapping the incoming JSON
     * @throws IllegalArgumentException If the found motivation is not one of the expected ones
     */
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY })
    private ContentStateAnnotation getAnnotation(final String aID, final String aMotivation, final JsonNode aNode,
            final JsonParser aParser) throws JsonMappingException {
        final JsonNode targetsNode = aNode.get(JsonKeys.TARGET);
        return aMotivation != null
                ? new ContentStateAnnotation(aID, getTargets(targetsNode, aParser))
                        .setMotivation(Motivation.fromLabel(aMotivation))
                : new ContentStateAnnotation(aID, getTargets(targetsNode, aParser));
    }

    /**
     * Gets an annotation body from the supplied incoming JSON.
     *
     * @param aNode A <code>JsonNode</code> that represents an annotation body
     * @param aTypeCheck A function that checks that the required value exists
     * @return A list of annotation resources
     */
    private List<ContentResource> getBody(final JsonNode aNode, final BiFunction<String, JsonNode, String> aTypeCheck) {
        final List<ContentResource> resources = new ArrayList<>();

        if (aNode != null) {
            final JsonNode itemsNode = aNode.get(JsonKeys.ITEMS);

            // If the items node is empty, we expect to have to parse a single object or a string value
            if (itemsNode == null) {
                if (aNode.isObject()) {
                    resources.add(getResource(aTypeCheck.apply(JsonKeys.TYPE, aNode.get(JsonKeys.TYPE)), aNode));
                } else if (aNode.isValueNode() && ResourceTypes.RDF_NIL.equals(aNode.asText())) {
                    resources.add(null);
                } else if (aNode.isArray()) {
                    aNode.elements().forEachRemaining(node -> resources
                            .add(getResource(aTypeCheck.apply(JsonKeys.TYPE, node.get(JsonKeys.TYPE)), node)));
                } // else warning?
            } else {
                itemsNode.forEach(node -> resources
                        .add(getResource(aTypeCheck.apply(JsonKeys.TYPE, node.get(JsonKeys.TYPE)), node)));
            }
        }

        return resources;
    }

    /**
     * Gets a CanvasTarget from a supplied JsonNode.
     *
     * @param aIdNode A JsonNode representing the CanvasTarget's ID
     * @param aPartOfNode A partOf node from the incoming JSON
     * @return A newly parsed CanvasTarget
     */
    @SuppressWarnings({ PMD.USE_DIAMOND_OPERATOR })
    private CanvasTarget getCanvasTarget(final JsonNode aIdNode, final JsonNode aPartOfNode) {
        if (aPartOfNode == null) {
            return new CanvasTarget(aIdNode.asText());
        }

        if (aPartOfNode.isArray()) {
            return new CanvasTarget(aIdNode.asText(),
                    JSON.convertValue(aPartOfNode, new TypeReference<List<PartOf>>() {}));
        }

        return new CanvasTarget(aIdNode.asText(), JSON.convertValue(aPartOfNode, PartOf.class));
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
     * Gets an optional IIIF-style <code>Label</code> from the incoming JSOM.
     *
     * @param aLabel A label node
     * @return An optional label
     */
    private Optional<Label> getLabel(final JsonNode aLabel) {
        return aLabel == null ? Optional.empty() : Optional.of(JSON.convertValue(aLabel, Label.class));
    }

    /**
     * Gets a resource to add to the annotation's body.
     *
     * @param aType A type of content resource
     * @param aNode A JSON node representing the resource
     * @return A new content resource to add to the annotation's body
     */
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY })
    private ContentResource getResource(final String aType, final JsonNode aNode) {
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
     * @throws JsonMappingException If there is trouble mapping the incoming JSON
     */
    private Target getTarget(final JsonNode aNode, final JsonParser aParser) throws JsonMappingException {
        final JsonNode typeNode;

        if (aNode == null) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_132);
            throw new JsonMappingException(aParser, message, aParser.currentTokenLocation());
        }

        // If our target is a value node, it should be a URI
        if (aNode.isValueNode()) {
            return new Target(aNode.asText());
        }

        if ((typeNode = aNode.get(JsonKeys.TYPE)) != null) {
            final JsonNode idNode = aNode.get(JsonKeys.ID);
            final JsonNode partOfNode = aNode.get(JsonKeys.PART_OF);

            return switch (typeNode.asText()) {
                case ResourceTypes.SPECIFIC_RESOURCE -> JSON.convertValue(aNode, SpecificResource.class);
                case ResourceTypes.CANVAS -> getCanvasTarget(idNode, partOfNode);
                default -> throw error(aParser, LOGGER.getMessage(MessageCodes.JPA_153));
            };
        }

        // If our target is not a value node, it should be a specific resource
        return JSON.convertValue(aNode, SpecificResource.class);
    }

    /**
     * Gets an annotation target from the supplied incoming JSON.
     *
     * @param aNode A <code>JsonNode</code> that represents an annotation target
     * @param aJsonParser A JSON parser
     * @return A list of annotation targets
     * @throws JsonMappingException If there is trouble mapping the incoming JSON
     */
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, Sonar.COGNITIVE_COMPLEXITY })
    private List<Target> getTargets(final JsonNode aNode, final JsonParser aJsonParser) throws JsonMappingException {
        final List<Target> targets = new ArrayList<>();

        if (aNode != null) {
            final JsonNode targetsNode = aNode.get(JsonKeys.TARGET);

            // If the items node is empty, we expect to have to parse a single object or a string value
            if (targetsNode == null) {
                if (aNode.isObject() || !aNode.isArray()) {
                    targets.add(getTarget(aNode, aJsonParser));
                } else {
                    final Iterator<JsonNode> iterator = aNode.elements();

                    while (iterator.hasNext()) {
                        targets.add(getTarget(iterator.next(), aJsonParser));
                    }
                }
            } else {
                final Iterator<JsonNode> iterator = targetsNode.elements();

                while (iterator.hasNext()) {
                    targets.add(getTarget(iterator.next(), aJsonParser));
                }
            }
        }

        return targets;
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

            if (timeMode.isEmpty() && LOGGER.isWarnEnabled()) {
                LOGGER.warn(MessageCodes.JPA_130, aTimeModeNode.asText());
            }

            return timeMode;
        }

        if (aTimeModeNode != null && LOGGER.isWarnEnabled()) {
            LOGGER.warn(MessageCodes.JPA_130, aTimeModeNode.toString());
        }

        return Optional.empty();
    }
}
