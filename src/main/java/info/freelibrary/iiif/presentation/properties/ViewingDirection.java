
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ViewingDirection {

    LEFT_TO_RIGHT("left-to-right"),
    RIGHT_TO_LEFT("right-to-left"),
    TOP_TO_BOTTOM("top-to-bottom"),
    BOTTOM_TO_TOP("bottom-to-top");

    private final String myValue;

    private ViewingDirection(final String aDirection) {
        myValue = aDirection;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

}
