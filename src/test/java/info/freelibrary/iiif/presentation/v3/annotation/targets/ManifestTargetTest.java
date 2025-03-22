
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;

/**
 * Tests of {@link _Target}.
 */
public class ManifestTargetTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests {@link _ManifestTarget#equals(Object) ManifestTarget}.
     */
    @Test
    public final void testTargetEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final Target test1 = new ManifestTarget(HTTPS + id);
        final Target test2 = new ManifestTarget(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link _ManifestTarget#equals(Object) ManifestTarget}.
     */
    @Test
    public final void testTargetEqualsHashCodeNot() {
        final ManifestTarget test1 = new ManifestTarget(HTTPS + UUID.randomUUID().toString());
        final ManifestTarget test2 = new ManifestTarget(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link _ManifestTarget#equals(Object) ManifestTarget}.
     */
    @Test
    public final void testTargetEqualsNull() {
        final ManifestTarget test = new ManifestTarget(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link _ManifestTarget#equals(Object) ManifestTarget}.
     */
    @Test
    public final void testTargetEqualsSame() {
        final String id = UUID.randomUUID().toString();
        assertEquals(new ManifestTarget(HTTPS + id), new ManifestTarget(HTTPS + id));
    }

    /**
     * Tests {@link _ManifestTarget#equals(Object) ManifestTarget}.
     */
    @Test
    public final void testTargetEqualsSameNot() {
        final ManifestTarget test1 = new ManifestTarget(HTTPS + UUID.randomUUID().toString());
        final ManifestTarget test2 = new ManifestTarget(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link _ManifestTarget#equals(Object) ManifestTarget}.
     */
    @Test
    public final void testTargetEqualsSameObject() {
        final ManifestTarget test = new ManifestTarget(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link _ManifestTarget#equals(Object) ManifestTarget}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testTargetEqualsString() {
        final ManifestTarget test = new ManifestTarget(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, new String(Constants.EMPTY));
    }
}
