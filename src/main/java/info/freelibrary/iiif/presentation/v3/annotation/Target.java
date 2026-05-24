
package info.freelibrary.iiif.presentation.v3.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.Range;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.AnnotationTargetDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.AnnotationTargetSerializer;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An annotation target. There are several types of extensions of this sealed class: {@code SpecificResource},
 * {@code ManifestTarget}, and {@code CanvasTarget}.
 */
@JsonSerialize(using = AnnotationTargetSerializer.class)
@JsonDeserialize(using = AnnotationTargetDeserializer.class)
public sealed class Target permits SpecificResource {

    /**
     * The target's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Target.class, MessageCodes.BUNDLE);

    /**
     * Whether the resource should be serialized as an object or URI string.
     */
    private boolean isSerializedAsObject;

    /**
     * An embedded resource.
     */
    private Resource<?> myEmbeddedResource;

    /**
     * The URI for the annotation target.
     */
    private String myID;

    /**
     * A part of an annotation target.
     */
    private List<PartOf> myPartOfs;

    /**
     * The type of the resource that's a target.
     */
    private String myType;

    /**
     * Creates a canvas target of an annotation.
     *
     * @param <T> A type of {@code CanvasResource}
     * @param aCanvas A {@code CanvasResource}
     */
    public <T extends CanvasResource<T>> Target(final CanvasResource<T> aCanvas) {
        this(aCanvas, false);
    }

    /**
     * Creates a canvas target of an annotation.
     *
     * @param <T> A type of {@code CanvasResource}
     * @param aCanvas A {@code CanvasResource}
     * @param aObject Whether the resource should be serialized as a JSON object or URI
     */
    public <T extends CanvasResource<T>> Target(final CanvasResource<T> aCanvas, final boolean aObject) {
        myEmbeddedResource = aCanvas;
        isSerializedAsObject = aObject;
    }

    /**
     * Creates a manifest target of an annotation.
     *
     * @param aManifest A manifest to target
     */
    public Target(final Manifest aManifest) {
        this(aManifest, false);
    }

    /**
     * Creates a manifest target of an annotation.
     *
     * @param aManifest A manifest to target
     * @param aObject Whether the resource should be serialized as a JSON object or URI
     */
    public Target(final Manifest aManifest, final boolean aObject) {
        myEmbeddedResource = aManifest;
        isSerializedAsObject = aObject;
    }

    /**
     * Creates a range target of an annotation.
     *
     * @param aRange A range to target
     */
    public Target(final Range aRange) {
        this(aRange, false);
    }

    /**
     * Creates a range target of an annotation.
     *
     * @param aRange A range to target
     * @param aObject Whether the resource should be serialized as a JSON object or URI
     */
    public Target(final Range aRange, final boolean aObject) {
        myEmbeddedResource = aRange;
        isSerializedAsObject = aObject;
    }

    /**
     * Creates a new target from the supplied ID, which must be a valid URI. A generic target is used when the target
     * doesn't need to be serialized with a type property.
     *
     * @param aID An annotation target ID
     */
    public Target(final String aID) {
        myID = aID;
    }

    /**
     * Creates a new target of an annotation from the supplied ID and list of partOf(s).
     *
     * @param aID A ID of a canvas to target
     * @param aType A type of resource
     * @param aPartOfList A list of partOf(s)
     */
    public Target(final String aID, final String aType, final List<PartOf> aPartOfList) {
        isSerializedAsObject = true;
        myPartOfs = aPartOfList;
        myType = aType;
        myID = aID;
    }

    /**
     * Creates a new annotation target from the supplied ID and array of partOf(s).
     *
     * @param aID An ID for the annotation target
     * @param aType A type of resource
     * @param aPartOfArray An array of partOf(s).
     */
    public Target(final String aID, final String aType, final PartOf... aPartOfArray) {
        myPartOfs = new ArrayList<>(Arrays.asList(aPartOfArray));
        isSerializedAsObject = true;
        myType = aType;
        myID = aID;
    }

