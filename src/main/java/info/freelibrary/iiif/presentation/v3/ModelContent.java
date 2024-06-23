
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

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
    @JsonSetter(JsonKeys.BEHAVIOR)
    public ModelContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
        }

        return super.setBehaviors(aBehaviorList);
    }

    /**
     * Returns model content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a model content resource
     * @return The model content
     * @throws JsonParsingException If the model content cannot be deserialized from the supplied JSON
     */
    static ModelContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(ModelContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
