
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.iiif.presentation.v3.properties.I18n;
import info.freelibrary.iiif.presentation.v3.properties.Property;
import info.freelibrary.iiif.presentation.v3.properties.geo.Properties;

/**
 * A serializer for the <code>NavPlaceFeature</code>'s properties.
 */
public class PropertiesSerializer extends StdSerializer<Properties> {

    /** The <code>serialVersionUID</code> for the <code>PropertiesSerializer</code>. */
    private static final long serialVersionUID = -1223488631719356526L;

    /**
     * Creates a new <code>GeometrySerializer</code>.
     */
    public PropertiesSerializer() {
        super(Properties.class, true);
    }

    @Override
    public void serialize(final Properties aProperties, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        aJsonGenerator.writeStartObject();

        for (final Property property : aProperties) {
            aJsonGenerator.writeObjectFieldStart(property.getName());

            for (final I18n i18n : property.getI18ns()) {
                aJsonGenerator.writeArrayFieldStart(i18n.getLang());

                for (final String value : i18n.getStrings().toArray(new String[] {})) {
                    aJsonGenerator.writeString(value);
                }

                aJsonGenerator.writeEndArray();
            }

            aJsonGenerator.writeEndObject();
        }

        aJsonGenerator.writeEndObject();
    }
}
