
package info.freelibrary.iiif.presentation.services;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

/**
 * An API compliance level test.
 */
public class APIComplianceLevelTest {

    public static final String IMAGE_INFO_LEVEL_0 = "http://iiif.io/api/image/2/level0.json";

    public static final String IMAGE_INFO_LEVEL_1 = "http://iiif.io/api/image/2/level1.json";

    public static final String IMAGE_INFO_LEVEL_2 = "http://iiif.io/api/image/2/level2.json";

    /**
     * Tests of the API compliance levels.
     *
     * @throws MalformedURLException If the levels have malformed URLs
     * @throws URISyntaxException If there is a syntactical problem with the level URIs
     */
    @Test
    public void test() throws MalformedURLException, URISyntaxException {

        assertEquals(IMAGE_INFO_LEVEL_0, APIComplianceLevel.ZERO.string());
        assertEquals(new URL(IMAGE_INFO_LEVEL_0), APIComplianceLevel.ZERO.url());
        assertEquals(new URI(IMAGE_INFO_LEVEL_0), APIComplianceLevel.ZERO.uri());

        assertEquals(IMAGE_INFO_LEVEL_1, APIComplianceLevel.ONE.string());
        assertEquals(new URL(IMAGE_INFO_LEVEL_1), APIComplianceLevel.ONE.url());
        assertEquals(new URI(IMAGE_INFO_LEVEL_1), APIComplianceLevel.ONE.uri());

        assertEquals(IMAGE_INFO_LEVEL_2, APIComplianceLevel.TWO.string());
        assertEquals(new URL(IMAGE_INFO_LEVEL_2), APIComplianceLevel.TWO.url());
        assertEquals(new URI(IMAGE_INFO_LEVEL_2), APIComplianceLevel.TWO.uri());

    }

}
