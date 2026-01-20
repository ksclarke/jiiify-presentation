
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.iiif.presentation.v3.utils.JsonKeys.BODY;
import static info.freelibrary.iiif.presentation.v3.utils.JsonKeys.CONTEXT;
import static info.freelibrary.iiif.presentation.v3.utils.JsonKeys.MOTIVATION;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.SequenceWriter;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.json.Json;
import info.freelibrary.json.JsonOptions;
import info.freelibrary.json.JsonValue;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;
import org.junit.Assert;
import org.junit.rules.TestName;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utilities for running tests.
 */
public final class TestUtils {

    /** A file into which to write out any error diffs. */
    public static final Path DIFF_LINKS = Path.of("target/fixtures-diffs.html");

    /** The directory path of test fixtures. */
    public static final String TEST_DIR = "src/test/resources/json";

    /** The JSON properties that are okay to collapse. */
    private static final List<String> COLLAPSIBLES = Arrays.asList(CONTEXT, BODY, MOTIVATION);

    /** The logger for the test utilities. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class, MessageCodes.BUNDLE);

    /**
     * Creates a new private test utilities class.
     */
    private TestUtils() {
        // This is intentionally left empty.
    }

    /**
     * Tests equality of two JSON strings.
     *
     * @param aName The name of the test being executed
     * @param anExpectedResult An expected JSON result
     * @param anActualResult An actual JSON result
     * @throws AssertionError If the two JSON strings are not equal
     * @throws RuntimeException If the fixtures' diffs file could not be written
     */
    public static void assertEquals(final TestName aName, final String anExpectedResult, final String anActualResult) {
        final JsonOptions config = new JsonOptions().ignoreOrder(true).setCollapsibleArrays(COLLAPSIBLES).format(true);
        final JsonValue expected = Json.parse(anExpectedResult);
        final JsonValue actual = Json.parse(anActualResult);

        // This double equality check can be problematic, since the first equals() is looser than the assertEquals --
        // `equals` will normalize duration floats, but `assertEquals` will not; so, the wrong error may be displayed
        if (!expected.equals(actual, config)) {
            try {
                Assert.assertEquals(expected.toString(config), actual.toString(config));
            } catch (final AssertionError details) {
                final Encoder encoder = Base64.getEncoder();
                final String diffLink = StringUtils.format(
                        "<p><a href=\"https://jsondiff.com/#left=data:base64,{}&right=data:base64,{}\">{}</a></p>",
                        new String(encoder.encode(expected.toString(config).getBytes()), UTF_8),
                        new String(encoder.encode(actual.toString(config).getBytes()), UTF_8), aName.getMethodName());

                try {
                    // Write a file of HTML links pointing to a better diffs display than what JUnit outputs
                    // We don't append, so this is really only useful when we're running a single test
                    Files.write(DIFF_LINKS, Collections.singletonList(diffLink), StandardOpenOption.CREATE,
                            StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                } catch (final IOException ioErrDetails) {
                    throw new RuntimeException(ioErrDetails);
                }

                throw new AssertionError(details.getMessage());
            }
        }
    }

    /**
     * A test utility to make working with {@code Optional}s easier.
     *
     * @param <T> A type of value
     * @param aValue A value to test against
     * @param anOptValue A value wrapped in an {@code Optional}
     * @throws AssertionError The error thrown in the test fails
     */
    public static <T> void assertOptEquals(final T aValue, final Optional<T> anOptValue) throws AssertionError {
        if (anOptValue.isEmpty()) {
            throw new AssertionError(LOGGER.getMessage(MessageCodes.JPA_148));
        }

        Assert.assertEquals(aValue, anOptValue.get());
    }

    /**
     * A convenience method that pretty prints a JSON string so that multiple comparison strings will have the same
     * formatting.
     *
     * @param aJsonString A JSON string
     * @return A formatted JSON string
     * @throws JsonParsingException If the format cannot be parsed from the supplied JSON string
     */
    public static String format(final String aJsonString) {
        try {
            return JSON.getPrettyWriter().writeValueAsString(JSON.readTree(aJsonString));
        } catch (final IOException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Creates an ID suffix.
     *
     * @return An ID suffix
     */
    public static String getRandom() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final StringBuilder buffer = new StringBuilder();

        for (int index = 0; index < 4; index++) {
            buffer.append(random.nextInt(10));
        }

        return buffer.toString();
    }

    /**
     * Returns an object as JSON.
     *
     * @param aObject An object to convert into JSON
     * @return The supplied object's JSON representation
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final Object aObject) throws IOException {
        return toJson(null, aObject, false);
    }

    /**
     * Returns an object as JSON.
     *
     * @param aObject An object to convert into JSON
     * @param aList Whether aObject should be interpreted as a list of objects to serialize
     * @return The supplied object's JSON representation
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final Object aObject, final boolean aList) throws IOException {
        return toJson(null, aObject, aList);
    }

    /**
     * Returns a named object as JSON.
     *
     * @param aName A name for the supplied object
     * @param aObject An object to convert into JSON
     * @return The supplied object's JSON representation
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final String aName, final Object aObject) throws IOException {
        return toJson(aName, aObject, false);
    }

    /**
     * Returns a named object as JSON.
     *
     * @param aName A name for the supplied object
     * @param aObject An object to convert into JSON
     * @param aList Whether aObject should be interpreted as a list of objects to serialize
     * @return The supplied object's JSON representation
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final String aName, final Object aObject, final boolean aList) throws IOException {
        final StringWriter writer = new StringWriter();

        if (aList) {
            final SequenceWriter sequenceWriter = JSON.getWriter().writeValuesAsArray(writer);

            for (final Object object : (List<?>) aObject) {
                sequenceWriter.write(object);
            }

            sequenceWriter.close();
        } else {
            JSON.getWriter().writeValue(writer, aObject);
        }

        return aName != null ? "{\"" + aName + "\" : " + writer.toString() + "}" : writer.toString();
    }
}
