
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.iiif.presentation.v3.ContextList.PRESENTATION_CONTEXT_URI;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import info.freelibrary.iiif.presentation.v3.ContextList;

import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.util.List;

/**
 * A custom deserializer for ContextList. Deserialization is a little different because it either deserializes from a
 * single string or a list of strings, depending on list size.
 */
public class ContextListDeserializer extends StdDeserializer<List<URI>> {

    /** The {@code ContextListDeserializer}'s {@code serialVersionUID}. */
    @Serial
    private static final long serialVersionUID = 3623670131870204191L;

    /**
     * Creates a new {@link ContextList} deserializer.
     */
    public ContextListDeserializer() {
        this(null);
    }

    /**
     * Creates a new {@link ContextList} deserializer.
     *
     * @param aClass The class to deserialize
     */
    protected ContextListDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public List<URI> deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        final JsonNode currentNode = aParser.getCodec().readTree(aParser);
        final ContextList contexts = new ContextList();

        if (currentNode.isArray()) {
            currentNode.forEach(context -> {
                final URI uri = URI.create(context.textValue());

                if (!PRESENTATION_CONTEXT_URI.equals(uri)) {
                    contexts.add(contexts.size() - 1, uri);
                }
            });
        } else {
            final URI uri = URI.create(currentNode.textValue());

            if (!PRESENTATION_CONTEXT_URI.equals(uri)) {
                contexts.add(contexts.size() - 1, uri);
            }
        }

        return contexts;
    }
}
