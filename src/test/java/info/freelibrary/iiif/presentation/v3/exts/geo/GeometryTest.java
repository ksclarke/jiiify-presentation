
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests of the Geometry interface.
 */
public class GeometryTest {

    /**
     * Tests Geometry types' fromLabel().
     */
    @Test
    public void testTypeFromLabel() {
        assertTrue(Geometry.Type.fromLabel(null).isEmpty());
        assertTrue(Geometry.Type.fromLabel(EMPTY).isEmpty());
        assertEquals(Geometry.Type.POINT, Geometry.Type.fromLabel("point").get());
        assertEquals(Geometry.Type.POINT, Geometry.Type.fromLabel("Point").get());
        assertEquals(Geometry.Type.MULTIPOINT, Geometry.Type.fromLabel("multipoint").get());
        assertEquals(Geometry.Type.MULTIPOINT, Geometry.Type.fromLabel("MultiPoint").get());
        assertEquals(Geometry.Type.COORDINATES, Geometry.Type.fromLabel("coordinates").get());
        assertEquals(Geometry.Type.COORDINATES, Geometry.Type.fromLabel("Coordinates").get());
        assertEquals(Geometry.Type.LINESTRING, Geometry.Type.fromLabel("linestring").get());
        assertEquals(Geometry.Type.LINESTRING, Geometry.Type.fromLabel("LineString").get());
        assertEquals(Geometry.Type.MULTILINESTRING, Geometry.Type.fromLabel("multilinestring").get());
        assertEquals(Geometry.Type.MULTILINESTRING, Geometry.Type.fromLabel("MultiLineString").get());
    }

    /**
     * Tests Geometry types' toString() and label() return the same thing.
     */
    @Test
    public void testTypeToString() {
        assertEquals(Geometry.Type.POINT.toString(), Geometry.Type.POINT.label());
        assertEquals(Geometry.Type.MULTIPOINT.toString(), Geometry.Type.MULTIPOINT.label());
        assertEquals(Geometry.Type.COORDINATES.toString(), Geometry.Type.COORDINATES.label());
        assertEquals(Geometry.Type.LINESTRING.toString(), Geometry.Type.LINESTRING.label());
        assertEquals(Geometry.Type.MULTILINESTRING.toString(), Geometry.Type.MULTILINESTRING.label());
    }

}
