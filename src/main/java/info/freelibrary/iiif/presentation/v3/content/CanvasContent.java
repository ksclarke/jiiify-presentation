
package info.freelibrary.iiif.presentation.v3.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;

import java.util.ArrayList;
import java.util.List;

/**
 * Canvas content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
public class CanvasContent extends AbstractContentResource<CanvasContent> {

    /**
     * Creates a canvas content resource from the supplied ID.
     *
     * @param aID A canvas content resource ID
     */
    public CanvasContent(final String aID) {
        super(ResourceTypes.CANVAS, aID, true, ResourceBehavior.class, null);
        // myFormat = MediaType.APPLICATION_JSON;
    }

    /**
     * Creates a copy of the canvas content resource.
     *
     * @param aCanvasContent A canvas content resource to copy
     */
    public CanvasContent(final CanvasContent aCanvasContent) {
        super(ResourceTypes.CANVAS, aCanvasContent.getID(), true, ResourceBehavior.class, null);
        // myFormat = MediaType.APPLICATION_JSON;

        myLanguages = new ArrayList<>(aCanvasContent.getLanguages());
        aCanvasContent.getFormat().ifPresent(format -> myFormat = format);
        setBehaviors(aCanvasContent.getBehaviors());

        super.copyTo(aCanvasContent);
    }

    /**
     * Creates a canvas content resource for Jackson's deserialization processes.
     */
    private CanvasContent() {
        super(ResourceTypes.CANVAS, ResourceBehavior.class);
        // myFormat = MediaType.APPLICATION_JSON;
    }

    @Override
    public CanvasContent copy() {
        return new CanvasContent(this);
    }

    @Override
    @JsonIgnore
    public final CanvasContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public final CanvasContent setBehaviors(final List<Behavior> aBehaviorList) {
        final CanvasContent canvasContent;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            canvasContent = super.setBehaviors(behaviorList);
        } else {
            canvasContent = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return canvasContent;
    }
}
