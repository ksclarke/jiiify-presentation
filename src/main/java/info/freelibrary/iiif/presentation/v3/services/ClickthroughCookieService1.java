
package info.freelibrary.iiif.presentation.v3.services;

/**
 * An access cookie service that uses the click-through pattern. With this pattern, the user will be required to click a
 * button within the client using content provided in the service description.
 */
public class ClickthroughCookieService1 extends AbstractUserMediatedService<ClickthroughCookieService1>
        implements UserMediatedCookieService {

    /**
     * Creates a new access cookie service using the click-through pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public ClickthroughCookieService1(final String aID, final String aLabel) {
        super(AuthCookieService.Profile.CLICKTHROUGH, aID, aLabel);
    }
}
