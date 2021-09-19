
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * A internationalized property test.
 */
public class I18nPropertyTest {

    private static final String ONE = "one";

    private static final String TWO = "two";

    private static final String THREE = "three";

    private static final String FOUR = "four";

    private static final String ENG = "eng";

    private static final String FRE = "fre";

    /**
     * Tests construction of an internationalized property.
     */
    @Test
    public void testI18nPropertyValueArray() {
        assertEquals(ONE, new I18nProperty<Value>(new I18n(ENG, ONE), new I18n(FRE, TWO)).getString());
    }

    /**
     * Tests construction of an internationalized property.
     */
    @Test
    public void testI18nPropertyStringArray() {
        assertEquals(ONE, new I18nProperty<Value>(ONE, TWO).getString());
    }

    /**
     * Tests setting the strings of an internationalized property.
     */
    @Test
    public void testSetStrings() {
        assertEquals(TWO, new I18nProperty<Value>(ONE).setStrings(TWO).getString());
    }

    /**
     * Tests getting the internationalizations from a property.
     */
    @Test
    public void testGetI18ns() {
        final I18nProperty<?> i18np = new I18nProperty<>(ONE, TWO);
        final List<I18n> values = i18np.getI18ns();

        assertEquals(2, values.size());
    }

    /**
     * Test of hashCode with double values
     */
    @Test
    public void testHashCodeDoubleValue() {
        final I18nProperty<?> i18np1 = new I18nProperty<>(new I18n(ENG, ONE));
        final I18nProperty<?> i18np2 = new I18nProperty<>(new I18n(ENG, ONE));

        assertEquals(i18np1.hashCode(), i18np2.hashCode());
    }

    /**
     * Test of hashCode with double values
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
        final I18nProperty<?> i18np1 = new I18nProperty<>(ONE, TWO);
        final I18nProperty<?> i18np2 = new I18nProperty<>(ONE, TWO);

        assertEquals(i18np1.hashCode(), i18np2.hashCode());
    }

    /**
     * Test of hashCode override for multiple strings.
     */
    @Test
    public void testHashCodeMultipleValues() {
        final I18nProperty<?> i18np1 = new I18nProperty<>(ONE).addStrings(TWO, THREE);
        final I18nProperty<?> i18np2 = new I18nProperty<>(ONE).addStrings(TWO, THREE);

        assertEquals(i18np1.hashCode(), i18np2.hashCode());
    }

    /**
     * Test of hashCode override for multiple strings.
     */
    @Test
    public void testNotEqualHashCode() {
        final I18nProperty<?> i18np1 = new I18nProperty<>(ONE);
        final I18nProperty<?> i18np2 = new I18nProperty<>(TWO);

        assertNotEquals(i18np1.hashCode(), i18np2.hashCode());
    }

    /**
     * Test of hashCode override for multiple strings.
     */
    @Test
    public void testNotEqualHashCodeMultipleValues() {
        final I18nProperty<?> i18np1 = new I18nProperty<>(ONE).addStrings(TWO, THREE);
        final I18nProperty<?> i18np2 = new I18nProperty<>(ONE).addStrings(TWO, FOUR);

        assertNotEquals(i18np1.hashCode(), i18np2.hashCode());
    }

    /**
     * Tests getting an internationalized property as a string value.
     */
    @Test
    public void testGetString() {
        assertEquals(ONE, new I18nProperty<Value>(ONE).getString());
    }

    /**
     * Tests getting an internationalized property as a string value.
     */
    @Test
    public void testGetStringNull() {
        final I18nProperty<Value> i18np = new I18nProperty<>(ONE);

        i18np.getI18ns().remove(0);

        assertEquals(null, i18np.getString());
    }

    /**
     * Tests adding strings to an internationalized property.
     */
    @Test
    public void testAddStrings() {
        assertEquals(3, new I18nProperty<Value>(ONE).addStrings(TWO, THREE).getI18ns().size());
    }

    /**
     * Tests adding internationalizations to a property.
     */
    @Test
    public void testAddI18ns() {
        assertEquals(3,
                new I18nProperty<Value>(ONE).addI18ns(new I18n(ENG, TWO), new I18n(FRE, THREE)).getI18ns().size());
    }

    /**
     * Tests whether an internationalized property has strings.
     */
    @Test
    public void testHasStrings() {
        assertTrue(new I18nProperty<Value>(ONE).hasStrings());
    }

    /**
     * Tests whether an internationalized property has strings.
     */
    @Test
    public void testHasNoStrings() {
        final I18nProperty<?> i18np = new I18nProperty<>(ONE);

        i18np.getI18ns().remove(0);

        assertFalse(i18np.hasStrings());
    }

    /**
     * Tests getting a simple value.
     */
    @Test
    public void testGetStringSimple() {
        assertEquals(ONE, new I18nProperty<Value>(ONE).getString());
    }

    /**
     * Tests getting a null value.
     */
    @Test
    public void testGetI18nsNull() {
        final I18nProperty<?> i18np = new I18nProperty<>(ONE);

        i18np.getI18ns().remove(0);

        assertEquals(null, i18np.toMap());
    }

}
