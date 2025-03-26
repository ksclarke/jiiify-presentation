
package info.freelibrary.iiif.presentation.v3.utils.json;

import static com.pivovarit.function.ThrowingBiFunction.sneaky;
import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pivovarit.function.ThrowingBiFunction;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.ContentStateAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.Motivation;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A serializer for {@code ContentStateAnnotation}(s).
 */
public class ContentStateSerializer extends StdSerializer<ContentStateAnnotation> {

    /** A logger for the serializer. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentStateSerializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for a <code>ContentStateAnnotationSerializer</code>. */
    private static final long serialVersionUID = -5755418273140298532L;

    /**
     * Creates a new <code>ContentStateAnnotationSerializer</code>.
     */
    public ContentStateSerializer() {
        super(ContentStateAnnotation.class, true);
    }

    @Override
    @SuppressWarnings({ PMD.PRESERVE_STACK_TRACE, PMD.CYCLOMATIC_COMPLEXITY, PMD.COGNITIVE_COMPLEXITY,
        PMD.N_PATH_COMPLEXITY })
    public void serialize(final ContentStateAnnotation aAnnotation, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final List<ContentResource> resources = aAnnotation.getBody();
        final List<Target> targets = aAnnotation.getTargets();
        final Optional<Motivation> motivation = aAnnotation.getMotivation();
        final Optional<TimeMode> timeMode = aAnnotation.getTimeMode();
        final Optional<Label> label = aAnnotation.getLabel();
        final List<URI> contexts;

        // Check that required values exist in the ContentStateAnnotation and fail if they don't
        final ThrowingBiFunction<String, String, String, JsonGenerationException> check = (aKey, aValue) -> {
            if (aValue == null) {
                throw new JsonGenerationException(LOGGER.getMessage(MessageCodes.JPA_012, aKey), aJsonGenerator);
            }

            return aValue;
        };

        try {
            // Start writing our JSON output
            aJsonGenerator.writeStartObject();

            contexts = aAnnotation.getContexts();

            if (contexts.size() == SINGLE_INSTANCE) {
                aJsonGenerator.writeStringField(JsonKeys.CONTEXT, contexts.get(0).toString());
            } else if (contexts.size() > SINGLE_INSTANCE) {
                final Iterator<URI> iterator = contexts.iterator();

                aJsonGenerator.writeFieldName(JsonKeys.CONTEXT);
                aJsonGenerator.writeStartArray();

                while (iterator.hasNext()) {
                    aJsonGenerator.writeString(iterator.next().toString());
                }

                aJsonGenerator.writeEndArray();
            }

            aJsonGenerator.writeObjectField(JsonKeys.ID, sneaky(check).apply(JsonKeys.ID, aAnnotation.getID()));
            aJsonGenerator.writeObjectField(JsonKeys.TYPE, ResourceTypes.ANNOTATION);

            if (motivation.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.MOTIVATION,
                        sneaky(check).apply(JsonKeys.MOTIVATION, motivation.get().toString()));
            }

            if (label.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.LABEL, label.get());
            }

            if (!resources.isEmpty()) {
                serializeResources(resources, aAnnotation.bodyHasChoice(), aJsonGenerator);
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
    public void serializeWithType(final ContentStateAnnotation aAnnotation, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This serializes a ContentStateAnnotation when type is called, like when processing a List
        serialize(aAnnotation, aJsonGenerator, aProvider);
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
