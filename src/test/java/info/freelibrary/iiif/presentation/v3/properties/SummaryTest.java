
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A test of summary.
 */
public class SummaryTest {

    /** A test summary value. */
    private static final String VALUE = "asdf";

    /** A test summary language. */
    private static final String LANG = "none";

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
        final Summary summary = new Summary(new I18n(LANG, VALUE));

        assertEquals(VALUE, summary.getString());
    }

}
