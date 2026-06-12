
package info.freelibrary.iiif.presentation.v3.services;

/**
 * An access cookie service that uses the external pattern. With this pattern the user is expected to have already
 * acquired the appropriate cookie, and the access cookie service will not be used at all.
 */
public class ExternalCookieService1 extends AbstractCookieService<ExternalCookieService1> implements AuthCookieService {

    /**
     * Creates an access cookie service that uses the external pattern.
     */
    public ExternalCookieService1() {
        super(AuthCookieService.Profile.EXTERNAL);
    }

    /**
     * Creates a new access cookie service from the supplied one.
     *
     * @param aService A service to copy
     */
    public ExternalCookieService1(final ExternalCookieService1 aService) {
        super(AuthCookieService.Profile.EXTERNAL);
        aService.copyTo(this);
    }

    /**
     * Creates a copy of this service.
     *
     * @return A copy of this service
     */
    @Override
    public ExternalCookieService1 copy() {
        return new ExternalCookieService1(this);
    }
}
