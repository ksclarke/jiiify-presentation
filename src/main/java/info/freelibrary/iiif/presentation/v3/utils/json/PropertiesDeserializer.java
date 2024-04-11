
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import info.freelibrary.iiif.presentation.v3.exts.geo.Properties;
import info.freelibrary.iiif.presentation.v3.properties.I18n;
import info.freelibrary.iiif.presentation.v3.properties.Property;

/**
 * A deserializer for GeoJSON properties.
 */
public class PropertiesDeserializer extends StdDeserializer<Properties> {

    /** The <code>serialVersionUID</code> for the <code>PropertiesDeserializer</code>. */
    private static final long serialVersionUID = 4319399180670985903L;

    /**
     * Creates a new properties deserializer.
     */
    PropertiesDeserializer() {
        this(null);
    }

    /**
     * Creates a new properties deserializer.
     *
     * @param aClass A class
     */
    PropertiesDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Properties deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JacksonException {
        final JsonNode currentNode = aParser.getCodec().readTree(aParser);
        final Iterator<Entry<String, JsonNode>> iterator = currentNode.fields();
        final Properties properties = new Properties();

        while (iterator.hasNext()) {
            final Entry<String, JsonNode> entry = iterator.next();

            properties.add(new Property(entry.getKey(), getI18nStrings(entry.getValue())));
        }

        return properties;
    }

    /**
     * Parses the I18n from a I18nProperty node.
     *
     * @param aPropertyNode A property that extends the I18nProperty class
     * @return An array of I18n
     */
    protected I18n[] getI18nStrings(final JsonNode aPropertyNode) {
        final Iterator<Entry<String, JsonNode>> iterator = aPropertyNode.fields();
        final List<I18n> i18n = new ArrayList<>();

        while (iterator.hasNext()) {
            final Entry<String, JsonNode> entry = iterator.next();
            final JsonNode jsonNode = entry.getValue();

            if (jsonNode.isArray()) {
                final ArrayNode arrayNode = (ArrayNode) jsonNode;
                final List<String> langStrings = new ArrayList<>();

                for (int index = 0; index < arrayNode.size(); index++) {
                    langStrings.add(arrayNode.get(index).asText());
                }

                i18n.add(new I18n(entry.getKey(), langStrings));
            }
        }

        return i18n.toArray(new I18n[0]);
    }
}
