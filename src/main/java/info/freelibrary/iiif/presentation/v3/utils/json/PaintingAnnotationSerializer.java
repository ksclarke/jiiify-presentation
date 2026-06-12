
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;
import static info.freelibrary.util.ThrowingBiFunction.unwrap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.Motivation;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation.Stylesheet;
import info.freelibrary.iiif.presentation.v3.annotation.Target;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.ThrowingBiFunction;
import info.freelibrary.util.warnings.PMD;

import java.io.IOException;
import java.io.Serial;
import java.util.List;
import java.util.Optional;

/**
 * A serializer for {@code PaintingAnnotation}(s).
 */
public class PaintingAnnotationSerializer extends StdSerializer<PaintingAnnotation> {

    /**
     * A logger for the serializer.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(PaintingAnnotationSerializer.class, MessageCodes.BUNDLE);

    /**
     * The <code>serialVersionUID</code> for a <code>PaintingAnnotationSerializer</code>.
     */
    @Serial
    private static final long serialVersionUID = -5151418273140218531L;

    /**
     * Creates a new <code>PaintingAnnotationSerializer</code>.
     */
    public PaintingAnnotationSerializer() {
        super(PaintingAnnotation.class, true);
    }

    @Override
    @SuppressWarnings({ PMD.PRESERVE_STACK_TRACE, PMD.CYCLOMATIC_COMPLEXITY, PMD.COGNITIVE_COMPLEXITY,
        PMD.N_PATH_COMPLEXITY })
    public void serialize(final PaintingAnnotation aPaintingAnnotation, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final List<ContentResource<?>> resources = aPaintingAnnotation.getBody();
        final Optional<String> bodyID = aPaintingAnnotation.getBodyID();
        final List<Target> targets = aPaintingAnnotation.getTargets();
        final Optional<Motivation> motivation = aPaintingAnnotation.getMotivation();
        final Optional<TimeMode> timeMode = aPaintingAnnotation.getTimeMode();
        final Optional<Label> label = aPaintingAnnotation.getLabel();
        final Optional<Stylesheet> stylesheet = aPaintingAnnotation.getStylesheet();

        // Check that required values exist in the PaintingAnnotation and fail if they don't
        final ThrowingBiFunction<String, String, String, JsonGenerationException> check = (aKey, aValue) -> {
            if (aValue == null) {
                throw new JsonGenerationException(LOGGER.getMessage(MessageCodes.JPA_012, aKey), aJsonGenerator);
            }

            return aValue;
        };

        try {
            final List<SeeAlso> seeAlsoRefs = aPaintingAnnotation.getSeeAlsoRefs();

            // Start writing our JSON output
            aJsonGenerator.writeStartObject();
            aJsonGenerator.writeObjectField(JsonKeys.ID, unwrap(check).apply(JsonKeys.ID, aPaintingAnnotation.getID()));
            aJsonGenerator.writeObjectField(JsonKeys.TYPE, ResourceTypes.ANNOTATION);

            if (motivation.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.MOTIVATION,
                        unwrap(check).apply(JsonKeys.MOTIVATION, motivation.get().toString()));
            }

            if (timeMode.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.TIMEMODE, timeMode.get());
            }

            if (stylesheet.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.STYLESHEET, stylesheet.get());
            }

            if (label.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.LABEL, label.get());
            }

            if (!resources.isEmpty()) {
                serializeResources(resources, aPaintingAnnotation.bodyHasChoice(), bodyID, aJsonGenerator);
            }

            if (!seeAlsoRefs.isEmpty()) {
                aJsonGenerator.writeArrayFieldStart(JsonKeys.SEE_ALSO);

                for (final SeeAlso seeAlsoRef : seeAlsoRefs) {
                    aJsonGenerator.writeObject(seeAlsoRef);
                }

                aJsonGenerator.writeEndArray();
            }

            if (targets.size() == SINGLE_INSTANCE) {
                aJsonGenerator.writeObjectField(JsonKeys.TARGET, targets.getFirst());
            } else if (targets.size() > SINGLE_INSTANCE) {
                aJsonGenerator.writeFieldName(JsonKeys.TARGET);
                aJsonGenerator.writeStartArray();

                for (final Target target : targets) {
                    aJsonGenerator.writeObject(target);
                }

                aJsonGenerator.writeEndArray();
            }

            aJsonGenerator.writeEndObject();
        } catch (final JsonParseException details) {
            throw (JsonGenerationException) details.getCause();
        }
    }

    @Override
    public void serializeWithType(final PaintingAnnotation aPaintingAnnotation, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This serializes a PaintingAnnotation when type is called, like when processing a List
        serialize(aPaintingAnnotation, aJsonGenerator, aProvider);
    }

    /**
     * Serialize the annotation's resources.
     *
     * @param aList A list of annotation resources
     * @param aChoice True if the list is a choice between resources; else false
     * @param aBodyID An optional body ID
     * @param aJsonGenerator A JSON generator
     * @throws IOException If there is trouble writing to the generator
     * @throws JsonProcessingException If there is trouble parsing the source annotation
     */
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY })
    private void serializeResources(final List<ContentResource<?>> aList, final boolean aChoice,
            final Optional<String> aBodyID, final JsonGenerator aJsonGenerator) throws IOException {
        if (aList.size() == SINGLE_INSTANCE) {
            aJsonGenerator.writeObjectField(JsonKeys.BODY, aList.getFirst());
        } else {
            aJsonGenerator.writeFieldName(JsonKeys.BODY);

            if (aChoice) {
                aJsonGenerator.writeStartObject();

                if (aBodyID.isPresent()) {
                    aJsonGenerator.writeObjectField(JsonKeys.ID, aBodyID.get());
                }

                aJsonGenerator.writeObjectField(JsonKeys.TYPE, ResourceTypes.CHOICE);
                aJsonGenerator.writeArrayFieldStart(JsonKeys.ITEMS);
            } else {
                aJsonGenerator.writeStartArray();
            }

            for (final ContentResource<?> contentResource : aList) {
                if (contentResource == null) {
                    aJsonGenerator.writeString(ResourceTypes.RDF_NIL);
                } else {
                    aJsonGenerator.writeObject(contentResource);
                }
            }

            aJsonGenerator.writeEndArray();

            if (aChoice) {
                aJsonGenerator.writeEndObject();
            }
        }
    }
}
