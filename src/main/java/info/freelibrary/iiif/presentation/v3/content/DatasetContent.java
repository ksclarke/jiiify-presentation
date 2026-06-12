
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
 * Dataset content that can be associated with an annotation or set as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class DatasetContent extends AbstractContentResource<DatasetContent>
        implements AnnotatedContentResource<DatasetContent> {

    /**
     * Creates a dataset content resource from the supplied ID.
     *
     * @param aID A dataset content ID
     */
    public DatasetContent(final String aID) {
        super(ResourceTypes.DATASET, aID, false, ResourceBehavior.class, null);
    }

    /**
     * Creates a dataset content resource from another dataset content resource.
     *
     * @param aDatasetContent Another dataset content resource
     */
    public DatasetContent(final DatasetContent aDatasetContent) {
        this(aDatasetContent.getID());

        myLanguages = new ArrayList<>(aDatasetContent.getLanguages());
        aDatasetContent.getFormat().ifPresent(format -> myFormat = format);
        setBehaviors(aDatasetContent.getBehaviors());

        super.copyTo(aDatasetContent);
    }

    /**
     * Creates a dataset content resource. This is used by Jackson for its deserialization processes.
     */
    private DatasetContent() {
        super(ResourceTypes.DATASET, ResourceBehavior.class);
    }

    @Override
    public DatasetContent copy() {
        return new DatasetContent(this);
    }

    @Override
    @JsonIgnore
    public final DatasetContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public final DatasetContent setBehaviors(final List<Behavior> aBehaviorList) {
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
