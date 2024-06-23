
package info.freelibrary.iiif.presentation.v3.services;

/**
 * An access cookie service that uses the kiosk pattern. Using this service, the user will not be required to interact
 * with an authentication system, the client is expected to use the access cookie service automatically.
 */
public class KioskCookieService1 extends AbstractCookieService<KioskCookieService1> implements AuthCookieService {

    /**
     * Creates a new access cookie service using the kiosk pattern.
     *
     * @param aID An ID of the service
     */
    public KioskCookieService1(final String aID) {
        super(AuthCookieService.Profile.KIOSK, aID);
    }
}
