
package info.freelibrary.iiif.presentation.v3.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;

/**
 * Tests of {@link Target}.
 */
public class TargetTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests {@link Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final Target test1 = new Target(HTTPS + id);
        final Target test2 = new Target(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsHashCodeNot() {
        final Target test1 = new Target(HTTPS + UUID.randomUUID().toString());
        final Target test2 = new Target(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsNull() {
        final Target test = new Target(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsSame() {
        final String id = UUID.randomUUID().toString();
        assertEquals(new Target(HTTPS + id), new Target(HTTPS + id));
    }

    /**
     * Tests {@link Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsSameNot() {
        final Target test1 = new Target(HTTPS + UUID.randomUUID().toString());
        final Target test2 = new Target(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsSameObject() {
        final Target test = new Target(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link Target#equals(Object) Target}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testTargetEqualsString() {
        final Target test = new Target(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, new String(Constants.EMPTY));
    }
}
