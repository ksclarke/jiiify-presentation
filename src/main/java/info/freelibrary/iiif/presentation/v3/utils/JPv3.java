
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.ThrowingConsumer;
import info.freelibrary.util.warnings.Checkstyle;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationCollection;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;

/**
 * A jpv3 executable.
 */
public final class JPv3 {

    /** The logger for the executable. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3.class, MessageCodes.BUNDLE);

    /**
     * Creates a new JPv3 instance.
     */
    private JPv3() {
        // This is intentionally left empty
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
                final JsonToken token = parser.nextToken();

                if (JsonToken.FIELD_NAME.equals(token) && aKey.equals(parser.currentName())) {
                    parser.nextToken(); // Increment the parser to the property value token
                    return Optional.ofNullable(parser.getValueAsString());
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Runs the jpv3 executable. This is just a toy at the moment. Expect it to fail.
     *
     * @param anArgsArray An array of arguments
     * @throws IOException If the JSON file cannot be read
     */
    @SuppressWarnings({ PMD.SYSTEM_PRINTLN, Checkstyle.UNCOMMENTED_MAIN, Sonar.SYSTEM_OUT_ERR })
    public static void main(final String[] anArgsArray) throws IOException {
        if (anArgsArray.length > 0) {
            final Path path = Path.of(anArgsArray[0]);

            if (Files.exists(path)) {
                if (anArgsArray.length == SINGLE_INSTANCE) {
                    findValue(path, JsonKeys.TYPE).ifPresentOrElse(
                            (ThrowingConsumer<String, IOException>) type -> System.out.println(read(path, type)),
                            () -> System.err.println(LOGGER.getMessage(MessageCodes.JPA_156)));
                } else {
                    System.out.println(read(path, anArgsArray[1]));
                }
            } else {
                System.err.println(LOGGER.getMessage(MessageCodes.JPA_157));
            }
        } else {
            System.err.println(LOGGER.getMessage(MessageCodes.JPA_158));
        }
    }

    /**
     * Reads a particular IIIF Presentation JSON file of the supplied type.
     *
     * @param aPath A path to a JSON IIIF Presentation file
     * @param aType A type of IIIF Presentation file
     * @return The contents of the supplied file
     * @throws IOException If there is trouble reading the JSON source file
     */
    private static String read(final Path aPath, final String aType) throws IOException {
        final String content = new String(Files.readAllBytes(aPath), StandardCharsets.UTF_8);
        return switch (aType) {
            case ResourceTypes.MANIFEST -> JSON.readValue(content, Manifest.class).toString();
            case ResourceTypes.COLLECTION -> JSON.readValue(content, Collection.class).toString();
            case ResourceTypes.ANNOTATION -> JSON.readValue(content, Annotation.class).toString();
            case ResourceTypes.ANNOTATION_COLLECTION -> JSON.readValue(content, AnnotationCollection.class).toString();
            case ResourceTypes.ANNOTATION_PAGE -> JSON.readValue(content, AnnotationPage.class).toString();
            default -> LOGGER.getMessage(LOGGER.getMessage(MessageCodes.JPA_159, aType));
        };
    }
}
