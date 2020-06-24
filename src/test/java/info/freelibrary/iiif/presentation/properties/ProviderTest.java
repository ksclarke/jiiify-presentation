
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.ImageContent;
import info.freelibrary.iiif.presentation.ResourceTypes;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * Tests for the provider property.
 */
public class ProviderTest extends AbstractTest {

    private static final File TEST_FIXTURE = new File("src/test/resources/json/provider.json");

    private URI myID;

    private Label myLabel;

    private Homepage myHomepage;

    private ImageContent myLogo;

    private SeeAlso mySeeAlso;

    /**
     * Sets up the tests.
     */
    @Before
    public final void setUp() {
        myID = URI.create(UUID.randomUUID().toString());
        myLabel = new Label(LOREM_IPSUM.getTitle(5));
        myHomepage = new Homepage(LOREM_IPSUM.getUrl(), LOREM_IPSUM.getTitle(5));
        myLogo = new ImageContent(StringUtils.format("http://library.ucla.edu/images/{}/image.jpg", myID));
        mySeeAlso = new SeeAlso(LOREM_IPSUM.getUrl(), ResourceTypes.TEXT);
    }

    /**
     * Test method for {@link Provider#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        final Provider firstProvider = new Provider(myID, myLabel);
        final Provider secondProvider = new Provider(myID, myLabel);

        firstProvider.setHomepages(myHomepage).setLogos(myLogo).setSeeAlsoRefs(mySeeAlso);
        secondProvider.setHomepages(myHomepage).setLogos(myLogo).setSeeAlsoRefs(mySeeAlso);

        assertEquals(firstProvider.hashCode(), secondProvider.hashCode());
    }

    /**
     * Test method for {@link Provider#setID(URI)}.
     */
    @Test
    public final void testSetGetID() {
        final URI id = URI.create(LOREM_IPSUM.getUrl());
        assertEquals(myID, new Provider(id, myLabel).setID(myID).getID());
    }

    /**
     * Test method for {@link Provider#setLabel(Label)}.
     */
    @Test
    public final void testSetGetLabelLabel() {
        final Label label = new Label(LOREM_IPSUM.getTitle(5));
        assertEquals(myLabel, new Provider(myID, label).setLabel(myLabel).getLabel());
    }

    /**
     * Test method for {@link Provider#setLabel(String)}.
     */
    @Test
    public final void testSetGetLabelString() {
        final Label label = new Label(LOREM_IPSUM.getTitle(5));
        assertEquals(myLabel, new Provider(myID, label).setLabel(myLabel.getString()).getLabel());
    }

    /**
     * Test method for {@link Provider#setHomepages(Homepage[])}.
     */
    @Test
    public final void testSetGetHomepagesHomepageArray() {
        final Provider provider = new Provider(myID, myLabel);

        assertEquals(0, provider.getHomepages().size());
        assertEquals(1, provider.setHomepages(myHomepage).getHomepages().size());
    }

    /**
     * Test method for {@link Provider#setHomepages(List)}.
     */
    @Test
    public final void testSetGetHomepagesListOfHomepage() {
        final List<Homepage> list = Arrays.asList(myHomepage);
        final Provider provider = new Provider(myID, myLabel);

        assertEquals(0, provider.getHomepages().size());
        assertEquals(1, provider.setHomepages(list).getHomepages().size());
    }

    /**
     * Test method for {@link Provider#setLogos(Logo[])}.
     */
    @Test
    public final void testSetLogosLogoArray() {
        final Provider provider = new Provider(myID, myLabel);

        assertEquals(0, provider.getLogos().size());
        assertEquals(1, provider.setLogos(myLogo).getLogos().size());
    }

    /**
     * Test method for {@link Provider#setLogos(List)}.
     */
    @Test
    public final void testSetLogosListOfLogo() {
        final List<ImageContent> list = Arrays.asList(myLogo);
        final Provider provider = new Provider(myID, myLabel);

        assertEquals(0, provider.getLogos().size());
        assertEquals(1, provider.setLogos(list).getLogos().size());
    }

    /**
     * Test method for {@link Provider#setSeeAlsoRefs(SeeAlso[])}.
     */
    @Test
    public final void testSetSeeAlsoRefsSeeAlsoArray() {
        final Provider provider = new Provider(myID, myLabel);

        assertEquals(0, provider.getSeeAlsoRefs().size());
        assertEquals(1, provider.setSeeAlsoRefs(mySeeAlso).getSeeAlsoRefs().size());
    }

    /**
     * Test method for {@link Provider#setSeeAlsoRefs(List)}.
     */
    @Test
    public final void testSetSeeAlsoRefsListOfSeeAlso() {
        final List<SeeAlso> list = Arrays.asList(mySeeAlso);
        final Provider provider = new Provider(myID, myLabel);

        assertEquals(0, provider.getSeeAlsoRefs().size());
        assertEquals(1, provider.setSeeAlsoRefs(list).getSeeAlsoRefs().size());
    }

    /**
     * Test method for {@link Provider#equals(Object)}.
     */
    @Test
    public final void testEqualsObject() {
        final Provider firstProvider = new Provider(myID, myLabel);
        final Provider secondProvider = new Provider(myID, myLabel);

        firstProvider.setHomepages(myHomepage).setLogos(myLogo).setSeeAlsoRefs(mySeeAlso);
        secondProvider.setHomepages(myHomepage).setLogos(myLogo).setSeeAlsoRefs(mySeeAlso);

        assertTrue(firstProvider.equals(secondProvider));
    }

    /**
     * Test method for {@link Provider#toString()}.
     */
    @Test
    public final void testFromToString() throws IOException {
        final String testFixture = StringUtils.read(TEST_FIXTURE);
        final Provider provider = Provider.fromString(testFixture);

        assertEquals(new JsonObject(testFixture), new JsonObject(provider.toString()));
    }

    /**
     * Test method for {@link Provider#toString()}.
     */
    @Test
    public final void testFromToJSON() throws IOException {
        final JsonObject testFixture = new JsonObject(StringUtils.read(TEST_FIXTURE));
        final Provider provider = Provider.fromJSON(testFixture);

        assertEquals(testFixture, provider.toJSON());
    }
}
