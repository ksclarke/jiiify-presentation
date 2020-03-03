
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A package-level content class that extends Resource and is extended by other public classes.
 *
 * @param <T> The class that's extending this content
 */
class Content<T extends Content<T>> extends Resource<T> {

    private static final int REQ_ARG_COUNT = 2;

    private URI myOn;

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aTypeString The type in string form
     * @param aIdString The ID in string form
     * @param aCanvas A canvas
     */
    protected Content(final String aTypeString, final String aIdString, final Canvas aCanvas) {
        super(aTypeString, aIdString, REQ_ARG_COUNT);
        myOn = aCanvas.getID();
    }

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aTypeString The type in string form
     * @param aID A URI ID
     * @param aCanvas A canvas
     */
    protected Content(final String aTypeString, final URI aID, final Canvas aCanvas) {
        super(aTypeString, aID, REQ_ARG_COUNT);
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

    @JsonGetter(Constants.ON)
    public URI getOn() {
        return myOn;
    }

    @JsonIgnore
    public T setOn(final URI aURI) {
        myOn = aURI;
        return (T) this;
    }

    @JsonSetter(Constants.ON)
    public T setOn(final String aUriString) {
        myOn = URI.create(aUriString);
        return (T) this;
    }
}
