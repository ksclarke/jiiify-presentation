
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import info.freelibrary.iiif.presentation.v3.properties.Value;

/**
 * Deserializes values from JSON documents into {@link Value} objects.
 */
public class ValueDeserializer extends AbstractI18nStdDeserializer<Value> {

    /**
     * The <code>serialVersionUID</code> of ValueSerializer.
     */
    private static final long serialVersionUID = 8482393289401619224L;

    /**
     * Creates a new value deserializer.
     */
    public ValueDeserializer() {
        this(null);
    }

    /**
     * Creates a new value deserializer.
     *
     * @param aClass A class
     */
    public ValueDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a value from its JSON structure.
     */
    @Override
    public Value deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        return new Value(getI18nStrings(aParser.getCodec().readTree(aParser)));
    }
}
