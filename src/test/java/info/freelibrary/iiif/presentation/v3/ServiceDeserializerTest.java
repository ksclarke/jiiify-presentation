
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertOptEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.services.ClickthroughCookieService1;
import info.freelibrary.iiif.presentation.v3.services.ExternalCookieService1;
import info.freelibrary.iiif.presentation.v3.services.GeoJsonService;
import info.freelibrary.iiif.presentation.v3.services.ImageService2;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;
import info.freelibrary.iiif.presentation.v3.services.KioskCookieService1;
import info.freelibrary.iiif.presentation.v3.services.LoginCookieService1;
import info.freelibrary.iiif.presentation.v3.services.OtherService2;
import info.freelibrary.iiif.presentation.v3.services.OtherService3;
import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * Tests {@code ServiceDeserializer}.
 */
public class ServiceDeserializerTest {

    /**
     * Tests deserializing a {@code ClickthroughCookieService1}.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test
    public void testDeserializeAuthClickthrough() throws JsonProcessingException {
        final ClickthroughCookieService1 service = JSON.getReader(ClickthroughCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "@id": "https://authentication.example.org/clickthrough",
                "profile": "http://iiif.io/api/auth/1/clickthrough",
                "label": "Terms of Use for Example Institution",
                "header": "Restricted Material with Terms of Use",
                "description": "<span>... terms of use ... </span>",
                "confirmLabel": "I Agree",
                "failureHeader": "Terms of Use Not Accepted",
                "failureDescription": "You must accept the terms of use to see the content."
            }
            """);

        assertOptEquals("https://authentication.example.org/clickthrough", service.getID());
    }

    /**
     * Tests deserializing a {@code ClickthroughCookieService1} without an ID.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test(expected = JsonParseException.class)
    public void testDeserializeAuthClickthroughNoID() throws JsonProcessingException {
        JSON.getReader(ClickthroughCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "profile": "http://iiif.io/api/auth/1/clickthrough",
                "label": "Terms of Use for Example Institution",
                "header": "Restricted Material with Terms of Use",
                "description": "<span>... terms of use ... </span>",
                "confirmLabel": "I Agree",
                "failureHeader": "Terms of Use Not Accepted",
                "failureDescription": "You must accept the terms of use to see the content."
            }
            """);
    }

    /**
     * Tests deserializing a {@code ClickthroughCookieService1} without a label.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test(expected = JsonParseException.class)
    public void testDeserializeAuthClickthroughNoLabel() throws JsonProcessingException {
        JSON.getReader(ClickthroughCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "@id": "https://authentication.example.org/clickthrough",
                "profile": "http://iiif.io/api/auth/1/clickthrough",
                "header": "Restricted Material with Terms of Use",
                "description": "<span>... terms of use ... </span>",
                "confirmLabel": "I Agree",
                "failureHeader": "Terms of Use Not Accepted",
                "failureDescription": "You must accept the terms of use to see the content."
            }
            """);
    }

    /**
     * Tests deserializing a {@code ExternalCookieService1}.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test
    public void testDeserializeAuthExternal() throws JsonProcessingException {
        final ExternalCookieService1 service = JSON.getReader(ExternalCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "profile": "http://iiif.io/api/auth/1/external",
                "label": "External Authentication Required",
                "failureHeader": "Restricted Material",
                "failureDescription": "This material is not viewable without prior agreement"
            }
            """);

        assertOptEquals("Restricted Material", service.getFailureHeader());
    }

    /**
     * Tests deserializing a {@code LoginCookieService1}.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test
    public void testDeserializeAuthKiosk() throws JsonProcessingException {
        final KioskCookieService1 service = JSON.getReader(KioskCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "@id": "https://authentication.example.org/cookiebaker",
                "profile": "http://iiif.io/api/auth/1/kiosk",
                "label": "Internal cookie granting service",
                "failureHeader": "Ooops!",
                "failureDescription": "Call Bob at ext. 1234 to reboot the cookie server"
            }
            """);

        assertOptEquals("https://authentication.example.org/cookiebaker", service.getID());
    }

    /**
     * Tests deserializing a {@code KioskCookieService1}.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test(expected = JsonParseException.class)
    public void testDeserializeAuthKioskNoID() throws JsonProcessingException {
        JSON.getReader(KioskCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "profile": "http://iiif.io/api/auth/1/kiosk",
                "label": "Internal cookie granting service",
                "failureHeader": "Ooops!",
                "failureDescription": "Call Bob at ext. 1234 to reboot the cookie server"
            }
            """);
    }

    /**
     * Tests deserializing a {@code LoginCookieService1}.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test
    public void testDeserializeAuthLogin() throws JsonProcessingException {
        final LoginCookieService1 service = JSON.getReader(LoginCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "@id": "https://authentication.example.org/login",
                "profile": "http://iiif.io/api/auth/1/login",
                "label": "Login to Example Institution",
                "header": "Please Log In",
                "description": "Example Institution requires that you log in with your account.",
                "confirmLabel": "Login",
                "failureHeader": "Authentication Failed",
                "failureDescription": "<a href=\\"http://example.org/policy\\">Access Policy</a>"
            }
            """);

        assertEquals("Login", service.getConfirmLabel());
        assertEquals("Example Institution requires that you log in with your account.", service.getDescription());
        assertOptEquals("Authentication Failed", service.getFailureHeader());
        assertOptEquals("<a href=\"http://example.org/policy\">Access Policy</a>", service.getFailureDescription());
        assertOptEquals("https://authentication.example.org/login", service.getID());
    }

    /**
     * Tests deserializing a {@code LoginCookieService1} without an ID.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test(expected = JsonParseException.class)
    public void testDeserializeAuthLoginNoID() throws JsonProcessingException {
        JSON.getReader(LoginCookieService1.class).readValue("""
            {
                "@context": "http://iiif.io/api/auth/1/context.json",
                "profile": "http://iiif.io/api/auth/1/login",
                "label": "Login to Example Institution",
                "header": "Please Log In",
                "description": "Example Institution requires that you log in with your example account.",
                "confirmLabel": "Login",
                "failureHeader": "Authentication Failed",
                "failureDescription": "<a href=\\"http://example.org/policy\\">Access Policy</a>"
            }
            """);
    }

    /**
     * Tests deserializing a {@code GeoJsonService}.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test
    public void testDeserializeGeoJsonService() throws JsonProcessingException {
        final GeoJsonService service = JSON.getReader(GeoJsonService.class).readValue("""
            {
                "@context" : "http://geojson.org/geojson-ld/geojson-context.jsonld",
                "@id" : "http://www.example.org/geojson/paris.json",
                "type": "Feature",
                "properties": {"name": "Paris"},
                "geometry": {
                    "type": "Point",
                    "coordinates" : [48.8567,2.3508]
                }
            }
            """);

        assertOptEquals("http://www.example.org/geojson/paris.json", service.getID());
    }

    /**
     * Tests deserializing a {@code GeoJsonService} that just links to the external service.
     *
     * @throws JsonProcessingException If there is trouble deserializing the JSON input
     */
    @Test
    public void testDeserializeGeoJsonServiceLinked() throws JsonProcessingException {
        final GeoJsonService service = JSON.getReader(GeoJsonService.class).readValue("""
            {
                "@context" : "http://geojson.org/geojson-ld/geojson-context.jsonld",
                "@id" : "http://www.example.org/geojson/paris-linked.json"
            }
            """);

        assertOptEquals("http://www.example.org/geojson/paris-linked.json", service.getID());
    }

