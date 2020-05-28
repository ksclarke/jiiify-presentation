
package info.freelibrary.iiif.presentation.properties.behaviors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.properties.Behavior;

public enum ResourceBehavior implements Behavior {

    HIDDEN(BehaviorConstants.HIDDEN);

    private final String myValue;

    ResourceBehavior(final String aBehavior) {
        myValue = aBehavior;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

}
