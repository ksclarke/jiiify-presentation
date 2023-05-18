
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.iiif.presentation.v3.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotations.Target;

/**
 * A serializer for {@code Annotation.Target}(s).
 */
public class AnnotationTargetSerializer extends StdSerializer<Target> {

    /** The <code>serialVersionUID</code> for a <code>AnnotationTargetSerializer</code>. */
    private static final long serialVersionUID = -5745518273140398531L;

    /**
     * Creates a new <code>AnnotationTargetSerializer</code>.
     */
    public AnnotationTargetSerializer() {
        super(Target.class, true);
    }

    @Override
    public void serialize(final Target aTarget, final JsonGenerator aJsonGenerator, final SerializerProvider aProvider)
            throws IOException, JsonProcessingException {
        final Optional<SpecificResource> specificResource = aTarget.getSpecificResource();

        if (specificResource.isPresent()) {
            aJsonGenerator.writeObject(specificResource.get());
        } else {
            aJsonGenerator.writeObject(aTarget.getURI());
        }
    }

    @Override
    public void serializeWithType(final Target aTarget, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer)
            throws IOException, JsonProcessingException {
        // This serializes a target when type is called, like when processing a list of targets
        serialize(aTarget, aJsonGenerator, aProvider);
    }
}
