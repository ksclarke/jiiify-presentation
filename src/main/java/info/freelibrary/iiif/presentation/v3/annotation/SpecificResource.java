
package info.freelibrary.iiif.presentation.v3.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.util.warnings.Eclipse;

import java.util.Objects;
import java.util.Optional;

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
    public boolean equals(final Object aObject) {
        final SpecificResource other;

        if (this == aObject) {
            return true;
        }

        if (!(aObject instanceof SpecificResource)) {
            return false;
        }

        other = (SpecificResource) aObject;
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

    /**
     * Sets the type for a specific resource.
     *
     * @param aType The specific resource type
     * @return This specific resource
     */
    @Override
    public SpecificResource setType(final String aType) {
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
}
