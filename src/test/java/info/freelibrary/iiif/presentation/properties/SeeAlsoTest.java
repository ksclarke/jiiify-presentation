
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

public class SeeAlsoTest {

    @Test
    public void test() {
        final SeeAlso seeAlso = new SeeAlso("id-a");

        assertEquals(URI.create("id-a"), seeAlso.getValues().get(0).getID());
    }

}
