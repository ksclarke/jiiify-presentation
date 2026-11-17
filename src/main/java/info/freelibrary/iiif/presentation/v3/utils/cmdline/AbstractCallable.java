package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static info.freelibrary.util.Constants.*;
import static info.freelibrary.util.ThrowingConsumer.uncheck;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.Manifestor;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.csv.CsvSources;
import info.freelibrary.iiif.presentation.v3.utils.csv.Keys;
import info.freelibrary.iiif.presentation.v3.utils.csv.Row;
import info.freelibrary.iiif.presentation.v3.utils.csv.ZipWriter;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.HTTP;
import info.freelibrary.util.IllegalArgumentI18nException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.PMD;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * An abstract class for command line callables. It contains the plumbing needed to support the application executable.
 */
@SuppressWarnings({PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS})
public abstract class AbstractCallable implements Callable<Integer> {

    /** The expected prefix for the IIIF manifests and collection documents server. */
    public static final String ENDPOINT_PREFIX = "ingest";

    /** The expected endpoint for collection documents. */
    public static final String COLLECTIONS_PATH = "/collections/";

    /** The file extension for CSV files. */
    public static final String CSV_EXT = DOT_CHAR + MediaType.TEXT_CSV.getExt();

    /** The file extension for ZIP files. */
    public static final String ZIP_EXT = DOT_CHAR + MediaType.APPLICATION_ZIP.getExt();

    /**
     * Retrieves what will be used as the parent directory for a ZIP file or CSV file.
     *
     * @param aPath The {@link Path} to process
     * @return The name of the parent directory
     * @throws info.freelibrary.util.IllegalArgumentI18nException If the path is not a directory, ZIP file, or CSV
     *   file
     */
    protected static String getPathParent(final Path aPath) {
        if (Objects.requireNonNull(aPath).toFile().isDirectory()) {
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
     * Updates the URI path to include the `ingest` endpoint if it's not already present. If a URI for a collection doc
     * is requested, it is returned as is.
     *
     * @param aURI The URI to update
     * @return The updated URI
     * @throws URISyntaxException If the URI doesn't have a valid URI
     * @throws NullPointerException If the URI is null
     */
    protected URI formatPath(final URI aURI) throws URISyntaxException {
        if (Objects.requireNonNull(aURI).toString().contains(COLLECTIONS_PATH)) {
            // If this is a collection doc request, just let it through as is
            return aURI;
        }

        if (!JPv3.INGEST_ENDPOINT_PATH.equals(aURI.getPath())) {
            return new URI(aURI.getScheme(), aURI.getUserInfo(), aURI.getHost(), aURI.getPort(),
              JPv3.INGEST_ENDPOINT_PATH, aURI.getQuery(), aURI.getFragment());
        }

        return aURI;
    }

    /**
     * Updates the host of a URI with an `ingest` endpoint prefix, if necessary.
     *
     * @param aURI A URI to update with the endpoint prefix
     * @return The updated URI
     * @throws java.net.URISyntaxException If the URI doesn't have a host
     */
    protected URI formatHost(final URI aURI) throws URISyntaxException {
        return formatHost(aURI, EMPTY);
    }

    /**
     * Updates the host of a URI with an `ingest` endpoint prefix, if necessary.
     *
     * @param aURI A URI to update with the endpoint prefix
     * @param aCollectionID The collection ID to append to the URI path
     * @return The updated URI
     * @throws URISyntaxException If the URI doesn't have a host
     * @throws NullPointerException If the URI is null
     */
    protected URI formatHost(final URI aURI, final String aCollectionID) throws URISyntaxException {
        final String ingestHost;
        final String host;
        final String path;

        if ((host = Objects.requireNonNull(aURI).getHost()) == null) {
            throw new URISyntaxException(aURI.toString(), getLogger().getMessage(MessageCodes.JPA_190));
        }

        // Append the ingest endpoint prefix to the host if it's not already there
        ingestHost = host.contains(ENDPOINT_PREFIX + DOT_CHAR) ? host : ENDPOINT_PREFIX + DOT_CHAR + host;

        if (!aCollectionID.isBlank()) {
            final String uriPath = aURI.getPath();

            // If no path is supplied, and we have a collection ID, we're looking up a collection
            if (SLASH.equals(uriPath) || uriPath.isBlank()) {
                // This collection doc lookup is hard-coded to Fester's path for now
                path = COLLECTIONS_PATH + URLEncoder.encode(aCollectionID, StandardCharsets.UTF_8);
            } else {
                path = uriPath;
            }
        } else {
            path = aURI.getPath();
        }

        return formatPath(new URI(aURI.getScheme(), aURI.getUserInfo(), ingestHost, aURI.getPort(), path, null, null));
    }

    /**
     * Converts a renote URI to a local CSV file.
     *
     * @param aURI A URl representation a collection document
     * @param aUsername A username for authentication on the manifest server
     * @param aPassword A password for authentication on the manifest server
     * @return A path to the local CSV file
     * @throws IOException If an I/O error occurs
     */
    protected Path convertToCSV(final URI aURI, final String aUsername, final String aPassword) throws IOException {
        final Path csvFile = Files.createTempFile(Keys.COLLECTION, DOT_CHAR + MediaType.TEXT_CSV.getExt());

        getLogger().info(MessageCodes.JPA_211, aURI);

        try (HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()) {
            final byte[] credentials = (aUsername + COLON + aPassword).getBytes(StandardCharsets.UTF_8);
            final String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);
            final HttpRequest request = HttpRequest.newBuilder().uri(Objects.requireNonNull(aURI)).header(
              HTTP.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()).header(HTTP.Header.AUTHORIZATION,
              basicAuth).GET().build();
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final int statusCode = response.statusCode();

            if (statusCode == HTTP.OK) {
                final Manifestor manifestor = new Manifestor();
                final Collection collection = manifestor.readCollection(response.body());

                // Clear out all items and keep just the collection metadata, then write the CSV file
                collection.getItems().clear();
                writeCSV(collection, csvFile);
            } else {
                throw new IOException(getLogger().getMessage(MessageCodes.JPA_212, aURI, statusCode));
            }
        } catch (final InterruptedException details) {
            throw new IOException(details);
        }

        return csvFile;
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
    public String updateCSV(final String aCsvString, final String aHost) throws IOException {
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
                final String id = row.get(idKey);
                final String objectType = JPv3Utils.getObjectType(row);
                final String resourceType = JPv3Utils.getResourceType(row);
                final Row rowObject = new Row(id, objectType, resourceType);
                final String url;

                if (id == null) {
                    throw new IllegalArgumentI18nException(MessageCodes.BUNDLE, MessageCodes.JPA_012, idKey);
                }

                // We're going to make assumptions about the manifest server's endpoints here
                if (JPv3Utils.isCollection(rowObject)) {
                    url = server + "collections/" + URLEncoder.encode(id, StandardCharsets.UTF_8);
                    row.put(Keys.IIIF_MANIFEST_URL, url);
                } else if (JPv3Utils.isManifest(rowObject)) {
                    url = server + URLEncoder.encode(id, StandardCharsets.UTF_8) + "/manifest";
                    row.put(Keys.IIIF_MANIFEST_URL, url);
                } else {
                    row.put(Keys.IIIF_MANIFEST_URL, EMPTY);
                }

                modifiedRows.add(row);
            }
        }

        readHeaders(aCsvString).stream().filter(header -> !Keys.IIIF_MANIFEST_URL.equals(header))
          .forEach(columns::addColumn);
        columns.addColumn(Keys.IIIF_MANIFEST_URL);

        return csvMapper.writerFor(modifiedRows.getClass()).with(columns.build()).writeValueAsString(modifiedRows);
    }

