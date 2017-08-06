
package info.freelibrary.iiif.presentation.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

public class GeoJSONServiceTest {

    private static final URI ID = URI.create("asdf");

    @Test
    public void test() {
        assertEquals(ID, new GeoJSONService(ID).getID());
        assertEquals(GeoJSONService.CONTEXT, new GeoJSONService(ID).getContext());
    }

}
