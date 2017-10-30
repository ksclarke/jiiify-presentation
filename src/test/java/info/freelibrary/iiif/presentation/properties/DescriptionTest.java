
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void testStringConstructor() {
        final Description description = new Description("asdf");

        assertEquals("asdf", description.getString());
    }

    @Test
    public void testValueConstructor() {
        final Description description = new Description(new Value("asdf"));

        assertEquals("asdf", description.getString());
    }

}
