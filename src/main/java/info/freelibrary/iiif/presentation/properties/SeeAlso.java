
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A link to a machine readable document that semantically describes the resource with the seeAlso property, such as
 * an XML or RDF description. This document could be used for search and discovery or inferencing purposes, or just to
 * provide a longer description of the resource. The profile and format properties of the document should be given to
 * help the client to make appropriate use of the document.
 */
public class SeeAlso {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeeAlso.class, Constants.BUNDLE_NAME);

    private final List<Value> myValues;

    /**
     * Creates a new see also from an array of URI IDs in string form.
     *
     * @param aIdArray The see also's ID(s)
     * @throws UnsupportedOperationException If an ID string can not be set
     */
    public SeeAlso(final String... aIdArray) {
        myValues = new ArrayList<>();

        for (final String id : aIdArray) {
            Objects.requireNonNull(id, LOGGER.getMessage(MessageCodes.JPA_009));

            if (!myValues.add(new Value(URI.create(id)))) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_047, id));
            }
        }
    }

    /**
     * Creates a new see also.
     *
     * @param aIdArray The see also's ID(s)
     */
    public SeeAlso(final URI... aIdArray) {
        myValues = new ArrayList<>();

        for (final URI uri : aIdArray) {
            Objects.requireNonNull(uri, LOGGER.getMessage(MessageCodes.JPA_009));

            if (!myValues.add(new Value(uri))) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_047, uri));
            }
        }

    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID in string form
     * @param aMediaType A media type
     */
    public SeeAlso(final String aID, final MediaType aMediaType) {
        this(URI.create(aID), aMediaType);
    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID
     * @param aMediaType A media type
     */
    public SeeAlso(final URI aID, final MediaType aMediaType) {
        Objects.requireNonNull(aID, MessageCodes.JPA_003);
        Objects.requireNonNull(aMediaType, MessageCodes.JPA_004);

        final Value value = new Value(aID, Optional.of(aMediaType));

        myValues = new ArrayList<>();

        if (!myValues.add(value)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_047, value));
        }
    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID
     * @param aMediaType A media type in string form
     * @param aProfile A profile
     */
    public SeeAlso(final URI aID, final String aMediaType, final URI aProfile) {
        this(aID, MediaType.parse(aMediaType), aProfile);
    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID in string form
     * @param aMediaType A media type
     * @param aProfile A profile in string form
     */
    public SeeAlso(final String aID, final MediaType aMediaType, final String aProfile) {
        this(URI.create(aID), aMediaType, URI.create(aProfile));
    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID
     * @param aMediaType A media type
     * @param aProfile A profile
     */
    public SeeAlso(final URI aID, final MediaType aMediaType, final URI aProfile) {
        Objects.requireNonNull(aID, MessageCodes.JPA_003);
        Objects.requireNonNull(aMediaType, MessageCodes.JPA_004);
        Objects.requireNonNull(aProfile, MessageCodes.JPA_005);

        final Value value = new Value(aID, Optional.of(aMediaType), Optional.of(aProfile));

        myValues = new ArrayList<>();

        if (!myValues.add(value)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_047, value));
        }
    }

    /**
     * Gets the first see also value's ID.
     *
     * @return The first see also value's ID
     */
    public URI getID() {
        if (myValues.isEmpty()) {
            return null;
        } else {
            return myValues.get(0).getID();
        }
    }

    /**
     * Gets the first see also value's profile.
     *
     * @return The first see also value's profile
     */
    @JsonIgnore
    public Optional<URI> getProfile() {
        if (myValues.isEmpty()) {
            return Optional.empty();
        } else {
            return myValues.get(0).getProfile();
        }
    }

    /**
     * Gets the first see also value's format.
     *
     * @return The first see also value's format
     */
    @JsonIgnore
    public Optional<String> getFormat() {
        if (myValues.isEmpty()) {
            return Optional.empty();
        } else {
            return myValues.get(0).getFormat();
        }
    }

    /**
     * Gets the first see also value's format media type.
     *
     * @return The first see also value's format media type
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        if (myValues.isEmpty()) {
            return Optional.empty();
        } else {
            return myValues.get(0).getFormatMediaType();
        }
    }

    /**
     * Adds a see also value.
     *
     * @param aIdArray An array of see also IDs in string form
     * @return The see also
     */
    public SeeAlso addValue(final String... aIdArray) {
        for (final String id : aIdArray) {
            Objects.requireNonNull(id, MessageCodes.JPA_003);

            final Value value = new Value(URI.create(id));

            if (!myValues.add(value)) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_047, value));
            }
        }

        return this;
    }

    /**
     * Adds a see also value.
     *
     * @param aIdArray A see also value
     * @return The see also
     */
    public SeeAlso addValue(final URI... aIdArray) {
        for (final URI id : aIdArray) {
            Objects.requireNonNull(id, MessageCodes.JPA_003);

            final Value value = new Value(id);

            if (!myValues.add(value)) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_047, value));
            }
        }

        return this;
    }

    /**
     * Returns number of see also values.
     *
     * @return The number of see also values
     */
    @JsonIgnore
    public int count() {
        return myValues.size();
    }

    /**
     * Gets the see also values.
     *
     * @return The see also values
     */
    @JsonIgnore
    public List<Value> getValues() {
        return myValues;
    }

    @JsonValue
    private Object toList() {
        if (myValues.size() > 0) {
            final List<Object> seeAlsoList = new ArrayList<>();

            for (final Value value : myValues) {
                final String id = value.getID().toString();
                final String format = value.getFormatAsString();
                final String profile = value.getProfileAsString();
                final Map map;

                if ((id != null) && (format != null) && (profile != null)) {
                    map = ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format, Constants.PROFILE, profile);
                    seeAlsoList.add(map);
                } else if ((id != null) && (format != null)) {
                    seeAlsoList.add(ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format));
                } else if ((id != null) && (profile != null)) {
                    seeAlsoList.add(ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format));
                } else if (id != null) {
                    seeAlsoList.add(id);
                }
            }

            return seeAlsoList;
        } else {
            return null;
        }
    }

    /**
     * A see also value.
     */
    public final class Value {

        private final URI myID;

        private final Optional<MediaType> myFormat;

        private final Optional<URI> myProfile;

        /**
         * Creates a new see also value from the supplied string ID.
         *
         * @param aID A string ID
         */
        private Value(final URI aID) {
            myID = aID;
            myFormat = Optional.empty();
            myProfile = Optional.empty();
        }

        /**
         * Creates a new see also value from the supplied ID.
         *
         * @param aID An ID
         */
        private Value(final URI aID, final Optional<MediaType> aMediaType) {
            myID = aID;
            myFormat = aMediaType;
            myProfile = Optional.empty();
        }

        /**
         * Creates a new see also value.
         *
         * @param aID An ID
         * @param aFormat A format
         * @param aProfile A profile
         */
        private Value(final URI aID, final Optional<MediaType> aFormat, final Optional<URI> aProfile) {
            myID = aID;
            myFormat = aFormat;
            myProfile = aProfile;
        }

        /**
         * Get ID.
         *
         * @return The ID
         */
        @JsonGetter(Constants.ID)
        public URI getID() {
            return myID;
        }

        /**
         * Get format.
         *
         * @return The format
         */
        @JsonIgnore
        public Optional<MediaType> getFormatMediaType() {
            return myFormat;
        }

        /**
         * Get format.
         *
         * @return The format
         */
        @JsonIgnore
        public Optional<String> getFormat() {
            return myFormat.isPresent() ? Optional.of(myFormat.get().toString()) : Optional.empty();
        }

        /**
         * Get profile.
         *
         * @return The profile
         */
        @JsonIgnore
        public Optional<URI> getProfile() {
            return myProfile;
        }

        @JsonGetter(Constants.PROFILE)
        private String getProfileAsString() {
            return myProfile.isPresent() ? myProfile.get().toString() : null;
        }

        @JsonGetter(Constants.FORMAT)
        private String getFormatAsString() {
            return myFormat.isPresent() ? myFormat.get().toString() : null;
        }

        @Override
        public String toString() {
            final String format = StringUtils.trimTo(getFormatAsString(), Constants.EMPTY);
            final String profile = StringUtils.trimTo(getProfileAsString(), Constants.EMPTY);

            return String.join(" : ", myID.toString(), format, profile);
        }
    }
}
