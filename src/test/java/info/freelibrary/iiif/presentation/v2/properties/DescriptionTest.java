
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A description test.
 */
public class DescriptionTest {

    private static final String VALUE = "asdf";

    /**
     * Tests a description constructor.
     */
    @Test
    public void testStringConstructor() {
        final Description description = new Description(VALUE);

        assertEquals(VALUE, description.getString());
    }

    /**
     * Tests a description constructed from a value.
     */
    @Test
    public void testValueConstructor() {
        final Description description = new Description(new Value(VALUE));

        assertEquals(VALUE, description.getString());
    }

}
