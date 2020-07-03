
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.util.StringUtils;

/**
 * Tests of {@link DiskjointChecker}.
 */
public class DisjointCheckerTest {

    private static final File DISJOINTS = new File("src/test/resources/disjoint.txt");

    /**
     * Tests {@link DisjointChecker#toString() toString}.
     *
     * @throws IOException If the disjoint values cannot be read from the test directory
     */
    @Test
    public final void testToString() throws IOException {
        assertEquals(StringUtils.read(DISJOINTS), new DisjointChecker().toString());
    }

    /**
     * Tests {@link DisjointChecker#check(Class, Behavior...) check} for behavior class ownership.
     */
    @Test
    public final void testCheckOwnership() {
        final DisjointChecker checker = new DisjointChecker();

        checker.check(CanvasBehavior.class, CanvasBehavior.FACING_PAGES);
        checker.check(RangeBehavior.class, RangeBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests {@link DisjointChecker#check(Class, Behavior...) check} for behavior class ownership.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testCheckInvalidOwnership() {
        new DisjointChecker().check(CanvasBehavior.class, CollectionBehavior.PAGED);
    }

    /**
     * Tests {@link DisjointChecker#check(Optional, Behavior...) check} for behavior disjoints.
     */
    @Test
    public final void testCheckBehaviorsWithoutClass() {
        final DisjointChecker checker = new DisjointChecker();
        checker.check(CollectionBehavior.class, CollectionBehavior.INDIVIDUALS, CollectionBehavior.MULTI_PART);
    }

    /**
     * Tests {@link DisjointChecker#check(Optional, Behavior...) check} for behavior disjoints.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testCheckDisjointedBehaviorsWithoutClass() {
        new DisjointChecker().check(ManifestBehavior.class, ManifestBehavior.INDIVIDUALS, ManifestBehavior.PAGED);
    }

    /**
     * Tests {@link DisjointChecker#check(Optional, Behavior...) check} for behavior disjoints.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testCheckDisjointedBehaviorsFromConstructor() {
        final List<Behavior> existingBehaviors = List.of(ManifestBehavior.PAGED);
        new DisjointChecker(existingBehaviors).check(ManifestBehavior.class, ManifestBehavior.INDIVIDUALS);
    }

    /**
     * Tests {@link DisjointChecker#check(Optional, Behavior...) check} for behavior disjoints.
     */
    @Test
    public final void testCheckDupBehaviorsFromConstructor() {
        final List<Behavior> existingBehaviors = List.of(ManifestBehavior.PAGED);
        new DisjointChecker(existingBehaviors).check(ManifestBehavior.class, ManifestBehavior.PAGED);
    }

    /**
     * Tests {@link DisjointChecker#check(Optional, Behavior...) check} for behavior disjoints.
     */
    @Test
    public final void testCheckDupBehaviors() {
        new DisjointChecker().check(ManifestBehavior.class, ManifestBehavior.PAGED, ManifestBehavior.PAGED);
    }
}
