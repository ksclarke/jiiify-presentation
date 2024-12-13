
package info.freelibrary.iiif.webrepl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jdk.jshell.Diag;

/**
 * Tests of {@link DiagConsumer}.
 */
class DiagConsumerTest {

    /** A constant for closing brackets. */
    private static final String CLOSE_BRACKETS = "]]";

    /** An expected error message. */
    private static final String ERROR_MESSAGE = "Could not parse:";

    /** A constant for opening brackets. */
    private static final String OPEN_BRACKETS = "[[";

    /** A constant for the syntax error result. */
    private static final String SYNTAX_ERROR = "Syntax error";

    /** The test code passed to the consumer. */
    private static final String TEST_CODE = "System.out.println(\"Hello, World!\");";

    /** The test buffer. */
    private StringBuilder myBuffer;

    /** The {@link DiagConsumer} being tested. */
    private DiagConsumer myDiagConsumer;

    /**
     * Sets up the testing environment.
     */
    @BeforeEach
    void setUp() {
        myBuffer = new StringBuilder();
        myDiagConsumer = new DiagConsumer(myBuffer);
    }

    /**
     * Tests accepting a consumer with a range error.
     */
    @Test
    void testAcceptWithRangeError() {
        final Diag diag = mock(Diag.class);
        final String result;

        myBuffer.append(TEST_CODE);

        when(diag.getMessage(Locale.US)).thenReturn("Missing semicolon");
        when(diag.getStartPosition()).thenReturn(24L);
        when(diag.getEndPosition()).thenReturn(32L);

        myDiagConsumer.accept(diag);

        result = myBuffer.toString();
        assertTrue(result.contains(ERROR_MESSAGE));
        assertTrue(result.contains(OPEN_BRACKETS) && result.contains(CLOSE_BRACKETS));
        assertTrue(result.contains("Reason: Missing semicolon"));
    }

    /**
     * Tests a consumer with a single character error.
     */
    @Test
    void testAcceptWithSingleCharacterError() {
        final Diag diag = mock(Diag.class);
        final String result;

        myBuffer.append(TEST_CODE);

        when(diag.getMessage(Locale.US)).thenReturn(SYNTAX_ERROR);
        when(diag.getStartPosition()).thenReturn(7L);
        when(diag.getEndPosition()).thenReturn(7L);

        myDiagConsumer.accept(diag);

        result = myBuffer.toString();
        assertTrue(result.contains(ERROR_MESSAGE));
        assertTrue(result.contains(OPEN_BRACKETS) && result.contains(CLOSE_BRACKETS));
        assertTrue(result.contains("Reason: Syntax error"));
    }

    /**
     * Tests a consumer's template code.
     */
    @Test
    void testAcceptWithTemplateCode() {
        final Diag diag = mock(Diag.class);
        final String result;

        myBuffer.append("""
            public static void main(String[] args) {
                System.out.println("Hello, World!");
            }""");

        when(diag.getMessage(Locale.US)).thenReturn("Template issue");
        when(diag.getStartPosition()).thenReturn(5L);
        when(diag.getEndPosition()).thenReturn(50L);

        myDiagConsumer.accept(diag);

        result = myBuffer.toString();
        assertTrue(result.contains(ERROR_MESSAGE));
        assertTrue(result.contains(OPEN_BRACKETS) && result.contains(CLOSE_BRACKETS));
        assertTrue(result.contains("Reason: Template issue"));
        assertTrue(!result.contains("public static void main(String[] args)"));
    }

    /**
     * Tests clearing the buffer after the consumer accepts.
     */
    @Test
    void testBufferClearedAfterAccept() {
        final Diag diag = mock(Diag.class);

        myBuffer.append(TEST_CODE);

        when(diag.getMessage(Locale.US)).thenReturn(SYNTAX_ERROR);
        when(diag.getStartPosition()).thenReturn(7L);
        when(diag.getEndPosition()).thenReturn(7L);

        myDiagConsumer.accept(diag);

        assertTrue(myBuffer.toString().startsWith(ERROR_MESSAGE));
        assertTrue(myBuffer.length() > 0);
    }

}
