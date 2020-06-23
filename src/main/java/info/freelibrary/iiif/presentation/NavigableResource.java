
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.Summary;

/**
 * A navigable resource.
 */
class NavigableResource<T extends NavigableResource<T>> extends AbstractResource<NavigableResource<T>> {

    private NavDate myNavDate;

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type in string form
     */
    protected NavigableResource(final String aType) {
        super(aType);
    }

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID An ID in string form
     */
    protected NavigableResource(final String aType, final URI aID) {
        super(aType, aID);
    }

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID An ID in string form
     * @param aLabel A descriptive label, in string form
     */
    protected NavigableResource(final String aType, final String aID, final String aLabel) {
        super(aType, aID, aLabel);
    }

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID An ID
     * @param aLabel A descriptive label
     */
    protected NavigableResource(final String aType, final URI aID, final Label aLabel) {
        super(aType, aID, aLabel);
    }

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID An ID in string form
     * @param aLabel A descriptive label in string form
     * @param aMetadata A resource's metadata
     * @param aSummary A summary in string form
     * @param aThumbnail A thumbnail
     */
    protected NavigableResource(final String aType, final String aID, final String aLabel, final Metadata aMetadata,
            final String aSummary, final Thumbnail aThumbnail) {
        super(aType, aID, aLabel, aMetadata, aSummary, aThumbnail);
    }

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID An ID
     * @param aLabel A descriptive label
     * @param aMetadata A resource's metadata
     * @param aSummary A summary
     * @param aThumbnail A thumbnail
     */
    protected NavigableResource(final String aType, final URI aID, final Label aLabel, final Metadata aMetadata,
            final Summary aSummary, final Thumbnail aThumbnail) {
        super(aType, aID, aLabel, aMetadata, aSummary, aThumbnail);
    }

    /**
     * Gets a navigation date.
     *
     * @return The navigation date
     */
    @JsonGetter(Constants.NAV_DATE)
    public NavDate getNavDate() {
        return myNavDate;
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The navigable resource
     */
    @JsonSetter(Constants.NAV_DATE)
    protected NavigableResource<T> setNavDate(final NavDate aNavDate) {
        myNavDate = aNavDate;
        return this;
    }
}
