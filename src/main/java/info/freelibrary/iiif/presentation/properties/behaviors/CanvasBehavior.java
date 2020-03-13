
package info.freelibrary.iiif.presentation.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.properties.Behavior;

public enum CanvasBehavior implements Behavior {

    AUTOADVANCE("auto-advance"), NOAUTOADVANCE("no-auto-advance"), FACINGPAGES("facing-pages"), NONPAGED("non-paged");

    private final String myValue;

    CanvasBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

}
