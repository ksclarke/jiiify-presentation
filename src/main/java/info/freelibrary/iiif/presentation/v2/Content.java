
package info.freelibrary.iiif.presentation.v2;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v2.properties.Type;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * IIIF content.
 *
 * @param <T> The type of content
 */
class Content<T extends Content<T>> extends Resource<T> {

    /**
     * The required argument type for constructing content.
     */
    private static final int REQ_ARG_COUNT = 2;

    /**
     * What the content is on.
     */
    private URI myOn;

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aType A content type
     * @param aID A content ID
     * @param aCanvas A content canvas
     */
    protected Content(final String aType, final String aID, final Canvas aCanvas) {
        super(aType, aID, REQ_ARG_COUNT);
        myOn = aCanvas.getID();
    }

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aType A content type
     * @param aID A content ID
     * @param aCanvas A content canvas
     */
    protected Content(final String aType, final URI aID, final Canvas aCanvas) {
        super(aType, aID, REQ_ARG_COUNT);
        myOn = aCanvas.getID();
    }

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aType A type of Content
     */
    protected Content(final Type aType) {
        super(aType);
    }

    /**
     * Gets the content's on.
     *
     * @return The URL for what the content is on
     */
    @JsonGetter(Constants.ON)
    public URI getOn() {
        return myOn;
    }

    /**
     * Sets the content's on.
     *
     * @param aURI The URL for what the content is on
     * @return The content
     */
    @JsonIgnore
    protected Content<T> setOn(final URI aURI) {
        myOn = aURI;
        return this;
    }

    /**
     * Sets the content's on.
     *
     * @param aURI The string form of the URL from what the content is on
     * @return The content
     */
    @JsonSetter(Constants.ON)
    protected Content<T> setOn(final String aURI) {
        myOn = URI.create(aURI);
        return this;
    }
}
