
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;

/**
 * A serializer for {@code MediaType}(s).
 */
public class MediaTypeSerializer extends StdSerializer<MediaType> {

    /** The <code>serialVersionUID</code> for a <code>MediaTypeSerializer</code>. */
    private static final long serialVersionUID = -5745518273140398531L;

    /**
     * Creates a new <code>MediaTypeSerializer</code>.
     */
    public MediaTypeSerializer() {
        super(MediaType.class, true);
    }

    @Override
    public void serialize(final MediaType aMediaType, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException, JsonProcessingException {
        if (aMediaType != null) {
            aJsonGenerator.writeString(aMediaType.toString());
        }
    }

    @Override
    public void serializeWithType(final MediaType aMediaType, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer)
            throws IOException, JsonProcessingException {
        // This serializes a media type when type is called, like when processing a list of media types
        serialize(aMediaType, aJsonGenerator, aProvider);
    }
}
