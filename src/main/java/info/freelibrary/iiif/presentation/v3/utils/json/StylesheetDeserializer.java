
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.PaintingAnnotation.Stylesheet;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A stylesheet deserializer to be used by Jackson.
 */
public class StylesheetDeserializer extends StdDeserializer<Stylesheet> {

    /** The <code>serialVersionUID</code> of the <code>StylesheetDeserializer</code>. */
    private static final long serialVersionUID = -7707466202850085859L;

    /** The logger for the <code>StylesheetDeserializer</code>. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StylesheetDeserializer.class, MessageCodes.BUNDLE);

    /**
     * Creates a new {@code Stylesheet} deserializer.
     */
    StylesheetDeserializer() {
        this(Stylesheet.class);
    }

    /**
     * Creates a new {@code Stylesheet} deserializer.
     *
     * @param aClass A class to be deserialized
     */
    StylesheetDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Stylesheet deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JacksonException {
        final JsonNode currentNode = JSON.getReader().readTree(aParser);
        final Stylesheet stylesheet;

        if (currentNode.isTextual()) {
            final String externalStylesheetURI = currentNode.asText();

            if (StringUtils.trimToNull(externalStylesheetURI) == null) {
                throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_142),
                        aParser.currentLocation());
            }

            stylesheet = new Stylesheet(URI.create(externalStylesheetURI));
        } else {
            final JsonNode valueNode = currentNode.get(JsonKeys.VALUE);
            final String value;

            if (valueNode == null || (value = StringUtils.trimToNull(valueNode.asText())) == null) {
                throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_142),
                        aParser.currentLocation());
            }

            stylesheet = new Stylesheet(value);
        }

        return stylesheet;
    }

}
