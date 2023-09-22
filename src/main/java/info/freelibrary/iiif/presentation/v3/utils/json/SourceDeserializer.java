
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.v3.SpecificResource.Source;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A deserializer for for {@code SpecificResource.Source}.
 */
public class SourceDeserializer extends StdDeserializer<Source> {

    /** The <code>serialVersionUID</code> for a <code>SourceDeserializer</code>. */
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
    public Source deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JacksonException {
        final JsonNode currentNode = JSON.getReader().readTree(aParser);

        if (!currentNode.isTextual()) {
            final JsonNode idNode = currentNode.get(JsonKeys.ID);
            final JsonNode typeNode = currentNode.get(JsonKeys.TYPE);
            final JsonNode partOfNode = currentNode.get(JsonKeys.PART_OF);
            final String type = typeNode.textValue();
            final String id = idNode.textValue();

            if (partOfNode.isArray()) {
                final List<PartOf> list = JSON.getReader(new TypeReference<List<PartOf>>() {}).readValue(partOfNode);
                return new Source(id, type, list.toArray(new PartOf[] {}));
            }

            return new Source(id, type, JSON.getReader(PartOf.class).readValue(partOfNode));
        }

        return new Source(currentNode.textValue());
    }

}
