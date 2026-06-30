
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static info.freelibrary.iiif.presentation.v3.utils.cmdline.JPv3.ENDPOINT_PREFIX;
import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.PERIOD;
import static info.freelibrary.util.Constants.SLASH;
import static info.freelibrary.util.ThrowingConsumer.uncheck;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationCollection;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.csv.CsvSources;
import info.freelibrary.iiif.presentation.v3.utils.csv.Keys;
import info.freelibrary.iiif.presentation.v3.utils.csv.Row;
import info.freelibrary.iiif.presentation.v3.utils.csv.ZipWriter;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.IllegalArgumentI18nException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.Checkstyle;
import info.freelibrary.util.warnings.PMD;
import org.jspecify.annotations.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * Utility class for working with IIIF Presentation JSON files.
 */
@SuppressWarnings({ PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS, PMD.TOO_MANY_STATIC_IMPORTS })
public final class JPv3Utils {

    /** The file extension for CSV files. */
    public static final String CSV_EXT = info.freelibrary.util.Constants.DOT_CHAR + MediaType.TEXT_CSV.getExt();

    /** The file extension for ZIP files. */
    public static final String ZIP_EXT = info.freelibrary.util.Constants.DOT_CHAR + MediaType.APPLICATION_ZIP.getExt();

    /** A logger for the JPv3Utils class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3Utils.class, MessageCodes.BUNDLE);

    /**
     * Creates a new JPv3Utils.
     */
    private JPv3Utils() {
        // This is intentionally left empty
    }

    /**
     * Closes an {@link AutoCloseable} quietly.
     *
     * @param aCloseable An {@link AutoCloseable} to close
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    public static void closeQuietly(final AutoCloseable aCloseable) {
        if (aCloseable != null) {
            try {
                aCloseable.close();
            } catch (final Exception ignored) {
                // This is intentionally ignored
            }
        }
    }

    /**
     * Checks if a path represents a CSV file.
     *
     * @param aPath A path to check
     * @return True if the path is a CSV file; else, false
     */
    public static boolean isCSV(final Path aPath) {
        final Path fileName = aPath.getFileName(); // Throw NPE instead of handling nulls

        if (fileName != null) {
            final String name = fileName.toString();
            return name.endsWith(CSV_EXT) && !name.startsWith(PERIOD);
        }

        return false;
    }

    /**
     * Updates the host of a URI with an `ingest` endpoint prefix, if necessary.
     *
     * @param aURI A URI to update with the endpoint prefix
     * @return The updated URI
     * @throws URISyntaxException If the URI doesn't have a host
     */
    public static URI formatHost(final URI aURI) throws URISyntaxException {
        final String host = aURI.getHost();

        if (host == null) {
            throw new URISyntaxException(aURI.toString(), LOGGER.getMessage(MessageCodes.JPA_190));
        }

        final String ingestHost = host.contains(ENDPOINT_PREFIX + PERIOD) ? host : ENDPOINT_PREFIX + PERIOD + host;

        return formatPath(new URI(aURI.getScheme(), aURI.getUserInfo(), ingestHost, aURI.getPort(), aURI.getPath(),
                aURI.getQuery(), aURI.getFragment()));
    }

    /**
     * Updates the URI path to include the `ingest` endpoint if it's not already present.
     *
     * @param aURI The URI to update
     * @return The updated URI
     * @throws URISyntaxException If the URI doesn't have a valid URI
     */
    public static URI formatPath(final URI aURI) throws URISyntaxException {
        if (!JPv3.INGEST_ENDPOINT_PATH.equals(aURI.getPath())) {
            return new URI(aURI.getScheme(), aURI.getUserInfo(), aURI.getHost(), aURI.getPort(),
                    JPv3.INGEST_ENDPOINT_PATH, aURI.getQuery(), aURI.getFragment());
        }

        return aURI;
    }

    /**
     * Sets the default log level for the supplied logger's root logger.
     *
     * @param aLogger The logger on which to set the supplied level
     * @param aLogLevel The desired log level
     */
    public static void setLogLevel(final Logger aLogger, final String aLogLevel) {
        final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) aLogger.getLoggerImpl();
        final ch.qos.logback.classic.Logger rootLogger = logger.getLoggerContext().getLogger(ROOT_LOGGER_NAME);
        final Level currentLevel = rootLogger.getLevel();
        final Level newLevel;

        if (aLogLevel.isBlank()) {
            newLevel = Level.DEBUG;
        } else {
            newLevel = Level.toLevel(aLogLevel.trim().toUpperCase(Locale.ENGLISH), Level.DEBUG);
        }

