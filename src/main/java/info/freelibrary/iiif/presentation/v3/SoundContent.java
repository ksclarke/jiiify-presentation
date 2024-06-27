
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
 * Sound content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.DURATION,
    JsonKeys.LANGUAGE })
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class SoundContent extends AbstractContentResource<SoundContent>
        implements TemporalContentResource, AnnotatedContentResource<SoundContent>, Resource<SoundContent> {

    /** The class of media type this content represents. */
    private static final String MEDIA_TYPE_CLASS = "audio";

    /** The sound content's duration. */
    private float myDuration;

    /**
     * Creates sound content with the supplied ID.
     *
     * @param aID A sound content ID
     */
    public SoundContent(final String aID) {
        super(ResourceTypes.SOUND, aID, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Constructs a sound content resource for Jackson's deserialization process.
     */
    private SoundContent() {
        super(ResourceTypes.SOUND, ResourceBehavior.class);
    }

    /**
     * Gets the duration of the sound content.
     *
     * @return The duration of the sound content
     */
    @Override
    @JsonGetter(JsonKeys.DURATION)
    @JsonInclude(Include.NON_DEFAULT)
    public float getDuration() {
        return myDuration;
    }

    @Override
    @JsonIgnore
    public SoundContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public SoundContent setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
        }

        return super.setBehaviors(aBehaviorList);
    }

    /**
     * Sets the duration of the sound content. Duration must be positive and finite.
     *
     * @param aDuration A sound content duration
     * @return The sound content
     */
    @Override
    @JsonSetter(JsonKeys.DURATION)
    public SoundContent setDuration(final Number aDuration) {
        myDuration = convertToFinitePositiveFloat(aDuration);
        return this;
    }

    /**
     * Returns sound content from its JSON representation.
     *
     * @param aJsonString A JSON serialization of sound content
     * @return The sound content
     * @throws JsonParsingException If there is trouble parsing the JSON
     */
    static SoundContent fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(SoundContent.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
