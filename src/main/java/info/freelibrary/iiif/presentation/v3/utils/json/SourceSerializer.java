
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.iiif.presentation.v3.SpecificResource.Source;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A serializer for a {@code SpecificResource.Source}.
 */
public class SourceSerializer extends StdSerializer<Source> {

    /** A <code>serialVersionUID</code> for <code>SourceSerializer</code>. */
    private static final long serialVersionUID = 2442075335371467518L;

    /**
     * Creates a new <code>SourceDeserializer</code>.
     */
    public SourceSerializer() {
        super(Source.class, true);
    }

    @Override
    public void serialize(final Source aSource, final JsonGenerator aJsonGenerator, final SerializerProvider aProvider)
            throws IOException {
        final List<PartOf> partOfs = aSource.getPartOfs();

        if (partOfs.isEmpty() && aSource.getType().isEmpty()) {
            aJsonGenerator.writeString(aSource.getID());
        } else {
            final Optional<String> type = aSource.getType();

            aJsonGenerator.writeStartObject();
            aJsonGenerator.writeStringField(JsonKeys.ID, aSource.getID());

            if (type.isPresent()) {
                aJsonGenerator.writeStringField(JsonKeys.TYPE, type.get());
            }

            if (!partOfs.isEmpty()) {
                aJsonGenerator.writeObjectField(JsonKeys.PART_OF, partOfs);
            }

            aJsonGenerator.writeEndObject();
        }
    }

    @Override
    public void serializeWithType(final Source aSource, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer)
            throws IOException, JsonProcessingException {
        // This serializes a source when type is called, like when processing a list of sources
        serialize(aSource, aJsonGenerator, aProvider);
    }
}
