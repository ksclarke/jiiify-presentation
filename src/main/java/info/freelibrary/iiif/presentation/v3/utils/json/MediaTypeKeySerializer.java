
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * The {@link MediaType} key's serializer.
 */
public class MediaTypeKeySerializer extends JsonSerializer<Optional<MediaType>> {

    /** A key serializer logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaTypeKeySerializer.class, MessageCodes.BUNDLE);

    @Override
    public void serialize(final Optional<MediaType> aMediaType, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        LOGGER.info("===================================== key serializer =======================================");

        if (aMediaType != null && aMediaType.isPresent()) {
            aJsonGenerator.writeFieldName(JsonKeys.FORMAT);
        }
    }

}
