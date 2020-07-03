
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;

public enum ManifestBehavior implements Behavior {

    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE), INDIVIDUALS(
            BehaviorConstants.INDIVIDUALS), CONTINUOUS(BehaviorConstants.CONTINUOUS), REPEAT(
                    BehaviorConstants.REPEAT), NO_REPEAT(BehaviorConstants.NO_REPEAT), PAGED(BehaviorConstants.PAGED),
    UNORDERED(BehaviorConstants.UNORDERED);

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
