
package info.freelibrary.iiif.presentation.v3.exts.geo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A representation of an aggregation of spatially bounded areas.
 */
public class NavPlace {

    /** The <code>NavPlace</code> logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NavPlace.class, MessageCodes.BUNDLE);

    /** The navPlace's features. */
    private List<NavPlaceFeature> myFeatures;

    /** The navPlace ID. */
    private String myID;

    /**
     * Creates a new navPlace from the supplied URI ID.
     *
     * @param aID A <a href="https://iiif.io/api/presentation/3.0/#61-uri-recommendations">commonly used HTTP(S) URI
     *        identifier</a>
     */
    public NavPlace(final String aID) {
        myID = UriUtils.checkID(aID, true);
    }

    /**
     * Helps Jackson constructs a NavPlace during deserialization.
     */
    @SuppressWarnings({ Eclipse.UNUSED })
    private NavPlace() {
        // This private constructor is for Jackson's deserialization purposes.
    }

    /**
     * Gets the navPlace's features.
     *
     * @return A list of features
     */
    @JsonGetter(JsonKeys.FEATURES)
    public List<NavPlaceFeature> getFeatures() {
        if (myFeatures == null) {
            myFeatures = new ArrayList<>();
        }

        return myFeatures;
    }

    /**
     * Gets the navPlace ID.
     *
     * @return The navPlace ID
     */
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return myID;
    }

    /**
     * Gets the navPlace type.
     *
     * @return The navPlace type
     */
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return JsonKeys.FEATURE_COLLECTION;
    }

    /**
     * Sets the navPlace's features.
     *
     * @param aFeatureList A list of features to add
     * @return This NavPlace
     */
    @JsonSetter(JsonKeys.FEATURES)
    public NavPlace setFeatures(final List<NavPlaceFeature> aFeatureList) {
        myFeatures = aFeatureList;
        return this;
    }

    /**
     * Sets the navPlace's features.
     *
     * @param aFeatureArray An array of features to add
     * @return This NavPlace
     */
    @JsonIgnore
    public NavPlace setFeatures(final NavPlaceFeature... aFeatureArray) {
        return setFeatures(Arrays.asList(aFeatureArray));
    }

    /**
     * Sets the navPlace ID.
     *
     * @param aID A <a href="https://iiif.io/api/presentation/3.0/#61-uri-recommendations">commonly used HTTP(S) URI
     *        identifier</a>
     * @return This navPlace
     */
    @JsonSetter(JsonKeys.ID)
    public NavPlace setID(final String aID) {
        myID = UriUtils.checkID(aID, true);
        return this;
    }

    /**
     * Sets the navPlace type.
     *
     * @param aType The navPlace type
     */
    @JsonSetter(JsonKeys.TYPE)
    private void setType(final String aType) {
        if (!JsonKeys.FEATURE_COLLECTION.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_140, aType));
        }
    }
}
