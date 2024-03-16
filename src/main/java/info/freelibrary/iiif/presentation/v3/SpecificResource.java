
package info.freelibrary.iiif.presentation.v3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.utils.json.SourceDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.SourceSerializer;

/**
 * A specific resource that can reference a particular region, time frame, or other aspect of another resource.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.SOURCE, JsonKeys.SELECTOR })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SpecificResource implements ContentResource<SpecificResource> {

    /** A format for this specific resource. */
    private MediaType myFormat;

    /** The specific resources's ID. */
    private String myID;

    /** The specific resource's selector. */
    private Selector mySelector;

    /** The specific resource's source. */
    private Source mySource;

    /**
     * Creates a new specific resource from the supplied source and selector.
     *
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final Source aSource, final Selector aSelector) {
        mySource = aSource;
        mySelector = aSelector;
    }

    /**
     * Creates a new specific resource from the supplied source and selector.
     *
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final String aSource, final Selector aSelector) {
        this(new Source(aSource), aSelector);
    }

    /**
     * Creates a new specific resource from the supplied ID, source, and selector.
     *
     * @param aID An ID
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final String aID, final Source aSource, final Selector aSelector) {
        this(aSource, aSelector);
        myID = UriUtils.checkID(aID, true);
    }

    /**
     * Creates a new specific resource from the supplied ID, source, and selector.
     *
     * @param aID An ID
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final String aID, final String aSource, final Selector aSelector) {
        this(aID, new Source(aSource), aSelector);
    }

    /**
     * Allows Jackson to create a new SpecificResource while deserializing JSON.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SpecificResource() {
        // This is intentionally left empty
    }

    /**
     * Gets the format of the specific resource.
     *
     * @return An optional format
     */
    @Override
    @JsonGetter(JsonKeys.FORMAT)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    @Override
    @JsonGetter(JsonKeys.ID)
    @JsonInclude(Include.NON_EMPTY)
    public String getID() {
        return myID;
    }

    /**
     * Gets the specific resource selector.
     *
     * @return The specific resource selector
     */
    @JsonGetter(JsonKeys.SELECTOR)
    public Selector getSelector() {
        return mySelector;
    }

    /**
     * Gets the specific resource's source.
     *
     * @return The specific resource's source
     */
    @JsonGetter(JsonKeys.SOURCE)
    @JsonInclude(Include.NON_EMPTY)
    public Source getSource() {
        return mySource;
    }

    /**
     * Gets the specific resource type.
     *
     * @return The specific resource type
     */
    @Override
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return ResourceTypes.SPECIFIC_RESOURCE;
    }

    /**
     * Sets a format for this specific resource.
     *
     * @param aMediaType A specific resource format
     * @return This specific resource
     */
    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public SpecificResource setFormat(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Sets the ID.
     *
     * @param aID The ID
     * @return This specific resource
     */
    @Override
    @JsonSetter(JsonKeys.ID)
    public SpecificResource setID(final String aID) {
        myID = UriUtils.checkID(aID, true);
        return this;
    }

    /**
     * Sets the specific resource selector.
     *
     * @param aSelector A selector to use for the specific resource
     * @return This specific resource
     */
    @JsonSetter(JsonKeys.SELECTOR)
    public SpecificResource setSelector(final Selector aSelector) {
        mySelector = aSelector;
        return this;
    }

    /**
     * Sets the specific resource's source.
     *
     * @param aSource A source
     * @return This specific resource
     */
    @JsonSetter(JsonKeys.SOURCE)
    public SpecificResource setSource(final Source aSource) {
        mySource = aSource;
        return this;
    }

    /**
     * Sets the type for a specific resource.
     *
     * @param aType The specific resource type
     * @return This specific resource
     */
    @JsonSetter(JsonKeys.TYPE)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER) // This method is just used by Jackson's deserialization processes
    public SpecificResource setType(final String aType) { // NOPMD
        if (!ResourceTypes.SPECIFIC_RESOURCE.equals(aType)) {
            throw new IllegalArgumentException(aType);
        }

        return this;
    }

    @Override
    public String toString() {
        try {
            return JSON.getWriter(SpecificResource.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Returns a SpecificResource from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a specific resource
     * @return The specific resource
     * @throws JsonParsingException If the specific resource cannot be deserialized from the supplied JSON
     */
    public static SpecificResource fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(SpecificResource.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * A SpecificResource's source. This may be represented by a single IRI or a combination of ID, type and
     * {@code PartOf}.
     */
    @JsonSerialize(using = SourceSerializer.class)
    @JsonDeserialize(using = SourceDeserializer.class)
    public static class Source {

        /** The source's ID. */
        private final String myID;

        /** A list of partOf(s). */
        private List<PartOf> myPartOfs;

        /** The source's type. The {@code ResourceTypes} class can be used for pre-configured values. */
        private String myType;

        /**
         * Creates a SpecificResource source from the supplied Internationalized Resource Identifier (IRI).
         *
         * @param aID An IRI representing the source
         */
        public Source(final String aID) {
            myID = Objects.requireNonNull(aID);
        }

        /**
         * Creates a SpecificResource source from the supplied Internationalized Resource Identifier (IRI).
         *
         * @param aID An IRI representing the source
         * @param aType A source type (e.g. Canvas)
         * @param aPartOfs A list of partOf relationships
         */
        public Source(final String aID, final String aType, final PartOf... aPartOfs) {
            myType = Objects.requireNonNull(aType);
            myID = Objects.requireNonNull(aID);
            myPartOfs = Arrays.asList(aPartOfs);
        }

        /**
         * Gets the source ID.
         *
         * @return The source ID
         */
        @JsonIgnore
        public String getID() {
            return myID;
        }

        /**
         * Gets a part of the source.
         *
         * @return A list of partOf relationships
         */
        @JsonIgnore
        public List<PartOf> getPartOfs() {
            if (myPartOfs == null) {
                myPartOfs = new ArrayList<>();
            }

            return myPartOfs;
        }

        /**
         * Gets the source type.
         *
         * @return The source type
         */
        @JsonIgnore
        public Optional<String> getType() {
            return Optional.ofNullable(myType);
        }
    }
}
