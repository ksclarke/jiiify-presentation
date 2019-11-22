
package info.freelibrary.iiif.presentation.utils;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/*
 * Imports are important here. Only the Collection is pulled in and the Collection.Manifest() is referenced from that.
 * For the full manifest, we can pull that in using Manifest();
 */
import info.freelibrary.iiif.presentation.Collection;
import info.freelibrary.iiif.presentation.Manifest;

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
