
package info.freelibrary.iiif.presentation.properties;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Deserializes labels from JSON documents into {@link Label} objects.
 */
class LabelDeserializer extends AbstractI18nStdDeserializer<Label> {

    /**
     * The <code>serialVersionUID</code> of LabelSerializer.
     */
    private static final long serialVersionUID = -1205197208026340074L;

    /**
     * Creates a new label deserializer.
     */
    LabelDeserializer() {
        this(null);
    }

    /**
     * Creates a new label deserializer.
     *
     * @param aClass A class
     */
    LabelDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a Label from its JSON structure.
     */
    @Override
    public Label deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        return new Label(getI18nStrings(aParser.getCodec().readTree(aParser)));
    }
}
