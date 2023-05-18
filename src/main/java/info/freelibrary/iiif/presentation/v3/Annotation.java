
package info.freelibrary.iiif.presentation.v3;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
import info.freelibrary.iiif.presentation.v3.annotations.Motivation;
import info.freelibrary.iiif.presentation.v3.annotations.QuestioningAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ReplyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.TaggingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.Target;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An interface that defines methods related to the Web Annotation Data Model as it's used by IIIF.
 *
 * @param <A> A class that implements {@code Annotation}
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = JsonKeys.MOTIVATION,
        visible = true)
@JsonSubTypes({ //
    @Type(value = AssessingAnnotation.class, name = "assessing"),
    @Type(value = BookmarkingAnnotation.class, name = "bookmarking"),
    @Type(value = ClassifyingAnnotation.class, name = "classifying"),
    @Type(value = CommentingAnnotation.class, name = "commenting"),
    @Type(value = DescribingAnnotation.class, name = "describing"),
    @Type(value = EditingAnnotation.class, name = "editing"),
    @Type(value = HighlightingAnnotation.class, name = "highlighting"),
    @Type(value = IdentifyingAnnotation.class, name = "identifying"),
    @Type(value = LinkingAnnotation.class, name = "linking"),
    @Type(value = ModeratingAnnotation.class, name = "moderating"),
    @Type(value = PaintingAnnotation.class, name = "painting"),
    @Type(value = QuestioningAnnotation.class, name = "questioning"),
    @Type(value = ReplyingAnnotation.class, name = "replying"),
    @Type(value = SupplementingAnnotation.class, name = "supplementing"),
    @Type(value = TaggingAnnotation.class, name = "tagging") //
})
public interface Annotation<A extends Annotation<A>> {

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if body contains a choice; else, false
     */
    boolean bodyHasChoice();

    /**
     * Gets the annotation body's resources.
     *
     * @return The resources associated with this annotation
     */
    List<ContentResource<?>> getBody();

    /**
     * Gets the annotation's ID.
     *
     * @return The annotation's ID.
     */
    String getID();

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    Motivation getMotivation();

    /**
     * Gets the target of this annotation.
     *
     * @return The target
     */
    Target getTarget();

    /**
     * Gets the optional time mode.
     *
     * @return The time mode
     */
    Optional<TimeMode> getTimeMode();

    /**
     * Sets the annotation body's resources.
     *
     * @param aResourceArray An array of resources
     * @return This annotation
     */
    @JsonIgnore
    A setBody(ContentResource<?>... aResourceArray);

    /**
     * Sets the annotation body's resources.
     *
     * @param aResourceList A list of resources
     * @return This annotation
     */
    A setBody(List<ContentResource<?>> aResourceList);

    /**
     * Sets whether the annotation body contains a choice.
     *
     * @param aChoice A boolean indicating whether the annotation's body contains a choice
     * @return This annotation
     */
    A setChoice(boolean aChoice);

    /**
     * Sets the annotation ID.
     *
     * @param aID An ID
     * @return The annotation
     */
    A setID(String aID);

    /**
     * Sets the annotation's motivation.
     *
     * @param aMotivation A motivation
     * @return The annotation
     */
    A setMotivation(Motivation aMotivation);

    /**
     * Sets the target of this annotation.
     *
     * @param aTarget An annotation target
     * @return This annotation
     */
    A setTarget(Target aTarget);

    /**
     * Sets the time mode.
     *
     * @param aTimeMode A time mode
     * @return This annotation
     */
    A setTimeMode(TimeMode aTimeMode);
}
