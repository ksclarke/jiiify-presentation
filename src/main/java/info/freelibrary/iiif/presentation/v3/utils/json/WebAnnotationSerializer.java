
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;
import static info.freelibrary.util.ThrowingBiFunction.unwrap;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.ThrowingBiFunction;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.Motivation;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A serializer for {@code WebAnnotation}(s).
 */
public class WebAnnotationSerializer extends StdSerializer<WebAnnotation> {

    /** A logger for the serializer. */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAnnotationSerializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for a <code>WebAnnotationSerializer</code>. */
    private static final long serialVersionUID = -5755418273140298532L;

    /**
     * Creates a new <code>WebAnnotationSerializer</code>.
     */
    public WebAnnotationSerializer() {
        super(WebAnnotation.class, true);
    }

    @Override
    @SuppressWarnings({ PMD.PRESERVE_STACK_TRACE, PMD.CYCLOMATIC_COMPLEXITY })
    public void serialize(final WebAnnotation aWebAnnotation, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final List<ContentResource> resources = aWebAnnotation.getBody();
        final List<Target> targets = aWebAnnotation.getTargets();
        final Optional<Motivation> motivation = aWebAnnotation.getMotivation();
        final Optional<TimeMode> timeMode = aWebAnnotation.getTimeMode();
        final Optional<Label> label = aWebAnnotation.getLabel();

        // Check that required values exist in the WebAnnotation and fail if they don't
        final ThrowingBiFunction<String, String, String, JsonGenerationException> check = (aKey, aValue) -> {
            if (aValue == null) {
                throw new JsonGenerationException(LOGGER.getMessage(MessageCodes.JPA_012, aKey), aJsonGenerator);
            }

            return aValue;
        };

        try {
            // Start writing our JSON output
            aJsonGenerator.writeStartObject();
            aJsonGenerator.writeObjectField(JsonKeys.ID, unwrap(check).apply(JsonKeys.ID, aWebAnnotation.getID()));
            aJsonGenerator.writeObjectField(JsonKeys.TYPE, ResourceTypes.ANNOTATION);

            if (motivation.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.MOTIVATION,
                        unwrap(check).apply(JsonKeys.MOTIVATION, motivation.get().toString()));
            }

            if (label.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.LABEL, label.get());
            }

            if (!resources.isEmpty()) {
                serializeResources(resources, aWebAnnotation.bodyHasChoice(), aJsonGenerator);
            }

            if (targets.size() == SINGLE_INSTANCE) {
                aJsonGenerator.writeObjectField(JsonKeys.TARGET, targets.get(0));
            } else if (targets.size() > SINGLE_INSTANCE) {
                aJsonGenerator.writeFieldName(JsonKeys.TARGET);
                aJsonGenerator.writeStartArray();

                for (final Target target : targets) {
                    aJsonGenerator.writeObject(target);
                }

                aJsonGenerator.writeEndArray();
            }

            if (timeMode.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.TIMEMODE, timeMode.get());
            }

            aJsonGenerator.writeEndObject();
        } catch (final JsonParseException details) {
            throw (JsonGenerationException) details.getCause();
        }
    }

    @Override
    public void serializeWithType(final WebAnnotation aWebAnnotation, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This serializes a WebAnnotation when type is called, like when processing a List of WebAnnotation(s)
        serialize(aWebAnnotation, aJsonGenerator, aProvider);
    }

    /**
     * Serialize the annotation's resources.
     *
     * @param aList A list of annotation resources
     * @param aChoice True if the list is a choice between resources; else false
     * @param aJsonGenerator A JSON generator
     * @throws IOException If there is trouble writing to the generator
     * @throws JsonProcessingException If there is trouble parsing the source annotation
     */
    private void serializeResources(final List<ContentResource> aList, final boolean aChoice,
            final JsonGenerator aJsonGenerator) throws IOException {
        if (aList.size() == SINGLE_INSTANCE) {
            aJsonGenerator.writeObjectField(JsonKeys.BODY, aList.get(0));
        } else {
            aJsonGenerator.writeFieldName(JsonKeys.BODY);

            if (aChoice) {
                aJsonGenerator.writeStartObject();
                aJsonGenerator.writeObjectField(JsonKeys.TYPE, ResourceTypes.CHOICE);
                aJsonGenerator.writeArrayFieldStart(JsonKeys.ITEMS);
            } else {
                aJsonGenerator.writeStartArray();
            }

            for (final ContentResource contentResource : aList) {
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
