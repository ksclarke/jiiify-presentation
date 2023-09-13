
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A value test.
 */
public class ValueTest {

    /** A test language code. */
    private static final String ENG = "eng";

    /** A test value. */
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
     * Tests the hashCode() function.
     */
    @Test
    public void testValueHashCode() {
        final Value value1 = new Value(ONE, ENG);
        final Value value2 = new Value(ONE, ENG);

        assertEquals(value1.hashCode(), value2.hashCode());
    }

    /**
     * Tests the hashCode() function.
     */
    @Test
    public void testValueEquals() {
        final Value value1 = new Value(ONE, ENG);
        final Value value2 = new Value(ONE, ENG);

        assertTrue(value1.equals(value2));
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
