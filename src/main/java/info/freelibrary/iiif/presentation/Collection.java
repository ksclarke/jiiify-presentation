
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Description;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A collection resource.
 */
public class Collection extends Resource<Collection> {

    private static final String TYPE = "sc:Collection";

    private static final int REQ_ARG_COUNT = 3;

    private NavDate myNavDate;

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final String aID, final String aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final URI aID, final Label aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     * @param aMetadata A collection's metadata
     * @param aDescription A collection description
     * @param aThumbnail A collection thumbnail
     */
    public Collection(final String aID, final String aLabel, final Metadata aMetadata, final String aDescription,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, aMetadata, aDescription, aThumbnail, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     * @param aMetadata A collection's metadata
     * @param aDescription A collection description
     * @param aThumbnail A collection thumbnail
     */
    public Collection(final URI aID, final Label aLabel, final Metadata aMetadata, final Description aDescription,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, aMetadata, aDescription, aThumbnail, REQ_ARG_COUNT);
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The collection
     */
    @JsonSetter(Constants.NAV_DATE)
    public Collection setNavDate(final NavDate aNavDate) {
        myNavDate = aNavDate;
        return this;
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
