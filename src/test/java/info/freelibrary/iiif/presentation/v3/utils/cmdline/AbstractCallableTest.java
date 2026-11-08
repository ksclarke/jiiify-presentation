package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static org.junit.Assert.assertEquals;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;
import org.jspecify.annotations.NonNull;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Tests the {@link AbstractCallable} class.
 */
public class AbstractCallableTest {

    /** A host for testing. */
    private static final URI HOST = URI.create("https://test.ingest.iiif.library.ucla.edu");

    /** A nested zip file name pattern. */
    private static final String NESTED_ZIP = "nested{}.zip";

    /** A target directory for testing. */
    private static final String TARGET = "target";

    /** A list of expected nested files. */
    private static final List<String> EXPECTED_NESTED_FILES = Stream.of(
        "nested/collection/jbu-2-collection.csv",
        "nested/layers/jbu-2-layers.csv",
        "nested/pages/jbu-2-pages.csv",
        "nested/works/jbu-2-works.csv"
      ).map(Path::of).map(Path::toString).toList();

    /** A test callable. */
    private TestCallable myCallable;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myCallable = new TestCallable();
    }

    /**
     * Tests the {@code formatHost} method.
     *
     * @throws URISyntaxException If the tested URI is malformed
     */
    @Test
    public void testFormatHost() throws URISyntaxException {
        final URI host = myCallable.formatHost(URI.create("https://iiif.library.ucla.edu"));

        assertEquals(URI.create("https://ingest.iiif.library.ucla.edu/package"), host);
        assertEquals(HOST + JPv3.INGEST_ENDPOINT_PATH, myCallable.formatHost(HOST).toString());
    }

    /**
     * Tests the outputZipFile method of {@code AbstractCallable}.
     */
    @Test
    public void testOutputZipFileDirs() throws IOException {
        final Path source = Path.of("src/test/resources/csv/nested");
        final String zipFile = StringUtils.format(NESTED_ZIP, Constants.DASH + UUID.randomUUID());
        final Path target = Path.of(TARGET, zipFile);

        myCallable.outputZipFile(source, target, HOST);
        checkZipEntries(target, EXPECTED_NESTED_FILES);
    }

    /**
     * Tests the outputZipFile method of JPv3Utils.
     */
    @Test
    public void testOutputZipFileFlat() throws IOException {
        final Path source = Path.of("src/test/resources/zip/layers-choice.zip");
        final Path target = Path.of(TARGET, "layers-choice.zip");
        final List<String> expected = Stream.of("layers-choice/collection.csv", "layers-choice/layers.csv",
          "layers-choice/pages.csv", "layers-choice/works.csv").map(Path::of).map(Path::toString).toList();

        myCallable.outputZipFile(source, target, HOST);
        checkZipEntries(target, expected);
    }

    /**
     * Tests the outputZipFile method of JPv3Utils.
     */
    @Test
    public void testOutputZipFileNested() throws IOException {
        final Path source = Path.of("src/test/resources/zip/nested.zip");
        final Path target = Path.of(TARGET, StringUtils.format(NESTED_ZIP, Constants.EMPTY));

        myCallable.outputZipFile(source, target, HOST);
        checkZipEntries(target, EXPECTED_NESTED_FILES);
    }

    /**
     * Tests reading headers from a CSV file.
     *
     * @throws IOException If there is trouble reading the CSV data
     */
    @Test
    public void testReadHeaders() throws IOException {
        final String source = StringUtils.read(new File("src/test/resources/csv/jbu-collection.csv"));
        final List<String> actual = myCallable.readHeaders(source);
        final List<String> expected = List.of("File Name", "Object Type", "Title", "Item Sequence", "Item ARK",
          "Parent ARK", "IIIF target", "viewingHint", "Text direction", "Bucketeer state", "Thumbnail",
          "media.height", "media.width", "IIIF Access URL", "NOTES");

        assertEquals(expected, actual);
    }

    /**
     * Tests converting a remote URI into a local path.
     *
     * @throws IOException If there is I/O trouble while testing
     */
    @Test // FIXME: U/P hardcoded
    public void testConvertToCSV() throws IOException {
        final URI uri = URI.create("https://ingest.iiif.library.ucla.edu/collections/ark%3A%2F21198%2Fz1fb75c1");
        final Path expected = myCallable.convertToCSV(uri, "admin", "EvduqxEpak.nd3YhLww7");

    }

    /**
     * Checks the contents of a zip file.
     *
     * @param aZipPath A path to a zip file
     * @param aExpectedList The expected contents of the zip file
     * @throws IOException If there is trouble reading the zip file
     */
    private void checkZipEntries(final Path aZipPath, final List<String> aExpectedList) throws IOException {
        final List<String> entryNames;

        try (ZipFile zipFile = new ZipFile(Objects.requireNonNull(aZipPath).toFile())) {
            entryNames = zipFile.stream().filter(entry -> !entry.isDirectory()).map(ZipEntry::getName).toList();
        }

        assertEquals(aExpectedList, entryNames);
    }

    /**
     * A base class for testing the protected methods of {@code AbstractCallable}.
     */
    private static final class TestCallable extends AbstractCallable {

        /** The test logger. */
        private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCallable.class, MessageCodes.BUNDLE);

        @Override
        public @NonNull Integer call() throws Exception {
            return 0; // We're not testing this, but the protected methods
        }

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }
    }
}
