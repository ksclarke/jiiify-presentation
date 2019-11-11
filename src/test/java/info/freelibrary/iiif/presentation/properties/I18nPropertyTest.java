
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * A internationalized property test.
 */
public class I18nPropertyTest {

    private static final String ONE = "one";

    private static final String TWO = "two";

    private static final String THREE = "three";

    /**
     * Tests construction of an internationalized property.
     */
    @Test
    public void testI18nPropertyValueArray() {
        assertEquals(ONE, new I18nProperty(new Value(ONE), new Value(TWO)).getString());
    }

    /**
     * Tests construction of an internationalized property.
     */
    @Test
    public void testI18nPropertyStringArray() {
        assertEquals(ONE, new I18nProperty(ONE, TWO).getString());
    }

    /**
     * Tests setting the value of an internationalized property.
     */
    @Test
    public void testSetValue() {
        assertEquals(TWO, new I18nProperty(ONE).setValue(TWO).getString());
    }

    /**
     * Tests getting the value from an internationalized property.
     */
    @Test
    public void testGetValues() {
        final I18nProperty i18np = new I18nProperty(ONE, TWO);
        final List<Value> values = i18np.getValues();

        assertEquals(2, values.size());
    }

    /**
     * Tests getting an internationalized property as a string value.
     */
    @Test
    public void testGetString() {
        assertEquals(ONE, new I18nProperty(ONE).getString());
    }

    /**
     * Tests getting an internationalized property as a string value.
     */
    @Test
    public void testGetStringNull() {
        final I18nProperty i18np = new I18nProperty(ONE);

        i18np.getValues().remove(0);

        assertEquals(null, i18np.getString());
    }

    /**
     * Tests adding values to an internationalized property.
     */
    @Test
    public void testAddValueStringArray() {
        assertEquals(3, new I18nProperty(ONE).addValue(TWO, THREE).getValues().size());
    }

    /**
     * Tests adding values to an internationalized property.
     */
    @Test
    public void testAddValueValueArray() {
        assertEquals(3, new I18nProperty(ONE).addValue(new Value(TWO), new Value(THREE)).getValues().size());
    }

    /**
     * Tests whether an internationalized property has values.
     */
    @Test
    public void testHasValues() {
        assertTrue(new I18nProperty(ONE).hasValues());
    }

    /**
     * Tests whether an internationalized property has values.
     */
    @Test
    public void testHasNoValues() {
        final I18nProperty i18np = new I18nProperty(ONE);

        i18np.getValues().remove(0);

        assertFalse(i18np.hasValues());
    }

    /**
     * Tests getting a simple value.
     */
    @Test
    public void testGetValueSimple() {
        assertEquals(ONE, new I18nProperty(ONE).getJsonValue());
    }

    /**
     * Tests getting multiple simple values.
     */
    @Test
    public void testGetValueSimpleValueTwo() {
        final List<String> expected = Arrays.asList(new String[] { ONE, TWO });
        assertEquals(expected, new I18nProperty(new Value(ONE), new Value(TWO)).getJsonValue());
    }

    /**
     * Tests getting a null value.
     */
    @Test
    public void testGetValueNull() {
        final I18nProperty i18np = new I18nProperty(ONE);

        i18np.getValues().remove(0);

        assertEquals(null, i18np.getJsonValue());
    }

    /**
     * Tests getting a simple value.
     */
    @Test
    public void testGetValueSimpleValue() {
        final Value value = new Value(ONE, "eng");

        assertEquals(value, new I18nProperty(value).getJsonValue());
    }

}
