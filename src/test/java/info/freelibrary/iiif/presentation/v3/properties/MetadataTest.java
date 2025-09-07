
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.assertEquals;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A test of metadata.
 */
public class MetadataTest {

    /** A logger for the Metadata tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataTest.class, MessageCodes.BUNDLE);

    /** A test label. */
    private static final String LABEL = "A test label";

    /** A test value. */
    private static final String VALUE = "A test value";

    /** The test label. */
    private Label myLabel;

    /** The test value. */
    private Value myValue;

    /**
     * Set up our test environment.
     */
    @Before
    public void setUp() {
        myLabel = new Label(LABEL);
        myValue = new Value(VALUE);
    }

    /**
     * Tests creating a metadata property.
     */
    @Test
    public void testMetadataLabelValue() {
        final Metadata metadata = new Metadata(myLabel, myValue);

        assertEquals(myLabel, metadata.getLabel());
        assertEquals(myValue, metadata.getValue());
    }

    /**
     * Tests creating a metadata property.
     */
    @Test
    public void testMetadataStringString() {
        final String first = myLabel.getFirstValue().orElseThrow(
                () -> new AssertionError(LOGGER.getMessage(MessageCodes.JPA_161, Metadata.class.getSimpleName())));
        final String second = myValue.getFirstValue().orElseThrow(
                () -> new AssertionError(LOGGER.getMessage(MessageCodes.JPA_161, Metadata.class.getSimpleName())));
        final Metadata metadata = new Metadata(first, second);

        assertEquals(myLabel, metadata.getLabel());
        assertEquals(myValue, metadata.getValue());

    }

    /**
     * Tests setting the label of a metadata property.
     */
    @Test
    public void testSetLabel() {
        final Metadata metadata = new Metadata(myLabel, myValue);
        final Label label = new Label(VALUE);

        metadata.setLabel(label);
        assertEquals(label, metadata.getLabel());
    }

    /**
     * Tests setting the value of a metadata property.
     */
    @Test
    public void testSetValue() {
        final Metadata metadata = new Metadata(myLabel, myValue);
        final Value value = new Value(LABEL);

        metadata.setValue(value);
        assertEquals(value, metadata.getValue());
    }
}
