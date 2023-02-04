
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import info.freelibrary.iiif.presentation.v3.annotations.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.ClassifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.CommentingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.DescribingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.IdentifyingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.LinkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotations.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.TaggingAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An interface that defines methods related to annotations.
 *
 * @param <T> A class that implements {@code Annotation}
 */
// @JsonDeserialize(using = AnnotationDeserializer.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = JsonKeys.MOTIVATION,
        visible = true)
@JsonSubTypes({ //
    @Type(value = PaintingAnnotation.class, name = "painting"),
    @Type(value = SupplementingAnnotation.class, name = "supplementing"),
    @Type(value = BookmarkingAnnotation.class, name = "bookmarking"),
    @Type(value = ClassifyingAnnotation.class, name = "classifying"),
    @Type(value = CommentingAnnotation.class, name = "commenting"),
    @Type(value = DescribingAnnotation.class, name = "describing"),
    @Type(value = IdentifyingAnnotation.class, name = "identifying"),
    @Type(value = LinkingAnnotation.class, name = "linking"),
    @Type(value = TaggingAnnotation.class, name = "tagging") })
public interface Annotation<T extends Annotation<T>> {

    /**
     * Gets the annotation's ID.
     *
     * @return The annotation's ID.
     */
    String getID();

    /**
     * Sets the annotation ID.
     *
     * @param aID An ID
     * @return The annotation
     */
    T setID(String aID);

    /**
     * Gets the content resources associated with this annotation.
     *
     * @return The content resources associated with this annotation
     */
    List<AnnotationBody<?>> getBodies();

    /**
     * Sets the content resources.
     *
     * @param aContentArray An array of content resources
     * @return This annotation
     */
    @JsonIgnore
    T setBodies(AnnotationBody<?>... aContentArray);

    /**
     * Sets the content resources.
     *
     * @param aContentList A list of content resources
     * @return This annotation
     */
    @JsonIgnore
    T setBodies(List<AnnotationBody<?>> aContentList);

    /**
     * Gets the URI of the target of this annotation.
     * <p>
     * The return value is an instance of either {@link URI} or {@link SpecificResource}.
     * </p>
     *
     * @return The target
     */
    URI getTargetURI();

    /**
     * Gets the target if it's a specific resource; otherwise, it returns an empty {@link Optional}.
     *
     * @return The target if it's a specific resource
     */
    Optional<SpecificResource> getSpecificResourceTarget();

    /**
     * Sets the target of this annotation.
     *
     * @param aURI A target URI
     * @return This annotation
     */
    T setTarget(URI aURI);

    /**
     * Sets the target of this annotation in string form.
     *
     * @param aURI A target URI supplied in string form
     * @return This annotation
     */
    T setTarget(String aURI);

    /**
     * Sets the target of this annotation.
     *
     * @param aSpecificResource A target specific resource
     * @return This annotation
     */
    T setTarget(SpecificResource aSpecificResource);

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    String getMotivation();

    /**
     * Gets the time mode.
     *
     * @return The time mode
     */
    TimeMode getTimeMode();

    /**
     * Sets the time mode.
     *
     * @param aTimeMode A time mode
     * @return This annotation
     */
    T setTimeMode(TimeMode aTimeMode);

}
