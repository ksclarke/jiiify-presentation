
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A type test.
 */
public class TypeTest {

    private static final String ASDF = "asdf";

    private static final String AAAA = "aaaa";

    /**
     * Tests getting the type value.
     */
    @Test
    public void testGetString() {
        assertEquals(ASDF, new Type(ASDF).getString());
    }

    /**
     * Tests getting the type values.
     */
    @Test
    public void testGetValues() {
        assertEquals(3, new Type(ASDF, AAAA, "fdsa").getValues().size());
    }

    /**
     * Tests setting the value.
     */
    @Test
    public void testSetValue() {
        assertEquals(AAAA, new Type(ASDF).setValue(AAAA).getString());
    }

    /**
     * Tests adding a value.
     */
    @Test
    public void testAddValue() {
        assertEquals(2, new Type(ASDF).addValue(AAAA).getValues().size());
    }

    /**
     * Testing a value, expecting an error.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetValue2() {
        final Type type = new Type(ASDF);

        type.getValues().remove(0);
        type.getString();
    }

}
