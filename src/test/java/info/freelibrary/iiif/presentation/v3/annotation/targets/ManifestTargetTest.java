
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;

/**
 * Tests of {@link _Target}.
 */
public class ManifestTargetTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** A manifest label. */
    private static final Label LABEL = new Label("en", "A label");

    /**
     * Tests the constructor that takes a {@code Manifest}.
     */
    @Test
    public final void testManifestTargetManifest() {
        final Manifest manifest = new Manifest(HTTPS + UUID.randomUUID().toString(), LABEL);
        final ManifestTarget target = new ManifestTarget(manifest);

        assertEquals(manifest.getID(), target.getID());
        assertEquals(manifest.getType(), target.getType());
    }

    /**
     * Tests the constructor that takes a {@code Manifest} and {@code PartOf}.
     */
    @Test
    public final void testManifestTargetManifestPartOf() {
        final Manifest manifest = new Manifest(HTTPS + UUID.randomUUID().toString(), LABEL);
        final PartOf partOf = new PartOf(HTTPS + UUID.randomUUID().toString(), ResourceTypes.MANIFEST);
        final ManifestTarget target = new ManifestTarget(manifest, partOf);

        assertEquals(manifest.getID(), target.getID());
        assertEquals(manifest.getType(), target.getType());
        assertEquals(SINGLE_INSTANCE, target.getPartOfs().size());
    }

    /**
     * Tests the array setter for {@code PartOf}.
     */
    @Test
    public final void testManifestTargetSetPartOfArray() {
        final Manifest manifest = new Manifest(HTTPS + UUID.randomUUID().toString(), LABEL);
        final PartOf partOf = new PartOf(HTTPS + UUID.randomUUID().toString(), ResourceTypes.MANIFEST);
        final ManifestTarget target = new ManifestTarget(manifest);

        target.setPartOfs(partOf);
        assertEquals(1, target.getPartOfs().size());
        assertEquals(partOf.getID(), target.getPartOfs().get(0).getID());
    }

    /**
     * Tests the list setter for {@code PartOf}.
     */
    @Test
    public final void testManifestTargetSetPartOfList() {
        final Manifest manifest = new Manifest(HTTPS + UUID.randomUUID().toString(), LABEL);
        final PartOf partOf = new PartOf(HTTPS + UUID.randomUUID().toString(), ResourceTypes.MANIFEST);
        final ManifestTarget target = new ManifestTarget(manifest);

        target.setPartOfs(List.of(partOf));
        assertEquals(1, target.getPartOfs().size());
        assertEquals(partOf.getID(), target.getPartOfs().get(0).getID());
    }

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
        assertNotEquals(test, Constants.EMPTY);
    }

    /**
     * Tests {@link _ManifestTarget#getType() ManifestTarget}.
     */
    @Test
    public final void testTargetGetType() {
        final Optional<String> type = new ManifestTarget(HTTPS + UUID.randomUUID().toString()).getType();

        assertTrue(type.isPresent());
        assertEquals(ResourceTypes.MANIFEST, type.get());
    }

    /**
     * Tests {@link _ManifestTarget#setID(String) ManifestTarget}.
     */
    @Test
    public final void testTargetSetID() {
        final String id = UUID.randomUUID().toString();
        final ManifestTarget test = new ManifestTarget(HTTPS + UUID.randomUUID().toString());

        assertEquals(id, test.setID(id).getID());
    }

    /**
     * Tests {@link _ManifestTarget#setType(String) ManifestTarget}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testTargetSetTypeBad() {
        final ManifestTarget target = new ManifestTarget(HTTPS + UUID.randomUUID().toString());
        target.setType("asdfasdf");
    }

    /**
     * Tests {@link _ManifestTarget#setType(String) ManifestTarget}.
     */
    @Test
    public final void testTargetSetTypeGood() {
        final ManifestTarget target = new ManifestTarget(HTTPS + UUID.randomUUID().toString());
        final Optional<String> type = target.setType(ResourceTypes.MANIFEST).getType();

        if (type.isPresent()) {
            assertEquals(ResourceTypes.MANIFEST, type.get());
        } else {
            fail();
        }
    }
}
