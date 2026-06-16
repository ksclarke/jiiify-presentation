
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.EMPTY;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.Target;
import info.freelibrary.iiif.presentation.v3.content.CanvasContent;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.content.DatasetContent;
import info.freelibrary.iiif.presentation.v3.content.ImageContent;
import info.freelibrary.iiif.presentation.v3.content.ModelContent;
import info.freelibrary.iiif.presentation.v3.content.SoundContent;
import info.freelibrary.iiif.presentation.v3.content.TextContent;
import info.freelibrary.iiif.presentation.v3.content.TextualBody;
import info.freelibrary.iiif.presentation.v3.content.VideoContent;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Jackson deserializer for annotation targets.
 */
@SuppressWarnings({ PMD.EXCESSIVE_IMPORTS })
public class AnnotationTargetDeserializer extends StdDeserializer<Target> {

    /** A logger for the <code>AnnotationTargetDeserializer</code>. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AnnotationTargetDeserializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for the <code>AnnotationTargetDeserializer</code>. */
    @Serial
    private static final long serialVersionUID = -6033073058449033461L;

    /**
     * Creates a new annotation target deserializer.
     */
    AnnotationTargetDeserializer() {
        this(Target.class);
    }

    /**
     * Creates a new annotation target deserializer.
     *
     * @param aClass A class to be deserialized
     */
    AnnotationTargetDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, PMD.CYCLOMATIC_COMPLEXITY, Sonar.COGNITIVE_COMPLEXITY,
        PMD.AVOID_DEEPLY_NESTED_IF_STMTS })
    public Target deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        final JsonNode currentNode = aParser.getCodec().readTree(aParser);
        final JsonNode typeNode = currentNode.get(JsonKeys.TYPE);
        final JsonNode idNode = currentNode.get(JsonKeys.ID);
        final Target target;

        if (typeNode != null && ResourceTypes.SPECIFIC_RESOURCE.equals(typeNode.asText())) {
            final JsonNode sourceNode = currentNode.get(JsonKeys.SOURCE);
            final JsonNode selectorNode = currentNode.get(JsonKeys.SELECTOR);
            final JsonNode styleClassNode = currentNode.get(JsonKeys.STYLE_CLASS);
            final SpecificResource specificResource;
            final ContentResource<?> source;
            final Selector selector;

            if (sourceNode == null) {
                throw new JsonMappingException(aParser,
                        LOGGER.getMessage(MessageCodes.JPA_131, currentNode.toPrettyString()),
                        aParser.currentLocation());
            } else if (sourceNode.isTextual()) {
                source = new TextContent(sourceNode.asText());
            } else {
                final JsonNode sourceTypeNode = sourceNode.get(JsonKeys.TYPE);
                final String sourceType = sourceTypeNode != null ? sourceTypeNode.asText() : EMPTY;
                final String json = sourceNode.toString();

                source = switch (sourceType) {
                    case ResourceTypes.DATASET -> JSON.getReader(DatasetContent.class).readValue(json);
                    case ResourceTypes.IMAGE -> JSON.getReader(ImageContent.class).readValue(json);
                    case ResourceTypes.MODEL -> JSON.getReader(ModelContent.class).readValue(json);
                    case ResourceTypes.SOUND -> JSON.getReader(SoundContent.class).readValue(json);
                    case ResourceTypes.TEXT -> JSON.getReader(TextContent.class).readValue(json);
                    case ResourceTypes.VIDEO -> JSON.getReader(VideoContent.class).readValue(json);
                    case ResourceTypes.TEXTUAL_BODY -> JSON.getReader(TextualBody.class).readValue(json);
                    case ResourceTypes.CANVAS -> JSON.getReader(CanvasContent.class).readValue(json);
                    case EMPTY -> {
                        final JsonNode sourceIDNode = sourceNode.get(JsonKeys.ID);

                        if (sourceIDNode == null) {
                            throw new JsonParseException(aParser,
                                    LOGGER.getMessage(MessageCodes.JPA_131, currentNode.toPrettyString()));
                        }

                        yield new TextContent(sourceIDNode.asText());
                    }
                    default -> throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_056, json));
                };
            }

            if (selectorNode != null) {
                selector = JSON.convertValue(selectorNode, Selector.class);
                specificResource = new SpecificResource(source, selector);
            } else {
                specificResource = new SpecificResource(source);
            }

            if (styleClassNode != null) {
                specificResource.setStyleClass(styleClassNode.asText());
            }

            if (idNode != null) {
                specificResource.setID(idNode.asText());
            }

            target = specificResource;
        } else if (currentNode.isTextual()) {
            target = new Target(currentNode.asText());
        } else if (typeNode != null && ResourceTypes.CANVAS.equals(typeNode.asText())) {
            final JsonNode partOfNode = currentNode.get(JsonKeys.PART_OF);

            if (partOfNode != null) {
                if (partOfNode.isArray()) {
                    final Iterator<JsonNode> iterator = partOfNode.elements();
                    final List<PartOf> partOfs = new ArrayList<>();

                    while (iterator.hasNext()) {
                        partOfs.add(JSON.getReader(PartOf.class).readValue(iterator.next()));
                    }

                    target = new Target(idNode.asText(), typeNode.asText(), partOfs);
                } else {
                    target = new Target(idNode.asText(), typeNode.asText(),
                            JSON.convertValue(partOfNode, PartOf.class));
                }
            } else {
                target = new Target(idNode.asText());
            }
        } else {
            throw new JsonMappingException(aParser,
                    LOGGER.getMessage(MessageCodes.JPA_131, currentNode.toPrettyString()), aParser.currentLocation());
        }

        return target;
    }
}