    /**
     * Tests deserializing an {@code ImageService2}.
     */
    @Test
    public void testDeserializeImageService2() throws JsonProcessingException {
        final ImageService2 service = JSON.getReader(ImageService2.class).readValue("""
            {
                "@context" : "http://iiif.io/api/image/2/context.json",
                "@id" : "http://www.example.org/image-service/abcd1234/1E34750D-38DA-4825-A38A-B60A345E591C",
                "protocol" : "http://iiif.io/api/image",
                "width" : 6000,
                "height" : 4000,
                "sizes" : [
                    {"width" : 150, "height" : 100},
                    {"width" : 600, "height" : 400},
                    {"width" : 3000, "height": 2000}
                ],
                "tiles": [
                    {"width" : 512, "scaleFactors" : [1,2,4,8,16]}
                ],
                "profile" : [ "http://iiif.io/api/image/2/level2.json" ]
            }
            """);

        assertOptEquals("http://www.example.org/image-service/abcd1234/1E34750D-38DA-4825-A38A-B60A345E591C",
                service.getID());
    }

    /**
     * Tests deserializing an {@code ImageService3}.
     */
    @Test
    public void testDeserializeImageService3() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abcd1234/1E34750D-38DB-4825-A38A-B60A345E591C",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "maxHeight": 2000,
                "maxWidth": 3000,
                "maxArea": 4000000
            }
            """);

        assertOptEquals("https://example.org/image-service/abcd1234/1E34750D-38DB-4825-A38A-B60A345E591C",
                service.getID());
    }

    /**
     * Tests deserializing an {@code ImageService3} with {@code extraFormats}.
     */
    @Test
    public void testDeserializeImageService3ExtraFormatsEmpty() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc333",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "extraFormats": []
            }
            """);

        assertOptEquals("https://example.org/image-service/abc333", service.getID());
        assertEquals(0, service.getExtraFormats().size());
    }

    /**
     * Tests deserializing an {@code ImageService3} with {@code extraFormats}.
     */
    @Test
    public void testDeserializeImageService3ExtraFormatsNoArray() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc334",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "extraFormats": "asdf"
            }
            """);

        assertOptEquals("https://example.org/image-service/abc334", service.getID());
        assertEquals(0, service.getExtraFormats().size());
    }

    /**
     * Tests deserializing an {@code ImageService3} with {@code extraQualities}.
     */
    @Test
    public void testDeserializeImageService3ExtraQualitiesEmpty() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc363",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "extraFormats": [ "webp" ],
                "extraQualities": []
            }
            """);

        assertOptEquals("https://example.org/image-service/abc363", service.getID());
        assertEquals(0, service.getExtraQualities().size());
    }

    /**
     * Tests deserializing an {@code ImageService3} with {@code extraQualities}.
     */
    @Test
    public void testDeserializeImageService3ExtraQualitiesNoArray() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc636",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "extraFormats": [ "webp" ],
                "extraQualities": "color"
            }
            """);

        assertOptEquals("https://example.org/image-service/abc636", service.getID());
        assertEquals(0, service.getExtraQualities().size());
    }

    /**
     * Tests deserializing an {@code ImageService3} with an empty sizes array.
     */
    @Test
    public void testDeserializeImageService3SizesEmpty() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc222",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "sizes": []
            }
            """);

        assertOptEquals("https://example.org/image-service/abc222", service.getID());
        assertEquals(0, service.getSizes().size());
    }

    /**
     * Tests deserializing an {@code ImageService3} with sizes not an array.
     */
    @Test
    public void testDeserializeImageService3SizesNoArray() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc111",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "sizes": "width"
            }
            """);

        assertOptEquals("https://example.org/image-service/abc111", service.getID());
        assertEquals(0, service.getSizes().size());
    }

    /**
     * Tests deserializing an {@code ImageService3} with an empty tiles array.
     */
    @Test
    public void testDeserializeImageService3TilesEmpty() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc888",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "tiles": []
            }
            """);

        assertOptEquals("https://example.org/image-service/abc888", service.getID());
        assertEquals(0, service.getTiles().size());
    }

    /**
     * Tests deserializing an {@code ImageService3} with tiles not an array.
     */
    @Test
    public void testDeserializeImageService3TilesNoArray() throws JsonProcessingException {
        final ImageService3 service = JSON.getReader(ImageService3.class).readValue("""
            {
                "@context": "http://iiif.io/api/image/3/context.json",
                "id": "https://example.org/image-service/abc000",
                "type": "ImageService3",
                "protocol": "http://iiif.io/api/image",
                "profile": "level2",
                "width": 6000,
                "height": 4000,
                "tiles": "asdf"
            }
            """);

        assertOptEquals("https://example.org/image-service/abc000", service.getID());
        assertEquals(0, service.getTiles().size());
    }

    /**
     * Tests deserializing an {@code OtherService2}.
     */
    @Test
    public void testDeserializeOtherService2() throws JsonProcessingException {
        final OtherService2 service = JSON.getReader(OtherService2.class).readValue("""
            {
                "@id": "https://example.org/service2",
                "@type": "ExampleExtensionService2",
                "profile": "https://example.org/docs/service2"
            }
            """);

        assertOptEquals("https://example.org/service2", service.getID());
        assertOptEquals("ExampleExtensionService2", service.getType());
        assertOptEquals(OtherService2.Profile.fromLabel("https://example.org/docs/service2").get(),
                service.getProfile());
    }

    /**
     * Tests deserializing an {@code OtherService2} without a profile property.
     */
    @Test
    public void testDeserializeOtherService2EmptyProfile() throws JsonProcessingException {
        final OtherService2 service = JSON.getReader(OtherService2.class).readValue("""
            {
                "@id": "https://example.org/service2NoProfile",
                "@type": "ExampleExtensionService2NoProfile",
                "profile": ""
            }
            """);

        assertOptEquals("https://example.org/service2NoProfile", service.getID());
        assertOptEquals("ExampleExtensionService2NoProfile", service.getType());
        assertTrue(service.getProfile().isEmpty());
    }

    /**
     * Tests deserializing an {@code OtherService2} without a type.
     */
    @Test
    public void testDeserializeOtherService2NoType() throws JsonProcessingException {
        final OtherService2 service = JSON.getReader(OtherService2.class).readValue("""
            {
                "id": "https://example.org/service2NoType",
                "profile": "https://example.org/docs/service2NoType"
            }
            """);

        assertOptEquals("https://example.org/service2NoType", service.getID());
        assertTrue(service.getType().isEmpty());
        assertOptEquals(OtherService2.Profile.fromLabel("https://example.org/docs/service2NoType").get(),
                service.getProfile());

    }

    /**
     * Tests deserializing an {@code OtherService2} without a type or profile.
     */
    @Test
    public void testDeserializeOtherService2NoTypeOrProfile() throws JsonProcessingException {
        final OtherService2 service = JSON.getReader(OtherService2.class).readValue("""
            {
                "id": "https://example.org/service2NoTypeNoProfile"
            }
            """);

        assertOptEquals("https://example.org/service2NoTypeNoProfile", service.getID());
        assertTrue(service.getType().isEmpty());
        assertTrue(service.getProfile().isEmpty());

    }

    /**
     * Tests deserializing an {@code OtherService3}.
     */
    @Test
    public void testDeserializeOtherService3() throws JsonProcessingException {
        final OtherService3 service = JSON.getReader(OtherService3.class).readValue("""
            {
                "id": "https://example.org/service3",
                "type": "ExampleExtensionService3",
                "profile": "https://example.org/docs/service3"
            }
            """);

        assertOptEquals("https://example.org/service3", service.getID());
        assertOptEquals("ExampleExtensionService3", service.getType());
        assertOptEquals(OtherService3.Profile.fromLabel("https://example.org/docs/service3").get(),
                service.getProfile());

    }

    /**
     * Tests deserializing an {@code OtherService3} without a profile.
     */
    @Test
    public void testDeserializeOtherService3NoProfile() throws JsonProcessingException {
        final OtherService3 service = JSON.getReader(OtherService3.class).readValue("""
            {
                "id": "https://example.org/service3NoProfile",
                "type": "ExampleExtensionService3NoProfile"
            }
            """);

        assertOptEquals("https://example.org/service3NoProfile", service.getID());
        assertOptEquals("ExampleExtensionService3NoProfile", service.getType());
        assertTrue(service.getProfile().isEmpty());

    }

    /**
     * Tests deserializing a textual {@code OtherService3}.
     */
    @Test
    public void testDeserializeOtherService3Textual() throws JsonProcessingException {
        final OtherService3 service = JSON.getReader(OtherService3.class).readValue("""
            "https://example.org/service3text"
            """);

        assertOptEquals("https://example.org/service3text", service.getID());

    }

    /**
     * Tests deserializing an {@code PhysicalDimsService}.
     */
    @Test
    public void testDeserializePhysicalDimsService() throws JsonProcessingException {
        final PhysicalDimsService service = JSON.getReader(PhysicalDimsService.class).readValue("""
            {
                "@context": "http://iiif.io/api/annex/services/physdim/1/context.json",
                "profile": "http://iiif.io/api/annex/services/physdim",
                "physicalScale": 0.0025,
                "physicalUnits": "in"
            }
            """);

        assertTrue(service.getID().isEmpty());
        assertTrue(service.getType().isEmpty());
        assertEquals(0.0025d, service.getPhysicalScale(), 0.0001d);
        assertEquals("in", service.getPhysicalUnits());
        assertOptEquals(PhysicalDimsService.Profile.DIMS_SERVICE, service.getProfile());
    }

    /**
     * Tests deserializing an {@code PhysicalDimsService} without physical scale.
     */
    @Test(expected = JsonParseException.class)
    public void testDeserializePhysicalDimsServiceNoScale() throws JsonProcessingException {
        JSON.getReader(PhysicalDimsService.class).readValue("""
            {
                "@context": "http://iiif.io/api/annex/services/physdim/1/context.json",
                "profile": "http://iiif.io/api/annex/services/physdim",
                "physicalUnits": "in"
            }
            """);
    }

    /**
     * Tests deserializing an {@code PhysicalDimsService} without physical units.
     */
    @Test(expected = JsonParseException.class)
    public void testDeserializePhysicalDimsServiceNoUnits() throws JsonProcessingException {
        JSON.getReader(PhysicalDimsService.class).readValue("""
            {
                "@context": "http://iiif.io/api/annex/services/physdim/1/context.json",
                "profile": "http://iiif.io/api/annex/services/physdim",
                "physicalScale": 0.0025
            }
            """);
    }

    /**
     * Test method for {@link ServiceDeserializer#ServiceDeserializer()}.
     */
    @Test
    public void testServiceDeserializer() {
        try {
            new ServiceDeserializer();
        } catch (final Throwable aThrowable) {
            fail(aThrowable.getMessage());
        }
    }

    /**
     * Test method for {@link ServiceDeserializer#ServiceDeserializer(Class)}.
     */
    @Test
    public void testServiceDeserializerClassOfQ() {
        try {
            new ServiceDeserializer(Service.class);
        } catch (final Throwable aThrowable) {
            fail(aThrowable.getMessage());
        }
    }

}
