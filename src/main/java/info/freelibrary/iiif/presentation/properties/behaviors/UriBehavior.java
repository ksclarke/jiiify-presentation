
package info.freelibrary.iiif.presentation.properties.behaviors;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.properties.Behavior;

public class UriBehavior implements Behavior {

    private final URI myURI;

    public UriBehavior(final URI aURI) {
        myURI = aURI;
    }

    public UriBehavior(final String aURI) {
        myURI = URI.create(aURI);
    }

    @Override
    @JsonValue
    public String toString() {
        return myURI.toString();
    }
}
