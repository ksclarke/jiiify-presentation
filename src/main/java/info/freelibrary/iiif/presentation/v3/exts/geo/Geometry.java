
package info.freelibrary.iiif.presentation.v3.exts.geo;

import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.Labeled;

import info.freelibrary.iiif.presentation.v3.utils.json.GeometryDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.GeometrySerializer;

/**
 * An interface for geometries.
 */
@JsonDeserialize(using = GeometryDeserializer.class)
@JsonSerialize(using = GeometrySerializer.class)
public interface Geometry {

    /** The geometry object type. */
    enum Type implements Labeled {

        /** The Coordinates geometry type. */
        COORDINATES("Coordinates"),

        /** The Point geometry type. */
        POINT("Point"),

        /** The LineString geometry type. */
        LINESTRING("LineString"),

        /** The MultiPoint geometry type. */
        MULTIPOINT("MultiPoint"),

        /** The MultiLineString geometry type. */
        MULTILINESTRING("MultiLineString");

        /** The internal name of the type enum. */
        private final String myName;

        /**
         * Creates a new type from the supplied name.
         *
         * @param aName A type name
         */
        Type(final String aName) {
            myName = aName;
        }

        @Override
        public String label() {
            return myName;
        }

        @Override
        public String toString() {
            return myName;
        }

        /**
         * Creates a geometry type from a string value.
         *
         * @param aLabel A geometry label
         * @return An optional geometry type
         */
        public static Optional<Type> fromLabel(final String aLabel) {
            for (final Type type : Type.values()) {
                if (type.label().equalsIgnoreCase(aLabel)) {
                    return Optional.of(type);
                }
            }

            return Optional.empty();
        }
    }

    /**
     * Gets the type of geometry.
     *
     * @return The geometry type
     */
    Type getType();

}