    /**
     * Reads the headers from a CSV string.
     *
     * @param aCsvString A string of CSV data
     * @return A list of CSV headers
     * @throws IOException If there is trouble reading the CSV string
     */
    protected List<String> readHeaders(final String aCsvString) throws IOException {
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
     * Outputs a ZIP file containing the supplied source files with an additional column for the manifest or collection
     * doc location.
     *
     * @param aSourceFile A location of source file(s)
     * @param aOutputFile An output ZIP file
     * @param aHost A host URI for the IIIF Presentation files
     * @return An exit code (0 for success)
     * @throws IOException If there is trouble writing the ZIP file
     */
    protected int outputZipFile(final Path aSourceFile, final Path aOutputFile, final URI aHost) throws IOException {
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
     * Returns the logger for this callable.
     *
     * @return The callable's logger
     */
    protected abstract Logger getLogger();

    /**
     * Writes the supplied collection to a CSV file.
     *
     * @param aCollection A collection doc to partially serialize to CSV
     * @param aCsvPath The path of the output CSV file
     * @throws IOException If there is trouble writing the CSV file
     * @throws IllegalArgumentI18nException If no title is found for the collection
     */
    private void writeCSV(final Collection aCollection, final Path aCsvPath) throws IOException {
        final List<String> row = new ArrayList<>();
        final CsvMapper mapper = new CsvMapper();
        final CsvSchema.Builder builder = CsvSchema.builder();
        final String id = aCollection.getID();
        final CsvSchema schema;

        // "Object Type" is always a collection here
        builder.addColumn(Keys.OBJECT_TYPE);
        row.add(Keys.COLLECTION);

        // Add "Title"
        Objects.requireNonNull(aCollection).getLabel().ifPresent(label -> {
            label.getDefaultValue().ifPresentOrElse(row::add, () -> {
                label.getFirstValue().ifPresentOrElse(row::add, () -> {
                    // All pre-existing records should already have a title
                    throw new IllegalArgumentI18nException(MessageCodes.JPA_210);
                });
            });

            builder.addColumn(Keys.TITLE);
        });

        // Add "Item ID", making the same assumption about the structure of manifest server URLs
        builder.addColumn(Keys.ITEM_ID);
        row.add(URLDecoder.decode(id.substring(id.lastIndexOf('/') + 1), StandardCharsets.UTF_8));

        // Add "Parent ID"
        builder.addColumn(Keys.PARENT_ID);
        row.add(EMPTY);

        // Prepare the headers
        schema = builder.setUseHeader(true).build();

        // Write the CSV file
        try (Writer fileWriter = Files.newBufferedWriter(aCsvPath, StandardCharsets.UTF_8); SequenceWriter csvWriter =
          mapper.writerFor(String[].class).with(schema).writeValues(fileWriter)) {
            csvWriter.write(row.toArray(new String[]{}));
        }
    }

    /**
     * Gets the file path within the ZIP archive for a given file path and temporary directory path.
     *
     * @param aPath The file path
     * @param aTmpDirPath The temporary directory path
     * @return The file path within the ZIP archive
     */
    private @NonNull String getZipFilePath(final Path aPath, final Path aTmpDirPath) {
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
     * Strips a supplied source path from another path.
     *
     * @param aSource The source path
     * @param aPath The path to strip
     * @return The stripped path
     */
    private String stripParentPath(final Path aSource, final String aPath) {
        final Path target = Path.of(aPath);

        // If the two supplied arguments are the same, just return the file name (which is stripped of path)
        if (aSource.equals(target)) {
            return target.getFileName().toString();
        }

        return aPath.startsWith(aSource.toString()) ? Path.of(getPathParent(aSource)).resolve(
          aSource.relativize(target)).toString() : aPath;
    }
}
