
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;

/**
 * Tests of the TextContent class.
 */
public class TextContentTest {

    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
    }

    /**
     * Tests clearing the required statement.
     */
    @Test
    public void testClearRequiredStatement() {
        final RequiredStatement reqStatement = new RequiredStatement("stmt-id", "stmt-label");
        final TextContent content = new TextContent(myID).setRequiredStatement(reqStatement);

        assertTrue(content.getRequiredStatement() != null);
        assertTrue(content.clearRequiredStatement().getRequiredStatement() == null);
    }

}
