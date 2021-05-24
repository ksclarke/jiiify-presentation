
package info.freelibrary.iiif.presentation.v3.services;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * A service for which there isn't yet a Jiiify Presentation implementation.
 *
 * @param <T> A type of service
 */
public interface OtherService<T extends Service<T>> {

    /**
     * Sets the profile of this other service.
     *
     * @param aProfile The other service's profile
     * @return The service
     */
    T setProfile(String aProfile);

    /**
     * Sets the format of this other service.
     *
     * @param aFormat The other service's format.
     * @return The service
     */
    T setFormat(String aFormat);

}
