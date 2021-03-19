
package info.freelibrary.iiif.presentation.v3.properties;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Deserializes summaries from JSON documents into {@link Summary} objects.
 */
class SummaryDeserializer extends AbstractI18nStdDeserializer<Summary> {

    /**
     * The <code>serialVersionUID</code> of SummarySerializer.
     */
    private static final long serialVersionUID = 6825081668840056682L;

    /**
     * Creates a new summary deserializer.
     */
    SummaryDeserializer() {
        this(null);
    }

    /**
     * Creates a new summary deserializer.
     *
     * @param aClass A class
     */
    SummaryDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a Summary from its JSON structure.
     */
    @Override
    public Summary deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        return new Summary(getI18nStrings(aParser.getCodec().readTree(aParser)));
    }
}
