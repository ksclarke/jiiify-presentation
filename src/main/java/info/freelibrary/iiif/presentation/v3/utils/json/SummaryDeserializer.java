
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import info.freelibrary.iiif.presentation.v3.properties.Summary;

/**
 * Deserializes summaries from JSON documents into {@link Summary} objects.
 */
public class SummaryDeserializer extends AbstractI18nStdDeserializer<Summary> {

    /**
     * The <code>serialVersionUID</code> of SummarySerializer.
     */
    private static final long serialVersionUID = 6825081668840056682L;

    /**
     * Creates a new summary deserializer.
     */
    public SummaryDeserializer() {
        this(null);
    }

    /**
     * Creates a new summary deserializer.
     *
     * @param aClass A class
     */
    public SummaryDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a Summary from its JSON structure.
     */
    @Override
    public Summary deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        return new Summary(getI18nStrings(aParser.getCodec().readTree(aParser)));
    }
}
