
package info.freelibrary.iiif.presentation.v3.content;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.exts.geo.Properties;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Tests of {@code AbstractContentResource}.
 */
public class AbstractContentResourceTest {

    /** Languages to use when testing. */
    private static final String[] LANGS = { "en", "fr" };

    /** A content resource to test. */
    private AbstractContentResource<TestContentResource> myResource;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myResource = new TestContentResource(ResourceTypes.IMAGE, ResourceBehavior.class);
    }

    /**
     * Tests {@link AbstractContentResource#equals(Object)}.
     */
    @Test
    public final void testAbstractContentResourceEqualsNull() {
        assertNotEquals(myResource, null);
    }

    /**
     * Tests {@link AbstractContentResource#equals(Object)}.
     */
    @Test
    public final void testAbstractContentResourceEqualsSameNot() {
        final AbstractContentResource<TestContentResource> resource =
                new TestContentResource(ResourceTypes.IMAGE, ResourceBehavior.class);

        resource.setID(getID());
        assertNotEquals(myResource, resource);
    }

    /**
     * Tests {@link AbstractContentResource#equals(Object)}.
     */
    @Test
    public final void testAbstractContentResourceEqualsSameObject() {
        assertEquals(myResource, myResource);
    }

    /**
     * Test method for {@link AbstractContentResource#getLanguages()}.
     */
    @Test
    public final void testGetLanguages() {
        assertEquals(0, myResource.getLanguages().size());
        myResource.setLanguages(LANGS);
        assertEquals(2, myResource.getLanguages().size());
    }

    /**
     * Tests {@link Properties#equals(Object) Properties}.
     */
    @Test
    public final void testPropertiesEqualsHashCode() {
        final AbstractContentResource<TestContentResource> resource =
                new TestContentResource(ResourceTypes.IMAGE, ResourceBehavior.class);
        assertEquals(myResource.hashCode(), resource.hashCode());
    }

    /**
     * Tests {@link AbstractContentResource#equals(Object)}.
     */
    @Test
    public final void testPropertiesEqualsHashCodeNot() {
        final AbstractContentResource<TestContentResource> resource =
                new TestContentResource(ResourceTypes.IMAGE, ResourceBehavior.class);

        resource.setID(getID());
        assertNotEquals(myResource.hashCode(), resource.hashCode());
    }

    /**
     * Tests {@link AbstractContentResource#equals(Object)}.
     */
    @Test
    public final void testPropertiesEqualsSame() {
        final AbstractContentResource<TestContentResource> resource =
                new TestContentResource(ResourceTypes.IMAGE, ResourceBehavior.class);
        assertEquals(myResource, resource);
    }

    /**
     * Tests {@link AbstractContentResource#equals(Object)}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testPropertiesEqualsString() {
        assertNotEquals(myResource, EMPTY);
    }

    /**
     * Test method for {@link AbstractContentResource#setAnnotations(AnnotationPage...)}.
     */
    @Test
    public final void testSetAnnotationsAnnotationPageOfWebAnnotationArray() {
        final BookmarkingAnnotation anno1 = new BookmarkingAnnotation(getID(), new Canvas(getID()));
        final BookmarkingAnnotation anno2 = new BookmarkingAnnotation(getID(), new Canvas(getID()));
        final AnnotationPage<WebAnnotation> page1 = new AnnotationPage<>(getID());
        final AnnotationPage<WebAnnotation> page2 = new AnnotationPage<>(getID());

        page1.addAnnotations(anno1);
        page2.addAnnotations(anno2);

        assertEquals(0, myResource.getAnnotations().size());
        myResource.setAnnotations(page1, page2);
        assertEquals(2, myResource.getAnnotations().size());
    }

    /**
     * Test method for {@link AbstractContentResource#setAnnotations(List)}.
     */
    @Test
    public final void testSetAnnotationsListOfAnnotationPageOfWebAnnotation() {
        final BookmarkingAnnotation anno = new BookmarkingAnnotation(getID(), new Canvas(getID()));
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(getID());

        page.addAnnotations(anno);

        assertEquals(0, myResource.getAnnotations().size());
        myResource.setAnnotations(List.of(page));
    }

    /**
     * Test method for {@link AbstractContentResource#setFormat(MediaType)}.
     */
    @Test
    public final void testSetFormat() {
        myResource.setFormat(MediaType.IMAGE_GIF);
        assertTrue(myResource.getFormat().isPresent());
        assertEquals(MediaType.IMAGE_GIF, myResource.getFormat().get());
    }

    /**
     * Gets a new ID to used in testing.
     *
     * @return A unique ID
     */
    private String getID() {
        return "https://" + UUID.randomUUID();
    }

    /**
     * Creates a new ContentResource for testing.
     */
    private static final class TestContentResource extends AbstractContentResource<TestContentResource> {

        /**
         * Creates a new {@code TestContentResource}.
         *
         * @param aType A resource type
         * @param aBehaviorClass A class of allowed behaviors for the supplied resource type
         */
        private TestContentResource(final String aType, final Class<? extends Behavior> aBehaviorClass) {
            super(aType, aBehaviorClass);
        }

        /**
         * Creates a new {@code TestContentResource} from an existing one.
         *
         * @param aResource An existing {@code TestContentResource}
         */
        private TestContentResource(final TestContentResource aResource) {
            super(aResource.getType().isPresent() ? aResource.getType().get() : null, ResourceBehavior.class);
            aResource.copyTo(this);
        }

        /**
         * Creates a copy of this test object.
         *
         * @return A copy of this test object
         */
        public TestContentResource copy() {
            return new TestContentResource(this);
        }
    }
}
