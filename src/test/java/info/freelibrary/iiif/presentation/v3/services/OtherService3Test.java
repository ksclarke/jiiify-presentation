
package info.freelibrary.iiif.presentation.v3.services;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertOptEquals;
import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.exts.geo.Properties;
import info.freelibrary.iiif.presentation.v3.services.OtherService.Profile;

/**
 * Tests of {@code OtherService3}.
 */
public class OtherService3Test {

    /** An ID to be used in testing. */
    private static final String SERVICE_ID = "https://example.org/service";

    /** A service profile to be used in testing. */
    private static final OtherService3.Profile SERVICE_PROFILE =
            new OtherService3.Profile("https://example.org/docs/service");

    /** A service type to by used in testing. */
    private static final String SERVICE_TYPE = "ExampleExtensionService";

    /**
     * Test method for {@link OtherService3#OtherService3(String)}.
     */
    @Test
    public void testOtherService3String() {
        assertOptEquals(SERVICE_ID, new OtherService3(SERVICE_ID).getID());
    }

    /**
     * Test method for {@link OtherService3#OtherService3(String, Profile)}.
     */
    @Test
    public void testOtherService3StringProfile() {
        final OtherService3 service = new OtherService3(SERVICE_ID, SERVICE_PROFILE);

        assertOptEquals(SERVICE_ID, service.getID());
        assertEquals(SERVICE_PROFILE, service.getProfile().get());
    }

    /**
     * Test method for {@link OtherService3#OtherService3(String, String)}.
     */
    @Test
    public void testOtherService3StringString() {
        final OtherService3 service = new OtherService3(SERVICE_ID, SERVICE_TYPE);

        assertOptEquals(SERVICE_ID, service.getID());
        assertOptEquals(SERVICE_TYPE, service.getType());
    }

    /**
     * Test method for {@link OtherService3#OtherService3(String, String, Profile)}.
     */
    @Test
    public void testOtherService3StringStringProfile() {
        final OtherService3 service = new OtherService3(SERVICE_ID, SERVICE_TYPE, SERVICE_PROFILE);

        assertOptEquals(SERVICE_ID, service.getID());
        assertOptEquals(SERVICE_TYPE, service.getType());
        assertOptEquals(SERVICE_PROFILE, service.getProfile());
    }

    /**
     * Test method the {@link Service.Profile#equals(Object)}.
     */
    @Test
    public void testOtherServiceProfileEquals() {
        final OtherService3 service = new OtherService3(SERVICE_ID, SERVICE_TYPE, SERVICE_PROFILE);
        final Service.Profile profile = service.getProfile().get();

        assertEquals(profile, profile);
    }

    /**
     * Tests {@link Service.Profile#equals(Object)}.
     */
    @Test
    public final void testOtherServiceProfileEqualsHashCode() {
        final OtherService3 service1 = new OtherService3(SERVICE_ID, SERVICE_TYPE, SERVICE_PROFILE);
        final Service.Profile profile1 = service1.getProfile().get();

        final OtherService3 service2 = new OtherService3(SERVICE_ID, SERVICE_TYPE, SERVICE_PROFILE);
        final Service.Profile profile2 = service2.getProfile().get();

        assertEquals(profile1.hashCode(), profile2.hashCode());
    }

    /**
     * Tests {@link OtherService.Profile#equals(Object)}.
     */
    @Test
    public final void testOtherServiceProfileEqualsSame() {
        assertEquals(SERVICE_PROFILE, SERVICE_PROFILE);
    }

    /**
     * Test method the {@link Service.Profile#equals(Object)}.
     */
    @Test
    public void testOtherServiceProfileNotEqualsEmpty() {
        final OtherService3 service = new OtherService3(SERVICE_ID, SERVICE_TYPE, SERVICE_PROFILE);
        final Service.Profile profile = service.getProfile().get();

        assertNotEquals(profile, EMPTY);
    }

    /**
     * Test method the {@link Service.Profile#equals(Object)}.
     */
    @Test
    public void testOtherServiceProfileNotEqualsNull() {
        final OtherService3 service = new OtherService3(SERVICE_ID, SERVICE_TYPE, SERVICE_PROFILE);
        final Service.Profile profile = service.getProfile().get();

        assertNotEquals(profile, null);
    }

    /**
     * Tests {@link Properties#equals(Object) Properties}.
     */
    @Test
    public final void testPropertiesEqualsSameNot() {
        assertNotEquals(SERVICE_PROFILE, Service.Profile.fromLabel("https://FAKE_PROFILE"));
    }
}
