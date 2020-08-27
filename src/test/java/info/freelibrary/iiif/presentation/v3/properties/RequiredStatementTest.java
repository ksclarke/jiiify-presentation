
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A test of required statement.
 */
public class RequiredStatementTest {

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
     * Tests creating a required statement.
     */
    @Test
    public void testRequiredStatementLabelValue() {
        final RequiredStatement reqStmt = new RequiredStatement(myLabel, myValue);

        assertEquals(myLabel, reqStmt.getLabel());
        assertEquals(myValue, reqStmt.getValue());
    }

    /**
     * Tests creating a required statement.
     */
    @Test
    public void testRequiredStatementStringString() {
        final RequiredStatement reqStmt = new RequiredStatement(myLabel.getString(), myValue.getString());

        assertEquals(myLabel, reqStmt.getLabel());
        assertEquals(myValue, reqStmt.getValue());
    }

    /**
     * Tests setting the label of a required statement.
     */
    @Test
    public void testSetLabel() {
        final RequiredStatement reqStmt = new RequiredStatement(myLabel, myValue);
        final Label label = new Label(VALUE);

        reqStmt.setLabel(label);
        assertEquals(label, reqStmt.getLabel());
    }

    /**
     * Tests setting the value of a required statement.
     */
    @Test
    public void testSetValue() {
        final RequiredStatement reqStmt = new RequiredStatement(myLabel, myValue);
        final Value value = new Value(LABEL);

        reqStmt.setValue(value);
        assertEquals(value, reqStmt.getValue());
    }
}
