
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.annotation.AssessingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.ClassifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.CommentingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.ContentStateAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.DescribingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.EditingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.HighlightingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.IdentifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.LinkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.ModeratingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.Purpose;
import info.freelibrary.iiif.presentation.v3.annotation.QuestioningAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.ReplyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.TaggingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation deserializer that can handle deserializing even if 'motivation' is an array. It basically determines
 * what type of {@code Annotation} is being deserialized and hands off the job to the best deserializer for that class.
 */
@SuppressWarnings({ PMD.EXCESSIVE_IMPORTS })
public class AnnotationDeserializer extends StdDeserializer<Annotation<?>> {

    /** The deserializer's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationDeserializer.class, MessageCodes.BUNDLE);

    /** The {@code serialVersionUID} for the {@code AnnotationDeserializer} class. */
    private static final long serialVersionUID = 431628099542258580L;

    /**
     * Creates a new annotation deserializer.
     *
     * @param aClass A class to be deserialized
     */
    public AnnotationDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Creates a new annotation deserializer.
     */
    AnnotationDeserializer() {
        this(Annotation.class);
    }

    @Override
    public Annotation<?> deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException {
        final JsonNode node = (JsonNode) JSON.readTree(aParser);
        final Optional<Purpose> purpose = getPurpose(node.get(JsonKeys.MOTIVATION));

        if (purpose.isEmpty()) {
            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_152));
        }

        return aContext.readTreeAsValue(node, getAnnotationClass(purpose.get().label()));
    }

    /**
     * Gets the JPv3 annotation subclass from the supplied motivation.
     *
     * @param aMotivation A motivation of the annotation
     * @return An implementation of Annotation
     */
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY })
    private Class<? extends Annotation<?>> getAnnotationClass(final String aMotivation) {
        return switch (aMotivation) {
            case "assessing" -> AssessingAnnotation.class;
            case "bookmarking" -> BookmarkingAnnotation.class;
            case "classifying" -> ClassifyingAnnotation.class;
            case "commenting" -> CommentingAnnotation.class;
            case "contentState" -> ContentStateAnnotation.class;
            case "describing" -> DescribingAnnotation.class;
            case "editing" -> EditingAnnotation.class;
            case "highlighting" -> HighlightingAnnotation.class;
            case "identifying" -> IdentifyingAnnotation.class;
            case "linking" -> LinkingAnnotation.class;
            case "moderating" -> ModeratingAnnotation.class;
            case "painting" -> PaintingAnnotation.class;
            case "questioning" -> QuestioningAnnotation.class;
            case "replying" -> ReplyingAnnotation.class;
            case "supplementing" -> SupplementingAnnotation.class;
            case "tagging" -> TaggingAnnotation.class;
            default -> WebAnnotation.class;
        };
    }

    /**
     * Gets a purpose from the motivation. Note that we don't currently handle multiple motivations, even though that's
     * supported by the Web Annotation spec. We just take the first that we know about.
     *
     * @param aMotivation A JSON node representing the annotation's motivation
     * @return A purpose if the supplied motivation is known and can be handled
     */
    private Optional<Purpose> getPurpose(final JsonNode aMotivation) {
        if (aMotivation == null) {
            return Optional.empty();
        }

        if (aMotivation.isArray() && aMotivation.size() > 0) {
            for (final JsonNode purposeNode : aMotivation) {
                final Optional<Purpose> purpose = Purpose.fromLabel(purposeNode.asText());

                if (purpose.isPresent()) {
                    return purpose;
                }
            }
        } else if (aMotivation.isTextual()) {
            return Purpose.fromLabel(aMotivation.asText());
        }

        return Optional.empty();
    }
}
