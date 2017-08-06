
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.helpers.Constants;
import info.freelibrary.iiif.presentation.services.ImageInfoService;

/**
 * A logo property.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Logo extends ServiceProperty<Logo> {

    /**
     * Creates a logo property.
     *
     * @param aID A logo ID
     */
    public Logo(final String... aID) {
        addImage(aID);
    }

    /**
     * Creates a logo property.
     *
     * @param aID A logo ID
     */
    public Logo(final URI... aID) {
        addImage(aID);
    }

    /**
     * Creates a logo property.
     *
     * @param aID An ID for the logo image
     * @param aService A service for the logo image
     */
    public Logo(final String aID, final ImageInfoService aService) {
        addImage(URI.create(aID), aService);
    }

    /**
     * Creates a logo property that can contain multiple images. If multiple images are used they should each be
     * different.
     *
     * @param aID An ID for the logo image
     * @param aService A service for the logo image
     */
    public Logo(final URI aID, final ImageInfoService aService) {
        addImage(aID, aService);
    }

    @Override
    @JsonGetter(Constants.LOGO)
    protected Object getValue() {
        return super.getValue();
    }

}
