
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * An ordered list of annotations, typically associated with a single canvas.
 */
public class AnnotationList extends Resource<AnnotationList> {

    private static final String TYPE = "sc:AnnotationList";

    private static final int REQ_ARG_COUNT = 2;

    /**
     * Creates an annotation list resource.
     *
     * @param aID An annotation list ID
     */
    public AnnotationList(final String aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates an annotation list resource.
     *
     * @param aID An annotation list ID
     */
    public AnnotationList(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public AnnotationList setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public AnnotationList addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

}
