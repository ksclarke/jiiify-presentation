
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;

public enum RangeBehavior implements Behavior {

    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE), INDIVIDUALS(
            BehaviorConstants.INDIVIDUALS), NO_NAV(BehaviorConstants.NO_NAV), CONTINUOUS(BehaviorConstants.CONTINUOUS),
    PAGED(BehaviorConstants.PAGED), UNORDERED(BehaviorConstants.UNORDERED), THUMBNAIL_NAV(
            BehaviorConstants.THUMBNAIL_NAV), SEQUENCE(BehaviorConstants.SEQUENCE);

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
