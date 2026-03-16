
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.ThrowingConsumer.sneaky;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import info.freelibrary.iiif.presentation.v3.content.TextContent;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.Serial;
import java.util.List;
import java.util.Optional;

/**
 * A serializer for {@code TextContent}(s).
 */
public class TextContentSerializer extends StdSerializer<TextContent> {

    /** The <code>serialVersionUID</code> for the SvgSerializer. */
    @Serial
    private static final long serialVersionUID = 381951202256600155L;

    /**
     * Creates a new <code>SvgDeserializer</code>.
     */
    public TextContentSerializer() {
        super(Document.class, true);
    }

    @Override
    public void serialize(final TextContent aTextContent, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final String id = aTextContent.getID();
        final TextContent content = new TextContent(aTextContent.getID());

        // Test if only the ID is set or whether other fields have values too
        if (content.equals(aTextContent)) {
            aJsonGenerator.writeString(id);
        } else {
            final List<String> languages = aTextContent.getLanguages();
            final Optional<String> typeOpt = aTextContent.getType();
            final Optional<MediaType> formatOpt = aTextContent.getFormat();
            final Optional<Label> labelOpt = aTextContent.getLabel();

            aJsonGenerator.writeStartObject();
            aJsonGenerator.writeStringField(JsonKeys.ID, id);

            typeOpt.ifPresent(sneaky(type -> aJsonGenerator.writeStringField(JsonKeys.TYPE, type)));
            formatOpt.ifPresent(sneaky(format -> aJsonGenerator.writeStringField(JsonKeys.FORMAT, format.toString())));
            labelOpt.ifPresent(sneaky(label -> aJsonGenerator.writeObjectField(JsonKeys.LABEL, label)));

            if (languages != null && !languages.isEmpty()) {
                aJsonGenerator.writeArrayFieldStart(JsonKeys.LANGUAGE);

                for (final String language : languages) {
                    aJsonGenerator.writeString(language);
                }

                aJsonGenerator.writeEndArray();
            }

            aJsonGenerator.writeEndObject();
        }
    }

    @Override
    public void serializeWithType(final TextContent aTextContent, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This handles serialization of lists, where types are used, by serializing each individually
        serialize(aTextContent, aJsonGenerator, aProvider);
    }
}
