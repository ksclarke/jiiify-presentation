
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TypeTest {

    @Test
    public void testGetString() {
        assertEquals("asdf", new Type("asdf").getString());
    }

    @Test
    public void testGetValues() {
        assertEquals(3, new Type("asdf", "aaaa", "fdsa").getValues().size());
    }

    @Test
    public void testSetValue() {
        assertEquals("aaaa", new Type("asdf").setValue("aaaa").getString());
    }

    @Test
    public void testAddValue() {
        assertEquals(2, new Type("asdf").addValue("aaaa").getValues().size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetValue2() {
        final Type type = new Type("asdf");

        type.getValues().remove(0);
        type.getString();
    }

}
