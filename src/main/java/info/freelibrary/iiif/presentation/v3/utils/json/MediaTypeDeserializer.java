
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A deserializer for {@code MediaType}(s).
 */
@SuppressWarnings(PMD.EXCESSIVE_IMPORTS)
public class MediaTypeDeserializer extends StdDeserializer<MediaType> {

    /** Logger for this deserializer. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaTypeDeserializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for this deserializer. */
    private static final long serialVersionUID = -1902362571104671943L;

    /**
     * Creates a new <code>MediaTypeDeserializer</code>.
     */
    public MediaTypeDeserializer() {
        super(MediaType.class);
    }

    /**
     * Creates a new <code>MediaTypeDeserializer</code>.
     *
     * @param aClass A class to deserialize
     */
    public MediaTypeDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    @SuppressWarnings(JDK.DEPRECATION)
    public MediaType deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final String value = node.asText(EMPTY);

        Optional<MediaType> mediaType = MediaType.fromString(value);

        // We take a stab at guessing based on the supplied value
        if (mediaType.isEmpty()) {
            final String[] parts = value.split(Constants.SLASH);

            if (parts.length > SINGLE_INSTANCE) {
                mediaType = MediaType.fromExt(parts[1], parts[0]);
            } else {
                mediaType = MediaType.fromExt(parts[0]);

                if (mediaType.isEmpty()) {
                    LOGGER.warn(MessageCodes.JPA_133, value);
                }
            }
        }

        return mediaType.isPresent() ? mediaType.get() : null;
    }
}
