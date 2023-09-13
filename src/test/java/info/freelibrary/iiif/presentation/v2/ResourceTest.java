
package info.freelibrary.iiif.presentation.v2;

import java.net.MalformedURLException;
import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.Attribution;
import info.freelibrary.iiif.presentation.v2.properties.Description;
import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.License;
import info.freelibrary.iiif.presentation.v2.properties.Logo;
import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v2.properties.Thumbnail;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;

/**
 * A resource test.
 */
public class ResourceTest extends AbstractTest {

    /** A test value. */
    private static final String AAAA = "aaaa";

    /** A test value. */
    private static final String BBBB = "bbbb";

    /** A test value. */
    private static final String ASDF_JPG = "asdf.jpg";

    /** A test value. */
    private static final String SILS_URL = "http://ils.unc.edu";

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

        test.setID(AAAA);
        test.setLabel(BBBB);
        test.setMetadata(new Metadata("myLabel", "myValue"));
        test.setDescription("a description");
        test.setThumbnail(new Thumbnail(ASDF_JPG, service));
        test.setAttribution("an attribution");
        test.setLicense(SILS_URL);
        test.setLogo(new Logo(ASDF_JPG, service));
        test.setViewingHint(Option.CONTINUOUS);
        test.setSeeAlso("http://www.unc.edu");

        // System.out.println(JsonObject.mapFrom(test).encodePrettily());
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

        test.setID(AAAA);
        test.setLabel(new Label(BBBB).addValue("cccc"));
        test.setMetadata(new Metadata("myLabel1", "myValue1").add("myLabel2", "myValue2")); // addValue?
        test.setDescription(new Description("a first description", "a second description"));
        test.setThumbnail(new Thumbnail("dddd.jpg", service).addImage("eeee.jpg", service)); // should be value too?
        test.setAttribution(new Attribution("a first attribution").addValue("a second attribution"));
        test.setLicense(new License("http://ils.unc.edu/license1").addValue("http://ils.unc.edu/license2"));
        test.setLogo(new Logo("ffff.jpg", service).addImage("gggg.jpg", service));
        test.setViewingHint(new ViewingHint(Option.CONTINUOUS).addValue("http://ils.unc.edu/viewingHint"));
        test.setSeeAlso(new SeeAlso("http://1.unc.edu").addValue("http://2.unc.edu"));

        // System.out.println(JsonObject.mapFrom(test).encodePrettily());
    }

    /**
     * Tests constructing a resource.
     */
    class TestResource extends Resource<TestResource> {

        /**
         * Creates a new test resource.
         */
        TestResource() {
            super("fake", 1);
        }
    }

}
