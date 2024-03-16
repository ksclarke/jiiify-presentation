
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;

public interface AnnotatedContent<T> {

    /**
     * Gets the content resource's annotations.
     *
     * @return A list of annotations
     */
    List<AnnotationPage<WebAnnotation>> getAnnotations();

    /**
     * Sets the content resource's annotations.
     *
     * @param aAnnotationList A list of annotations
     * @return The content resource
     */
    T setAnnotations(final List<AnnotationPage<WebAnnotation>> aAnnotationList);

    /**
     * Sets the content resource's annotations.
     *
     * @param aAnnotationArray An array of annotations
     * @return The content resource
     */
    @SuppressWarnings(JDK.UNCHECKED)
    T setAnnotations(final AnnotationPage<WebAnnotation>... aAnnotationArray);

}
