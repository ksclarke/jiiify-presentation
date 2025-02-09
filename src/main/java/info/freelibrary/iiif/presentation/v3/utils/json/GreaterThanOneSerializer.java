
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * A JSON serializer that doesn't serializer integers when they are less than one.
 */
public class GreaterThanOneSerializer extends StdSerializer<Integer> {

    private static final long serialVersionUID = 419755030663923313L;

    /**
     * Creates a serializer to integers that must be greater than one to be output.
     */
    public GreaterThanOneSerializer() {
        super(Integer.class);
    }

    @Override
    public void serialize(final Integer aIntValue, final JsonGenerator aJsonGenerator,
            final SerializerProvider aSerializer) throws IOException {
        if (aIntValue != null && aIntValue > 1) {
            aJsonGenerator.writeNumber(aIntValue);
        }
    }
}
