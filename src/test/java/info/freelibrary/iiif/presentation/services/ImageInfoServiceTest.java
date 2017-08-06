
package info.freelibrary.iiif.presentation.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

public class ImageInfoServiceTest {

    public static final String CONTEXT = "http://iiif.io/api/image/2/context.json";

    private static final URI ID = URI.create("asdf");

    @Test
    public void testGetID() {
        assertEquals(ID, new ImageInfoService(ID).getID());
    }

    @Test
    public void testGetContext() {
        assertEquals(CONTEXT, new ImageInfoService(APIComplianceLevel.ZERO, ID).getContext());
    }

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
