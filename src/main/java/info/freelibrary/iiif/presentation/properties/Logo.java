
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.Optional;

import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.services.ImageInfoService;

public interface Logo {

    /**
     * Sets the logo's associated service
     *
     * @param aService The logo's associated service
     * @return The logo
     */
    Logo setService(ImageInfoService aService);

    /**
     * Gets the logo's associated service.
     *
     * @return The logo's associated service
     */
    Optional<ImageInfoService> getService();

    /**
     * Sets the logo ID.
     *
     * @param aID The logo's ID
     * @return The logo
     */
    Logo setID(URI aID);

    /**
     * Sets the logo ID in string form.
     *
     * @param aID The logo's ID in string form
     * @return The logo
     */
    Logo setID(String aID);

    /**
     * Gets the logo ID
     *
     * @return The logo's ID
     */
    URI getID();

    /**
     * Gets the logo's height.
     *
     * @return The logo's height
     */
    int getHeight();

    /**
     * Gets the logo's width.
     *
     * @return The logo's width
     */
    int getWidth();

    /**
     * Sets the width and height of the logo.
     *
     * @param aWidth An image width
     * @param aHeight An image height
     * @return The logo
     */
    Logo setWidthHeight(int aWidth, int aHeight);

    /**
     * Gets the media type format of the logo.
     *
     * @return The media type format of the logo
     */
    Optional<MediaType> getFormatMediaType();

    /**
     * Gets the format of the logo.
     *
     * @return A string representation of the logo's format
     */
    String getFormat();

    /**
     * Gets the media type format of the logo.
     *
     * @param aMediaType A media type to set
     * @return The media type format of the logo
     */
    Logo setFormatMediaType(MediaType aMediaType);

    /**
     * Sets the format of the logo from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The logo
     */
    Logo setFormat(String aMediaType);

    /**
     * Gets the logo type.
     *
     * @return The logo's type
     */
    String getType();
}
