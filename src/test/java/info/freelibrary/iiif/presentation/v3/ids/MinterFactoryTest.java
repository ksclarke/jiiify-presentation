
package info.freelibrary.iiif.presentation.v3.ids;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the {@link MinterFactory}.
 */
public class MinterFactoryTest {

    private URI myManifestID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myManifestID = URI.create(UUID.randomUUID().toString());
    }

    /**
     * Tests the {@link MinterFactory#getMinter(URI)}. This can take up to two seconds to run because the first run
     * initializes the minter.
     */
    @Test
    public final void testGetMinter() {
        assertEquals(DefaultMinter.class.getName(), MinterFactory.getMinter(myManifestID).getClass().getName());
    }

    /**
     * Tests the {@link MinterFactory#getMinter(URI)} when the minter is set via System property.
     */
    @Test
    public final void testGetMinterViaSysProperty() {
        System.setProperty(MinterFactory.MINTER_NAME_PROPERTY, AltMinter.class.getName());
        assertEquals(AltMinter.class.getName(), MinterFactory.getMinter(myManifestID).getClass().getName());
        System.clearProperty(MinterFactory.MINTER_NAME_PROPERTY);
    }

    /**
     * Tests the {@link MinterFactory#getMinter(URI)} when the minter is set via environmental property.
     */
    @Test
    public final void testGetMinterViaEnvProperty() {
        MinterFactory.setMinter(EnvAltMinter.class);
        assertEquals(EnvAltMinter.class.getName(), MinterFactory.getMinter(myManifestID).getClass().getName());
        assertEquals(EnvAltMinter.class.getName(), MinterFactory.clearMinter());
    }
}
