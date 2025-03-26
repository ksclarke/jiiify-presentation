
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.ThrowingConsumer;
import info.freelibrary.util.ThrowingRunnable;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A serializer for PartOf(s).
 */
public class PartOfSerializer extends StdSerializer<PartOf> {

    /** A context list filter for embedded resources. */
    private static final ContextFilterProvider FILTER = new ContextFilterProvider();

    /** A logger for the <code>PartOfSerializer</code> to use. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PartOfSerializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> of the PartOfSerializer. */
    private static final long serialVersionUID = -5725508283144396531L;

    /**
     * Creates a new <code>PartOfSerializer</code>.
     */
    public PartOfSerializer() {
        super(PartOf.class, true);
    }

    @Override
    public void serialize(final PartOf aPartOf, final JsonGenerator aJsonGenerator, final SerializerProvider aProvider)
            throws IOException {
        final boolean useURIs = Boolean.TRUE.equals(aProvider.getAttribute(JSON.URI_LINKS));

        aPartOf.getEmbeddedResource().ifPresentOrElse((ThrowingConsumer<Resource<?>, IOException>) resource -> {
            // We want to serialize the embedded resource without contexts since it's embedded
            final ObjectWriter writer = JSON.copy().setFilterProvider(FILTER).writerFor(resource.getClass());
            aJsonGenerator.writeRawValue(writer.withAttribute(JSON.URI_LINKS, useURIs).writeValueAsString(resource));
        }, ThrowingRunnable.wrap(() -> {
            if (aPartOf.getType().isEmpty()) {
                throw new JsonGenerationException(LOGGER.getMessage(MessageCodes.JPA_154, aPartOf.getID()),
                        aJsonGenerator);
            }

            if (aPartOf.hasObject() || !useURIs) {
                aJsonGenerator.writeStartObject();
                aJsonGenerator.writeStringField(JsonKeys.ID, aPartOf.getID());
                aJsonGenerator.writeStringField(JsonKeys.TYPE, aPartOf.getType().get());
                aJsonGenerator.writeEndObject();
            } else {
                aJsonGenerator.writeString(aPartOf.getID());
            }
        }));
    }

    @Override
    public void serializeWithType(final PartOf aPartOf, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This serializes a target when type is called, like when processing a list of PartOf(s)
        serialize(aPartOf, aJsonGenerator, aProvider);
    }
}
