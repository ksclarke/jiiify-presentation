
package info.freelibrary.iiif.webrepl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import info.freelibrary.util.StringUtils;

/**
 * Tests of the {@code ParsingError} class.
 */
class ParsingErrorTest {

    /** The code snippet used in the test. */
    private static final String TEST_CODE = "Hello!";

    /**
     * Tests the {@code ParsingError} constructor.
     */
    @Test
    final void testParsingErrorConstruction() {
        assertEquals(StringUtils.format(ParsingError.MESSAGE_TEMPLATE, TEST_CODE),
                new ParsingError(TEST_CODE).toString());
    }
}
