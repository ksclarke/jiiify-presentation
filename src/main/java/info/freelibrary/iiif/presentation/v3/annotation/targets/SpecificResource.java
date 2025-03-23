
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.utils.json.SourceDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.SourceSerializer;

/**
 * A specific resource that can reference a particular region, time frame, or other aspect of another resource.
 */
public non-sealed class SpecificResource extends Target implements ContentResource {

    /** A format for this specific resource. */
    private MediaType myFormat;

    /** The specific resource's selector. */
    private Selector mySelector;

    /** The specific resource's source. */
    private Source mySource;

    /** The specific resource's styleClass. */
    private String myStyleClass;

    /**
     * Creates a new specific resource from the supplied source.
     *
     * @param aSource A source
     */
    public SpecificResource(final Source aSource) {
        mySource = aSource;
    }

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
        super.setID(aID);
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
    protected SpecificResource() {
        super();
    }

    @Override
    public boolean equals(final Object object) {
        final SpecificResource other;

        if (this == object) {
            return true;
        }

        if (!(object instanceof SpecificResource)) {
            return false;
        }

        other = (SpecificResource) object;
        return Objects.equals(getID(), other.getID()) && Objects.equals(myFormat, other.myFormat) &&
                Objects.equals(mySelector, other.mySelector) && Objects.equals(mySource, other.mySource) &&
                Objects.equals(myStyleClass, other.myStyleClass);
    }

    /**
     * Gets the format of the specific resource.
     *
     * @return An optional format
     */
    @Override
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Gets the specific resource selector.
     *
     * @return The specific resource selector
     */
    public Optional<Selector> getSelector() {
        return Optional.ofNullable(mySelector);
    }

    /**
     * Gets the specific resource's source.
     *
     * @return The specific resource's source
     */
    public Source getSource() {
        return mySource;
    }

    /**
     * Gets the specific resource's styleClass.
     *
     * @return The specific resource's styleClass
     */
    public Optional<String> getStyleClass() {
        return Optional.ofNullable(myStyleClass);
    }

    /**
     * Gets the specific resource type.
     *
     * @return The specific resource type
     */
    @Override
    public Optional<String> getType() {
        return Optional.of(ResourceTypes.SPECIFIC_RESOURCE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), myFormat, mySelector, mySource, myStyleClass);
    }

    /**
     * Sets a format for this specific resource.
     *
     * @param aMediaType A specific resource format
     * @return This specific resource
     */
    @Override
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
    public SpecificResource setID(final String aID) {
        return (SpecificResource) super.setID(aID);
    }

    /**
     * Sets the specific resource selector.
     *
     * @param aSelector A selector to use for the specific resource
     * @return This specific resource
     */
    public SpecificResource setSelector(final Selector aSelector) {
        mySelector = Objects.requireNonNull(aSelector);
        return this;
    }

    /**
     * Sets the specific resource's source.
     *
     * @param aSource A source
     * @return This specific resource
     */
    public SpecificResource setSource(final Source aSource) {
        mySource = Objects.requireNonNull(aSource);
        return this;
    }

    /**
     * Sets the specific resource's styleClass.
     *
     * @param aStyleClass The style class for this specific resource
     * @return The specific resource
     */
    public SpecificResource setStyleClass(final String aStyleClass) {
        myStyleClass = Objects.requireNonNull(aStyleClass);
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
     * Sets the type for a specific resource.
     *
     * @param aType The specific resource type
     * @return This specific resource
     */
    @Override
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER) // This method is just used by Jackson's deserialization processes
    protected SpecificResource setType(final String aType) {
        if (!ResourceTypes.SPECIFIC_RESOURCE.equals(aType)) {
            throw new IllegalArgumentException(aType);
        }

        return this;
    }

    /**
     * A SpecificResource's source. This may be represented by a single IRI or a combination of ID, type and
     * {@code PartOf}.
     */
    @JsonSerialize(using = SourceSerializer.class)
    @JsonDeserialize(using = SourceDeserializer.class)
    public static class Source {

        /** The source's format. */
        private MediaType myFormat;

        /** The source's height. */
        private int myHeight;

        /** The source's ID. */
        private String myID;

        /** A list of partOf(s). */
        private List<PartOf> myPartOfs;

        /** The source's services. */
        private List<Service> myServices;

        /** The source's type. The {@code ResourceTypes} class can be used for pre-configured values. */
        private String myType;

        /** The source's width. */
        private int myWidth;

        /**
         * Creates a SpecificResource source from the supplied Internationalized Resource Identifier (IRI).
         *
         * @param aID An IRI representing the source
         */
        public Source(final String aID) {
            myID = Objects.requireNonNull(aID);
        }

        /**
         * Gets the source's format.
         *
         * @return The source's format
         */
        public Optional<MediaType> getFormat() {
            return Optional.ofNullable(myFormat);
        }

        /**
         * Gets the source's height.
         *
         * @return The soruce's height
         */
        public OptionalInt getHeight() {
            return myHeight == 0 ? OptionalInt.empty() : OptionalInt.of(myHeight);
        }

        /**
         * Gets the source ID.
         *
         * @return The source ID
         */
        public String getID() {
            return myID;
        }

        /**
         * Gets a part of the source.
         *
         * @return A list of partOf relationships
         */
        public List<PartOf> getPartOfs() {
            if (myPartOfs == null) {
                myPartOfs = new ArrayList<>();
            }

            return myPartOfs;
        }

        /**
         * Gets the source's services.
         *
         * @return A list of services
         */
        public List<Service> getServices() {
            if (myServices == null) {
                myServices = new ArrayList<>();
            }

            return myServices;
        }

        /**
         * Gets the source type.
         *
         * @return The source type
         */
        public Optional<String> getType() {
            return Optional.ofNullable(myType);
        }

        /**
         * Gets the source's width.
         *
         * @return The source's width
         */
        public OptionalInt getWidth() {
            return myWidth == 0 ? OptionalInt.empty() : OptionalInt.of(myWidth);
        }

        /**
         * Sets the source's format.
         *
         * @param aFormat A resource's format
         * @return The source
         */
        @SuppressWarnings({ JDK.UNCHECKED })
        public Source setFormat(final MediaType aFormat) {
            myFormat = Objects.requireNonNull(aFormat);
            return this;
        }

        /**
         * Sets the source ID.
         *
         * @param aID The source ID
         * @return The source
         */
        public Source setID(final String aID) {
            myID = Objects.requireNonNull(aID);
            return this;
        }

        /**
         * Sets the source's partOfs. The supplied list is ignored if it is empty.
         *
         * @param aPartOfList A list of partOfs
         * @return The source
         */
        public Source setPartOfs(final List<PartOf> aPartOfList) {
            if (!Objects.requireNonNull(aPartOfList).isEmpty()) {
                final List<PartOf> partOfs = getPartOfs();

                partOfs.clear();
                partOfs.addAll(aPartOfList);
            }

            return this;
        }

        /**
         * Sets the source's services. The supplied list is ignored if it is empty.
         *
         * @param aServiceList A list of services
         * @return The source
         */
        public Source setServices(final List<Service> aServiceList) {
            if (!Objects.requireNonNull(aServiceList).isEmpty()) {
                final List<Service> services = getServices();

                services.clear();
                services.addAll(aServiceList);
            }

            return this;
        }

        /**
         * Sets the source's type.
         *
         * @param aType A source type
         * @return The source
         */
        public Source setType(final String aType) {
            myType = Objects.requireNonNull(aType);
            return this;
        }

        /**
         * Sets the source's width and height.
         *
         * @param aWidth A width for the source
         * @param aHeight A height for the source
         * @return The source
         */
        public Source setWidthHeight(final int aWidth, final int aHeight) {
            myWidth = aWidth;
            myHeight = aHeight;
            return this;
        }
    }
}
