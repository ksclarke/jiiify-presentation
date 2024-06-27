
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.annotations.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;

/**
 * Tests of {@code AnnotationPage}.
 */
public class AnnotationPageTest {

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
     * Tests setting and getting the next annotation page.
     */
    @Test
    public void testSetGetNextPage() {
        final AnnotationPage<WebAnnotation> myNextPage = new AnnotationPage<>(myID);
        final AnnotationPage<WebAnnotation> myPage = new AnnotationPage<WebAnnotation>(myID).setNextPage(myNextPage);

        myPage.getNextPage().ifPresentOrElse(page -> assertEquals(page.getID(), myID), Assert::fail);
    }

}
