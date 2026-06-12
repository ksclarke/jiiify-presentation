
package info.freelibrary.iiif.presentation.v3.content;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sound content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.DURATION,
    JsonKeys.LANGUAGE })
public class SoundContent extends AbstractContentResource<SoundContent>
        implements TemporalContentResource<SoundContent>, AnnotatedContentResource<SoundContent> {

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
        super(ResourceTypes.SOUND, aID, false, ResourceBehavior.class, MEDIA_TYPE_CLASS);
    }

    /**
     * Copy constructor for sound content.
     *
     * @param aSoundContent The sound content to copy
     */
    public SoundContent(final SoundContent aSoundContent) {
        this(aSoundContent.getID());

        myDuration = aSoundContent.getDuration();
        myLanguages = new ArrayList<>(aSoundContent.getLanguages());
        aSoundContent.getFormat().ifPresent(format -> myFormat = format);
        setBehaviors(aSoundContent.getBehaviors());

        super.copyTo(aSoundContent);
    }

    /**
     * Constructs a sound content resource for Jackson's deserialization process.
     */
    private SoundContent() {
        super(ResourceTypes.SOUND, ResourceBehavior.class);
    }

    @Override
    public SoundContent copy() {
        return new SoundContent(this);
    }

    @Override
    public boolean equals(final Object aObject) {
        final SoundContent other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (SoundContent) aObject;

        return Objects.equals(myDuration, other.myDuration) && super.equals(other);
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
    public int hashCode() {
        return Objects.hash(super.hashCode(), myDuration);
    }

    @Override
    @JsonIgnore
    public final SoundContent setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public final SoundContent setBehaviors(final List<Behavior> aBehaviorList) {
        final SoundContent soundContent;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            soundContent = super.setBehaviors(behaviorList);
        } else {
            soundContent = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return soundContent;
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
}
