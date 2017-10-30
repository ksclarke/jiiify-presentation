
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class I18nPropertyTest {

    @Test
    public void testI18nPropertyValueArray() {
        assertEquals("one", new I18nProperty(new Value("one"), new Value("two")).getString());
    }

    @Test
    public void testI18nPropertyStringArray() {
        assertEquals("one", new I18nProperty("one", "two").getString());
    }

    @Test
    public void testSetValue() {
        assertEquals("two", new I18nProperty("one").setValue("two").getString());
    }

    @Test
    public void testGetValues() {
        final I18nProperty i18np = new I18nProperty("one", "two");
        final List<Value> values = i18np.getValues();

        assertEquals(2, values.size());
    }

    @Test
    public void testGetString() {
        assertEquals("one", new I18nProperty("one").getString());
    }

    @Test
    public void testGetStringNull() {
        final I18nProperty i18np = new I18nProperty("one");

        i18np.getValues().remove(0);

        assertEquals(null, i18np.getString());
    }

    @Test
    public void testAddValueStringArray() {
        assertEquals(3, new I18nProperty("one").addValue("two", "three").getValues().size());
    }

    @Test
    public void testAddValueValueArray() {
        assertEquals(3, new I18nProperty("one").addValue(new Value("two"), new Value("three")).getValues().size());
    }

    @Test
    public void testHasValues() {
        assertTrue(new I18nProperty("one").hasValues());
    }

    @Test
    public void testHasNoValues() {
        final I18nProperty i18np = new I18nProperty("one");

        i18np.getValues().remove(0);

        assertFalse(i18np.hasValues());
    }

    @Test
    public void testGetValueSimple() {
        assertEquals("one", new I18nProperty("one").getValue());
    }

    @Test
    public void testGetValueSimpleValueTwo() {
        final List<String> expected = Arrays.asList(new String[] { "one", "two" });
        assertEquals(expected, new I18nProperty(new Value("one"), new Value("two")).getValue());
    }

    @Test
    public void testGetValueNull() {
        final I18nProperty i18np = new I18nProperty("one");

        i18np.getValues().remove(0);

        assertEquals(null, i18np.getValue());
    }

    @Test
    public void testGetValueSimpleValue() {
        final Value value = new Value("one", "eng");
        final List<Value> values = Arrays.asList(new Value[] { value });

        assertEquals(values, new I18nProperty(value).getValue());
    }

}
