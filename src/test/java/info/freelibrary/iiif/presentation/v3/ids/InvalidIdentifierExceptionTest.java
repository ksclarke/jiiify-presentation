
package info.freelibrary.iiif.presentation.v3.ids;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Tests {@code InvalidIdentifierException}.
 */
public class InvalidIdentifierExceptionTest {

    /** The logger used in testing. */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidIdentifierException.class, MessageCodes.BUNDLE);

    /**
     * Test method for {@link InvalidIdentifierException#InvalidIdentifierException(Locale, String)}.
     */
    @Test
    public void testInvalidIdentifierExceptionLocaleString() {
        try {
            throw new InvalidIdentifierException(Locale.US, MessageCodes.JPA_000);
        } catch (final InvalidIdentifierException details) {
            assertEquals(LOGGER.getMessage(MessageCodes.JPA_000), details.getMessage());
        }
    }

    /**
     * Test method for {@link InvalidIdentifierException#InvalidIdentifierException(Locale, String, Object[])}.
     */
    @Test
    public void testInvalidIdentifierExceptionLocaleStringObjectArray() {
        final String message = UUID.randomUUID().toString();

        try {
            throw new InvalidIdentifierException(Locale.US, MessageCodes.JPA_013, message);
        } catch (final InvalidIdentifierException details) {
            assertEquals(LOGGER.getMessage(MessageCodes.JPA_013, message), details.getMessage());
        }
    }

    /**
     * Test method for {@link InvalidIdentifierException#InvalidIdentifierException(String)}.
     */
    @Test
    public void testInvalidIdentifierExceptionString() {
        final String message = UUID.randomUUID().toString();

        try {
            throw new InvalidIdentifierException(MessageCodes.JPA_013, message);
        } catch (final InvalidIdentifierException details) {
            assertEquals(LOGGER.getMessage(MessageCodes.JPA_013, message), details.getMessage());
        }
    }

    /**
     * Test method for {@link InvalidIdentifierException#InvalidIdentifierException(Throwable)}.
     */
    @Test
    public void testInvalidIdentifierExceptionThrowable() {
        try {
            throw new InvalidIdentifierException(new IOException());
        } catch (final InvalidIdentifierException details) {
            assertEquals(IOException.class.getName(), details.getCause().getClass().getName());
        }
    }

    /**
     * Test method for {@link InvalidIdentifierException#InvalidIdentifierException(Throwable, Locale, String)}.
     */
    @Test
    public void testInvalidIdentifierExceptionThrowableLocaleString() {
        try {
            throw new InvalidIdentifierException(new IOException(), Locale.US, MessageCodes.JPA_000);
        } catch (final InvalidIdentifierException details) {
            assertEquals(LOGGER.getMessage(MessageCodes.JPA_000), details.getMessage());
            assertEquals(IOException.class.getName(), details.getCause().getClass().getName());
        }
    }

    /**
     * Test method for
     * {@link InvalidIdentifierException#InvalidIdentifierException(Throwable, Locale, String, Object[])}.
     */
    @Test
    public void testInvalidIdentifierExceptionThrowableLocaleStringObjectArray() {
        final String message = UUID.randomUUID().toString();

        try {
            throw new InvalidIdentifierException(new IOException(), Locale.US, MessageCodes.JPA_013, message);
        } catch (final InvalidIdentifierException details) {
            assertEquals(LOGGER.getMessage(MessageCodes.JPA_013, message), details.getMessage());
            assertEquals(IOException.class.getName(), details.getCause().getClass().getName());
        }
    }

    /**
     * Test method for {@link InvalidIdentifierException#InvalidIdentifierException(Throwable, String)}.
     */
    @Test
    public void testInvalidIdentifierExceptionThrowableString() {
        try {
            throw new InvalidIdentifierException(new IOException(), MessageCodes.JPA_000);
        } catch (final InvalidIdentifierException details) {
            assertEquals(LOGGER.getMessage(MessageCodes.JPA_000), details.getMessage());
            assertEquals(IOException.class.getName(), details.getCause().getClass().getName());
        }
    }

    /**
     * Test method for {@link InvalidIdentifierException#InvalidIdentifierException(Throwable, String, Object[])}.
     */
    @Test
    public void testInvalidIdentifierExceptionThrowableStringObjectArray() {
        final String message = UUID.randomUUID().toString();

        try {
            throw new InvalidIdentifierException(new IOException(), MessageCodes.JPA_013, message);
        } catch (final InvalidIdentifierException details) {
            assertEquals(LOGGER.getMessage(MessageCodes.JPA_013, message), details.getMessage());
            assertEquals(IOException.class.getName(), details.getCause().getClass().getName());
        }
    }

}
