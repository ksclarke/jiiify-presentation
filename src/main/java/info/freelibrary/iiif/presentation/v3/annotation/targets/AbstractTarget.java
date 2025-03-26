
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;

/**
 * A base class for annotation targets.
 */
abstract class AbstractTarget {

    /** Whether the ID uses HTTPS. */
    private boolean hasSecureID;

    /** The URI for the annotation target. */
    private String myID;

    /** A part of an annotation target. */
    private List<PartOf> myPartOfs;

    /**
     * Creates a new AbstractTarget for Jackson deserialization.
     */
    protected AbstractTarget() {
        // This is intentionally left empty
    }

    /**
     * Creates a new annotation target from the supplied ID.
     *
     * @param aID An ID for the annotation target
     * @param aHttpsReq Whether the ID must use HTTPS
     */
    protected AbstractTarget(final String aID, final boolean aHttpsReq) {
        myID = UriUtils.checkID(aID, aHttpsReq);
        hasSecureID = aHttpsReq;
    }

    /**
     * Creates a new annotation target from the supplied ID and list of partOf(s).
     *
     * @param aID An ID for the annotation target
     * @param aHttpsReq Whether the ID must use HTTPS
     * @param aPartOfList A list of partOf(s) for the target
     */
    protected AbstractTarget(final String aID, final boolean aHttpsReq, final List<PartOf> aPartOfList) {
        myID = UriUtils.checkID(aID, aHttpsReq);
        hasSecureID = aHttpsReq;
        myPartOfs = new ArrayList<>();
        myPartOfs.addAll(aPartOfList);
    }

    /**
     * Creates a new annotation target from the supplied ID and partOf array.
     *
     * @param aID An ID for the annotation target
     * @param aHttpsReq Whether the ID must use HTTPS
     * @param aPartOfArray An array of partOf(s) for the target
     */
    protected AbstractTarget(final String aID, final boolean aHttpsReq, final PartOf... aPartOfArray) {
        myID = UriUtils.checkID(aID, aHttpsReq);
        hasSecureID = aHttpsReq;
        myPartOfs = new ArrayList<>();
        myPartOfs.addAll(Arrays.asList(aPartOfArray));
    }

    /**
     * Checks if this target is equal to another object.
     *
     * @param aOther The object to compare
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object aOther) {
        final AbstractTarget target;

        if (this == aOther) {
            return true;
        }

        if (aOther == null || getClass() != aOther.getClass()) {
            return false;
        }

        target = (AbstractTarget) aOther;

        return Objects.equals(myID, target.myID) && Objects.equals(myPartOfs, target.myPartOfs) &&
                Objects.equals(hasSecureID, target.hasSecureID);
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
     * Gets the optional type for a particular target. If there is no type (the target is just a generic target with a
     * URI for an ID), an empty {@code Optional} is returned.
     *
     * @return An optional target type; or, an empty optional if there is no type property
     */
    public Optional<String> getType() {
        return Optional.empty();
    }

    /**
     * Computes the hash code for this target.
     *
     * @return A hash code value for this target
     */
    @Override
    public int hashCode() {
        return Objects.hash(myID, hasSecureID, myPartOfs);
    }

    /**
     * Gets the target's partOf property.
     *
     * @return An optional partOf if there is one; else, an empty optional
     */
    protected List<PartOf> getPartOfs() {
        if (myPartOfs == null) {
            myPartOfs = new ArrayList<>();
        }

        return myPartOfs;
    }

    /**
     * Sets the ID of the annotation target.
     *
     * @param aID An annotation target ID
     * @return The annotation target
     */
    protected AbstractTarget setID(final String aID) {
        myID = UriUtils.checkID(aID, hasSecureID);
        return this;
    }

    /**
     * Sets the target's partOf property.
     *
     * @param aPartOfList A list of partOf(s)
     * @return The target
     */
    protected AbstractTarget setPartOfs(final List<PartOf> aPartOfList) {
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
    protected AbstractTarget setPartOfs(final PartOf... aPartOfArray) {
        if (myPartOfs == null) {
            myPartOfs = new ArrayList<>();
        } else {
            myPartOfs.clear();
        }

        myPartOfs.addAll(Arrays.asList(aPartOfArray));
        return this;
    }

    /**
     * Sets the target type property. If a target that subclasses this abstract class doesn't have a type property, an
     * {@code IllegalArgumentException} is thrown. Additionally, targets that have a specific type do not need to have
     * their type properties set. This is done automatically by virtue of the more specific target's class.
     *
     * @param aType A type property value
     * @return The target
     */
    protected abstract AbstractTarget setType(String aType);
}
