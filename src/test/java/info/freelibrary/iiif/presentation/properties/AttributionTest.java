
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * An attribution test.
 */
public class AttributionTest {

    private static final String VALUE = "asdf";

    /**
     * Tests constructing an attribution from a value.
     */
    @Test
    public void testAttributionValueArray() {
        final Attribution attribution = new Attribution(new I18nValue(VALUE));

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
