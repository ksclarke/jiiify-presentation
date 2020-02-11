
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

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
        final Summary summary = new Summary(VALUE);

        assertEquals(VALUE, summary.getString());
    }

    /**
     * Tests a description constructed from a value.
     */
    @Test
    public void testValueConstructor() {
        final Summary summary = new Summary(new Value(VALUE));

        assertEquals(VALUE, summary.getString());
    }

}
