
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.Constants;

import info.freelibrary.iiif.presentation.v3.annotations.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;

/**
 * Tests of {@code AnnotationPage}.
 */
public class AnnotationPageTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** An ID to use when creating pages. */
    private String myID;

    /**
     * Set up the testing environment.
     */
    @Before
    public void setUp() {
        myID = "https://" + UUID.randomUUID().toString();
    }

    /**
     * Tests adding annotations to an annotation page.
     */
    @Test
    public void testAddAnnotations() {
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(myID);

        page.addAnnotations(new BookmarkingAnnotation(myID, new Canvas(myID)),
                new WebAnnotation(myID, new Canvas(myID)));

        assertEquals(2, page.getAnnotations().size());
    }

    /**
     * Tests {@link AnnotationPage#equals(Object) AnnotationPage}.
     */
    @Test
    public final void testAnnotationPageEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final AnnotationPage<WebAnnotation> test1 = new AnnotationPage<>(HTTPS + id);
        final AnnotationPage<WebAnnotation> test2 = new AnnotationPage<>(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AnnotationPage#equals(Object) AnnotationPage}.
     */
    @Test
    public final void testAnnotationPageEqualsHashCodeNot() {
        final AnnotationPage<WebAnnotation> test1 = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<WebAnnotation> test2 = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link AnnotationPage#equals(Object) AnnotationPage}.
     */
    @Test
    public final void testAnnotationPageEqualsNull() {
        final AnnotationPage<WebAnnotation> test = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());
        assertFalse(test.equals(null));
    }

    /**
     * Tests {@link AnnotationPage#equals(Object) AnnotationPage}.
     */
    @Test
    public final void testAnnotationPageEqualsSame() {
        final String id = UUID.randomUUID().toString();
        final AnnotationPage<WebAnnotation> test1 = new AnnotationPage<>(HTTPS + id);
        final AnnotationPage<WebAnnotation> test2 = new AnnotationPage<>(HTTPS + id);

        assertTrue(test1.equals(test2));
    }

    /**
     * Tests {@link AnnotationPage#equals(Object) AnnotationPage}.
     */
    @Test
    public final void testAnnotationPageEqualsSameNot() {
        final AnnotationPage<WebAnnotation> test1 = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());
        final AnnotationPage<WebAnnotation> test2 = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());

        assertFalse(test1.equals(test2));
    }

    /**
     * Tests {@link AnnotationPage#equals(Object) AnnotationPage}.
     */
    @Test
    public final void testAnnotationPageEqualsSameObject() {
        final AnnotationPage<WebAnnotation> test = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());
        assertTrue(test.equals(test));
    }

    /**
     * Tests {@link AnnotationPage#equals(Object) AnnotationPage}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testAnnotationPageEqualsString() {
        final AnnotationPage<WebAnnotation> test = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());
        assertFalse(test.equals(new String(Constants.EMPTY)));
    }

    /**
     * Tests {@link AnnotationPage#removeExternalContext()}.
     */
    @Test
    public void testRemoveExternalContext() {
        final AnnotationPage<WebAnnotation> test = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());

        assertFalse(test.hasExternalContext());
        test.setExternalContext();
        assertTrue(test.hasExternalContext());
        test.removeExternalContext();
        assertFalse(test.hasExternalContext());
    }

    /**
     * Tests setting the page's annotations.
     */
    @Test
    public void testSetAnnotations() {
        final AnnotationPage<WebAnnotation> test = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());

        assertEquals(0, test.getAnnotations().size());
        test.setAnnotations(new BookmarkingAnnotation(HTTPS + UUID.randomUUID().toString(), new Canvas(myID)));
        test.setAnnotations(new BookmarkingAnnotation(HTTPS + UUID.randomUUID().toString(), new Canvas(myID)));
        assertEquals(1, test.getAnnotations().size());
    }

    /**
     * Sets the page's annotations via a list.
     */
    @Test
    public void testSetAnnotationsList() {
        final AnnotationPage<WebAnnotation> test = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());

        assertEquals(0, test.getAnnotations().size());
        test.setAnnotations(new BookmarkingAnnotation(HTTPS + UUID.randomUUID().toString(), new Canvas(myID)));
        test.setAnnotations(List.of(new BookmarkingAnnotation(HTTPS + UUID.randomUUID().toString(), new Canvas(myID))));
        assertEquals(1, test.getAnnotations().size());
    }

    /**
     * Tests {@link AnnotationPage#setBehaviors(Behavior)}.
     */
    @Test
    public void testSetBehaviorList() {
        final AnnotationPage<WebAnnotation> test = new AnnotationPage<>(HTTPS + UUID.randomUUID().toString());
        assertEquals(1, test.setBehaviors(List.of(ResourceBehavior.HIDDEN)).getBehaviors().size());
    }

    /**
     * Tests setting and getting the next annotation page.
     */
    @Test
    public void testSetGetNextPage() {
        final AnnotationPage<WebAnnotation> myNextPage = new AnnotationPage<>(myID);
        final AnnotationPage<WebAnnotation> myPage = new AnnotationPage<WebAnnotation>(myID).setNextPage(myNextPage);

        myPage.getNextPage().ifPresentOrElse(page -> assertEquals(page.getID(), myID), Assert::fail);
    }

}
