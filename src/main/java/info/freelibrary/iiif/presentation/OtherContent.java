
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * Other, non-image, resources that are associated with a {@link Canvas}.
 */
public class OtherContent extends Content<OtherContent> {

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID An other content ID in string form
     * @param aCanvas A canvas for other content
     */
    public OtherContent(final String aID, final Canvas aCanvas) {
        super(ResourceTypes.ANNOTATION_PAGE, aID, aCanvas);
    }

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID A other content ID
     * @param aCanvas A canvas for other content
     */
    public OtherContent(final URI aID, final Canvas aCanvas) {
        super(ResourceTypes.ANNOTATION_PAGE, aID, aCanvas);
    }

    /**
     * Creates a IIIF presentation content resource.
     */
    private OtherContent() {
        super(ResourceTypes.ANNOTATION_PAGE);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public OtherContent setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public OtherContent addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

}
