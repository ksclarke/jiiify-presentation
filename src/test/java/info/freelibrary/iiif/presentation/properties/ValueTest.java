
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValueTest {

    @Test
    public void testValueString() {
        final Value value = new Value("asdf");

        assertEquals("asdf", value.getValue());
    }

    @Test
    public void testValueStringString() {
        final Value value = new Value("asdf_", "eng");

        assertEquals("asdf_eng", value.getValue() + value.getLang().get());
    }

    @Test
    public void testSetValue() {
        final Value value = new Value("one");

        assertEquals("two", value.setValue("two").getValue());
    }

    @Test
    public void testSetLang() {
        final Value value = new Value("one");

        assertEquals("eng", value.setLang("eng").getLang().get());
    }

}
