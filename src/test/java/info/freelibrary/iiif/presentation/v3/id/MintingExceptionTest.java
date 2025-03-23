
package info.freelibrary.iiif.presentation.v3.id;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import info.freelibrary.util.I18nObject;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Unit tests for the {@link MintingException} class.
 */
public class MintingExceptionTest extends I18nObject {

    /** Details used in the test. */
    private static final String[] TEST_DETAILS = { "TEST_ID" };

    /**
     * Creates a new test instance.
     */
    public MintingExceptionTest() {
        super(MessageCodes.BUNDLE);
    }

    /**
     * Tests the constructor that accepts only a root cause (Throwable). Verifies that the cause is stored correctly.
     */
    @Test
    public void testConstructorWithCause() {
        final Throwable cause = new RuntimeException("Underlying issue");
        final MintingException exception = new MintingException(cause);
        assertEquals(cause, exception.getCause());
    }

    /**
     * Tests the constructor that accepts both a cause and a message key. Verifies that the message includes the key and
     * the cause is correctly assigned.
     */
    @Test
    public void testConstructorWithCauseAndMessageKey() {
        final Throwable cause = new RuntimeException("Root");
        final MintingException exception = new MintingException(cause, MessageCodes.JPA_000);
        assertEquals(cause, exception.getCause());
        assertTrue(exception.getMessage().equals(getI18n(MessageCodes.JPA_000)));
    }

    /**
     * Tests the constructor that accepts a cause, a message key, and additional details. Verifies that all components
     * are correctly applied to the exception.
     */
    @Test
    public void testConstructorWithCauseMessageKeyAndDetails() {
        final Throwable cause = new RuntimeException("Nested problem");
        final MintingException exception = new MintingException(cause, MessageCodes.JPA_100, (Object[]) TEST_DETAILS);
        assertEquals(cause, exception.getCause());
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().equals(getI18n(MessageCodes.JPA_100, TEST_DETAILS[0])));
    }

    /**
     * Tests the constructor that accepts a message key only. Verifies that the message includes the supplied key.
     */
    @Test
    public void testConstructorWithMessageKey() {
        final MintingException exception = new MintingException(MessageCodes.JPA_000);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(getI18n(MessageCodes.JPA_000)));
    }

    /**
     * Tests the constructor that accepts a message key and additional details. Verifies that the message contains the
     * key and optionally includes detail values.
     */
    @Test
    public void testConstructorWithMessageKeyAndDetails() {
        final MintingException exception = new MintingException(MessageCodes.JPA_100, (Object[]) TEST_DETAILS);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().equals(getI18n(MessageCodes.JPA_100, TEST_DETAILS[0])));
    }
}