    /**
     * Creates a copy of the supplied target.
     *
     * @param aTarget The target to copy
     */
    public Target(final Target aTarget) {
        this();

        if (aTarget.myPartOfs != null) {
            myPartOfs = aTarget.myPartOfs.stream().map(PartOf::copy).collect(Collectors.toCollection(ArrayList::new));
        }

        if (aTarget.myEmbeddedResource != null) {
            myEmbeddedResource = aTarget.myEmbeddedResource.copy();
        }

        isSerializedAsObject = aTarget.isSerializedAsObject;
        myType = aTarget.myType;
        myID = aTarget.myID;
    }

    /**
     * Creates a new target for the JSON deserializer.
     */
    protected Target() {
        super();
    }

    /**
     * Creates a copy of this target.
     *
     * @return A copy of this target
     */
    public Target copy() {
        return new Target(this);
    }

    /**
     * Checks if this target is equal to another object.
     *
     * @param aObject The object to compare
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object aObject) {
        final Target target;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        target = (Target) aObject;

        return Objects.equals(myID, target.myID) && Objects.equals(myPartOfs, target.myPartOfs) &&
                Objects.equals(myType, target.myType);
    }

    /**
     * Gets the ID for the annotation target.
     *
     * @return The annotation target's ID
     */
    public String getID() {
        return myID;
    }

    /**
     * Gets the target's partOf property.
     *
     * @return An optional partOf if there is one; else, an empty optional
     */
    public List<PartOf> getPartOfs() {
        if (myPartOfs == null) {
            myPartOfs = new ArrayList<>();
        }

        return myPartOfs;
    }

    /**
     * Gets the optional type for a particular target. If there is no type (the target is just a generic target with a
     * URI for an ID), an empty {@code Optional} is returned.
     *
     * @return An optional target type; or, an empty optional if there is no type property
     */
    public Optional<String> getType() {
        return Optional.ofNullable(myType);
    }

    /**
     * Computes the hash code for this target.
     *
     * @return A hash code value for this target
     */
    @Override
    public int hashCode() {
        return Objects.hash(myID, myType, myPartOfs);
    }

    /**
     * Whether the resource is serialized as a URI or object. Embedded resources will always be serialized as objects.
     *
     * @return True if the resource is serialized as a JSON object; else, false
     */
    public boolean isObject() {
        return isSerializedAsObject || myEmbeddedResource != null;
    }

    /**
     * Sets the ID of the target resource.
     *
     * @param aID A resource ID
     * @return This target
     */
    public Target setID(final String aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the target's partOf property.
     *
     * @param aPartOfList A list of partOf(s)
     * @return The target
     */
    public Target setPartOfs(final List<PartOf> aPartOfList) {
        if (myPartOfs == null) {
            myPartOfs = new ArrayList<>();
        } else {
            myPartOfs.clear();
        }

        myPartOfs.addAll(aPartOfList);
        return this;
    }

    /**
     * Sets the target's partOf property.
     *
     * @param aPartOfArray An array of partOf(s)
     * @return The target
     */
    public Target setPartOfs(final PartOf... aPartOfArray) {
        if (myPartOfs == null) {
            myPartOfs = new ArrayList<>();
        } else {
            myPartOfs.clear();
        }

        myPartOfs.addAll(Arrays.asList(aPartOfArray));
        return this;
    }

    /**
     * Sets the type of the target resource.
     *
     * @param aType A resource type
     * @return This target
     * @throws IllegalArgumentException If the type isn't one of the supported types
     */
    public Target setType(final String aType) {
        Objects.requireNonNull(aType, LOGGER.getMessage(MessageCodes.JPA_160));

        switch (aType) {
            case ResourceTypes.RANGE, ResourceTypes.MANIFEST, ResourceTypes.SPECIFIC_RESOURCE, ResourceTypes.CANVAS -> {
                myType = aType;
            }
            default -> throw new IllegalArgumentException();
        }

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
            final boolean useURIs = Boolean.parseBoolean(System.getenv(JSON.URI_LINKS));
            return JSON.getWriter(this.getClass()).withAttribute(JSON.URI_LINKS, useURIs).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }
}
