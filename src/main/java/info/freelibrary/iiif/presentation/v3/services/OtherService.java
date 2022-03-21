
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.Service;

/**
 * A service for which there isn't yet a Jiiify Presentation implementation.
 *
 * @param <T> A type of service
 */
public interface OtherService<T extends OtherService<T>> extends Service<T> {

    /**
     * Sets the profile of this other service.
     *
     * @param aProfile The other service's profile
     * @return The service
     */
    T setProfile(Profile aProfile);

    /**
     * Sets the profile of this other service.
     *
     * @param aProfile The other service's profile in string form.
     * @return The service
     */
    @Override
    T setProfile(String aProfile);

    /**
     * Sets the service type.
     *
     * @param aType A service type
     * @return The service
     */
    @Override
    T setType(String aType);

    /**
     * Sets the format of this service from its string form.
     *
     * @param aFormat The service's format in string form
     * @return The service
     */
    T setFormat(String aFormat);

    /**
     * Sets the media type of this service.
     *
     * @param aMediaType The service's media type
     * @return The service
     */
    T setFormat(MediaType aMediaType);

    /**
     * A profile for other services.
     */
    interface Profile extends Service.Profile {

        @Override
        String string();

        @Override
        URI uri();

    }
}
