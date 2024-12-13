
package info.freelibrary.iiif.webrepl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests of response status.
 */
class StatusTest {

    /**
     * Test method for {@link Status#getCode()}.
     */
    @Test
    final void testGetCode() {
        assertEquals(201, Status.OK.getCode());
        assertEquals(404, Status.NOT_FOUND.getCode());
        assertEquals(405, Status.METHOD_NOT_ALLOWED.getCode());
    }

    /**
     * Test method for {@link Status#getMessage()}.
     */
    @Test
    final void testGetMessage() {
        assertEquals("OK", Status.OK.getMessage());
        assertEquals("Not Found", Status.NOT_FOUND.getMessage());
        assertEquals("Method Not Allowed", Status.METHOD_NOT_ALLOWED.getMessage());
    }

}
