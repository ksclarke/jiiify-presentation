
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.iiif.presentation.v3.PaintingAnnotation.Stylesheet;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * The stylesheet serializer to be used by Jackson.
 */
public class StylesheetSerializer extends StdSerializer<Stylesheet> {

    /** The <code>serialVersionUID</code> for the <code>StylesheetSerializer</code>. */
    private static final long serialVersionUID = 4404820939177658414L;

    /**
     * Creates a new <code>StylesheetDeserializer</code>.
     */
    public StylesheetSerializer() {
        this(null);
    }

    /**
     * Creates a new <code>StylesheetDeserializer</code> from a supplied class.
     *
     * @param aStylesheetClass The class for the serializer
     */
    public StylesheetSerializer(final Class<Stylesheet> aStylesheetClass) {
        super(aStylesheetClass);
    }

    @Override
    public void serialize(final Stylesheet aStylesheet, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final Optional<URI> uri = aStylesheet.getURI();
        final Optional<String> value = aStylesheet.getValue();

        if (uri.isPresent()) {
            aJsonGenerator.writeString(uri.get().toString());
        } else if (value.isPresent()) {
            aJsonGenerator.writeStartObject();
            aJsonGenerator.writeStringField(JsonKeys.TYPE, Stylesheet.TYPE);
            aJsonGenerator.writeStringField(JsonKeys.VALUE, value.get());
            aJsonGenerator.writeEndObject();
        }
    }

    @Override
    public void serializeWithType(final Stylesheet aStylesheet, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer)
            throws IOException, JsonProcessingException {
        // This serializes a source when type is called, like when processing a list of sources
        serialize(aStylesheet, aJsonGenerator, aProvider);
    }
}
