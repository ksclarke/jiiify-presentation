
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A test of metadata.
 */
public class MetadataTest {

    private static final String LABEL = "A test label";

    private static final String VALUE = "A test value";

    private Label myLabel;

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
        final Metadata metadata = new Metadata(myLabel.getString(), myValue.getString());

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
