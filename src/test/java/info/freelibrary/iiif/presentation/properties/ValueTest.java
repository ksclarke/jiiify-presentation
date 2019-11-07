
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A value test.
 */
public class ValueTest {

    private static final String ENG = "eng";

    private static final String ONE = "one";

    /**
     * Tests constructing a value.
     */
    @Test
    public void testValueString() {
        final String asdf = "asdf";
        final Value value = new Value(asdf);

        assertEquals(asdf, value.getValue());
    }

    /**
     * Tests constructing an I18n value.
     */
    @Test
    public void testValueStringString() {
        final Value value = new Value("asdf_", ENG);

        assertEquals("asdf_eng", value.getValue() + value.getLang().get());
    }

    /**
     * Tests setting a value.
     */
    @Test
    public void testSetValue() {
        final String two = "two";
        final Value value = new Value(ONE);

        assertEquals(two, value.setValue(two).getValue());
    }

    /**
     * Tests setting a language for the value.
     */
    @Test
    public void testSetLang() {
        final Value value = new Value(ONE);

        assertEquals(ENG, value.setLang(ENG).getLang().get());
    }

}
