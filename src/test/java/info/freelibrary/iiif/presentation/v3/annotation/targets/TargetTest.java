
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;

/**
 * Tests of {@link _Target}.
 */
public class TargetTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /**
     * Tests the construction of a {@code Target} from a supplied ID and boolean flag.
     */
    @Test
    public final void testTargetConstructorStringBoolean() {
        final String id = HTTPS + UUID.randomUUID().toString();
        assertEquals(id, new Target(id, true).getID());
    }

    /**
     * Tests the construction of a {@code Target} from a supplied ID, boolean flag, and {@code PartOf} array.
     */
    @Test
    public final void testTargetConstructorStringBooleanPartOf() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PartOf partOf = new PartOf(id, ResourceTypes.MANIFEST);
        final Target target = new Target(id, true, partOf);

        assertEquals(id, target.getID());
        assertEquals(id, target.getPartOfs().get(0).getID());
    }

    /**
     * Tests {@link _Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final Target test1 = new Target(HTTPS + id);
        final Target test2 = new Target(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link _Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsHashCodeNot() {
        final Target test1 = new Target(HTTPS + UUID.randomUUID().toString());
        final Target test2 = new Target(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link _Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsNull() {
        final Target test = new Target(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link _Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsSame() {
        final String id = UUID.randomUUID().toString();
        assertEquals(new Target(HTTPS + id), new Target(HTTPS + id));
    }

    /**
     * Tests {@link _Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsSameNot() {
        final Target test1 = new Target(HTTPS + UUID.randomUUID().toString());
        final Target test2 = new Target(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link _Target#equals(Object) Target}.
     */
    @Test
    public final void testTargetEqualsSameObject() {
        final Target test = new Target(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link _Target#equals(Object) Target}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testTargetEqualsString() {
        final Target test = new Target(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, new String(Constants.EMPTY));
    }

    /**
     * Tests setting the ID for a {@code Target}.
     */
    @Test
    public final void testTargetSetID() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Target target = new Target(HTTPS + UUID.randomUUID().toString());

        assertEquals(id, target.setID(id).getID());
    }

    /**
     * Tests setting the type of a {@code Target}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testTargetSetType() {
        new Target(HTTPS + UUID.randomUUID().toString()).setType(ResourceTypes.MANIFEST);
    }
}
