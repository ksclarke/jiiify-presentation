
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void test() {
        final Description description = new Description("asdf");

        assertEquals("asdf", description.getString());
    }

}
