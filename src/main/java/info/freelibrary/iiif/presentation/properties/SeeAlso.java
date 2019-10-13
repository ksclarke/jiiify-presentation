
package info.freelibrary.iiif.presentation.properties;

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

import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

public class SeeAlso {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeeAlso.class, Constants.BUNDLE_NAME);

    private final List<Value> myValues;

    /**
     * Creates a new see also.
     *
     * @param aID The see also's ID(s)
     */
    public SeeAlso(final String... aID) {
        myValues = new ArrayList<>();

        for (final String id : aID) {
            Objects.requireNonNull(id, LOGGER.getMessage(MessageCodes.EXC_009));

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
            Objects.requireNonNull(uri, LOGGER.getMessage(MessageCodes.EXC_009));

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

        Objects.requireNonNull(aID, MessageCodes.EXC_003);
        Objects.requireNonNull(aMediaType, MessageCodes.EXC_004);

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

        Objects.requireNonNull(aID, MessageCodes.EXC_003);
        Objects.requireNonNull(aMediaType, MessageCodes.EXC_004);
        Objects.requireNonNull(aProfile, MessageCodes.EXC_005);

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
     * @param aID A see also value
     * @return The see also
     */
    public SeeAlso addValue(final String... aID) {
        for (final String id : aID) {
            Objects.requireNonNull(id, MessageCodes.EXC_003);

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
            Objects.requireNonNull(id, MessageCodes.EXC_003);

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

    @JsonValue
    private Object getValue() {
        if (myValues.size() == 1) {
            final Value value = myValues.get(0);
            final String id = value.getID().toString();

            if (value.getFormat().isPresent() || value.getProfile().isPresent()) {
                return value;
            } else {
                return id;
            }
        } else {
            final List<Object> list = new ArrayList<>();

            for (final Value value : myValues) {
                final String id = value.getID().toString();
                final String format = value.getFormatAsString();
                final String profile = value.getProfileAsString();

                if ((id != null) && (format != null) && (profile != null)) {
                    list.add(ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format, Constants.PROFILE, profile));
                } else if ((id != null) && (format != null)) {
                    list.add(ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format));
                } else if ((id != null) && (profile != null)) {
                    list.add(ImmutableMap.of(Constants.ID, id, Constants.FORMAT, format));
                } else if (id != null) {
                    list.add(id);
                }
            }

            return list;
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
    }
}
