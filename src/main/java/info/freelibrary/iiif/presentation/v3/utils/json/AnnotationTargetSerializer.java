
package info.freelibrary.iiif.presentation.v3.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.Target;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.ThrowingConsumer;
import info.freelibrary.util.ThrowingRunnable;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import java.io.IOException;
import java.io.Serial;
import java.util.List;
import java.util.Optional;

/**
 * A serializer for {@code Annotation.Target}(s).
 */
public class AnnotationTargetSerializer extends StdSerializer<Target> {

    /**
     * The {@code }serialVersionUID} for a {@code AnnotationTargetSerializer}.
     */
    @Serial
    private static final long serialVersionUID = -5745518273140398531L;

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationTargetSerializer.class, MessageCodes.BUNDLE);

    /**
     * Creates a new <code>AnnotationTargetSerializer</code>.
     */
    public AnnotationTargetSerializer() {
        super(Target.class, true);
    }

    @Override
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, PMD.SWITCH_DENSITY, Sonar.COGNITIVE_COMPLEXITY })
    public void serialize(final Target aTarget, final JsonGenerator aJsonGenerator, final SerializerProvider aProvider)
            throws IOException {
        try {
            aTarget.getType().ifPresentOrElse((ThrowingConsumer<String, IOException>) type -> {
                switch (type) {
                    case ResourceTypes.SPECIFIC_RESOURCE -> {
                        final SpecificResource specificResource = (SpecificResource) aTarget;
                        final Optional<String> styleClass = specificResource.getStyleClass();
                        final Optional<Selector> selector = specificResource.getSelector();

                        aJsonGenerator.writeStartObject();

                        if (specificResource.getID() != null) {
                            aJsonGenerator.writeStringField(JsonKeys.ID, specificResource.getID());
                        }

                        aJsonGenerator.writeStringField(JsonKeys.TYPE, ResourceTypes.SPECIFIC_RESOURCE);
                        aJsonGenerator.writeObjectField(JsonKeys.SOURCE, specificResource.getSource());

                        if (selector.isPresent()) {
                            aJsonGenerator.writeObjectField(JsonKeys.SELECTOR, selector.get());
                        }

                        if (styleClass.isPresent()) {
                            aJsonGenerator.writeObjectField(JsonKeys.STYLE_CLASS, styleClass.get());
                        }

                        aJsonGenerator.writeEndObject();
                    }
                    case ResourceTypes.MANIFEST -> {
                        final Target target = aTarget;
                        final List<PartOf> partOfList = target.getPartOfs();

                        aJsonGenerator.writeStartObject();

                        if (target.getID() != null) {
                            aJsonGenerator.writeStringField(JsonKeys.ID, target.getID());
                        }

                        aJsonGenerator.writeStringField(JsonKeys.TYPE, ResourceTypes.CANVAS);

                        if (!partOfList.isEmpty()) {
                            aJsonGenerator.writeFieldName(JsonKeys.PART_OF);
                            aJsonGenerator.writeStartArray();

                            for (final PartOf partOf : partOfList) {
                                aJsonGenerator.writeObject(partOf);
                            }

                            aJsonGenerator.writeEndArray();
                        }

                        aJsonGenerator.writeEndObject();
                    }
                    case ResourceTypes.CANVAS -> {
                        LOGGER.info("=========================================================");
                        final Target target = aTarget;
                        final List<PartOf> partOfList = target.getPartOfs();

                        aJsonGenerator.writeStartObject();

                        if (target.getID() != null) {
                            aJsonGenerator.writeStringField(JsonKeys.ID, target.getID());
                        }

                        aJsonGenerator.writeStringField(JsonKeys.TYPE, ResourceTypes.CANVAS);

                        if (!partOfList.isEmpty()) {
                            aJsonGenerator.writeFieldName(JsonKeys.PART_OF);
                            aJsonGenerator.writeStartArray();

                            for (final PartOf partOf : partOfList) {
                                aJsonGenerator.writeObject(partOf);
                            }

                            aJsonGenerator.writeEndArray();
                        }

                        aJsonGenerator.writeEndObject();
                    }
                    default -> aJsonGenerator.writeObject(aTarget.getID());

                }
            }, ThrowingRunnable.wrap(() -> aJsonGenerator.writeObject(aTarget.getID())));
        } catch (final I18nRuntimeException details) {
            throw new IOException(details.getCause().getMessage(), details);
        }
    }

    @Override
    public void serializeWithType(final Target aTarget, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This serializes a target when type is called, like when processing a list of targets
        serialize(aTarget, aJsonGenerator, aProvider);
    }
}
