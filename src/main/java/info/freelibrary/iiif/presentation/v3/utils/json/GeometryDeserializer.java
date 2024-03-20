
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.exts.geo.Geometry;
import info.freelibrary.iiif.presentation.v3.exts.geo.LineString;
import info.freelibrary.iiif.presentation.v3.exts.geo.MultiLineString;
import info.freelibrary.iiif.presentation.v3.exts.geo.MultiPoint;
import info.freelibrary.iiif.presentation.v3.exts.geo.Point;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A deserializer for GeoJSON geometries.
 */
public class GeometryDeserializer extends StdDeserializer<Geometry> {

    /** The <code>serialVersionUID</code> for GeometryDeserializer. */
    private static final long serialVersionUID = -5899453866378497817L;

    /** The deserializer's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeometryDeserializer.class, MessageCodes.BUNDLE);

    /**
     * Creates a new <code>GeometryDeserializer</code>.
     */
    GeometryDeserializer() {
        this(null);
    }

    /**
     * Creates a new <code>GeometryDeserializer</code> from the supplied class.
     *
     * @param aClass A class from which to create a deserializer
     */
    GeometryDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Geometry deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode currentNode = aParser.getCodec().readTree(aParser);
        final String label = currentNode.get(JsonKeys.TYPE).asText();
        final Optional<Geometry.Type> type = Geometry.Type.fromLabel(label);
        final JsonNode coordinates = currentNode.get(JsonKeys.COORDINATES);

        if (!type.isPresent()) {
            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_139), aParser.currentLocation());
        }

        return switch (type.get()) {
            case POINT -> getPoint(coordinates, aParser, type.get());
            case MULTIPOINT -> new MultiPoint(getPoints(coordinates, aParser, type.get()));
            case LINESTRING -> new LineString(getPoints(coordinates, aParser, type.get()));
            case MULTILINESTRING -> new MultiLineString(getLineStrings(coordinates, aParser, type.get()));
            default -> throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_138, type.get()),
                    aParser.currentLocation());
        };
    }

    /**
     * Deserializes a point from the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aParser A JSON parser
     * @param aType A type of geometry
     * @return A deserialized point
     * @throws JsonMappingException If there is trouble deserializing the JSON structures
     */
    @SuppressWarnings({ JDK.DEPRECATION })
    private Point getPoint(final JsonNode aNode, final JsonParser aParser, final Geometry.Type aType)
            throws JsonMappingException {
        final double xCoordinate;
        final double yCoordinate;

        if (!aNode.isArray()) {
            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_137, aType),
                    aParser.getCurrentLocation());
        }

        xCoordinate = aNode.get(0).asDouble();
        yCoordinate = aNode.get(1).asDouble();

        return new Point(xCoordinate, yCoordinate);
    }

    /**
     * Deserializes a list of <code>LineString</code>s from the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aParser A JSON parser
     * @param aType A type of geometry
     * @return A list of <code>LineString</code>s
     * @throws JsonMappingException If there is trouble deserializing the supplied JSON node
     */
    @SuppressWarnings(JDK.DEPRECATION)
    private List<LineString> getLineStrings(final JsonNode aNode, final JsonParser aParser, final Geometry.Type aType)
            throws JsonMappingException {
        final ArrayList<LineString> lineStrings = new ArrayList<>();
        final Iterator<JsonNode> iterator;

        if (!aNode.isArray()) {
            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_049, aType),
                    aParser.getCurrentLocation());
        }

        iterator = aNode.elements();

        while (iterator.hasNext()) {
            final List<Point> points = new ArrayList<>();
            final JsonNode array = iterator.next();
            final Iterator<JsonNode> arrayIterator;

            if (!array.isArray()) {
                throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_120, aType),
                        aParser.getCurrentLocation());
            }

            arrayIterator = array.elements();

            while (arrayIterator.hasNext()) {
                final JsonNode childArray = arrayIterator.next();
                final double xCoordinate;
                final double yCoordinate;

                if (!childArray.isArray()) {
                    throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_120, aType),
                            aParser.getCurrentLocation());
                }

                xCoordinate = childArray.get(0).asDouble();
                yCoordinate = childArray.get(1).asDouble();
                points.add(new Point(xCoordinate, yCoordinate));
            }

            lineStrings.add(new LineString(points));
        }

        return lineStrings;
    }

    /**
     * Deserializes a list of points.
     *
     * @param aNode The JSON node being parsed
     * @param aParser The JSON parser
     * @param aType The type of geometry being deserialized
     * @return A list of points
     * @throws JsonMappingException If there is trouble deserializing the JSON
     */
    @SuppressWarnings(JDK.DEPRECATION)
    private List<Point> getPoints(final JsonNode aNode, final JsonParser aParser, final Geometry.Type aType)
            throws JsonMappingException {
        final ArrayList<Point> points = new ArrayList<>();
        final Iterator<JsonNode> iterator;

        if (!aNode.isArray()) {
            throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_049, aType),
                    aParser.getCurrentLocation());
        }

        iterator = aNode.elements();

        while (iterator.hasNext()) {
            final JsonNode array = iterator.next();
            final double xCoordinate;
            final double yCoordinate;

            if (!array.isArray()) {
                throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_120, aType),
                        aParser.getCurrentLocation());
            }

            xCoordinate = array.get(0).asDouble();
            yCoordinate = array.get(1).asDouble();
            points.add(new Point(xCoordinate, yCoordinate));
        }

        return points;
    }

}
