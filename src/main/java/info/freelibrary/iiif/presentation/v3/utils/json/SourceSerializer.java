
package info.freelibrary.iiif.presentation.v3.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import info.freelibrary.iiif.presentation.v3.annotation.Source;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

import java.io.IOException;
import java.io.Serial;

/**
 * A serializer for a {@code SpecificResource.Source}.
 */
public class SourceSerializer extends StdSerializer<Source> {

    /** A <code>serialVersionUID</code> for <code>SourceSerializer</code>. */
    @Serial
    private static final long serialVersionUID = 2442075335371467518L;

    /** A context list filter for embedded resources (omit contexts when embedded). */
    private static final ContextFilterProvider FILTER = new ContextFilterProvider();

    /**
     * Creates a new <code>SourceDeserializer</code>.
     */
    public SourceSerializer() {
        super(Source.class, true);
    }

    @Override
    public void serialize(final Source aSource, final JsonGenerator aJsonGenerator, final SerializerProvider aProvider)
            throws IOException {
        final ContentResource resource;

        if (aSource == null) {
            aJsonGenerator.writeNull();
            return;
        }

        // If Source is "just a string" (no embedded ContentResource), output JSON string
        resource = aSource.getResource().orElse(null);

        if (resource == null) {
            aJsonGenerator.writeString(aSource.getID());
            return;
        }

        // Otherwise serialize the embedded ContentResource as an object
        final boolean useURIs = Boolean.TRUE.equals(aProvider.getAttribute(JSON.URI_LINKS));
        final ObjectWriter writer = JSON.copy().setFilterProvider(FILTER).writerFor(resource.getClass());

        aJsonGenerator.writeRawValue(writer.withAttribute(JSON.URI_LINKS, useURIs).writeValueAsString(resource));

    }

    @Override
    public void serializeWithType(final Source aSource, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer) throws IOException {
        // This serializes a source when type is called, like when processing a list of sources
        serialize(aSource, aJsonGenerator, aProvider);
    }
}
