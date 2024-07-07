
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A spatially bounded thing.
 */
@JsonInclude(NON_EMPTY)
public class NavPlaceFeature {

    /** The NavPlaceFeature's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NavPlaceFeature.class, MessageCodes.BUNDLE);

    /** A bounding box for the navPlace feature. */
    private BoundingBox myBoundingBox;

    /** A geometry for the navPlace feature. */
    private Geometry myGeometry;

    /** The navPlace feature ID. */
    private String myID;

    /** A set of navPlace feature properties. */
    private Properties myProperties;

    /**
     * Creates a new navPlace feature.
     */
    public NavPlaceFeature() {
        // This is intentionally left empty.
    }

    /**
     * Creates a new navPlace feature from the supplied ID.
     *
     * @param aID A feature ID
     */
    public NavPlaceFeature(final String aID) {
        myID = UriUtils.checkID(aID, true);
    }

    /**
     * Gets the navPlace's bounding box.
     *
     * @return An optional bounding box
     */
    @JsonGetter(JsonKeys.BOUNDING_BOX)
    public Optional<BoundingBox> getBoundingBox() {
        return Optional.ofNullable(myBoundingBox);
    }

    /**
     * Gets the navPlace's geometry.
     *
     * @return The navPlace's geometry
     */
    @JsonGetter(JsonKeys.GEOMETRY)
    public Geometry getGeometry() {
        return myGeometry;
    }

    /**
     * Gets the navPlace feature ID.
     *
     * @return The navPlace feature ID
     */
    @JsonGetter(JsonKeys.ID)
    public Optional<String> getID() {
        return Optional.ofNullable(myID);
    }

    /**
     * Gets the list of properties associated with this navPlace feature.
     *
     * @return A list of navPlace feature properties
     */
    @JsonGetter(JsonKeys.PROPERTIES)
    public Properties getProperties() {
        if (myProperties == null) {
            myProperties = new Properties();
        }

        return myProperties;
    }

    /**
     * Gets the NavPlaceFeature's type.
     *
     * @return The feature's type
     */
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return JsonKeys.FEATURE;
    }

    /**
     * Sets the navPlace's bounding box.
     *
     * @param aBoundingBox A bounding box
     * @return This feature
     */
    @JsonSetter(JsonKeys.BOUNDING_BOX)
    public NavPlaceFeature setBoundingBox(final BoundingBox aBoundingBox) {
        myBoundingBox = aBoundingBox;
        return this;
    }

    /**
     * Sets the navPlace's geometry.
     *
     * @param aGeometry A geometry
     * @return This feature
     */
    @JsonSetter(JsonKeys.GEOMETRY)
    public NavPlaceFeature setGeometry(final Geometry aGeometry) {
        myGeometry = aGeometry;
        return this;
    }

    /**
     * Sets the navPlace feature ID.
     *
     * @param aID A feature ID
     * @return This feature
     */
    @JsonSetter(JsonKeys.ID)
    public NavPlaceFeature setID(final String aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the navPlace properties.
     *
     * @param aProperties A {@code LinkedHashSet} of properties
     * @return This feature
     */
    @JsonSetter(JsonKeys.PROPERTIES)
    public NavPlaceFeature setProperties(final Properties aProperties) {
        myProperties = aProperties;
        return this;
    }

    /**
     * Provides Jackson with a setter that supports setting type.
     *
     * @param aType A feature type
     */
    @JsonSetter(JsonKeys.TYPE)
    private void setType(final String aType) {
        if (!JsonKeys.FEATURE.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_146, aType));
        }
    }
}
