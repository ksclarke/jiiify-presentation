package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static org.junit.Assert.assertEquals;

import info.freelibrary.util.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
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

    /**
     * Tests the outputZipFile method of JPv3Utils.
     */
    @Test
    public void testOutputZipFileFlat() throws IOException {
        final Path source = Path.of("src/test/resources/zip/layers-choice.zip");
        final Path target = Path.of(TARGET, "layers-choice.zip");
        final List<String> expected = List.of("collection.csv", "layers.csv", "pages.csv", "works.csv");

        JPv3Utils.outputZipFile(source, target, HOST);
        checkZipEntries(target, expected);
    }

    /**
     * Tests the outputZipFile method of JPv3Utils.
     */
    @Test
    public void testOutputZipFileNested() throws IOException {
        final Path source = Path.of("src/test/resources/zip/nested.zip");
        final Path target = Path.of(TARGET, "nested.zip");
        final List<String> expected = List.of("nested/collection/jbu-2-collection.csv",
          "nested/layers/jbu-2-layers.csv", "nested/pages/jbu-2-pages.csv", "nested/works/jbu-2-works.csv");

        JPv3Utils.outputZipFile(source, target, HOST);
        checkZipEntries(target, expected.stream().map(Path::of).map(Path::toString).collect(Collectors.toList()));
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
