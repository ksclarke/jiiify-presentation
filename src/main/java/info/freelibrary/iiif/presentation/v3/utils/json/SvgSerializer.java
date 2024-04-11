
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * A serializer for an SVG <code>Document</code>s.
 */
public class SvgSerializer extends StdSerializer<Document> {

    /** The <code>serialVersionUID</code> for the SvgSerializer. */
    private static final long serialVersionUID = 3819513023259900166L;

    /**
     * Creates a new <code>SvgDeserializer</code>.
     */
    public SvgSerializer() {
        super(Document.class, true);
    }

    @Override
    public void serialize(final Document anSvgDocument, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final OutputSettings output = anSvgDocument.outputSettings();
        final String svg;

        output.prettyPrint(false).syntax(Document.OutputSettings.Syntax.xml);
        svg = anSvgDocument.outerHtml().replaceAll("\"", "'"); // Our SVG is inside a JSON string

        aJsonGenerator.writeString(svg);
    }

    @Override
    public void serializeWithType(final Document aSvgDocument, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer)
            throws IOException, JsonProcessingException {
        // This handles serialization of lists, where types are used, by serializing each individually
        serialize(aSvgDocument, aJsonGenerator, aProvider);
    }
}
