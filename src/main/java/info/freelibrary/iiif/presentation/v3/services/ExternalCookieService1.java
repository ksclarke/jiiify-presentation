
package info.freelibrary.iiif.presentation.v3.services;

/**
 * An access cookie service that uses the external pattern. With this pattern the user is expected to have already
 * acquired the appropriate cookie, and the access cookie service will not be used at all.
 */
public class ExternalCookieService1 extends AbstractCookieService<ExternalCookieService1>
        implements AuthCookieService<ExternalCookieService1> {

    /**
     * Creates an access cookie service that uses the external pattern.
     */
    public ExternalCookieService1() {
        super(AuthCookieService.Profile.EXTERNAL);
    }
}
