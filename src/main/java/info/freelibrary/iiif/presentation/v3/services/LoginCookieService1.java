
package info.freelibrary.iiif.presentation.v3.services;

/**
 * An access cookie service that uses the login pattern. Using this service, the user will be required to log in using a
 * separate window with a UI provided by an external authentication system.
 */
public class LoginCookieService1 extends AbstractUserMediatedService<LoginCookieService1>
        implements UserMediatedCookieService {

    /**
     * Creates a new access cookie service using the login pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public LoginCookieService1(final String aID, final String aLabel) {
        super(AuthCookieService.Profile.LOGIN, aID, aLabel);
    }
}
