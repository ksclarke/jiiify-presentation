
package info.freelibrary.iiif.presentation.v3.annotations;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Tests of {@link Motivation}.
 */
public class MotivationTest {

    /**
     * Tests {@link Motivation#equals(Object)}.
     */
    @Test
    public final void testEqualsDifferentObject() {
        assertNotEquals(new Motivation(Purpose.BOOKMARKING), EMPTY);
    }

    /**
     * Tests {@link Motivation#equals(Object) Motivation}.
     */
    @Test
    public final void testMotivationEqualsHashCode() {
        final Motivation test1 = new Motivation(Purpose.BOOKMARKING);
        final Motivation test2 = new Motivation(Purpose.BOOKMARKING);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Motivation#equals(Object) Motivation}.
     */
    @Test
    public final void testMotivationEqualsHashCodeNot() {
        final Motivation test1 = new Motivation(Purpose.DESCRIBING);
        final Motivation test2 = new Motivation(Purpose.BOOKMARKING);

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Motivation#equals(Object) Motivation}.
     */
    @Test
    public final void testMotivationEqualsNull() {
        final Motivation test = new Motivation(Purpose.BOOKMARKING);
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link Motivation#equals(Object) Motivation}.
     */
    @Test
    public final void testMotivationEqualsSame() {
        final Motivation test1 = new Motivation(Purpose.BOOKMARKING);
        final Motivation test2 = new Motivation(Purpose.BOOKMARKING);

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link Motivation#equals(Object) Motivation}.
     */
    @Test
    public final void testMotivationEqualsSameNot() {
        final Motivation test1 = new Motivation(Purpose.BOOKMARKING);
        final Motivation test2 = new Motivation(Purpose.DESCRIBING);

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link Motivation#equals(Object) Motivation}.
     */
    @Test
    public final void testMotivationEqualsSameObject() {
        final Motivation test = new Motivation(Purpose.BOOKMARKING);
        assertEquals(test, test);
    }

    /**
     * Tests {@link Motivation#equals(Object) Motivation}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testMotivationEqualsString() {
        assertNotEquals(new Motivation(Purpose.BOOKMARKING), EMPTY);
    }
}
