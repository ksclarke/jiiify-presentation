
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

    public static final String IMAGE_INFO_LEVEL_0 = "http://iiif.io/api/image/3/level0.json";

    public static final String IMAGE_INFO_LEVEL_1 = "http://iiif.io/api/image/3/level1.json";

    public static final String IMAGE_INFO_LEVEL_2 = "http://iiif.io/api/image/3/level2.json";

    /**
     * Tests API compliance level zero.
     *
     * @throws MalformedURLException If the levels have malformed URLs
     * @throws URISyntaxException If there is a syntactical problem with the level URIs
     */
    @Test
    public void testLevelZero() throws MalformedURLException, URISyntaxException {
        assertEquals(IMAGE_INFO_LEVEL_0, APIComplianceLevel.ZERO.string());
        assertEquals(new URL(IMAGE_INFO_LEVEL_0), APIComplianceLevel.ZERO.url());
        assertEquals(new URI(IMAGE_INFO_LEVEL_0), APIComplianceLevel.ZERO.uri());
    }

    /**
     * Tests API compliance level one.
     *
     * @throws MalformedURLException If the levels have malformed URLs
     * @throws URISyntaxException If there is a syntactical problem with the level URIs
     */
    @Test
    public void testLevelOne() throws MalformedURLException, URISyntaxException {
        assertEquals(IMAGE_INFO_LEVEL_1, APIComplianceLevel.ONE.string());
        assertEquals(new URL(IMAGE_INFO_LEVEL_1), APIComplianceLevel.ONE.url());
        assertEquals(new URI(IMAGE_INFO_LEVEL_1), APIComplianceLevel.ONE.uri());
    }

    /**
     * Tests API compliance level two.
     *
     * @throws MalformedURLException If the levels have malformed URLs
     * @throws URISyntaxException If there is a syntactical problem with the level URIs
     */
    @Test
    public void testLevelTwo() throws MalformedURLException, URISyntaxException {
        assertEquals(IMAGE_INFO_LEVEL_2, APIComplianceLevel.TWO.string());
        assertEquals(new URL(IMAGE_INFO_LEVEL_2), APIComplianceLevel.TWO.url());
        assertEquals(new URI(IMAGE_INFO_LEVEL_2), APIComplianceLevel.TWO.uri());
    }

    /**
     * Tests creating a compliance level from a string.
     */
    @Test
    public void testFromProfile() {
        assertEquals(IMAGE_INFO_LEVEL_0, APIComplianceLevel.fromProfile(IMAGE_INFO_LEVEL_0).string());
        assertEquals(IMAGE_INFO_LEVEL_1, APIComplianceLevel.fromProfile(IMAGE_INFO_LEVEL_1).string());
        assertEquals(IMAGE_INFO_LEVEL_2, APIComplianceLevel.fromProfile(IMAGE_INFO_LEVEL_2).string());
    }
}
