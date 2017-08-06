
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.helpers.Constants;
import info.freelibrary.iiif.presentation.properties.Description;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.Thumbnail;

/**
 * A collection resource.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Collection extends Resource<Collection> {

    static final String TYPE = "sc:Collection";

    private final static int REQ_ARG_COUNT = 3;

    private NavDate myNavDate;

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     * @throws IllegalArgumentException If the supplied ID is not a valid URI
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
     * @throws IllegalArgumentException If the supplied ID is not a valid URI
     */
    public Collection(final String aID, final String aLabel, final Metadata aMetadata, final String aDescription,
            final Thumbnail aThumbnail) throws URISyntaxException {
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
