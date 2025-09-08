
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * A seeAlso test.
 */
public class SeeAlsoTest extends AbstractTest {

    /**
     * A logger for the SeeAlso tests.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SeeAlsoTest.class, MessageCodes.BUNDLE);

    /**
     * A test format.
     */
    private static final String JPEG_FORMAT = "image/jpeg";

    /**
     * A test mime-type.
     */
    private static final MediaType MIME_TYPE = MediaType.fromString(JPEG_FORMAT)
            .orElseThrow(() -> new AssertionError(LOGGER.getMessage(MessageCodes.JPA_162, JsonKeys.FORMAT)));

    /**
     * A dynamically assigned test name.
     */
    @Rule
    public TestName myTestName = new TestName();

    /**
     * A test ID.
     */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = myLoremIpsum.getUrl();
    }

    /**
     * Tests getting an empty format.
     */
    @Test
    public void testGetFormatEmpty() {
        assertEquals(Optional.empty(), new SeeAlso(myID, ResourceTypes.DATASET).getFormat());
    }

    /**
     * Tests getting an empty label.
     */
    @Test
    public void testGetLabelEmpty() {
        assertEquals(Optional.empty(), new SeeAlso(myID, ResourceTypes.DATASET).getLabel());
    }

    /**
     * Tests getting a seeAlso profile.
     */
    @Test
    public void testGetProfileEmpty() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);
        assertEquals(Optional.empty(), seeAlso.getProfile());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoStringConstructor() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);

        assertEquals(myID, seeAlso.getID());
        assertEquals(ResourceTypes.DATASET, seeAlso.getType());
    }

    /**
     * Tests constructing a seeAlso.
     */
    @Test
    public void testSeeAlsoURIConstructor() {
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.TEXT);

        assertEquals(myID, seeAlso.getID());
        assertEquals(ResourceTypes.TEXT, seeAlso.getType());
    }

    /**
     * Tests setting and getting a format.
     */
    @Test
    public void testSetGetFormat() {
        new SeeAlso(myID, ResourceTypes.DATASET).setFormat(MIME_TYPE).getFormat().ifPresentOrElse(format -> {
            assertEquals(MIME_TYPE, format);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_162, JsonKeys.FORMAT)));
    }

    /**
     * Tests setting and getting a format as a MediaType.
     */
    @Test
    public void testSetGetFormatMediaType() {
        final MediaType mediaType = MediaType.fromString(JPEG_FORMAT).orElseThrow(() -> {
            return new AssertionError(LOGGER.getMessage(MessageCodes.JPA_162, MediaType.class.getSimpleName()));
        });

        new SeeAlso(myID, ResourceTypes.DATASET).setFormat(mediaType).getFormat().ifPresentOrElse(format -> {
            assertEquals(MediaType.IMAGE_JPEG, format);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_162, JsonKeys.FORMAT)));
    }

    /**
     * Tests setting and getting a label.
     */
    @Test
    public void testSetGetLabel() {
        final String text = myLoremIpsum.getWords(4);
        final SeeAlso seeAlso = new SeeAlso(myID, ResourceTypes.DATASET);

        assertEquals(Optional.of(new Label(text)), seeAlso.setLabel(new Label(text)).getLabel());
    }

    /**
     * Tests getting a seeAlso profile.
     */
    @Test
    public void testSetGetProfile() {
        final String url = myLoremIpsum.getUrl();

        new SeeAlso(myID, ResourceTypes.TEXT).setProfile(url).getProfile().ifPresentOrElse(profile -> {
            assertEquals(url, profile);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_162, JsonKeys.PROFILE)));
    }

    /**
     * Tests getting a seeAlso profile with URI.
     */
    @Test
    public void testSetGetProfileURI() {
        final String uri = myLoremIpsum.getUrl();

        new SeeAlso(myID, ResourceTypes.TEXT).setProfile(uri).getProfile().ifPresentOrElse(profile -> {
            assertEquals(uri, profile);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_162, JsonKeys.PROFILE)));
    }

    /**
     * Tests reading and writing a seeAlso as a part of a manifest.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public void testReadingWriting() throws IOException {
        final String json = Files.readString(Path.of("src/test/resources/json/seeAlso.json"));
        final Manifest manifest = JSON.readValue(json, Manifest.class);

        TestUtils.assertEquals(myTestName, json, manifest.toString());
    }
}
