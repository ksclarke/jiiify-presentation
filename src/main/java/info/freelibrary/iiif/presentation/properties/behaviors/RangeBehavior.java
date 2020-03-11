
package info.freelibrary.iiif.presentation.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.properties.Behavior;

public enum RangeBehavior implements Behavior {

    AUTOADVANCE("auto-advance"), NOAUTOADVANCE("no-auto-advance"), INDIVIDUALS("individuals"), NONAV("no-nav"),
    CONTINUOUS("continuous"), PAGED("paged"), UNORDERED("unordered"), THUMBNAILNAV("thumbnail-nav"), SEQUENCE(
            "sequence");

    private final String myValue;

    RangeBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

}
