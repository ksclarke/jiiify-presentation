
package info.freelibrary.iiif.presentation.v3.annotations;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.TextualBody;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;

/**
 * Tests of {@code TaggingAnnotation}.
 */
public class TaggingAnnotationTest {

    /** An annotation that can be used in testing. */
    private static final File JSON_FILE = new File("src/test/resources/json/tagging-annotation.json");

    /** An annotation ID. */
    private static final String ANNO_ID = "https://iiif.io/api/cookbook/recipe/0021-tagging/annotation/p0002-tag";

    /** A canvas ID. */
    private static final String CANVAS_ID = "https://iiif.io/api/cookbook/recipe/0021-tagging/canvas/p1";

    /**
     * Tests the {@code TaggingAnnotation#toString()}.
     *
     * @throws JsonProcessingException If there is trouble serializing a <code>TaggingAnnotation</code>
     * @throws IOException If there is trouble reading the test JSON annotation file
     */
    @Test
    public final void testToString() throws JsonProcessingException, IOException {
        final String expected = StringUtils.read(JSON_FILE, StandardCharsets.UTF_8);
        final TaggingAnnotation tagging = new TaggingAnnotation(ANNO_ID, new Canvas(CANVAS_ID));
        final TextualBody body = new TextualBody();

        body.setLanguage("de").setFormat(MediaType.TEXT_PLAIN).setValue("Gänseliesel-Brunnen");
        tagging.setBody(body);

        assertEquals(expected, tagging.toString());
    }

}
