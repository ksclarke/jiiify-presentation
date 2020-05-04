
package info.freelibrary.iiif.presentation.properties.selectors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.Constants;

import io.vertx.core.json.JsonObject;

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
    public final void testSerialization() {
        final JsonObject found = JsonObject.mapFrom(new PointSelector(10F));
        final JsonObject expected = new JsonObject();

        expected.put(PointSelector.T_COORDINATE, 10.0F).put(Constants.TYPE, PointSelector.class.getSimpleName());

        assertEquals(expected, found);
    }
}
