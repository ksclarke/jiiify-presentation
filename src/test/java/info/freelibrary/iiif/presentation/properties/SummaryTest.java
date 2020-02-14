
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A test of summary.
 */
public class SummaryTest {

    private static final String VALUE = "asdf";

    /**
     * Tests a summary constructor.
     */
    @Test
    public void testStringConstructor() {
        final Summary summary = new Summary(VALUE);

        assertEquals(VALUE, summary.getString());
    }

    /**
     * Tests a summary constructed from a value.
     */
    @Test
    public void testValueConstructor() {
        final Summary summary = new Summary(new Value(VALUE));

        assertEquals(VALUE, summary.getString());
    }

}
