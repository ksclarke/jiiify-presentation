
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * A serializer that can change the serialization of an {@code AnnotationPage}.
 */
public class AnnotationPageSerializer extends StdSerializer<AnnotationPage<?>> {

    /** The {@code serialVersionUID} for the {@code AnnotationPageSerializer} class. */
    private static final long serialVersionUID = -4520016192187749276L;

    /**
     * Creates a new <code>AnnotationPageSerializer</code> that will serialize to URIs if requested.
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public AnnotationPageSerializer() {
        super(AnnotationPage.class, false);
    }

    @Override
    public void serialize(final AnnotationPage<?> anAnnotationPage, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final boolean useURIs = Boolean.TRUE.equals(aProvider.getAttribute(JSON.URI_LINKS));

        if (useURIs) {
            aJsonGenerator.writeString(anAnnotationPage.getID());
        } else {
            aProvider.findValueSerializer(AnnotationPage.class).serialize(anAnnotationPage, aJsonGenerator, aProvider);
        }
    }
}
