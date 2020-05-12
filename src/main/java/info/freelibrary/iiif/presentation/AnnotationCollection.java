
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * A collection of annotations.
 */
public class AnnotationCollection extends Resource<AnnotationCollection> {

    private static final int REQ_ARG_COUNT = 3;

    private ViewingDirection myViewingDirection;

    /**
     * Creates a collection of annotations.
     *
     * @param aID A collection ID in string form
     * @param aLabel A descriptive label, in string form, for the collection
     */
    public AnnotationCollection(final String aID, final String aLabel) {
        super(ResourceTypes.ANNOTATION_COLLECTION, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a collection of annotations.
     *
     * @param aID A collection ID
     * @param aLabel A descriptive label for the collection
     */
    public AnnotationCollection(final URI aID, final Label aLabel) {
        super(ResourceTypes.ANNOTATION_COLLECTION, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The annotation collection
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public AnnotationCollection setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(Constants.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public AnnotationCollection setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public AnnotationCollection addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

}
