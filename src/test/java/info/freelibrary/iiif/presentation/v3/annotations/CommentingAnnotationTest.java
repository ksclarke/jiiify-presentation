
package info.freelibrary.iiif.presentation.v3.annotations;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.TextualBody;

/**
 * Tests of {@code CommentingAnnotation}.
 */
public class CommentingAnnotationTest {

    /** An annotation that can be used in testing. */
    private static final File JSON_FILE = new File("src/test/resources/json/commenting-annotation.json");

    /** An annotation ID. */
    private static final String ANNO_ID =
            "https://iiif.io/api/cookbook/recipe/0266-full-canvas-annotation/canvas-1/annopage-2/anno-1";

    /** A canvas ID. */
    private static final String CANVAS_ID = "https://iiif.io/api/cookbook/recipe/0266-full-canvas-annotation/canvas-1";

    /**
     * Tests the {@code CommentingAnnotation#toString()}.
     *
     * @throws JsonProcessingException If there is trouble serializing a <code>CommentingAnnotation</code>
     * @throws IOException If there is trouble reading the test JSON annotation file
     */
    @Test
    public final void testToString() throws JsonProcessingException, IOException {
        final String expected = StringUtils.read(JSON_FILE, StandardCharsets.UTF_8);
        final CommentingAnnotation commenting = new CommentingAnnotation(ANNO_ID, new Canvas(CANVAS_ID));
        final TextualBody body = new TextualBody();

        body.setLanguage("de").setFormat(MediaType.TEXT_PLAIN).setValue("Göttinger Marktplatz mit Gänseliesel Brunnen");
        commenting.setBodies(body);

        assertEquals(expected, commenting.toString());
    }

}
