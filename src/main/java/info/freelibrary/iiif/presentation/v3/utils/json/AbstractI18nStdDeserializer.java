
package info.freelibrary.iiif.presentation.v3.utils.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import info.freelibrary.iiif.presentation.v3.properties.I18n;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract deserializer that our other deserializers can extend.
 *
 * @param <T> The class being deserialized
 */
abstract class AbstractI18nStdDeserializer<T> extends StdDeserializer<T> {

    /**
     * The <code>serialVersionUID</code> for AbstractI18nStdDeserializer.
     */
    @Serial
    private static final long serialVersionUID = 433758172553865194L;

    /**
     * Creates a new deserializer.
     */
    AbstractI18nStdDeserializer() {
        this(null);
    }

    /**
     * Creates a new deserializer.
     *
     * @param aClass A class
     */
    AbstractI18nStdDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Parses the I18n from a I18nProperty node.
     *
     * @param aPropertyNode A property that extends the I18nProperty class
     * @return An array of I18n
     */
    protected I18n[] getI18nStrings(final JsonNode aPropertyNode) {
        final Iterator<String> iterator = aPropertyNode.fieldNames();
        final List<I18n> i18n = new ArrayList<>();

        while (iterator.hasNext()) {
            final String langTag = iterator.next();
            final List<JsonNode> nodes = aPropertyNode.findValues(langTag);

            nodes.forEach(jsonNode -> {
                if (jsonNode.isArray()) {
                    final ArrayNode arrayNode = (ArrayNode) jsonNode;
                    final List<String> langStrings = new ArrayList<>();

                    for (int index = 0; index < arrayNode.size(); index++) {
                        langStrings.add(arrayNode.get(index).asText());
                    }

                    i18n.add(new I18n(langTag, langStrings));
                }
            });
        }

        return i18n.toArray(new I18n[0]);
    }
}
