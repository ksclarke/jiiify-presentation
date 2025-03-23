
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.targets.CanvasTarget;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource.Source;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A Jackson deserializer for annotation targets.
 */
public class AnnotationTargetDeserializer extends StdDeserializer<Target> {

    /** A logger for the <code>AnnotationTargetDeserializer</code>. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AnnotationTargetDeserializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for the <code>AnnotationTargetDeserializer</code>. */
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
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, PMD.CYCLOMATIC_COMPLEXITY, Sonar.COGNITIVE_COMPLEXITY })
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
            final Selector selector;
            final Source source;

            if (sourceNode == null) {
                throw new JsonMappingException(aParser,
                        LOGGER.getMessage(MessageCodes.JPA_131, currentNode.toPrettyString()),
                        aParser.currentLocation());
            }

            source = JSON.convertValue(sourceNode, Source.class);

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
                        partOfs.add(JSON.getReader(PartOf.class).readValue(partOfNode));
                    }

                    target = new CanvasTarget(idNode.asText(), partOfs);
                } else {
                    target = new CanvasTarget(idNode.asText(), JSON.convertValue(partOfNode, PartOf.class));
                }
            } else {
                target = new CanvasTarget(idNode.asText());
            }
        } else {
            throw new JsonMappingException(aParser,
                    LOGGER.getMessage(MessageCodes.JPA_131, currentNode.toPrettyString()), aParser.currentLocation());
        }

        return target;
    }
}
