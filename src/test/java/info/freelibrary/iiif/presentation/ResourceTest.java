
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;

import org.junit.Test;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.iiif.presentation.properties.Attribution;
import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Behavior.Option;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.Rights;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.Constants;

import io.vertx.core.json.JsonObject;

/**
 * A resource test.
 */
public class ResourceTest extends AbstractTest {

    private static final String AAAA = "aaaa";

    private static final String BBBB = "bbbb";

    private static final String ASDF_JPG = "asdf.jpg";

    private static final String SILS_URL = "http://ils.unc.edu";

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
        final JsonObject json;

        test.setID(AAAA);
        test.setLabel(BBBB);
        test.setMetadata(new Metadata("myLabel", "myValue"));
        test.setSummary(myLorem.getWords(5, 8));
        test.setThumbnail(new Thumbnail(ASDF_JPG, service));
        test.setAttribution(myLorem.getWords(5, 8));
        test.setRights(SILS_URL);
        test.setLogo(new Logo(ASDF_JPG, service));
        test.setBehavior(Option.CONTINUOUS);
        test.setSeeAlso("http://www.unc.edu");

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
        test.setAttribution(new Attribution(myLorem.getWords(5, 8)).addStrings(myLorem.getWords(5, 8)));
        test.setRights(new Rights("http://ils.unc.edu/license1").addValue("http://ils.unc.edu/license2"));
        test.setLogo(new Logo("ffff.jpg", service).addImage("gggg.jpg", service));
        test.setBehavior(new Behavior(Option.CONTINUOUS).addValue("http://ils.unc.edu/behavior"));
        test.setSeeAlso(new SeeAlso("http://1.unc.edu").addValue("http://2.unc.edu"));

        json = JsonObject.mapFrom(test);

        assertEquals(AAAA, json.getString(Constants.ID));
    }

    /**
     * Tests constructing a resource.
     */
    class TestResource extends Resource<TestResource> {

        TestResource() {
            super("fake", 1);
        }
    }

}
