
package info.freelibrary.iiif.presentation.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.properties.Behavior;

public enum CanvasBehavior implements Behavior {

    AUTO_ADVANCE(BehaviorConstants.AUTO_ADVANCE), NO_AUTO_ADVANCE(BehaviorConstants.NO_AUTO_ADVANCE), FACING_PAGES(
            BehaviorConstants.FACING_PAGES), NON_PAGED(BehaviorConstants.NON_PAGED);

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
