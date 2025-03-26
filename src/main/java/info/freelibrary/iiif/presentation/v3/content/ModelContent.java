
package info.freelibrary.iiif.presentation.v3.content;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Model content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class ModelContent extends AbstractContentResource<ModelContent>
        implements AnnotatedContentResource<ModelContent>, Resource<ModelContent> {

    /** The class of media type this content represents. */
    private static final String MEDIA_TYPE_CLASS = "model";

    /**
     * Creates a model content resource from the supplied ID.
     *
     * @param aID An model content ID
     */
    public ModelContent(final String aID) {
        super(ResourceTypes.MODEL, aID, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Creates a model content annotation. This is used by Jackson's deserialization processes.
     */
    private ModelContent() {
        super(ResourceTypes.MODEL, ResourceBehavior.class);
    }

    @Override
    @JsonIgnore
    public ModelContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public ModelContent setBehaviors(final List<Behavior> aBehaviorList) {
        final ModelContent modelContent;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            modelContent = super.setBehaviors(behaviorList);
        } else {
            modelContent = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return modelContent;
    }
}
