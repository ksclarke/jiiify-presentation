
package info.freelibrary.iiif.presentation.properties;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Deserializes a required statement in a JSON document into a {@link RequiredStatement} object.
 */
class RequiredStatementDeserializer extends AbstractI18nStdDeserializer<RequiredStatement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredStatementDeserializer.class,
            Constants.BUNDLE_NAME);

    /**
     * The <code>serialVersionUID</code> of <code>RequiredStatementDeserializer</code>.
     */
    private static final long serialVersionUID = -3905197508023348074L;

    /**
     * Creates a new required statement deserializer.
     */
    RequiredStatementDeserializer() {
        this(null);
    }

    /**
     * Creates a new required statement deserializer.
     *
     * @param aClass A class
     */
    RequiredStatementDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes the required statement in JSON into a RequiredStatement class.
     */
    @Override
    public RequiredStatement deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final RequiredStatement reqStatement = new RequiredStatement();

        if (node.isArray()) {
            for (int index = 0; index < node.size(); index++) {
                final JsonNode reqStatementNode = node.get(index);
                final Label label = new Label(getI18nStrings(reqStatementNode.get(Constants.LABEL)));
                final Value value = new Value(getI18nStrings(reqStatementNode.get(Constants.VALUE)));

                reqStatement.add(label, value);
            }
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_023));
        }

        return reqStatement;
    }

}
