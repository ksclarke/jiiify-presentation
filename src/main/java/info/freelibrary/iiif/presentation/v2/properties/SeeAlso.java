
package info.freelibrary.iiif.presentation.v2.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A link to a machine readable document that semantically describes the resource with the seeAlso property, such as an
 * XML or RDF description. This document could be used for search and discovery or inferencing purposes, or just to
 * provide a longer description of the resource. The profile and format properties of the document should be given to
 * help the client to make appropriate use of the document.
 */
public class SeeAlso {

    /**
     * The see also logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SeeAlso.class, MessageCodes.BUNDLE);

    /**
     * The see also values.
     */
    private final List<Value> myValues;

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID(s)
     */
    public SeeAlso(final String... aID) {
        myValues = new ArrayList<>();

        for (final String id : aID) {
            Objects.requireNonNull(id, LOGGER.getMessage(MessageCodes.JPA_009));

            if (!myValues.add(new Value(URI.create(id)))) {
                throw new UnsupportedOperationException();
            }
        }
    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID(s)
     */
    public SeeAlso(final URI... aID) {
        myValues = new ArrayList<>();

        for (final URI uri : aID) {
            Objects.requireNonNull(uri, LOGGER.getMessage(MessageCodes.JPA_009));

            if (!myValues.add(new Value(uri))) {
                throw new UnsupportedOperationException();
            }
        }

    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID
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
        myValues = new ArrayList<>();

        Objects.requireNonNull(aID, MessageCodes.JPA_003);
        Objects.requireNonNull(aMediaType, MessageCodes.JPA_004);

        if (!myValues.add(new Value(aID, Optional.of(aMediaType)))) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID
     * @param aMediaType A media type
     * @param aProfile A profile
     */
    public SeeAlso(final URI aID, final String aMediaType, final URI aProfile) {
        this(aID, MediaType.parse(aMediaType), aProfile);
    }

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID
     * @param aMediaType A media type
     * @param aProfile A profile
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
        myValues = new ArrayList<>();

        Objects.requireNonNull(aID, MessageCodes.JPA_003);
        Objects.requireNonNull(aMediaType, MessageCodes.JPA_004);
        Objects.requireNonNull(aProfile, MessageCodes.JPA_005);

        if (!myValues.add(new Value(aID, Optional.of(aMediaType), Optional.of(aProfile)))) {
            throw new UnsupportedOperationException();
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
        }
        return myValues.get(0).getID();
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
        }
        return myValues.get(0).getProfile();
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
        }
        return myValues.get(0).getFormat();
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
        }
        return myValues.get(0).getFormatMediaType();
    }

    /**
     * Adds a see also value.
     *
     * @param aID A see also value
     * @return The see also
     */
    public SeeAlso addValue(final String... aID) {
        for (final String id : aID) {
            Objects.requireNonNull(id, MessageCodes.JPA_003);

            if (!myValues.add(new Value(URI.create(id)))) {
                throw new UnsupportedOperationException();
            }
        }

        return this;
    }

    /**
     * Adds a see also value.
     *
     * @param aID A see also value
     * @return The see also
     */
    public SeeAlso addValue(final URI... aID) {
        for (final URI id : aID) {
            Objects.requireNonNull(id, MessageCodes.JPA_003);

            if (!myValues.add(new Value(id))) {
                throw new UnsupportedOperationException();
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

    /**
     * Gets the JSON representation of the see also.
     *
     * @return The JSON representation of the see also
     */
    @JsonValue
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.UnusedPrivateMethod" })
    private Object getJsonValue() {
        if (myValues.size() == Constants.SINGLE_INSTANCE) {
            final Value value = myValues.get(0);
            final String id = value.getID().toString();

            if (value.getFormat().isPresent() || value.getProfile().isPresent()) {
                return value;
            }
            return id;
        }
        final List<Object> list = new ArrayList<>();

        for (final Value value : myValues) {
            final String id = value.getID().toString();
            final String format = value.getFormatAsString();
            final String profile = value.getProfileAsString();

            if (id != null && format != null && profile != null) {
                list.add(ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format, Constants.PROFILE, profile));
            } else if (id != null && (format != null || profile != null)) {
                list.add(ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format));
            } else if (id != null) {
                list.add(id);
            }
        }

        return list;
    }

    /**
     * A see also value.
     */
    public final class Value {

        /**
         * The see also's value ID.
         */
        private final URI myID;

        /**
         * The see also's value format.
         */
        private final Optional<MediaType> myFormat;

        /**
         * The see also's profile.
         */
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
         * @param aMediaType A media type
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

        /**
         * Gets the profile as a string.
         *
         * @return The profile in string form
         */
        @JsonGetter(Constants.PROFILE)
        private String getProfileAsString() {
            return myProfile.isPresent() ? myProfile.get().toString() : null;
        }

        /**
         * Gets the format as a string.
         *
         * @return The format in string form
         */
        @JsonGetter(Constants.FORMAT)
        private String getFormatAsString() {
            return myFormat.isPresent() ? myFormat.get().toString() : null;
        }
    }
}
