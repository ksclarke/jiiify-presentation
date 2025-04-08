
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;

/**
 * A range that's used as the target of an annotation.
 */
public non-sealed class RangeTarget extends Target {

    /**
     * Creates a new range target of an annotation.
     *
     * @param aID An ID of a range
     */
    public RangeTarget(final String aID) {
        super(aID, true);
    }

    /**
     * Creates a new range target of an annotation from the supplied ID and list of partOf(s).
     *
     * @param aID A ID of a range to target
     * @param aPartOfList A list of partOf(s)
     */
    public RangeTarget(final String aID, final List<PartOf> aPartOfList) {
        super(aID, true, aPartOfList);
    }

    /**
     * Creates a new range target of an annotation from the supplied ID and an array of partOf(s).
     *
     * @param aID A ID of a range to target
     * @param aPartOfArray An array of partOf(s)
     */
    public RangeTarget(final String aID, final PartOf... aPartOfArray) {
        super(aID, true, aPartOfArray);
    }

    @Override
    public List<PartOf> getPartOfs() {
        return super.getPartOfs();
    }

    @Override
    public Optional<String> getType() {
        return Optional.of(ResourceTypes.RANGE);
    }

    @Override
    public RangeTarget setID(final String aID) {
        return (RangeTarget) super.setID(aID);
    }

    @Override
    public RangeTarget setPartOfs(final List<PartOf> aPartOfList) {
        return (RangeTarget) super.setPartOfs(aPartOfList);
    }

    @Override
    public RangeTarget setPartOfs(final PartOf... aPartOfArray) {
        return (RangeTarget) super.setPartOfs(aPartOfArray);
    }

    @Override
    protected RangeTarget setType(final String aType) {
        if (!ResourceTypes.RANGE.equals(aType)) {
            throw new IllegalArgumentException();
        }

        return this;
    }
}
