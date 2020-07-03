
package info.freelibrary.iiif.presentation.v2.utils;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.Collection;
import info.freelibrary.iiif.presentation.v2.Manifest;

/**
 * A generic test for tests not tied to just one class.
 */
public class GenericTest {

    private String myID;

    private String myLabel;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
        myLabel = "label-" + UUID.randomUUID().toString();
    }

    /**
     * Test of using both a collection manifest and a full work manifest in the same code.
     */
    @Test
    public void test() {
        final Collection.Manifest collectionManifest = new Collection.Manifest(myID, myLabel);
        final Manifest manifest = new Manifest(myID, myLabel);

        assertEquals(collectionManifest.getID(), manifest.getID());
    }

}