        rootLogger.setLevel(newLevel);
        LOGGER.debug(MessageCodes.JPA_186, currentLevel, rootLogger.getEffectiveLevel());
    }

    /**
     * Returns a custom output path based on the input file path.
     *
     * @param aInputFile The input file path
     * @return The custom output path
     */
    public static Path getCustomOutputPath(final Path aInputFile) {
        return Path.of(EMPTY).resolve("output-" + getPathParent(aInputFile) + ZIP_EXT);
    }

    /**
     * Quickly finds a JSON property value.
     *
     * @param aFilePath A path to a JSON file
     * @param aKey A JSON property key
     * @return The optional property value, which will be empty if no value was found
     * @throws IOException If there is trouble parsing the JSON in the supplied file
     */
    public static Optional<String> findValue(final Path aFilePath, final String aKey) throws IOException {
        final JsonFactory factory = new JsonFactory();

        try (JsonParser parser = factory.createParser(aFilePath.toFile())) {
            while (!parser.isClosed()) {
                if (JsonToken.FIELD_NAME == parser.nextToken() && aKey.equals(parser.currentName())) {
                    parser.nextToken(); // Increment the parser to the property value token
                    return Optional.ofNullable(parser.getValueAsString());
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Outputs a ZIP file containing the supplied source files with an additional column for the manifest or collection
     * doc location.
     *
     * @param aSourceFile A location of source file(s)
     * @param aOutputFile An output ZIP file
     * @param aHost A host URI for the IIIF Presentation files
     * @return An exit code (0 for success)
     * @throws IOException If there is trouble writing the ZIP file
     */
    public static int outputZipFile(final Path aSourceFile, final Path aOutputFile, final URI aHost)
            throws IOException {
        final CsvSources csvSources = new CsvSources(Stream.of(aSourceFile), aHost);
        final Optional<Path> tmpDirOpt = csvSources.getTempDir();

        try (ZipWriter zipWriter = new ZipWriter(aOutputFile)) {
            csvSources.forEach(uncheck(path -> {
                final String contents = StringUtils.read(path.toFile());
                final String csvPath = path.toString();
                final String csvData = updateCSV(contents, aHost.toString());

                // If tmpDirOpt is present, we're working with a ZIP file
                if (tmpDirOpt.isPresent()) {
                    final Path tmpDirPath = tmpDirOpt.get();

                    if (csvPath.startsWith(tmpDirPath.toString())) {
                        final String parentPath = getPathParent(aOutputFile);
                        final String filePath = getZipFilePath(path, tmpDirPath);

                        zipWriter.writeFile(Path.of(parentPath, filePath).toString(), csvData);
                    } else {
                        zipWriter.writeFile(stripParentPath(aSourceFile, csvPath), csvData);
                    }
                } else {
                    zipWriter.writeFile(stripParentPath(aSourceFile, csvPath), csvData);
                }
            }));
        }

        return 0;
    }

    /**
     * Determines if the provided row represents a collection by checking its object type or resource type against a
     * defined collection key.
     *
     * @param aRow The row to evaluate, containing potential object and resource type data
     * @return {@code true} if the row represents a collection, otherwise {@code false}
     */
    public static boolean isCollection(final Row aRow) {
        final Optional<String> objectType = aRow.getObjectType();
        final Optional<String> resourceType;

        if (objectType.isPresent() && Keys.COLLECTION.equalsIgnoreCase(objectType.get())) {
            return true;
        }

        // We include IIIF Collections (which may be considered a "work" in another context: e.g., a periodical issue).
        resourceType = aRow.getResourceType();
        return resourceType.isPresent() && Keys.COLLECTION.equalsIgnoreCase(resourceType.get());
    }

    /**
     * Determines if the provided row represents a manifest by checking its object type against a defined manifest key.
     *
     * @param aRow The row to evaluate, containing potential object type data
     * @return {@code true} if the row represents a manifest, otherwise {@code false}
     */
    public static boolean isManifest(final Row aRow) {
        final AtomicBoolean isManifest = new AtomicBoolean(false);

        aRow.getObjectType().ifPresent(objectType -> {
            if (Keys.WORK.equalsIgnoreCase(objectType)) {
                isManifest.set(true);

                // We allow for the work designation to be overridden by a resource type of "collection".
                aRow.getResourceType().ifPresent(resourceType -> {
                    if (Keys.COLLECTION.equalsIgnoreCase(resourceType)) {
                        isManifest.set(false);
                    }
                });
            }
        });

        return isManifest.get();
    }

    /**
     * Gets the file path within the ZIP archive for a given file path and temporary directory path.
     *
     * @param aPath The file path
     * @param aTmpDirPath The temporary directory path
     * @return The file path within the ZIP archive
     */
    private static @NonNull String getZipFilePath(final Path aPath, final Path aTmpDirPath) {
        final Path zipPath = aTmpDirPath.relativize(aPath);
        final String filePath;

        // Check if our ZIP file contained a nested structure or not; if nested, strip first directory
        if (!zipPath.toString().contains(File.separator)) {
            filePath = zipPath.toString();
        } else {
            // If the ZIP file contains a nested structure, we strip the first path component; this makes the
            // assumption that nested ZIPs are always created with a parent directory. We could check this?
            filePath = zipPath.subpath(1, zipPath.getNameCount()).toString();
        }

        return filePath;
    }

    /**
     * Updates a CSV file with the IIIF Presentation URL for each manifest or collection.
     *
     * @param aCsvString A CSV file's contents
     * @param aHost A host URI for the IIIF Presentation files
     * @return The updated CSV file's contents
     * @throws IOException If there is trouble updating the CSV file
     * @throws IllegalArgumentI18nException If the object type isn't recognized
     */
    public static String updateCSV(final String aCsvString, final String aHost) throws IOException {
        final CsvSchema.Builder columns = CsvSchema.builder().setUseHeader(true);
        final String server = aHost.endsWith(SLASH) ? aHost : aHost + SLASH;
        final List<Map<String, String>> modifiedRows = new ArrayList<>();
        final CsvSchema schema = CsvSchema.emptySchema().withHeader();
        final CsvMapper csvMapper = new CsvMapper();
        final TypeReference<Map<String, String>> typeRef = new TypeReference<>() {};
        final ObjectReader reader = csvMapper.readerFor(typeRef).with(schema);
        final String idKey = "Item ARK"; // Need to handle this better in the future

        try (MappingIterator<Map<String, String>> originalRows = reader.readValues(aCsvString)) {
            while (originalRows.hasNext()) {
                final Map<String, String> row = new HashMap<>(originalRows.next());
                final Row rowObject = csvMapper.convertValue(row, Row.class);
                final String id = row.get(idKey);
                final String url;

                if (id == null) {
                    throw new IllegalArgumentI18nException(MessageCodes.BUNDLE, MessageCodes.JPA_012, idKey);
                }

                // We're going to make assumptions about the manifest server's endpoints here
                if (isCollection(rowObject)) {
                    url = server + "collections/" + URLEncoder.encode(id, StandardCharsets.UTF_8);
                    row.put(Keys.IIIF_MANIFEST_URL, url);
                } else if (isManifest(rowObject)) {
                    url = server + URLEncoder.encode(id, StandardCharsets.UTF_8) + "/manifest";
                    row.put(Keys.IIIF_MANIFEST_URL, url);
                } else {
                    row.put(Keys.IIIF_MANIFEST_URL, EMPTY);
                }

                modifiedRows.add(row);
            }
        }

        readHeaders(aCsvString).forEach(columns::addColumn);
        columns.addColumn(Keys.IIIF_MANIFEST_URL);

        return csvMapper.writerFor(modifiedRows.getClass()).with(columns.build()).writeValueAsString(modifiedRows);
    }

    /**
     * Reads a particular IIIF Presentation JSON file of the supplied type.
     *
     * @param aPath A path to a JSON IIIF Presentation file
     * @param aType A type of IIIF Presentation file
     * @return The contents of the supplied file
     * @throws IOException If there is trouble reading the JSON source file
     */
    public static String read(final Path aPath, final String aType) throws IOException {
        final String content = Files.readString(aPath);
        return switch (aType) {
            case ResourceTypes.MANIFEST -> JSON.readValue(content, Manifest.class).toString();
            case ResourceTypes.COLLECTION -> JSON.readValue(content, Collection.class).toString();
            case ResourceTypes.ANNOTATION -> JSON.readValue(content, Annotation.class).toString();
            case ResourceTypes.ANNOTATION_COLLECTION -> JSON.readValue(content, AnnotationCollection.class).toString();
            case ResourceTypes.ANNOTATION_PAGE -> JSON.readValue(content, AnnotationPage.class).toString();
            default -> LOGGER.getMessage(LOGGER.getMessage(MessageCodes.JPA_159, aType));
        };
    }

    /**
     * Reads the headers from a CSV string.
     *
     * @param aCsvString A string of CSV data
     * @return A list of CSV headers
     * @throws IOException If there is trouble reading the CSV string
     */
    public static List<String> readHeaders(final String aCsvString) throws IOException {
        final CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnReordering(true);
        final TypeReference<Map<String, String>> rowType = new TypeReference<>() {};
        final ObjectReader objectReader = new CsvMapper().readerFor(rowType).with(schema);

        try (MappingIterator<Map<String, String>> headerReader = objectReader.readValues(aCsvString)) {
            final List<String> headers;

            if (headerReader.hasNext()) {
                final CsvSchema actualSchema = (CsvSchema) headerReader.getParser().getSchema();
                headers = actualSchema.getColumnNames();
            } else {
                headers = List.of();
            }

            return headers;
        }
    }

    /**
     * Builds a CSV schema for a row from the supplied headers.
     *
     * @param aHeaders The CSV headers
     * @return A CSV schema using those headers
     */
    private static CsvSchema getCsvSchema(final List<String> aHeaders) {
        final CsvSchema.Builder schemaBuilder = CsvSchema.builder().setUseHeader(true);

        aHeaders.forEach(schemaBuilder::addColumn);

        return schemaBuilder.build();
    }

    /**
     * Reads a map-backed CSV row as a {@link Row}, using Jackson's CSV deserialization so Row aliases are honored.
     *
     * @param aRow A map-backed CSV row
     * @param aRowWriter The writer used to serialize a single map-backed row as CSV
     * @param aRowReader The reader used to deserialize a single CSV row as a Row
     * @return The row object
     * @throws IOException If there is trouble reading the row
     */
    private static Row readRow(final Map<String, String> aRow, final ObjectWriter aRowWriter,
      final ObjectReader aRowReader) throws IOException {
        return aRowReader.readValue(aRowWriter.writeValueAsString(aRow));
    }

    /**
     * Strips a supplied source path from another path.
     *
     * @param aSource The source path
     * @param aPath The path to strip
     * @return The stripped path
     */
    private static String stripParentPath(final Path aSource, final String aPath) {
        final String sourcePath = aSource.toString();

        if (aPath.startsWith(sourcePath)) {
            return Path.of(getPathParent(aSource)).resolve(aSource.relativize(Path.of(aPath))).toString();
        }

        return aPath;
    }

    /**
     * Retrieves what will be used as the parent directory for a ZIP file or CSV file.
     *
     * @param aPath The {@link Path} to process
     * @return The name of the parent directory
     * @throws IllegalArgumentI18nException If the path is not a directory, ZIP file, or CSV file
     */
    private static String getPathParent(final Path aPath) {
        if (aPath.toFile().isDirectory()) {
            // Use directory name if the path is a directory
            return aPath.getFileName().toString();
        }

        // Use ZIP file name if the path is a ZIP file
        if (aPath.toString().endsWith(ZIP_EXT) || aPath.toString().endsWith(CSV_EXT)) {
            return FileUtils.stripExt(aPath.getFileName().toString());
        }

        throw new IllegalArgumentI18nException(MessageCodes.BUNDLE, MessageCodes.JPA_192, aPath);
    }

    /**
     * A small file type detector that recognizes ZIP archives, CSV files, and other file types.
     */
    public static class JPv3FileDetector extends FileTypeDetector {

        /** The ZIP magic number. */
        private static final int ZIP_MAGIC = 0x504B0304;

        /** The header size. */
        private static final int HEADER_SIZE = 4;

        @Override
        @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY, Checkstyle.BOOLEAN_EXPR_COMPLEXITY,
            "BooleanExpressionComplexity" })
        public final String probeContentType(final Path aPath) throws IOException {
            // Check the file at the supplied path for the ZIP magic number
            try (InputStream inStream = Files.newInputStream(aPath)) {
                final byte[] header = new byte[4];
                final int bytesRead = inStream.read(header);

                if (bytesRead == HEADER_SIZE) {
                    final int magicNum = ((header[0] & 0xFF) << 24) | ((header[1] & 0xFF) << 16) |
                            ((header[2] & 0xFF) << 8) | (header[3] & 0xFF);

                    if (magicNum == ZIP_MAGIC) {
                        return MediaType.APPLICATION_ZIP.toString();
                    }
                }
            }

            try (BufferedReader reader = Files.newBufferedReader(aPath, StandardCharsets.UTF_8)) {
                int linesChecked = 0;
                String line;

                while ((line = reader.readLine()) != null && linesChecked < 10) {
                    // Skip empty lines at the top of the file
                    if ((line = line.trim()).isEmpty()) {
                        continue;
                    }

                    linesChecked++;

                    // Common CSV delimiters
                    if (line.contains(",") || line.contains("\t") || line.contains(";") || line.contains("|")) {
                        return MediaType.TEXT_CSV.toString();
                    }

                    // If we encounter non‑printable characters, bail out -- it's likely binary.
                    if (!line.chars().allMatch(ch -> ch >= 32 && ch <= 126 || ch == '\r' || ch == '\n')) {
                        break;
                    }
                }
            }

            return null;
        }
    }
}
