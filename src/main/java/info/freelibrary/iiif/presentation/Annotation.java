
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * Content resources and commentary are associated with a canvas via an annotation. This provides a single, coherent
 * method for aligning information, and provides a standards based framework for distinguishing parts of resources and
 * parts of canvases. As annotations can be added later, it promotes a distributed system in which publishers can
 * align their content with the descriptions created by others.
 */
public class Annotation extends Resource<Annotation> {

    private static final String TYPE = "sc:Annotation";

    private static final int REQ_ARG_COUNT = 1;

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An annotation ID
     */
    public Annotation(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An annotation ID
     */
    public Annotation(final String aID) {
        super(TYPE, URI.create(aID), REQ_ARG_COUNT);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Annotation setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public Annotation addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

}
