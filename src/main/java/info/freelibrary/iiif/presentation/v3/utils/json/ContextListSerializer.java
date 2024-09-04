
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.util.ThrowingConsumer;

import info.freelibrary.iiif.presentation.v3.ContextList;

/**
 * A custom serializer for ContextList. Serialization is a little different because it either serializes as a single
 * string or a list of strings, depending on list size.
 */
public class ContextListSerializer extends StdSerializer<ContextList> {

    /** The {@code ContextListSerializer}'s {@code serialVersionUID}. */
    private static final long serialVersionUID = 7538668057735707536L;

    /**
     * Creates a new {@code ContextListSerializer}.
     */
    public ContextListSerializer() {
        super(ContextList.class, true);
    }

    /**
     * Creates a new {@code ContextListSerializer} from the supplied class.
     *
     * @param aClass The class to serialize
     */
    public ContextListSerializer(final Class<ContextList> aClass) {
        super(aClass, true);
    }

    @Override
    public void serialize(final ContextList aContextList, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        if (aContextList.size() == SINGLE_INSTANCE) {
            aJsonGenerator.writeString(aContextList.get(0).toString());
        } else {
            aJsonGenerator.writeStartArray();
            aContextList.stream().map(URI::toString).forEach((ThrowingConsumer<String>) aJsonGenerator::writeString);
            aJsonGenerator.writeEndArray();
        }
    }
}
