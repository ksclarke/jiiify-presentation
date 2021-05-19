
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;

import info.freelibrary.iiif.presentation.v3.properties.TimeMode;

/**
 * An interface that defines methods related to content annotations.
 *
 * @param <T> A class that implements {@code ContentAnnotation}
 */
interface ContentAnnotation<T extends ContentAnnotation<T>> {

    /**
     * Clears the content resources.
     *
     * @return This annotation
     */
    T clearBodies();

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
    T setBodies(AnnotationBody<?>... aContentArray);

    /**
     * Sets the content resources.
     *
     * @param aContentList A list of content resources
     * @return This annotation
     */
    T setBodies(List<AnnotationBody<?>> aContentList);

    /**
     * Adds content resources to this annotation.
     *
     * @param aContentArray An array of content resources
     * @return This annotation
     */
    T addBodies(AnnotationBody<?>... aContentArray);

    /**
     * Adds content resources to this annotation.
     *
     * @param aContentList A list of content resources
     * @return This annotation
     */
    T addBodies(List<AnnotationBody<?>> aContentList);

    /**
     * Gets the target of this annotation.
     * <p>
     * The return value is an instance of either {@link URI} or {@link SpecificResource}. Users should check the type
     * with <code>instanceof</code> and then cast as appropriate.
     * </p>
     *
     * @return The target
     */
    Object getTarget();

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
