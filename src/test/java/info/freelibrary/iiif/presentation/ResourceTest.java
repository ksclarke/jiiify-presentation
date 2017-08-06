
package info.freelibrary.iiif.presentation;

import java.net.MalformedURLException;
import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Attribution;
import info.freelibrary.iiif.presentation.properties.Description;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.License;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.ViewingHint;
import info.freelibrary.iiif.presentation.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.services.ImageInfoService;

import io.vertx.core.json.JsonObject;

public class ResourceTest extends AbstractTest {

    @Test
    public void testSingleValues() throws MalformedURLException {
        final TestResource test = new TestResource();
        final ImageInfoService service = new ImageInfoService(URI.create("http://ils.unc.edu"));

        test.setID("aaaa");
        test.setLabel("bbbb");
        test.setMetadata(new Metadata("myLabel", "myValue"));
        test.setDescription("a description");
        test.setThumbnail(new Thumbnail("asdf", service));
        test.setAttribution("an attribution");
        test.setLicense("http://ils.unc.edu");
        test.setLogo(new Logo("asdf", service));
        test.setViewingHint(Option.CONTINUOUS);
        test.setSeeAlso("http://www.unc.edu");

        System.out.println(JsonObject.mapFrom(test).encodePrettily());
    }

    @Test
    public void testMultiValues() throws MalformedURLException {
        final TestResource test = new TestResource();
        final ImageInfoService service = new ImageInfoService(URI.create("http://ils.unc.edu"));

        test.setID("aaaa");
        test.setLabel(new Label("bbbb").addValue("cccc"));
        test.setMetadata(new Metadata("myLabel1", "myValue1").add("myLabel2", "myValue2")); // addValue?
        test.setDescription(new Description("a first description", "a second description"));
        test.setThumbnail(new Thumbnail("dddd", service).addImage("eeee", service)); // should be value too?
        test.setAttribution(new Attribution("a first attribution").addValue("a second attribution"));
        test.setLicense(new License("http://ils.unc.edu/license1").addValue("http://ils.unc.edu/license2"));
        test.setLogo(new Logo("ffff", service).addImage("gggg", service));
        test.setViewingHint(new ViewingHint(Option.CONTINUOUS).addValue("http://ils.unc.edu/viewingHint"));
        test.setSeeAlso(new SeeAlso("http://1.unc.edu").addValue("http://2.unc.edu"));

        System.out.println(JsonObject.mapFrom(test).encodePrettily());
    }

    class TestResource extends Resource<TestResource> {

        public TestResource() {
            super("fake", 1);
        }
    }

}
