
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AttributionTest {

    @Test
    public void testAttributionValueArray() {
        final Attribution attribution = new Attribution(new Value("asdf"));

        assertEquals("asdf", attribution.getString());
    }

    @Test
    public void testAttributionStringArray() {
        final Attribution attribution = new Attribution("asdf");

        assertEquals("asdf", attribution.getString());
    }

}
