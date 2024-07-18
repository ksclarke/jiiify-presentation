
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import info.freelibrary.iiif.presentation.v3.annotations.AssessingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ClassifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.CommentingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.DescribingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.EditingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.HighlightingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.IdentifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.LinkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ModeratingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.Purpose;
import info.freelibrary.iiif.presentation.v3.annotations.QuestioningAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ReplyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.TaggingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * Tests of the {@code WebAnnotationDeserializer}.
 */
public class WebAnnotationDeserializerTest {

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeAssessingAnnotation() throws JsonProcessingException {
        final AssessingAnnotation annotation = JSON.getReader(AssessingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "assessing",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some assessment of the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.ASSESSING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeBookmarkingAnnotation() throws JsonProcessingException {
        final BookmarkingAnnotation annotation = JSON.getReader(BookmarkingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "bookmarking",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some bookmark for the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.BOOKMARKING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeClassifyingAnnotation() throws JsonProcessingException {
        final ClassifyingAnnotation annotation = JSON.getReader(ClassifyingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "classifying",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some classification of the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.CLASSIFYING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeCommentingAnnotation() throws JsonProcessingException {
        final CommentingAnnotation annotation = JSON.getReader(CommentingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "commenting",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some comment on the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.COMMENTING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeDescribingAnnotation() throws JsonProcessingException {
        final DescribingAnnotation annotation = JSON.getReader(DescribingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "describing",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some description of the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.DESCRIBING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeEditingAnnotation() throws JsonProcessingException {
        final EditingAnnotation annotation = JSON.getReader(EditingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "editing",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some edit for the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.EDITING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeHighlightingAnnotation() throws JsonProcessingException {
        final HighlightingAnnotation annotation = JSON.getReader(HighlightingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "highlighting",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some highlight of the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.HIGHLIGHTING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeIdentifyingAnnotation() throws JsonProcessingException {
        final IdentifyingAnnotation annotation = JSON.getReader(IdentifyingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "identifying",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some identication of the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.IDENTIFYING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeLinkingAnnotation() throws JsonProcessingException {
        final LinkingAnnotation annotation = JSON.getReader(LinkingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "linking",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some link for the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.LINKING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeModeratingAnnotation() throws JsonProcessingException {
        final ModeratingAnnotation annotation = JSON.getReader(ModeratingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "moderating",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some moderation for the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.MODERATING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeQuestioningAnnotation() throws JsonProcessingException {
        final QuestioningAnnotation annotation = JSON.getReader(QuestioningAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "questioning",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some question about the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.QUESTIONING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeReplyingAnnotation() throws JsonProcessingException {
        final ReplyingAnnotation annotation = JSON.getReader(ReplyingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "replying",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some reply about the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.REPLYING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeTaggingAnnotation() throws JsonProcessingException {
        final TaggingAnnotation annotation = JSON.getReader(TaggingAnnotation.class).readValue("""
            {
                "id": "https://example.org/asdf/anno-1",
                "type": "Annotation",
                "motivation": "tagging",
                "body": {
                    "type": "TextualBody",
                    "language": "en",
                    "format": "text/plain",
                    "value": "Some tag for the canvas"
                },
                "target": "https://example.org/asdf/canvas-1"
            }
            """);

        assertTrue(Purpose.TAGGING.isSameAs(annotation.getMotivation().orElseThrow()));
    }

    /**
     * Test method for {@link WebAnnotationDeserializer#deserialize(JsonParser, DeserializationContext)}.
     */
    @Test
    public void testDeserializeWebAnnotation() throws JsonProcessingException {
        final WebAnnotation annotation = JSON.getReader(WebAnnotation.class).readValue("""
            {
                "@context": "http://www.w3.org/ns/anno.jsonld",
                "id": "http://example.org/anno9",
                "type":"Annotation",
                "body": {
                    "type" : "TextualBody",
                    "value" : "<p>Comment text</p>",
                    "format" : "text/html",
                    "language" : "en"
                },
                "target": "http://example.org/photo1"
            }
            """);

        assertTrue(annotation.getMotivation().isEmpty());
    }

}
