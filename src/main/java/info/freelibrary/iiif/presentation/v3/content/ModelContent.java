
package info.freelibrary.iiif.presentation.v3.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Model content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class ModelContent extends AbstractContentResource<ModelContent>
        implements AnnotatedContentResource<ModelContent> {

    /** The class of media type this content represents. */
    private static final String MEDIA_TYPE_CLASS = "model";

    /**
     * Creates a model content resource from the supplied ID.
     *
     * @param aID An model content ID
     */
    public ModelContent(final String aID) {
        super(ResourceTypes.MODEL, aID, false, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Creates a model content resource from another model content resource.
     *
     * @param aModelContent Another model content resource
     */
    public ModelContent(final ModelContent aModelContent) {
        this(aModelContent.getID());

        myLanguages = new ArrayList<>(aModelContent.getLanguages());
        aModelContent.getFormat().ifPresent(format -> myFormat = format);
        setBehaviors(aModelContent.getBehaviors());

        super.copyTo(aModelContent);
    }

    /**
     * Creates a model content annotation. This is used by Jackson's deserialization processes.
     */
    private ModelContent() {
        super(ResourceTypes.MODEL, ResourceBehavior.class);
    }

    @Override
    public ModelContent copy() {
        return new ModelContent(this);
    }

    @Override
    @JsonIgnore
    public final ModelContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public final ModelContent setBehaviors(final List<Behavior> aBehaviorList) {
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
