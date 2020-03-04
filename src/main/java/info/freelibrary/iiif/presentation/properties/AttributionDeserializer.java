
package info.freelibrary.iiif.presentation.properties;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Deserializes attributions from JSON documents into {@link Attribution} objects.
 */
class AttributionDeserializer extends AbstractStdDeserializer<Attribution> {

    /**
     * The <code>serialVersionUID</code> for AttributionDeserializer.
     */
    private static final long serialVersionUID = 4517649067251783702L;

    /**
     * Creates a new attribution deserializer.
     */
    AttributionDeserializer() {
        this(null);
    }

    /**
     * Creates a new attribution deserializer.
     *
     * @param aClass A class
     */
    AttributionDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes an Attribution from its JSON structure.
     */
    @Override
    public Attribution deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        return new Attribution(getI18nStrings(aParser.getCodec().readTree(aParser)));
    }
}
