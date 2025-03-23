
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import info.freelibrary.iiif.presentation.v3.annotation.ContentStateAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.Motivation;
import info.freelibrary.iiif.presentation.v3.annotation.Purpose;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;

/**
 * Tests for the {@link ContentStateSerializer}.
 */
public class ContentStateSerializerTest {

    /** A JSON generator. */
    private JsonGenerator myJsonGenerator;

    /** A serializer provider. */
    private SerializerProvider myProvider;

    /** A content state serializer. */
    private ContentStateSerializer mySerializer;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        mySerializer = new ContentStateSerializer();
        myJsonGenerator = mock(JsonGenerator.class);
        myProvider = mock(SerializerProvider.class);
    }

    /**
     * Tests that a label is serialized if present.
     */
    @Test
    public void testSerializeWithLabel() throws IOException {
        final ContentStateAnnotation annotation = mock(ContentStateAnnotation.class);
        final Label label = new Label("en", "Test");

        when(annotation.getID()).thenReturn("http://example.org/csa1");
        when(annotation.getContexts()).thenReturn(List.of());
        when(annotation.getMotivation()).thenReturn(Optional.empty());
        when(annotation.getLabel()).thenReturn(Optional.of(label));
        when(annotation.getTimeMode()).thenReturn(Optional.empty());
        when(annotation.getBody()).thenReturn(Collections.emptyList());
        when(annotation.getTargets()).thenReturn(Collections.emptyList());

        mySerializer.serialize(annotation, myJsonGenerator, myProvider);
        verify(myJsonGenerator).writeObjectField("label", label);
    }

    /**
     * Tests that a motivation is serialized when present.
     */
    @Test
    public void testSerializeWithMotivation() throws IOException {
        final ContentStateAnnotation annotation = mock(ContentStateAnnotation.class);
        final Motivation motivation = Motivation.fromLabel(Purpose.PAINTING);

        when(annotation.getID()).thenReturn("http://example.org/csa2");
        when(annotation.getContexts()).thenReturn(List.of());
        when(annotation.getMotivation()).thenReturn(Optional.of(motivation));
        when(annotation.getLabel()).thenReturn(Optional.empty());
        when(annotation.getTimeMode()).thenReturn(Optional.empty());
        when(annotation.getBody()).thenReturn(Collections.emptyList());
        when(annotation.getTargets()).thenReturn(Collections.emptyList());

        mySerializer.serialize(annotation, myJsonGenerator, myProvider);
        verify(myJsonGenerator).writeObjectField("motivation", motivation.toString());
    }

    /**
     * Tests that timeMode is serialized when present.
     */
    @Test
    public void testSerializeWithTimeMode() throws IOException {
        final ContentStateAnnotation annotation = mock(ContentStateAnnotation.class);
        final TimeMode timeMode = TimeMode.LOOP;

        when(annotation.getID()).thenReturn("http://example.org/csa3");
        when(annotation.getContexts()).thenReturn(List.of());
        when(annotation.getMotivation()).thenReturn(Optional.empty());
        when(annotation.getLabel()).thenReturn(Optional.empty());
        when(annotation.getTimeMode()).thenReturn(Optional.of(timeMode));
        when(annotation.getBody()).thenReturn(Collections.emptyList());
        when(annotation.getTargets()).thenReturn(Collections.emptyList());

        mySerializer.serialize(annotation, myJsonGenerator, myProvider);

        verify(myJsonGenerator).writeObjectField("timeMode", timeMode);
    }
}
