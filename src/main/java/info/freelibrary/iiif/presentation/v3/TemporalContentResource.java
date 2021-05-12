
package info.freelibrary.iiif.presentation.v3;

/**
 * An interface for content resources that have temporal characteristics.
 *
 * @param <T> A type of temporal content resource
 */
public interface TemporalContentResource<T extends TemporalContentResource<T>> extends ContentResource<T> {

    /**
     * Gets the duration of this content resource.
     *
     * @return The duration of this content resource
     */
    float getDuration();

    /**
     * Sets the duration of this content resource.
     *
     * @param aDuration The duration of this content resource
     * @return This content resource
     */
    T setDuration(Number aDuration);

}
