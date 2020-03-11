
package info.freelibrary.iiif.presentation.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.properties.Behavior;

public enum ManifestBehavior implements Behavior {

    AUTOADVANCE("auto-advance"), NOAUTOADVANCE("no-auto-advance"), INDIVIDUALS("individuals"), CONTINUOUS(
            "continuous"), REPEAT("repeat"), NOREPEAT("no-repeat"), PAGED("paged"), UNORDERED("unordered");

    private final String myValue;

    ManifestBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

}
