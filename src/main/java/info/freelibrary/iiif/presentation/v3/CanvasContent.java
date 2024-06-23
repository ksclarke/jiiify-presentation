
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Canvas content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class CanvasContent extends AbstractContentResource<CanvasContent>
        implements ContentResource, Resource<CanvasContent> {

    /**
     * Creates a canvas content resource from the supplied ID.
     *
     * @param aID A canvas content resource ID
     */
    public CanvasContent(final String aID) {
        super(ResourceTypes.CANVAS, aID, ResourceBehavior.class, null);
        myFormat = MediaType.APPLICATION_JSON;
    }

    /**
     * Creates a canvas content resource for Jackson's deserialization processes.
     */
    private CanvasContent() {
        super(ResourceTypes.CANVAS, ResourceBehavior.class);
        myFormat = MediaType.APPLICATION_JSON;
    }

    @Override
    @JsonIgnore
    public CanvasContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public CanvasContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
        }

        return super.setBehaviors(aBehaviorList);
    }
}
