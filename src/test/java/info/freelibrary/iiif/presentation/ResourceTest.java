
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import org.junit.Test;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Homepage;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.properties.Rights;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.Value;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.services.ImageInfoService;

import io.vertx.core.json.JsonObject;

/**
 * A resource test.
 */
public class ResourceTest extends AbstractTest {

    private static final String AAAA = "aaaa";

    private static final String BBBB = "bbbb";

    private static final String ASDF_JPG = "asdf.jpg";

    private static final String SILS_URL = "http://ils.unc.edu";

    private static final String EXAMPLE_URL = "http://example.org";

    private final Lorem myLorem = LoremIpsum.getInstance();

    /**
     * Constructs the resource test.
     */
    public ResourceTest() {
        super();
    }

    /**
     * Tests setting a resource with single values.
     *
     * @throws MalformedURLException If a URL is malformed
     */
    @Test
    public void testSingleValues() throws MalformedURLException {
        final TestResource test = new TestResource();
        final ImageInfoService service = new ImageInfoService(URI.create(SILS_URL));
        final Label label = new Label(myLorem.getWords(1));
        final Value value = new Value(myLorem.getWords(1));
        final JsonObject json;

        test.setID(AAAA);
        test.setLabel(BBBB);
        test.setMetadata(new Metadata("myLabel", "myValue"));
        test.setSummary(myLorem.getWords(5, 8));
        test.setThumbnail(new Thumbnail(ASDF_JPG, service));
        test.setRequiredStatement(new RequiredStatement(label, value));
        test.setRights(SILS_URL);
        test.setHomepages(new Homepage(SILS_URL, ResourceTypes.TEXT, AAAA));
        test.setLogo(new Logo(ASDF_JPG, service));
        test.setBehaviors(ResourceBehavior.HIDDEN);
        test.setSeeAlsoRefs(new SeeAlso(LOREM_IPSUM.getUrl(), ResourceTypes.DATASET));

        json = JsonObject.mapFrom(test);

        assertEquals(AAAA, json.getString(Constants.ID));
    }

    /**
     * Tests setting a resource with multiple values.
     *
     * @throws MalformedURLException If a URL is malformed
     */
    @Test
    public void testMultiValues() throws MalformedURLException {
        final TestResource test = new TestResource();
        final ImageInfoService service = new ImageInfoService(URI.create(SILS_URL));
        final JsonObject json;

        test.setID(AAAA);
        test.setLabel(new Label(BBBB).addStrings("cccc"));
        test.setMetadata(new Metadata("myLabel1", "myValue1").add("myLabel2", "myValue2")); // addValue?
        test.setSummary(new Summary(myLorem.getWords(5, 8), myLorem.getWords(5, 8)));
        test.setThumbnail(new Thumbnail("dddd.jpg", service).addImage("eeee.jpg", service)); // should be value too?
        test.setRequiredStatement(new RequiredStatement(myLorem.getWords(1), myLorem.getWords(1)));
        test.setRights(new Rights("http://ils.unc.edu/license1").addValue("http://ils.unc.edu/license2"));
        test.setHomepages(new Homepage(SILS_URL, ResourceTypes.TEXT, AAAA), new Homepage(EXAMPLE_URL,
                ResourceTypes.TEXT, BBBB));
        test.setLogo(new Logo("ffff.jpg", service).addImage("gggg.jpg", service));
        test.setBehaviors(ManifestBehavior.AUTO_ADVANCE);
        test.setSeeAlsoRefs(new SeeAlso(LOREM_IPSUM.getUrl(), ResourceTypes.DATASET));

        json = JsonObject.mapFrom(test);

        assertEquals(AAAA, json.getString(Constants.ID));
    }

    /**
     * Tests setting behaviors.
     */
    @Test
    public void testSetBehaviors() {
        final TestResource resource = new TestResource();

        resource.setBehaviors(CanvasBehavior.AUTO_ADVANCE, ManifestBehavior.CONTINUOUS);
        assertEquals(2, resource.getBehaviors().size());

        resource.setBehaviors(CollectionBehavior.INDIVIDUALS);
        assertEquals(1, resource.getBehaviors().size());
    }

    /**
     * Tests adding rather than setting a behavior.
     */
    @Test
    public void testAddBehaviors() {
        final TestResource resource = new TestResource();

        resource.setBehaviors(CanvasBehavior.AUTO_ADVANCE, ManifestBehavior.CONTINUOUS);
        assertEquals(2, resource.getBehaviors().size());

        resource.addBehaviors(CollectionBehavior.INDIVIDUALS);
        assertEquals(3, resource.getBehaviors().size());
    }

    /**
     * Tests removing behaviors from the resource.
     */
    @Test
    public void testRemoveBehaviors() {
        final TestResource resource = new TestResource();

        resource.setBehaviors(CanvasBehavior.AUTO_ADVANCE, ManifestBehavior.CONTINUOUS).clearBehaviors();
        assertEquals(0, resource.getBehaviors().size());
    }

    /**
     * Tests getting an unmodifiable list and trying to add something to it.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetBehaviors() {
        final TestResource resource = new TestResource();
        final List<Behavior> behaviors;

        resource.setBehaviors(CanvasBehavior.AUTO_ADVANCE, ManifestBehavior.CONTINUOUS);
        behaviors = resource.getBehaviors();
        behaviors.add(CollectionBehavior.CONTINUOUS);
    }

    /**
     * Tests constructing a resource.
     */
    class TestResource extends Resource<TestResource> {

        TestResource() {
            super("fake");
        }
    }

}
