
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.assertEquals;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Test;

/**
 * A test of summary.
 */
public class SummaryTest {

    /**
     * The logger for the Summary tests.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryTest.class, MessageCodes.BUNDLE);

    /**
     * A test summary language.
     */
    private static final String LANG = "none";

    /**
     * A test summary value.
     */
    private static final String VALUE = "asdf";

    /**
     * Tests a summary constructor.
     */
    @Test
    public void testStringConstructor() {
        new Summary(VALUE).getFirstValue().ifPresentOrElse(value -> assertEquals(VALUE, value), () -> {
            throw new AssertionError(LOGGER.getMessage(MessageCodes.JPA_162, Summary.class.getSimpleName()));
        });
    }

    /**
     * Tests a summary constructed from a value.
     */
    @Test
    public void testValueConstructor() {
        new Summary(new I18n(LANG, VALUE)).getFirstValue().ifPresentOrElse(value -> assertEquals(VALUE, value), () -> {
            throw new AssertionError(LOGGER.getMessage(MessageCodes.JPA_162, Summary.class.getSimpleName()));
        });
    }

}
