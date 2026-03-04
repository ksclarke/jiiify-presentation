package info.freelibrary.iiif.presentation.v3.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.ContentResourceDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.SourceDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.SourceSerializer;
import info.freelibrary.util.I18nRuntimeException;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a source object that contains information about a specific resource. This class is serialized to and
 * deserialized from JSON using a custom serializer and deserializer. A source can either be represented by a URI string
 * or as an object.
 */
@JsonSerialize(using = SourceSerializer.class)
@JsonDeserialize(using = SourceDeserializer.class)
public class Source {

    /** Whether the resource should be serialized as an object or URI string. */
    private boolean isSerializedAsObject;

    /** The URI for the annotation target. */
    private String myID;

    /** The source's content resource. */
    @JsonDeserialize(using = ContentResourceDeserializer.class)
    private ContentResource myContentResource;

    /**
     * Creates a {@code SpecificResource}'s source from the supplied {@code ContentResource}.
     *
     * @param aContentResource A {@code ContentResource}
     */
    public Source(final ContentResource aContentResource) {
        this(aContentResource, true);
    }

    /**
     * Creates a {@code SpecificResource}'s source from the supplied {@code ContentResource}.
     *
     * @param aContentResource A {@code ContentResource}
     * @param aObject Whether the resource should be serialized as a JSON object or URI
     */
    public Source(final ContentResource aContentResource, final boolean aObject) {
        myContentResource = aContentResource;
        isSerializedAsObject = aObject;
    }

    /**
     * Creates a new source from the supplied ID, which must be a valid URI. A generic source is used when the source
     * doesn't need to be serialized with a type property.
     *
     * @param aID A source ID
     */
    public Source(final String aID) {
        isSerializedAsObject = true;
        myID = aID;
    }

    /**
     * Creates a new source for the JSON deserializer.
     */
    protected Source() {
        super();
    }

    /**
     * Checks if this source is equal to another object.
     *
     * @param aObject The object to compare
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object aObject) {
        final Source source;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        source = (Source) aObject;
        return Objects.equals(myID, source.myID) && Objects.equals(myContentResource, source.myContentResource);
    }

    /**
     * Gets the ID for the annotation target.
     *
     * @return The annotation target's ID
     */
    public String getID() {
        return myID == null ? myContentResource.getID() : myID;
    }

    /**
     * Gets the source's content resource. This will return empty if the source is set using an ID alone.
     *
     * @return The source's content resource
     */
    public Optional<ContentResource> getResource() {
        return Optional.ofNullable(myContentResource);
    }

    /**
     * Computes the hash code for this source.
     *
     * @return A hash code value for this source
     */
    @Override
    public int hashCode() {
        return Objects.hash(myID, myContentResource);
    }

    /**
     * Whether the resource is serialized as a URI or object. Embedded resources will always be serialized as objects.
     *
     * @return True if the resource is serialized as a JSON object; else, false
     */
    public boolean isObject() {
        return isSerializedAsObject || myContentResource != null;
    }

    /**
     * Gets a JSON string representation of this object.
     *
     * @return A JSON string representation
     */
    @Override
    public String toString() {
        try {
            final boolean useURIs = Boolean.parseBoolean(System.getenv(JSON.URI_LINKS));
            return JSON.getWriter(this.getClass()).withAttribute(JSON.URI_LINKS, useURIs).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }
}
