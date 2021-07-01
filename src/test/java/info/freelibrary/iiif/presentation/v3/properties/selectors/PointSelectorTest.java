
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * Tests related to the PointSelector.
 */
public class PointSelectorTest extends AbstractTest {

    /**
     * Tests the X and Y coordinate constructor.
     */
    @Test
    public final void testXYCoordinateConstructor() {
        final PointSelector selector = new PointSelector(0, 10);

        assertEquals(0, selector.getX().getAsInt());
        assertEquals(10, selector.getY().getAsInt());
    }

    /**
     * Tests the X, Y, and time (T) coordinate constructor.
     */
    @Test
    public final void testXYTCoordinateConstructor() {
        final PointSelector selector = new PointSelector(0, 10, 1.5F);

        assertEquals(0, selector.getX().getAsInt());
        assertEquals(10, selector.getY().getAsInt());
        assertEquals(Float.valueOf(1.5F), selector.getSeconds().get());
    }

    /**
     * Tests <code>setX()</code> method.
     */
    @Test
    public final void testSettingX() {
        assertEquals(10, new PointSelector(10F).setX(10).getX().getAsInt());
    }

    /**
     * Tests <code>setY()</code> method.
     */
    @Test
    public final void testSettingY() {
        assertEquals(10, new PointSelector(10F).setY(10).getY().getAsInt());
    }

    /**
     * Tests the time (in seconds) constructor.
     */
    @Test
    public final void testTimeConstructorInSeconds() {
        assertEquals(Float.valueOf(10F), new PointSelector(10F).getSeconds().get());
    }

    /**
     * Test the time (in seconds) constructor.
     */
    @Test
    public final void testSettingTimeInSeconds() {
        assertEquals(Float.valueOf(10F), new PointSelector(2F).setSeconds(10F).getSeconds().get());
    }

    /**
     * Tests the time (in minutes) constructor.
     */
    @Test
    public final void testTimeConstructorInMinutes() {
        assertEquals(Float.valueOf(300F), new PointSelector(5L).getSeconds().get());
    }

    /**
     * Test the time (in minutes) constructor.
     */
    @Test
    public final void testSettingTimeInMinutes() {
        assertEquals(Float.valueOf(300F), new PointSelector(2F).setMinutes(5L).getSeconds().get());
    }

    /**
     * Tests the JSON serialization is what we expect (e.g., nulls are ignored).
     */
    @Test
    public final void testSerialization() throws JsonProcessingException {
        assertEquals(format("{ \"type\" : \"PointSelector\", \"t\" : 10.0 }"),
                format(JSON.getWriter(PointSelector.class).writeValueAsString(new PointSelector(10F))));
    }

}
