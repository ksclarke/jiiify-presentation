
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.junit.Test;

/**
 * Tests of {@link ContextList}.
 */
public class ContextListTest extends AbstractTest {

    /** A list of test contexts. */
    private final List<URI> myContexts = Arrays.asList(URI.create(getURL()), URI.create(getURL()), URI.create(getURL()),
            URI.create(getURL()), URI.create(getURL()), URI.create(getURL()), URI.create(getURL()),
            URI.create(getURL()), URI.create(getURL()), URI.create(getURL()), URI.create(getURL()),
            ContextList.PRESENTATION_CONTEXT_URI);

    /**
     * Test method for {@link ContextList#addAll(Collection)}.
     */
    @Test
    public final void testAddAllCollectionOfQextendsURI() {
        final ContextList contexts = new ContextList();

        assertEquals(1, contexts.size());
        contexts.addAll(myContexts);
        assertEquals(12, contexts.size());
    }

    /**
     * Test method for {@link ContextList#addAll(int, Collection)}.
     */
    @Test
    public final void testAddAllIntCollectionOfQextendsURI() {
        final ContextList contexts = new ContextList(myContexts);
        final URI newURI1 = URI.create(getURL());
        final URI newURI2 = URI.create(getURL());
        final URI oldURI = contexts.get(8);

        assertEquals(12, contexts.size());
        assertEquals(oldURI, contexts.get(8));
        contexts.addAll(8, List.of(newURI1, newURI2));
        assertEquals(14, contexts.size());
        assertEquals(newURI1, contexts.get(8));
    }

    /**
     * Test method for {@link ContextList#addFirst(URI)}.
     */
    @Test
    public final void testAddFirstURI() {
        final ContextList contexts = new ContextList();
        final URI uri = URI.create(getURL());

        assertEquals(1, contexts.size());
        contexts.addFirst(uri);
        assertEquals(2, contexts.size());
        assertEquals(ContextList.PRESENTATION_CONTEXT_URI, contexts.get(0));
        assertEquals(uri, contexts.get(1));
    }

    /**
     * Test method for {@link ContextList#add(int, URI)}.
     */
    @Test
    public final void testAddIntURI() {
        final ContextList contexts = new ContextList();
        final URI uri = URI.create(getURL());

        assertEquals(1, contexts.size());
        contexts.add(0, uri);
        assertEquals(2, contexts.size());
        assertEquals(uri, contexts.get(1));
        assertEquals(ContextList.PRESENTATION_CONTEXT_URI, contexts.get(0));
    }

    /**
     * Test method for {@link ContextList#addLast(URI)}.
     */
    @Test
    public final void testAddLastURI() {
        final ContextList contexts = new ContextList();
        final URI uri = URI.create(getURL());

        assertEquals(1, contexts.size());
        contexts.addLast(uri);
        assertEquals(2, contexts.size());
        assertEquals(uri, contexts.get(1));
        assertEquals(ContextList.PRESENTATION_CONTEXT_URI, contexts.get(0));
    }

    /**
     * Test method for {@link ContextList#add(URI)}.
     */
    @Test
    public final void testAddURI() {
        final ContextList contexts = new ContextList();
        final URI uri = URI.create(getURL());

        assertEquals(1, contexts.size());
        contexts.add(uri);
        assertEquals(2, contexts.size());
        assertEquals(uri, contexts.get(1));
        assertEquals(ContextList.PRESENTATION_CONTEXT_URI, contexts.get(0));
    }

    /**
     * Test method for {@link ContextList#clear()}.
     */
    @Test
    public final void testClear() {
        final ContextList contexts = new ContextList(myContexts);

        assertEquals(12, contexts.size());
        contexts.clear();
        assertEquals(1, contexts.size());
    }

    /**
     * Test method for {@link ContextList#ContextList()}.
     */
    @Test
    public final void testContextList() {
        final ContextList contexts = new ContextList();

        assertEquals(1, contexts.size());
        assertEquals(ContextList.PRESENTATION_CONTEXT_URI, contexts.get(0));
    }

    /**
     * Test method for {@link ContextList#ContextList(List)}.
     */
    @Test
    public final void testContextListListOfURI() {
        final ContextList contexts = new ContextList(myContexts);

        assertEquals(12, contexts.size());
        assertEquals(ContextList.PRESENTATION_CONTEXT_URI, contexts.get(0));
    }

