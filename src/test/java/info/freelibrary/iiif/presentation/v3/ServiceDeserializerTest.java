
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertOptEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.services.OtherService2;
import info.freelibrary.iiif.presentation.v3.services.OtherService3;
import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * Tests {@code ServiceDeserializer}.
 */
public class ServiceDeserializerTest {

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
