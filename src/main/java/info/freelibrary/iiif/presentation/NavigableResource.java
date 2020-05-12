
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Thumbnail;

/**
 * A navigable resource.
 */
class NavigableResource<T extends Resource<T>> extends Resource<T> {

    private NavDate myNavDate;

    /**
     * Creates a IIIF presentation navigable resource.
     *
     * @param aType A resource type in string form
     */
    NavigableResource(final String aType) {
        super(aType);
    }

    /**
     * Creates a IIIF presentation navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID A range ID in string form
     * @param aReqArgCount A required number of arguments
     */
    NavigableResource(final String aType, final URI aID, final int aReqArgCount) {
        super(aType, aID, aReqArgCount);
    }

    /**
     * Creates a IIIF presentation navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID A range ID in string form
     * @param aLabel A descriptive label, in string form, for the range
     * @param aReqArgCount A required number of arguments
     */
    NavigableResource(final String aType, final String aID, final String aLabel, final int aReqArgCount) {
        super(aType, aID, aLabel, aReqArgCount);
    }

    /**
     * Creates a IIIF presentation navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID A range resource ID
     * @param aLabel A descriptive label for the range resource
     * @param aReqArgCount A required number of arguments
     */
    NavigableResource(final String aType, final URI aID, final Label aLabel, final int aReqArgCount) {
        super(aType, aID, aLabel, aReqArgCount);
    }

    /**
     * Creates a IIIF presentation navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID A manifest ID in string form
     * @param aLabel A manifest label in string form
     * @param aMetadata A manifest's metadata
     * @param aSummary A manifest summary in string form
     * @param aThumbnail A manifest thumbnail
     * @param aReqArgCount A required argument count
     */
    NavigableResource(final String aType, final String aID, final String aLabel, final Metadata aMetadata,
            final String aSummary, final Thumbnail aThumbnail, final int aReqArgCount) {
        super(aType, aID, aLabel, aMetadata, aSummary, aThumbnail, aReqArgCount);
    }

    /**
     * Creates a IIIF presentation navigable resource.
     *
     * @param aType A resource type in string form
     * @param aID A manifest ID
     * @param aLabel A manifest label
     * @param aMetadata A manifest's metadata
     * @param aSummary A manifest summary
     * @param aThumbnail A manifest thumbnail
     * @param aReqArgCount A required argument count
     */
    NavigableResource(final String aType, final URI aID, final Label aLabel, final Metadata aMetadata,
            final Summary aSummary, final Thumbnail aThumbnail, final int aReqArgCount) {
        super(aType, aID, aLabel, aMetadata, aSummary, aThumbnail, aReqArgCount);
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The canvas
     */
    @JsonSetter(Constants.NAV_DATE)
    public T setNavDate(final NavDate aNavDate) {
        myNavDate = aNavDate;
        return (T) this;
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

}
