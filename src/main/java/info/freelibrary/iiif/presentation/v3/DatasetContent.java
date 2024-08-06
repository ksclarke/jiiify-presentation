
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Dataset content that can be associated with an annotation or set as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class DatasetContent extends AbstractContentResource<DatasetContent>
        implements AnnotatedContentResource<DatasetContent>, Resource<DatasetContent> {

    /**
     * Creates a dataset content resource from the supplied ID.
     *
     * @param aID A dataset content ID
     */
    public DatasetContent(final String aID) {
        super(ResourceTypes.DATASET, aID, ResourceBehavior.class, null);
    }

    /**
     * Creates a dataset content resource. This is used by Jackson for its deserialization processes.
     */
    private DatasetContent() {
        super(ResourceTypes.DATASET, ResourceBehavior.class);
    }

    @Override
    @JsonIgnore
    public DatasetContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public DatasetContent setBehaviors(final List<Behavior> aBehaviorList) {
        final DatasetContent datasetContent;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            datasetContent = super.setBehaviors(behaviorList);
        } else {
            datasetContent = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return datasetContent;
    }
}
