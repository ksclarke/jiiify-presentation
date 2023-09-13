
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * An attribution test.
 */
public class AttributionTest {

    /** A test value. */
    private static final String VALUE = "asdf";

    /**
     * Tests constructing an attribution from a value.
     */
    @Test
    public void testAttributionValueArray() {
        final Attribution attribution = new Attribution(new Value(VALUE));

        assertEquals(VALUE, attribution.getString());
    }

    /**
     * Tests constructing an attribution from a string.
     */
    @Test
    public void testAttributionStringArray() {
        final Attribution attribution = new Attribution(VALUE);

        assertEquals(VALUE, attribution.getString());
    }

}
