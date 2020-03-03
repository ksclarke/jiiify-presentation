
package info.freelibrary.iiif.presentation.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A ImageInfoService test.
 */
public class ImageInfoServiceTest {

    private static final URI ID = URI.create("asdf");

    /**
     * Tests getting the ImageInfoService's ID.
     */
    @Test
    public void testGetID() {
        assertEquals(ID, new ImageInfoService(ID).getID());
    }

    /**
     * Tests getting the ImageInfoService's context.
     */
    @Test
    public void testGetContext() {
        assertEquals(Constants.CONTEXT_URI, new ImageInfoService(APIComplianceLevel.ZERO, ID).getContext());
    }

    /**
     * Tests getting the ImageInfoService's API compliance level.
     */
    @Test
    public void testGetProfile() {
        assertEquals(APIComplianceLevelTest.IMAGE_INFO_LEVEL_0, new ImageInfoService(APIComplianceLevel.ZERO, ID)
                .getProfile());
        assertEquals(APIComplianceLevelTest.IMAGE_INFO_LEVEL_1, new ImageInfoService(APIComplianceLevel.ONE, ID)
                .getProfile());
        assertEquals(APIComplianceLevelTest.IMAGE_INFO_LEVEL_2, new ImageInfoService(APIComplianceLevel.TWO, ID)
                .getProfile());
    }

}
