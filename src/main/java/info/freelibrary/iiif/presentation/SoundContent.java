
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Sound content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.FORMAT, Constants.DURATION })
public class SoundContent extends AbstractContentResource<SoundContent> implements ContentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundContent.class, Constants.BUNDLE_NAME);

    private float myDuration;

    /**
     * Creates sound content with the supplied ID URI.
     *
     * @param aURI A sound content ID in string form
     */
    public SoundContent(final String aURI) {
        super(ResourceTypes.SOUND, aURI);
        setMediaTypeFromExt(aURI);
    }

    /**
     * Creates sound content with the supplied ID URI.
     *
     * @param aURI A sound content ID
     */
    public SoundContent(final URI aURI) {
        super(ResourceTypes.SOUND, aURI);
        setMediaTypeFromExt(aURI.toString());
    }

    /**
     * Constructs a sound content resource for Jackson's deserialization process.
     */
    private SoundContent() {
        super(ResourceTypes.SOUND);
    }

    /**
     * Gets the duration of the sound content.
     *
     * @return The duration of the sound content
     */
    @JsonGetter(Constants.DURATION)
    @JsonInclude(Include.NON_DEFAULT)
    public float getDuration() {
        return myDuration;
    }

    /**
     * Sets the duration of the sound content. Duration must be positive and finite.
     *
     * @param aDuration A sound content duration
     * @return The sound content
     */
    @JsonSetter(Constants.DURATION)
    public SoundContent setDuration(final float aDuration) {
        if ((aDuration > 0) && (Float.isFinite(aDuration))) {
            myDuration = aDuration;
            return this;
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_024, aDuration));
        }
    }

    /**
     * Returns sound content from its JSON representation.
     *
     * @param aJsonObject A sound content resource in JSON form
     * @return The sound content
     */
    public static SoundContent fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), SoundContent.class);
    }

    /**
     * Returns sound content from its JSON representation.
     *
     * @param aJsonString A sound content resource in string form
     * @return The sound content
     */
    public static SoundContent fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }

}
