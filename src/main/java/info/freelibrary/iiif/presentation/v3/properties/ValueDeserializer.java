
package info.freelibrary.iiif.presentation.v3.properties;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Deserializes values from JSON documents into {@link Value} objects.
 */
class ValueDeserializer extends AbstractI18nStdDeserializer<Value> {

    /**
     * The <code>serialVersionUID</code> of ValueSerializer.
     */
    private static final long serialVersionUID = 8482393289401619224L;

    /**
     * Creates a new value deserializer.
     */
    ValueDeserializer() {
        this(null);
    }

    /**
     * Creates a new value deserializer.
     *
     * @param aClass A class
     */
    ValueDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a value from its JSON structure.
     */
    @Override
    public Value deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        return new Value(getI18nStrings(aParser.getCodec().readTree(aParser)));
    }
}
