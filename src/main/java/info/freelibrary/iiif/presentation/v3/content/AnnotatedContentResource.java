
package info.freelibrary.iiif.presentation.v3.content;

import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.util.warnings.JDK;

import java.util.List;

/**
 * An interface for {@code ContentResources} that can have annotations.
 *
 * @param <T> A type of <code>ContentResource</code>
 */
public interface AnnotatedContentResource<T extends AnnotatedContentResource<T>> extends ContentResource<T> {

    /**
     * Gets the content resource's annotations.
     *
     * @return A list of annotations
     */
    List<AnnotationPage<WebAnnotation>> getAnnotations();

    /**
     * Sets the content resource's annotations.
     *
     * @param aAnnotationArray An array of annotations
     * @return The content resource
     */
    @SuppressWarnings(JDK.UNCHECKED)
    T setAnnotations(AnnotationPage<WebAnnotation>... aAnnotationArray);

    /**
     * Sets the content resource's annotations.
     *
     * @param aAnnotationList A list of annotations
     * @return The content resource
     */
    T setAnnotations(List<AnnotationPage<WebAnnotation>> aAnnotationList);

}
