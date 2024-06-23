
package info.freelibrary.iiif.presentation.v3;

/**
 * An interface for content resources that have temporal characteristics.
 */
public interface TemporalContentResource extends ContentResource {

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
    TemporalContentResource setDuration(Number aDuration);

}
