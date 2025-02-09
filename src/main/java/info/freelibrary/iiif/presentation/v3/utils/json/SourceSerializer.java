
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource.Source;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
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
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, PMD.CYCLOMATIC_COMPLEXITY })
    public void serialize(final Source aSource, final JsonGenerator aJsonGenerator, final SerializerProvider aProvider)
            throws IOException {
        final List<PartOf> partOfs = aSource.getPartOfs();

        if (partOfs.isEmpty() && aSource.getType().isEmpty()) {
            aJsonGenerator.writeString(aSource.getID());
        } else {
            final Optional<String> type = aSource.getType();
            final Optional<MediaType> mediaType = aSource.getFormat();
            final OptionalInt width = aSource.getWidth();
            final OptionalInt height = aSource.getHeight();
            final List<Service> services = aSource.getServices();
            final List<PartOf> partsOf = aSource.getPartOfs();

            aJsonGenerator.writeStartObject();
            aJsonGenerator.writeStringField(JsonKeys.ID, aSource.getID());

            if (!partsOf.isEmpty()) {
                final Iterator<PartOf> iterator = partsOf.iterator();

                aJsonGenerator.writeArrayFieldStart(JsonKeys.PART_OF);

                while (iterator.hasNext()) {
                    aJsonGenerator.writeObject(iterator.next());
                }

                aJsonGenerator.writeEndArray();
            }

            if (type.isPresent()) {
                aJsonGenerator.writeStringField(JsonKeys.TYPE, type.get());
            }

            if (mediaType.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.FORMAT, mediaType.get().toString());
            }

            if (width.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.WIDTH, width.getAsInt());
            }

            if (height.isPresent()) {
                aJsonGenerator.writeObjectField(JsonKeys.HEIGHT, height.getAsInt());
            }

            if (!services.isEmpty()) {
                aJsonGenerator.writeArrayFieldStart(JsonKeys.SERVICE);

                for (final Service service : services) {
                    aJsonGenerator.writeObject(service);
                }

                aJsonGenerator.writeEndArray();
            }

            aJsonGenerator.writeEndObject();
        }
    }

    @Override
    public void serializeWithType(final Source aSource, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This serializes a source when type is called, like when processing a list of sources
        serialize(aSource, aJsonGenerator, aProvider);
    }
}
