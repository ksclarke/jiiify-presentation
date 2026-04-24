
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static info.freelibrary.iiif.presentation.v3.utils.cmdline.JPv3Utils.CSV_EXT;
import static info.freelibrary.iiif.presentation.v3.utils.cmdline.JPv3Utils.ZIP_EXT;
import static info.freelibrary.util.Constants.SLASH;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.cmdline.JPv3Utils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/** The reader class provides functionality to read CSV files. */
public class Reader {

    /** The logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class, MessageCodes.BUNDLE);

    /** A preconfigured reader for CSV files. */
    private final ObjectReader myReader;

    /** Creates a new CSV reader capable of reading CSV files from the file system or a ZIP archive. */
    public Reader() {
        final CsvSchema schema = CsvSchema.emptySchema().withHeader();
        final CsvMapper.Builder builder = CsvMapper.builder();

        builder.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        builder.enable(CsvParser.Feature.TRIM_SPACES);
        builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        myReader = builder.build().readerFor(Row.class).with(schema);
    }

    /**
     * Processes a stream of file paths and produces a combined stream of rows contained in those files.
     *
     * @param aStream A stream of {@link Path} objects representing file paths to be read
     * @return A {@link Stream} of {@link Row} objects representing the parsed content of the files
     */
    public Stream<Row> rows(final Stream<Path> aStream) {
        return aStream.flatMap(this::rows);
    }

    /**
     * Processes a single file path and returns a stream of rows parsed from the file. Supports reading CSV files
     * directly or extracting and processing files from ZIP archives or directories containing CSV files.
     *
     * @param aPath A {@link Path} object representing the file path to be processed. This can be a path to a single CSV
     *        file, a directory containing CSV files, or a ZIP archive containing CSV files
     * @return A {@link Stream} of {@link Row} objects representing the parsed rows from the given file or files. An
     *         empty stream is returned if an error occurs during processing or if no valid CSV files are found
     */
    @SuppressWarnings({ "PMD.CloseResource" })
    public Stream<Row> rows(final Path aPath) {
        final String fileName = aPath.toString();

        if (!fileName.endsWith(CSV_EXT)) {
            try {
                if (Files.isDirectory(aPath)) {
                    final List<Path> files;

                    try (Stream<Path> paths = Files.walk(aPath)) {
                        files = paths.filter(Files::isRegularFile).filter(JPv3Utils::isCSV).toList();
                    } // Closes the walk stream, but we can still stream from our 'files' list

                    return files.stream().flatMap(this::rows);
                } else if (fileName.endsWith(ZIP_EXT)) {
                    // Lazily streams rows from ZIP and ensures resources are closed when the returned stream is closed
                    final FileSystem fileSystem = FileSystems.newFileSystem(aPath, (ClassLoader) null);

                    try {
                        final Stream<Path> paths = Files.walk(fileSystem.getPath(SLASH)).filter(Files::isRegularFile);
                        return paths.filter(JPv3Utils::isCSV).flatMap(this::rows).onClose(() -> {
                            JPv3Utils.closeQuietly(paths);
                            JPv3Utils.closeQuietly(fileSystem);
                        });
                    } catch (final IOException details) {
                        JPv3Utils.closeQuietly(fileSystem);

                        LOGGER.error(details, details.getMessage());
                        return Stream.empty();
                    }
                }
            } catch (final IOException details) {
                LOGGER.error(details, details.getMessage());
                return Stream.empty();
            }
        }

        try {
            final BufferedReader reader = Files.newBufferedReader(aPath, StandardCharsets.UTF_8);
            final MappingIterator<Row> iterator = myReader.readValues(reader);

            // Turn iterator into a Stream<Row>
            final Spliterator<Row> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
            final Stream<Row> stream = StreamSupport.stream(spliterator, false);

            // Ensure we close the iterator (which closes the underlying buffered reader) when the stream closes
            return stream.onClose(() -> {
                JPv3Utils.closeQuietly(iterator);
            });
        } catch (IOException details) {
            LOGGER.error(details, details.getMessage());
            return Stream.empty();
        }
    }
}
