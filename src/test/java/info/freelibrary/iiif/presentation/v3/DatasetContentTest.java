/**
 *
 */

package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;

/**
 * Tests of the DatasetContent class.
 */
public class DatasetContentTest {

    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
    }

    /**
     * Test method for {@link info.freelibrary.iiif.presentation.v3.DatasetContent#clearRequiredStatement()}.
     */
    @Test
    public void testClearRequiredStatement() {
        final RequiredStatement reqStatement = new RequiredStatement("stmt-id", "stmt-label");
        final DatasetContent content = new DatasetContent(myID).setRequiredStatement(reqStatement);

        assertTrue(content.getRequiredStatement() != null);
        assertTrue(content.clearRequiredStatement().getRequiredStatement() == null);
    }

}
