
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static info.freelibrary.iiif.presentation.v3.utils.cmdline.JPv3Utils.CSV_EXT;
import static info.freelibrary.iiif.presentation.v3.utils.cmdline.JPv3Utils.ZIP_EXT;
import static info.freelibrary.util.Constants.SLASH;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.cmdline.JPv3Utils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * An iterator for CSV data.
 */
public class CsvSources implements Iterable<Path> {

    /** A logger for the CsvSources class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvSources.class, MessageCodes.BUNDLE);

    /** The server URL of the IIIF manifest or collection document server. */
    private final String myServer;

    /** The CSV file paths. */
    private final List<Path> myPaths;

    /** The temporary directory for CSV processing. */
    private Path myTmpDir;

    /**
     * Creates a new CSV iterator from the supplied stream, updating the data with the supplied IIIF server URL.
     *
     * @param aCsvStream A stream of CSV files and data
     * @param aServer A IIIF manifest and collection document server
     */
    public CsvSources(final Stream<Path> aCsvStream, final URI aServer) {
        myPaths = gather(aCsvStream).sorted().toList(); // Sort for easier testing (predictability)
        myServer = aServer.toString();
    }

    @Override
    public @NonNull Iterator<Path> iterator() {
        return myPaths.iterator();
    }

    @Override
    public void forEach(final Consumer<? super Path> aAction) {
        myPaths.forEach(aAction);
    }

    /**
     * Gets the number of CSV files.
     *
     * @return The number of CSV files
     */
    public int size() {
        return myPaths.size();
    }

    /**
     * Gets the temporary directory used for CSV processing. This is only needed if the CSV files are compressed (ZIP).
     *
     * @return The temporary directory used for CSV processing
     */
    public Optional<Path> getTempDir() {
        return Optional.ofNullable(myTmpDir);
    }

    @Override
    public Spliterator<Path> spliterator() {
        return myPaths.spliterator();
    }

    /**
     * Gets the IIIF manifest and collection documents server URL.
     *
     * @return The URL of the IIIF manifests and collection documents server
     */
    public String getServer() {
        return myServer;
    }

    /**
     * Processes a stream of paths and recursively gathers file paths, expanding directories and compressed files as
     * needed.
     *
     * @param aStream A stream of {@code Path} objects to be processed
     * @return A flattened stream of all collected {@code Path} objects, including any recursively acquired paths
     */
    private Stream<Path> gather(final Stream<Path> aStream) {
        return aStream.flatMap(this::gather);
    }

    /**
     * Recursively collects file paths, processing directories and compressed files (e.g., ZIP) as needed.
     *
     * @param aPath A {@code Path} object representing a single file or directory to be processed. If the path points to
     *        a directory, its contents will be recursively processed. If the path is a ZIP archive, its entries will
     *        also be processed.
     * @return A {@code Stream<Path>} containing all collected CSV file paths, including any paths from nested
     *         directories or ZIP archives. If an error occurs during processing, an empty stream is returned.
     */
    private Stream<Path> gather(final Path aPath) {
        if (!aPath.endsWith(CSV_EXT)) {
            try {
                if (Files.isDirectory(aPath)) {
                    final List<Path> files;

                    try (Stream<Path> paths = Files.walk(aPath)) {
                        files = paths.filter(Files::isRegularFile).filter(JPv3Utils::isCSV).toList();
                    }

                    return files.stream().flatMap(this::gather);
                } else if (aPath.toString().endsWith(ZIP_EXT)) {
                    List<Path> files;

                    try (FileSystem fileSystem = FileSystems.newFileSystem(aPath, (ClassLoader) null)) {
                        myTmpDir = Files.createTempDirectory(UUID.randomUUID().toString());
                        try (Stream<Path> walk = Files.walk(fileSystem.getPath(SLASH))) {
                            files = walk.filter(Files::isRegularFile).filter(JPv3Utils::isCSV)
                                    .map(path -> copyFromZip(path, myTmpDir)).flatMap(Optional::stream)
                                    .flatMap(this::gather).toList();
                        }
                    } catch (final IOException details) {
                        LOGGER.error(MessageCodes.JPA_187, aPath, details);
                        files = Collections.emptyList();
                    }

                    return files.stream();
                } // Passthrough to the final return
            } catch (final IOException details) {
                LOGGER.error(details, details.getMessage());
                return Stream.empty();
            }
        }

        // Add .csv files as is
        return Stream.of(aPath);
    }

    /**
     * Copies a file from a Zip file onto the local file system.
     *
     * @param aSource A ZIP source file entry
     * @param aParent A parent directory on the local file system
     * @return A path to the copied file
     */
    private Optional<Path> copyFromZip(final Path aSource, final Path aParent) {
        final Path dest = Path.of(aParent.toString(), aSource.toString());

        try {
            Files.createDirectories(dest.getParent());
            Files.copy(aSource, dest, StandardCopyOption.REPLACE_EXISTING);

            return Optional.of(dest);
        } catch (final IOException details) {
            LOGGER.error(MessageCodes.JPA_188, aSource, dest, details);
            return Optional.empty();
        }
    }
}
