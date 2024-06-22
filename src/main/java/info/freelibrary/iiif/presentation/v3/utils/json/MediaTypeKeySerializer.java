
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * The {@link MediaType} key's serializer.
 */
public class MediaTypeKeySerializer extends JsonSerializer<Optional<MediaType>> {

    /**
     * Creates a new media type key serializer.
     */
    public MediaTypeKeySerializer() {
        // This is intentionally left empty
    }

    @Override
    public void serialize(final Optional<MediaType> aMediaType, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        if (aMediaType != null && aMediaType.isPresent()) {
            aJsonGenerator.writeFieldName(JsonKeys.FORMAT);
        }
    }

}
