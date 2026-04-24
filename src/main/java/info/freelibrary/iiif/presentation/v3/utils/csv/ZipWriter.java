
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The ZipWriter class provides functionality to create and write to ZIP files. It allows adding files with specified
 * content to a ZIP archive and managing the lifecycle of the underlying output stream.
 */
public class ZipWriter implements Closeable {

    /** The ZIP output stream. */
    private final ZipOutputStream myOutputStream;

    /** The writer's output file. */
    private final Path myOutputPath;

    /** The count of entries added to the ZIP archive. */
    private int myEntryCount;

    /**
     * Constructs a new {@code ZipWriter} instance that creates and writes to a ZIP file at the specified path.
     *
     * @param aZipFile The path of the ZIP file to be created or overwritten
     * @throws IOException If an I/O error occurs while opening the output stream
     */
    public ZipWriter(final Path aZipFile) throws IOException {
        myOutputStream = new ZipOutputStream(Files.newOutputStream(aZipFile));
        myOutputPath = aZipFile;
    }

    /**
     * Writes a file with the specified name and content to the ZIP output stream.
     *
     * @param aFileName The name of the file to be added to the ZIP archive, including any desired path
     * @param aFileContent The content to be written into the file inside the ZIP archive
     * @throws IOException If an I/O error occurs while writing to the ZIP output stream
     */
    public void writeFile(final String aFileName, final String aFileContent) throws IOException {
        try (InputStream inStream = new ByteArrayInputStream(aFileContent.getBytes(UTF_8))) {
            final ZipEntry entry = new ZipEntry(aFileName);

            myOutputStream.putNextEntry(entry);
            inStream.transferTo(myOutputStream);
            myOutputStream.closeEntry();
            myEntryCount++;
        }
    }

    /**
     * Returns the number of entries added to the ZIP archive by this {@code ZipWriter} instance.
     *
     * @return The number of entries added to the ZIP archive
     */
    public int getEntryCount() {
        return myEntryCount;
    }

    /**
     * Closes the ZIP output stream associated with this {@code ZipWriter} instance. This method must be called to
     * ensure that all resources held by the output stream are released and that the ZIP archive is properly finalized.
     *
     * @throws IOException If an I/O error occurs while closing the output stream
     */
    @Override
    public void close() throws IOException {
        myOutputStream.close();
    }

    /**
     * Gets the output path of the ZIP file.
     *
     * @return The output file path
     */
    public Path getOutputPath() {
        return myOutputPath;
    }
}
