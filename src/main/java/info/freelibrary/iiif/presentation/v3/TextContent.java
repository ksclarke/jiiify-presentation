
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
 * Text content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class TextContent extends AbstractContentResource<TextContent>
        implements AnnotatedContentResource<TextContent>, Resource<TextContent> {

    /**
     * Creates a text content resource.
     *
     * @param aID An text content resource ID
     */
    public TextContent(final String aID) {
        super(ResourceTypes.TEXT, aID, ResourceBehavior.class, null);
    }

    /**
     * Creates a text content resource.
     */
    private TextContent() {
        super(ResourceTypes.TEXT, ResourceBehavior.class);
    }

    @Override
    @JsonIgnore
    public TextContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public TextContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
        }

        return super.setBehaviors(aBehaviorList);
    }

    /**
     * Returns text content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a text content resource
     * @return The text content
     * @throws JsonParsingException If the text content cannot be deserialized from the supplied JSON
     */
    static TextContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(TextContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
