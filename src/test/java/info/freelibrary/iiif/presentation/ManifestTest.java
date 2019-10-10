
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;

import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.services.ImageInfoService;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * A manifest test.
 */
public class ManifestTest extends AbstractTest {

    private static final String SINAI_JSON = "src/test/resources/json/z1960050.json";

    private static final String SERVER = "https://sinai-images.library.ucla.edu/iiif/";

    private static final String MANIFEST_ID = "ark:%2F21198%2Fz1960050";

    private static final String MANIFEST_URI = SERVER + MANIFEST_ID + "/manifest";

    private static final String SEQUENCE_URI = SERVER + MANIFEST_ID + "/sequence/sequence-0";

    private static final String THUMBNAIL_PATH = "/0,1022,6132,6132/150,150/0/default.jpg";

    private static final String MANIFEST_THUMBNAIL_URI = SERVER + "ark:%2F21198%2Fz1d79t3q" + THUMBNAIL_PATH;

    private static final String LOGO_URI = "https://sinai-images.library.ucla.edu/images/logos/iiif_logo.png";

    private static final String TEST_TITLE = "Georgian NF Fragment 68a";

    private static final List<String[]> METADATA_PAIRS = Stream.of(new String[] { "Title", TEST_TITLE },
            new String[] { "Extent", "1 f" }, new String[] { "Overtext Language", "Georgian" }, new String[] {
                "Undertext Language(s)", "Christian Palestinian Aramaic" }).collect(Collectors.toList());

    private static final int HEIGHT = 8176;

    private static final int WIDTH = 6132;

    private List<String[]> myFirstCanvas;

    private List<String[]> mySecondCanvas;

    /**
     * Sets up the manifest testing environment.
     *
     * @throws IOException If there is trouble reading test files
     */
    @Before
    public void setUp() throws IOException {
        final CSVReader reader1 = new CSVReader(new FileReader("src/test/resources/csv/sinai-images-canvas-1.csv"));
        final CSVReader reader2 = new CSVReader(new FileReader("src/test/resources/csv/sinai-images-canvas-2.csv"));

        myFirstCanvas = reader1.readAll();
        mySecondCanvas = reader2.readAll();

        reader1.close();
        reader2.close();
    }

    /**
     * Test reading a Sinai manifest.
     *
     * @throws URISyntaxException If there is an invalid URI
     * @throws UnsupportedEncodingException If there is an unsupported encoding
     */
    @Test
    public void testSinaiManifest() throws URISyntaxException, UnsupportedEncodingException {
        final Buffer buffer = Vertx.factory.vertx().fileSystem().readFileBlocking(SINAI_JSON);
        final JsonObject expected = new JsonObject(buffer);

        final Manifest manifest = new Manifest(MANIFEST_URI, TEST_TITLE);
        final Metadata metadata = new Metadata();
        final Sequence sequence = new Sequence();

        for (final String[] kvPair : METADATA_PAIRS) {
            metadata.add(kvPair[0], kvPair[1]);
        }

        manifest.setMetadata(metadata);
        manifest.setLogo(LOGO_URI);
        manifest.setThumbnail(MANIFEST_THUMBNAIL_URI);
        manifest.setSequences(sequence);

        sequence.setID(SEQUENCE_URI).setLabel(URLDecoder.decode(MANIFEST_ID, StandardCharsets.UTF_8.name()));

        final String canvas1ID = SERVER + MANIFEST_ID + "/canvas/canvas-1";
        final String canvas1Label = "GeoNF-frg68a_001r_K-64-001";
        final String canvas1Thumb = SERVER + "ark:%2F21198%2Fz10v8vhm" + THUMBNAIL_PATH;
        final Canvas canvas1 = new Canvas(canvas1ID, canvas1Label, WIDTH, HEIGHT).setThumbnail(canvas1Thumb);
        final ImageContent content1 = new ImageContent(SERVER + MANIFEST_ID + "/imageanno/imageanno-1", canvas1);

        canvas1.addImageContent(content1);

        for (final String[] values : myFirstCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageInfoService service = new ImageInfoService(SERVER + values[1]);
            final ImageResource resource = new ImageResource(id, service);

            if (values.length == 2) {
                content1.addResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            } else if (values.length == 3) {
                content1.setDefaultResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            }
        }

        final String canvas2ID = SERVER + MANIFEST_ID + "/canvas/canvas-2";
        final String canvas2Label = "GeoNF-frg68a_001v_K-64-002";
        final String canvas2Thumb = SERVER + "ark:%2F21198%2Fz1gq7dfx" + THUMBNAIL_PATH;
        final Canvas canvas2 = new Canvas(canvas2ID, canvas2Label, WIDTH, HEIGHT).setThumbnail(canvas2Thumb);
        final ImageContent content2 = new ImageContent(SERVER + MANIFEST_ID + "/imageanno/imageanno-2", canvas2);

        canvas2.addImageContent(content2);

        for (final String[] values : mySecondCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageInfoService service = new ImageInfoService(SERVER + values[1]);
            final ImageResource resource = new ImageResource(id, service);

            if (values.length == 2) {
                content2.addResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            } else if (values.length == 3) {
                content2.setDefaultResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            }
        }

        sequence.addCanvas(canvas1, canvas2);

        assertEquals(expected, JsonObject.mapFrom(manifest));
    }

    /**
     * Tests reading an example manifest.
     *
     * @throws JsonProcessingException If there is trouble processing JSON
     */
    @Test
    public void testExampleFromDocs() throws JsonProcessingException {
        final Manifest manifest = new Manifest("https://example.org/iiif/001/manifest", "My document");
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.findAndRegisterModules(); // Needed for Jdk8Module

        // System.out.println(mapper.writeValueAsString(manifest));
    }

}
