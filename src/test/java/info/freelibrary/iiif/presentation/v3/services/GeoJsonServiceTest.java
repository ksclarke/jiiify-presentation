
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * A GeoJSON service test.
 */
public class GeoJsonServiceTest {

    private URI myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = URI.create(UUID.randomUUID().toString());
    }

    /**
     * Tests the GeoJSON service syntax.
     */
    @Test
    public void test() {
        assertEquals(myID, new GeoJsonService(myID).getID());
    }

}
