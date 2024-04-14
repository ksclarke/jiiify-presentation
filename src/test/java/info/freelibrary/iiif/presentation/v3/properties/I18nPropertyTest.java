
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * A internationalized property test.
 */
public class I18nPropertyTest {

    /** Sample test value. */
    private static final String ONE = "one";

    /** Sample test value. */
    private static final String TWO = "two";

    /** Sample test value. */
    private static final String ENG = "eng";

    /** Sample test value. */
    private static final String FRE = "fre";

    /**
     * Tests construction of an internationalized property.
     */
    @Test
    public void testI18nPropertyValueArray() {
        assertEquals(ONE, new I18nProperty<Value>(new I18n(ENG, ONE), new I18n(FRE, TWO)).getString());
    }

    /**
     * Test of hashCode with double values.
     */
    @Test
    public void testHashCodeDoubleValue() {
        final I18nProperty<?> i18np1 = new I18nProperty<>(new I18n(ENG, ONE));
        final I18nProperty<?> i18np2 = new I18nProperty<>(new I18n(ENG, ONE));

        assertEquals(i18np1.hashCode(), i18np2.hashCode());
    }

    /**
     * Test of hashCode with double values.
     */
    @Test
    public void testHashCodeDifferentDoubleValue() {
        final I18nProperty<?> i18np1 = new I18nProperty<>(new I18n(ENG, ONE));
        final I18nProperty<?> i18np2 = new I18nProperty<>(new I18n(FRE, ONE));

        assertNotEquals(i18np1.hashCode(), i18np2.hashCode());
    }

    /**
     * Test of hashCode override.
     */
    @Test
    public void testHashCode() {
        final I18nProperty<?> i18np1 = new I18nProperty<>(new I18n(ENG, ONE));
        final I18nProperty<?> i18np2 = new I18nProperty<>(new I18n(ENG, ONE));

        assertEquals(i18np1.hashCode(), i18np2.hashCode());
    }

}