    /**
     * Test method for {@link ContextList#equals(Object)}.
     */
    @Test
    public final void testEqualsObject() {
        final ContextList contexts1 = new ContextList(myContexts);
        final ContextList contexts2 = new ContextList(myContexts);

        assertEquals(contexts1, contexts2);
    }

    /**
     * Test method for {@link ContextList#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        final ContextList contexts1 = new ContextList(myContexts);
        final ContextList contexts2 = new ContextList(myContexts);

        assertEquals(contexts1.hashCode(), contexts2.hashCode());
    }

    /**
     * Test method for {@link ContextList#removeAll(Collection)}.
     */
    @Test
    public final void testRemoveAllCollectionOfQ() {
        final ContextList contexts = new ContextList(myContexts);
        final URI uri1 = contexts.get(5);
        final URI uri2 = contexts.get(8);
        final URI uri3 = contexts.get(11);
        final List<URI> list = List.of(uri1, uri2, uri3);

        assertEquals(12, contexts.size());
        contexts.removeAll(list);
        assertEquals(9, contexts.size());
    }

    /**
     * Test method for {@link ContextList#removeIf(Predicate)}.
     */
    @Test
    public final void testRemoveIfPredicateOfQsuperURI() {
        final ContextList contexts = new ContextList(myContexts);
        final URI kept = contexts.get(2);

        contexts.removeIf(uri -> !uri.equals(kept));
        assertEquals(2, contexts.size());
        assertTrue(contexts.contains(kept));
    }

    /**
     * Test method for {@link ContextList#remove(int)}.
     */
    @Test
    public final void testRemoveInt() {
        final ContextList contexts = new ContextList(myContexts);
        final URI uri = contexts.get(8);

        assertEquals(12, contexts.size());
        assertTrue(contexts.contains(uri));
        contexts.remove(8);
        assertEquals(11, contexts.size());
        assertFalse(contexts.contains(uri));
    }

    /**
     * Test method for {@link ContextList#removeLast()}.
     */
    @Test
    public final void testRemoveLast() {
        final ContextList contexts = new ContextList(myContexts);
        final URI uri = contexts.get(contexts.size() - 1);

        assertTrue(contexts.contains(uri));
        contexts.removeLast();
        assertFalse(contexts.contains(uri));
    }

    /**
     * Test method for {@link ContextList#remove(Object)}.
     */
    @Test
    public final void testRemoveObject() {
        final ContextList contexts = new ContextList(myContexts);
        final URI uri = contexts.get(8);

        assertTrue(contexts.contains(uri));
        contexts.remove(uri);
        assertFalse(contexts.contains(uri));
    }

    /**
     * Test method for {@link ContextList#replaceAll(UnaryOperator)}.
     */
    @Test
    public final void testReplaceAllUnaryOperatorOfURI() {
        final ContextList contexts = new ContextList(myContexts);
        final URI uri = contexts.get(10);
        final UnaryOperator<URI> uOp = u -> URI.create(u.toString().replace(uri.toString(), getURL()));

        assertTrue(contexts.contains(uri));
        contexts.replaceAll(uOp);
        assertFalse(contexts.contains(uri));
    }

    /**
     * Test method for {@link ContextList#retainAll(Collection)}.
     */
    @Test
    public final void testRetainAllCollectionOfQ() {
        final ContextList contexts = new ContextList(myContexts);
        final URI uri1 = contexts.get(5);
        final URI uri2 = contexts.get(8);

        assertEquals(12, contexts.size());
        contexts.retainAll(List.of(uri1, uri2));
        assertEquals(3, contexts.size());
    }

    /**
     * Test method for {@link ContextList#set(int, URI)}.
     */
    @Test
    public final void testSetIntURI() {
        final ContextList contexts = new ContextList(myContexts);
        final URI uri = URI.create(getURL());

        assertNotEquals(uri, contexts.get(1));
        contexts.set(1, uri);
        assertEquals(uri, contexts.get(1));
    }

    /**
     * Test method for {@link ContextList#sort(Comparator)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public final void testSortComparatorOfQsuperURI() {
        new ContextList(myContexts).sort((aUriOne, aUriTwo) -> 0);
    }

}
