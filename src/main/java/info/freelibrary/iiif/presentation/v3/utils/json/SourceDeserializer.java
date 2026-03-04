
package info.freelibrary.iiif.presentation.v3.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import info.freelibrary.iiif.presentation.v3.annotation.Source;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

import java.io.IOException;
import java.io.Serial;

/**
 * A deserializer for {@code Source}.
 */
public class SourceDeserializer extends StdDeserializer<Source> {

    /** The <code>serialVersionUID</code> for a <code>SourceDeserializer</code>. */
    @Serial
    private static final long serialVersionUID = 2493966189471740163L;

    /**
     * Creates a new {@code Source} deserializer.
     */
    SourceDeserializer() {
        this(Source.class);
    }

    /**
     * Creates a new {@code Source} deserializer.
     *
     * @param aClass A class to be deserialized
     */
    SourceDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Source deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        final JsonNode node = JSON.getReader().readTree(aParser);

        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }

        // "source": "https://example.org/resource"
        if (node.isTextual()) {
            return new Source(node.textValue());
        }

        // If the source is a full JSON object
        if (node.isObject()) {
            final ContentResource resource = JSON.convertValue(node, ContentResource.class);
            return new Source(resource, true);
        }

        throw new JsonParseException(aParser, "Expected Source to be a JSON string or object, got: " +
                                              node.getNodeType());
    }

}
