
package info.freelibrary.iiif.presentation.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.Constants;

/**
 * Tests of the {@link ContextListComparator}.
 */
public class ContextListComparatorTest extends AbstractTest {

    // Would prefer if Eclipse wouldn't keep indenting on each wrap, but just kept the first indent size
    private static final List<URI> CONTEXTS = Arrays.asList(URI.create(LOREM_IPSUM.getUrl()), URI.create(LOREM_IPSUM
            .getUrl()), URI.create(LOREM_IPSUM.getUrl()), URI.create(LOREM_IPSUM.getUrl()), URI.create(LOREM_IPSUM
                    .getUrl()), URI.create(LOREM_IPSUM.getUrl()), URI.create(LOREM_IPSUM.getUrl()), URI.create(
                            LOREM_IPSUM.getUrl()), URI.create(LOREM_IPSUM.getUrl()), URI.create(LOREM_IPSUM.getUrl()),
            URI.create(LOREM_IPSUM.getUrl()), Constants.CONTEXT_URI);

    /**
     * Tests the comparator's sort.
     */
    @Test
    public final void testComparatorSort() {
        final int lastIndex = CONTEXTS.size() - 1;
        final List<URI> preSort = new ArrayList<>();

        // Shuffle until our last list item isn't the required one
        while (Constants.CONTEXT_URI.equals(CONTEXTS.get(lastIndex))) {
            Collections.shuffle(CONTEXTS);
        }

        // Remember the state of our list before the sort, minus the required Context
        assertTrue(preSort.addAll(CONTEXTS));
        assertTrue(preSort.remove(Constants.CONTEXT_URI));

        // Sort list items
        Collections.sort(CONTEXTS, new ContextListComparator());

        // Check that the last URI in the list is our required one and
        // that list has same pre-sort order minus the required context
        assertEquals(CONTEXTS.get(lastIndex), Constants.CONTEXT_URI);
        assertEquals(preSort, CONTEXTS.subList(0, lastIndex));
    }

}
