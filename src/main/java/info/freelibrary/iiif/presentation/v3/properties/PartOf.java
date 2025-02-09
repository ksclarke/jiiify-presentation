
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.PartOfDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.PartOfSerializer;

/**
 * A containing resource that includes the resource that has the <code>partOf</code> property. For example, the
 * <code>partOf</code> property on a Canvas can be used to reference an external Manifest in order to enable the
 * discovery of further relevant information. Similarly, a Manifest can reference a containing Collection using
 * <code>partOf</code> to aid in navigation.
 */
@JsonSerialize(using = PartOfSerializer.class)
@JsonDeserialize(using = PartOfDeserializer.class)
public class PartOf {

    /** The logger used by {@code PartOf}. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PartOf.class, MessageCodes.BUNDLE);

    /** The resource that this partOf embeds. */
    private Resource<?> myEmbeddedResource;

    /** The resource that this partOf references. */
    private String[] myReferencedResource;

    /**
     * Creates a partOf that embeds a resource.
     *
     * @param aCollection
     */
    public PartOf(final Resource<?> aResource) {
        myEmbeddedResource = aResource;
    }

    /**
     * Creates a partOf that references a resource.
     *
     * @param aID A partOf ID
     * @param aType A partOf type
     */
    public PartOf(final String aID, final String aType) {
        myReferencedResource = new String[2];

        myReferencedResource[0] = aID;
        myReferencedResource[1] = aType;
    }

    @Override
    public boolean equals(final Object aObject) {
        final PartOf aPartOf;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        aPartOf = (PartOf) aObject;

        if (myEmbeddedResource != null && aPartOf.myEmbeddedResource != null) {
            return Objects.equals(myEmbeddedResource, aPartOf.myEmbeddedResource);
        }

        return Arrays.equals(myReferencedResource, aPartOf.myReferencedResource);
    }

    /**
     * Gets the full embedded resource, if there is one.
     *
     * @return An optional embedded resource
     */
    public Optional<Resource<?>> getEmbeddedResource() {
        return Optional.ofNullable(myEmbeddedResource);
    }

    /**
     * Gets the partOf resource's ID.
     *
     * @return The partOf's resource ID
     */
    public String getID() {
        return myReferencedResource == null ? myEmbeddedResource.getID() : myReferencedResource[0];
    }

    /**
     * Gets the partOf resource's type.
     *
     * @return The partOf resource's type
     */
    public Optional<String> getType() {
        return myReferencedResource == null ? myEmbeddedResource.getType() : Optional.of(myReferencedResource[1]);
    }

    @Override
    public int hashCode() {
        return myEmbeddedResource != null ? Objects.hash(myEmbeddedResource) : Arrays.hashCode(myReferencedResource);
    }

    /**
     * Sets the partOf resource's ID.
     *
     * @param aID A new ID for the partOf resource
     * @return This partOf
     */
    public PartOf setID(final String aID) {
        if (myReferencedResource == null) {
            myEmbeddedResource.setID(aID);
        } else {
            myReferencedResource[0] = aID;
        }

        return this;
    }

    /**
     * Sets the type on referenced resources. Type cannot be set on an embedded resource. Trying to do that will throw
     * an {@link UnsupportedOperationException}.
     *
     * @param aType A new resource type
     * @return This partOf
     * @throws UnsupportedOperationException If you try to set a type on an embedded resource
     */
    public PartOf setType(final String aType) {
        if (myReferencedResource == null) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_154));
        }

        myReferencedResource[1] = aType;
        return this;
    }

    /**
     * Gets a JSON string representation of this object.
     *
     * @return A JSON string representation
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(this.getClass()).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }
}
