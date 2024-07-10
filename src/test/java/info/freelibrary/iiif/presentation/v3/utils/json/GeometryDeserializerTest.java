
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.exts.geo.Geometry;
import info.freelibrary.iiif.presentation.v3.exts.geo.LineString;
import info.freelibrary.iiif.presentation.v3.exts.geo.MultiLineString;
import info.freelibrary.iiif.presentation.v3.exts.geo.MultiPoint;
import info.freelibrary.iiif.presentation.v3.exts.geo.Point;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Tests the {@link GeometryDeserializer}.
 */
public class GeometryDeserializerTest {

    /** The logger used in testing. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeometryDeserializerTest.class, MessageCodes.BUNDLE);

    /**
     * Tests deserializing a valid {@link LineString}.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeLineString() throws JsonMappingException, JsonProcessingException {
        final LineString lineString = JSON.getReader(LineString.class).readValue("""
            {
                "type": "LineString",
                "coordinates": [
                    [101.0, 0.0],
                    [102.0, 1.0]
                ]
            }
            """);

        assertEquals(Geometry.Type.LINESTRING, lineString.getType());
    }

    /**
     * Tests deserializing a {@link LineString} that lacks a coordinates array.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeLineStringWithNonArrayCoordinates()
            throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_049, Geometry.Type.LINESTRING), LineString.class, """
            { "type": "LineString", "coordinates": "invalid" }
            """);
    }

    /**
     * Tests deserializing a {@link LineString} that lacks coordinates.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeLineStringWithoutCoordinates() throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_049, Geometry.Type.LINESTRING), LineString.class, """
            { "type": "LineString" }
            """);
    }

    /**
     * Tests deserializing a valid {@link MultiLineString}.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeMultiLineString() throws JsonMappingException, JsonProcessingException {
        final MultiLineString multiLineString = JSON.getReader(MultiLineString.class).readValue("""
            {
                "type": "MultiLineString",
                "coordinates": [
                    [
                        [100.0, 0.0],
                        [101.0, 1.0]
                    ],
                    [
                        [102.0, 2.0],
                        [103.0, 3.0]
                    ]
                ]
            }
            """);

        assertEquals(Geometry.Type.MULTILINESTRING, multiLineString.getType());
    }

    /**
     * Tests deserializing a {@link MultiLineString} that lacks a coordinates array.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeMultiLineStringWithNonArrayCoordinates()
            throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_049, Geometry.Type.MULTILINESTRING), MultiLineString.class, """
            { "type": "MultiLineString", "coordinates": "invalid" }
            """);
    }

    /**
     * Tests deserializing a {@link MultiLineString} that lacks coordinates.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeMultiLineStringWithoutCoordinates()
            throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_049, Geometry.Type.MULTILINESTRING), MultiLineString.class, """
            { "type": "MultiLineString" }
            """);
    }

    /**
     * Tests deserializing a valid {@link MultiPoint}.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeMultiPoint() throws JsonMappingException, JsonProcessingException {
        final MultiPoint multipoint = JSON.getReader(MultiPoint.class).readValue("""
            {
                "type": "MultiPoint",
                "coordinates": [
                    [100.0, 0.0],
                    [101.0, 1.0]
                ]
            }
            """);

        assertEquals(Geometry.Type.MULTIPOINT, multipoint.getType());
    }

    /**
     * Tests deserializing a {@link MultiPoint} that lacks a coordinates array.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeMultiPointWithNonArrayCoordinates()
            throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_049, Geometry.Type.MULTIPOINT), MultiPoint.class, """
            { "type": "MultiPoint", "coordinates": "invalid" }
            """);
    }

    /**
     * Tests deserializing a {@link MultiPoint} that lacks coordinates.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializeMultiPointWithoutCoordinates() throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_049, Geometry.Type.MULTIPOINT), MultiPoint.class, """
            { "type": "MultiPoint" }
            """);
    }

    /**
     * Tests deserializing a valid {@link Point}.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializePoint() throws JsonMappingException, JsonProcessingException {
        final Point point = JSON.getReader(Point.class).readValue("""
            { "type": "Point", "coordinates": [ 9.938, 51.533 ] }
            """);

        assertEquals(Geometry.Type.POINT, point.getType());
    }

    /**
     * Tests deserializing a {@link Point} that lacks a coordinates array.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializePointWithNonArrayCoordinates() throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_137, Geometry.Type.POINT), Point.class, """
            { "type": "Point", "coordinates": "invalid" }
            """);
    }

    /**
     * Tests deserializing a {@link Point} that lacks coordinates.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testDeserializePointWithoutCoordinates() throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_137, Geometry.Type.POINT), Point.class, """
            { "type": "Point" }
            """);
    }

    /**
     * Tests deserializing a geometry without a type.
     *
     * @throws JsonMappingException If there is an issue mapping JSON to an object
     * @throws JsonProcessingException If there is an issue when parsing the JSON
     */
    @Test
    public void testMissingType() throws JsonMappingException, JsonProcessingException {
        check(LOGGER.getMessage(MessageCodes.JPA_139), Point.class, "{}");
    }

    /**
     * Tries to serialize a supplied JSON string and checks the expected exception type and message.
     *
     * @param <T> A class type (i.e., the thing being deserialized)
     * @param aSerialization A JSON serialization
     * @param aClass A class to deserialize the JSON into
     * @param aMessage The exception message that's expected
     */
    private <T> void check(final String aMessage, final Class<T> aClass, final String aSerialization) {
        assertEquals(aMessage,
                assertThrows(JsonMappingException.class, () -> JSON.getReader(aClass).readValue(aSerialization))
                        .getOriginalMessage());
    }
}
