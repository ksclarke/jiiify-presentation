
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Tests of {@link BehaviorList}.
 */
public class BehaviorListTest {

    /** A {@code BehaviorList} to test. */
    private BehaviorList myBehaviorList;

    /** A list of behaviors to test. */
    private List<Behavior> myList;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myList = new ArrayList<>();
        myList.addAll(List.of(ManifestBehavior.INDIVIDUALS, ManifestBehavior.REPEAT));
        myBehaviorList = new BehaviorList(ManifestBehavior.class, myList);
    }

    /**
     * Test method for {@link BehaviorList#addAll(Collection)}.
     */
    @Test
    public final void testAddAllCollectionOfExtendsBehavior() {
        final BehaviorList behaviors = new BehaviorList(ManifestBehavior.class);

        behaviors.addAll(myList);
        assertEquals(myList.size(), behaviors.size());
    }

    /**
     * Test method for {@link BehaviorList#addAll(int, Collection)}.
     */
    @Test
    public final void testAddAllIntCollectionOfQextendsBehavior() {
        final BehaviorList behaviors = new BehaviorList(ManifestBehavior.class);

        behaviors.addAll(0, myList);
        assertEquals(myList.size(), behaviors.size());
    }

    /**
     * Test method for {@link BehaviorList#add(Behavior)}.
     */
    @Test
    public final void testAddBehaviorWithDuplicate() {
        myBehaviorList.add(ManifestBehavior.REPEAT);
        assertEquals(2, myBehaviorList.size());
    }

    /**
     * Test method for {@link BehaviorList#add(Behavior)}.
     */
    @Test
    public final void testAddBehavior() {
        myBehaviorList.add(ManifestBehavior.AUTO_ADVANCE);
        assertEquals(3, myBehaviorList.size());
    }

    /**
     * Test method for {@link BehaviorList#add(int, Behavior)}.
     */
    @Test
    public final void testAddIntBehavior() {
        myBehaviorList.add(0, ManifestBehavior.REPEAT);
        assertEquals(3, myBehaviorList.size());
    }

    /**
     * Test method for {@link BehaviorList#BehaviorList(Class, Behavior[])}.
     */
    @Test
    public final void testBehaviorListClassOfTBehaviorArray() {
        myBehaviorList =
                new BehaviorList(ManifestBehavior.class, ManifestBehavior.INDIVIDUALS, ManifestBehavior.REPEAT);
        assertEquals(2, myBehaviorList.size());
    }

    /**
     * Test method for {@link BehaviorList#BehaviorList(Class, List)}.
     */
    @Test
    public final void testBehaviorListClassOfTBehaviorList() {
        final List<Behavior> list = List.of(ManifestBehavior.INDIVIDUALS, ManifestBehavior.REPEAT);

        myBehaviorList = new BehaviorList(ManifestBehavior.class, list);
        assertEquals(2, myBehaviorList.size());
    }

    /**
     * Tests {@link BehaviorList#equals(Object) BehaviorList}.
     */
    @Test
    public final void testBehaviorListEqualsHashCode() {
        final BehaviorList behaviorList = new BehaviorList(ManifestBehavior.class, myBehaviorList);
        assertEquals(myBehaviorList.hashCode(), behaviorList.hashCode());
    }

    /**
     * Tests {@link BehaviorList#equals(Object) BehaviorList}.
     */
    @Test
    public final void testBehaviorListEqualsNull() {
        assertNotEquals(myBehaviorList, null);
    }

    /**
     * Test method for {@link BehaviorList#getBehaviorType()}.
     */
    @Test
    public final void testGetBehaviorType() {
        assertEquals(myBehaviorList.getBehaviorType(), ManifestBehavior.class);
    }

    /**
     * Tests {@link BehaviorList#equals(Object) BehaviorList}.
     */
    @Test
    public final void testPropertiesEqualsHashCodeNot() {
        final BehaviorList list1 = new BehaviorList(ManifestBehavior.class, ManifestBehavior.REPEAT);
        final BehaviorList list2 = new BehaviorList(ManifestBehavior.class, ManifestBehavior.NO_REPEAT);

        assertNotEquals(list1.hashCode(), list2.hashCode());
    }

    /**
     * Tests {@link BehaviorList#equals(Object) BehaviorList}.
     */
    @Test
    public final void testPropertiesEqualsSame() {
        assertEquals(myBehaviorList, myBehaviorList);
    }
}
