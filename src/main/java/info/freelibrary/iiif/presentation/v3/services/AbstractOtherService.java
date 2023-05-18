
package info.freelibrary.iiif.presentation.v3.services;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeKeySerializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeSerializer;

/**
 * An abstract class for other services.
 *
 * @param <T> The type of other service
 */
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.V2_ID, JsonKeys.ID, JsonKeys.V2_TYPE, JsonKeys.TYPE, JsonKeys.PROFILE,
    JsonKeys.LABEL, JsonKeys.SERVICE })
@JsonInclude(Include.NON_EMPTY)
abstract class AbstractOtherService<T extends AbstractOtherService<T>> extends AbstractService<T> {

    /** This service's format. */
    @JsonDeserialize(using = MediaTypeDeserializer.class)
    @JsonProperty(JsonKeys.FORMAT)
    private MediaType myFormat;

    /**
     * Creates a new unspecified service from the supplied ID and type.
     *
     * @param aID A service ID
     * @param aType A service type
     */
    protected AbstractOtherService(final String aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Creates a new unspecified service from the supplied ID, type, and profile.
     *
     * @param aID A service ID
     * @param aType A service type
     * @param aProfile A service profile
     */
    protected AbstractOtherService(final String aID, final String aType, final Service.Profile aProfile) {
        super(aID, aType, aProfile);
    }

    /**
     * Gets the other service's media-type.
     *
     * @return The service's media-type
     */
    @JsonInclude(Include.NON_EMPTY)
    @JsonSerialize(contentUsing = MediaTypeSerializer.class, keyUsing = MediaTypeKeySerializer.class)
    protected Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Sets the other service's format from the supplied media-type.
     *
     * @param aFormat A format
     * @return This service
     */
    protected AbstractOtherService<T> setFormat(final MediaType aFormat) {
        myFormat = Objects.requireNonNull(aFormat);
        return this;
    }

}
