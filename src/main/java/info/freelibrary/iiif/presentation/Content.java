
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.utils.Constants;

class Content<T extends Content<T>> extends Resource<T> {

    private static final int REQ_ARG_COUNT = 2;

    private URI myOn;

    /**
     * Creates a IIIF presentation content resource.
     */
    protected Content(final String aType, final String aID, final Canvas aCanvas) {
        super(aType, aID, REQ_ARG_COUNT);
        myOn = aCanvas.getID();
    }

    /**
     * Creates a IIIF presentation content resource.
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
    public T setOn(final String aURI) {
        myOn = URI.create(aURI);
        return (T) this;
    }
}
