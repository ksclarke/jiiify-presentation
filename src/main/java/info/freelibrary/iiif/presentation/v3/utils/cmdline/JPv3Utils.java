
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationCollection;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Checkstyle;
import info.freelibrary.util.warnings.PMD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.Locale;
import java.util.Optional;

/**
 * Utility class for working with IIIF Presentation JSON files.
 */
public final class JPv3Utils {

    /** A logger for the JPv3Utils class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3Utils.class, MessageCodes.BUNDLE);

    /**
     * Creates a new JPv3Utils.
     */
    private JPv3Utils() {
        // This is intentionally left empty
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
