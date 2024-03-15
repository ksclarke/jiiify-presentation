
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import info.freelibrary.iiif.presentation.v3.properties.geo.Geometry;
import info.freelibrary.iiif.presentation.v3.properties.geo.LineString;
import info.freelibrary.iiif.presentation.v3.properties.geo.MultiLineString;
import info.freelibrary.iiif.presentation.v3.properties.geo.MultiPoint;
import info.freelibrary.iiif.presentation.v3.properties.geo.Point;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A serializer for GeoJSON geometries.
 */
public class GeometrySerializer extends StdSerializer<Geometry> {

    /** A <code>serialVersionUID</code> for <code>SourceSerializer</code>. */
    private static final long serialVersionUID = 2492075335371467277L;

    /**
     * Creates a new <code>GeometrySerializer</code>.
     */
    public GeometrySerializer() {
        super(Geometry.class, true);
    }

    @Override
    public void serialize(final Geometry aGeometry, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider) throws IOException {
        final Geometry.Type type = aGeometry.getType();

        aJsonGenerator.writeStartObject();
        aJsonGenerator.writeStringField(JsonKeys.TYPE, type.label());
        aJsonGenerator.writeArrayFieldStart(JsonKeys.COORDINATES);

        switch (type) {
            case LINESTRING -> {
                final Iterator<Point> pointIterator = ((LineString) aGeometry).iterator();

                while (pointIterator.hasNext()) {
                    aJsonGenerator.writeArray(pointIterator.next().toArray(), 0, 2);
                }
            }
            case MULTILINESTRING -> {
                final Iterator<LineString> lineStringIterator = ((MultiLineString) aGeometry).iterator();

                while (lineStringIterator.hasNext()) {
                    final Iterator<Point> pointIterator = lineStringIterator.next().iterator();

                    aJsonGenerator.writeStartArray();

                    while (pointIterator.hasNext()) {
                        aJsonGenerator.writeArray(pointIterator.next().toArray(), 0, 2);
                    }

                    aJsonGenerator.writeEndArray();
                }
            }
            case POINT -> {
                final Point point = (Point) aGeometry;

                aJsonGenerator.writeNumber(point.getX());
                aJsonGenerator.writeNumber(point.getY());
            }
            case MULTIPOINT -> {
                final ListIterator<Point> pointIterator = ((MultiPoint) aGeometry).iterator();

                while (pointIterator.hasNext()) {
                    aJsonGenerator.writeArray(pointIterator.next().toArray(), 0, 2);
                }
            }
            default -> {
                // placeholder
            }
        }

        aJsonGenerator.writeEndArray();
        aJsonGenerator.writeEndObject();
    }

    @Override
    public void serializeWithType(final Geometry aGeometry, final JsonGenerator aJsonGenerator,
            final SerializerProvider aProvider, final TypeSerializer aTypeSerializer)
            throws IOException, JsonProcessingException {
        // This serializes a source when type is called, like when processing a list of geometries
        serialize(aGeometry, aJsonGenerator, aProvider);
    }
}
