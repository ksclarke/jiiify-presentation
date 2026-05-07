
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static org.junit.Assert.assertEquals;

import info.freelibrary.util.Constants;
import info.freelibrary.util.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Unit tests for JPv3Utils.
 */
public class JPv3UtilsTest {

    /** A host for testing. */
    private static final URI HOST = URI.create("https://test.ingest.iiif.library.ucla.edu");

    /** A target directory for testing. */
    private static final String TARGET = "target";

    /** A nested zip file name pattern. */
    private static final String NESTED_ZIP = "nested{}.zip";

    /** A list of expected nested files. */
    private static final List<String> EXPECTED_NESTED_FILES = Stream
            .of("nested/collection/jbu-2-collection.csv", "nested/layers/jbu-2-layers.csv",
                    "nested/pages/jbu-2-pages.csv", "nested/works/jbu-2-works.csv")
            .map(Path::of).map(Path::toString).toList();

    /**
     * Tests the outputZipFile method of JPv3Utils.
     */
    @Test
    public void testOutputZipFileDirs() throws IOException {
        final Path source = Path.of("src/test/resources/csv/nested");
        final String zipFile = StringUtils.format(NESTED_ZIP, Constants.DASH + UUID.randomUUID().toString());
        final Path target = Path.of(TARGET, zipFile);

        JPv3Utils.outputZipFile(source, target, HOST);
        checkZipEntries(target, EXPECTED_NESTED_FILES);
    }

    /**
     * Tests the outputZipFile method of JPv3Utils.
     */
    @Test
    public void testOutputZipFileFlat() throws IOException {
        final Path source = Path.of("src/test/resources/zip/layers-choice.zip");
        final Path target = Path.of(TARGET, "layers-choice.zip");
        final List<String> expected = List.of("layers-choice/collection.csv", "layers-choice/layers.csv",
                "layers-choice/pages.csv", "layers-choice/works.csv");

        JPv3Utils.outputZipFile(source, target, HOST);
        checkZipEntries(target, expected);
    }

    /**
     * Tests the outputZipFile method of JPv3Utils.
     */
    @Test
    public void testOutputZipFileNested() throws IOException {
        final Path source = Path.of("src/test/resources/zip/nested.zip");
        final Path target = Path.of(TARGET, StringUtils.format(NESTED_ZIP, Constants.EMPTY));

        JPv3Utils.outputZipFile(source, target, HOST);
        checkZipEntries(target, EXPECTED_NESTED_FILES);
    }

    /**
     * Tests the updateHost method of JPv3Utils.
     */
    @Test
    public void testUpdateHost() {
        final URI host = JPv3Utils.updateHost(URI.create("https://iiif.library.ucla.edu"));

        assertEquals(URI.create("https://ingest.iiif.library.ucla.edu"), host);
        assertEquals(HOST, JPv3Utils.updateHost(HOST));
    }

    /**
     * Tests reading headers from a CSV file.
     *
     * @throws IOException If there is trouble reading the CSV data
     */
    @Test
    public void testReadHeaders() throws IOException {
        final String source = StringUtils.read(new File("src/test/resources/csv/jbu-collection.csv"));
        final List<String> actual = JPv3Utils.readHeaders(source);
        final List<String> expected = List.of("File Name", "Object Type", "Title", "Item Sequence", "Item ARK",
                "Parent ARK", "IIIF target", "viewingHint", "Text direction", "Bucketeer state", "Thumbnail",
                "media.height", "media.width", "IIIF Access URL", "NOTES");

        assertEquals(expected, actual);
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

        try (ZipFile zipFile = new ZipFile(aZipPath.toFile())) {
            entryNames = zipFile.stream().filter(entry -> !entry.isDirectory()).map(ZipEntry::getName).toList();
        }

        assertEquals(aExpectedList, entryNames);
    }
}
