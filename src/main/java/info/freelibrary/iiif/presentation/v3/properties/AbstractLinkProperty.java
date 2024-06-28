
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeKeySerializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeSerializer;

/**
 * A linking class that specific linking properties can extend.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.FORMAT, JsonKeys.PROFILE, JsonKeys.LANGUAGE })
abstract class AbstractLinkProperty<T extends AbstractLinkProperty<T>> implements Localized<T> {

    /** The linking property logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLinkProperty.class, MessageCodes.BUNDLE);

    /** The link property label. */
    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(JsonKeys.LABEL)
    protected Label myLabel;

    /** The link property format. */
    @JsonDeserialize(using = MediaTypeDeserializer.class)
    @JsonProperty(JsonKeys.FORMAT)
    private MediaType myFormat;

    /** The link property ID. */
    @JsonProperty(JsonKeys.ID)
    private String myID;

    /** The link property languages. */
    private List<String> myLanguages;

    /** The link property profile. */
    @JsonProperty(JsonKeys.PROFILE)
    private String myProfile;

    /** The link property type. */
    @JsonProperty(JsonKeys.TYPE)
    private String myType;

    /**
     * Creates an abstract link property for the Jackson deserialization process.
     */
    protected AbstractLinkProperty() {
        // This is intentionally empty
    }

    /**
     * Creates an abstract link property with a supplied type.
     *
     * @param aType The type of link property
     */
    protected AbstractLinkProperty(final String aType) {
        myType = Objects.requireNonNull(aType);
    }

    /**
     * Creates an abstract link property.
     *
     * @param aID An ID
     * @param aType A resource type
     */
    protected AbstractLinkProperty(final String aID, final String aType) {
        this(aType);
        myID = UriUtils.checkID(aID, false);
    }

    /**
     * Creates an abstract link property.
     *
     * @param aID An ID
     * @param aType A resource type
     * @param aLabel A label
     */
    protected AbstractLinkProperty(final String aID, final String aType, final Label aLabel) {
        this(aID, aType);
        myLabel = Objects.requireNonNull(aLabel);
    }

    /**
     * Tests whether the supplied object is equal to this one.
     *
     * @return True if the objects are equal; else, false
     */
    @Override
    @SuppressWarnings({ JDK.UNCHECKED })
    public boolean equals(final Object aObject) {
        if (aObject instanceof AbstractLinkProperty) {
            final T otherLink = (T) aObject;

            return Objects.equals(myID, otherLink.getID()) && Objects.equals(myType, otherLink.getType()) &&
                    Objects.equals(Optional.ofNullable(myFormat), otherLink.getFormat()) &&
                    Objects.equals(Optional.ofNullable(myProfile), otherLink.getProfile()) &&
                    Objects.equals(myLabel, otherLink.myLabel) &&
                    Objects.equals(getLanguages(), otherLink.getLanguages());
        }

        return false;
    }

    /**
     * Gets format as a media type.
     *
     * @return An optional media type format
     */
    @JsonInclude(Include.NON_EMPTY)
    @JsonSerialize(contentUsing = MediaTypeSerializer.class, keyUsing = MediaTypeKeySerializer.class)
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Gets the ID.
     *
     * @return An ID
     */
    public String getID() {
        return myID;
    }

    /**
     * Gets the resource's languages.
     *
     * @return A list of the resource's languages
     */
    @Override
    @JsonGetter(JsonKeys.LANGUAGE)
    public List<String> getLanguages() {
        if (myLanguages == null) {
            myLanguages = new ArrayList<>();
        }

        return myLanguages;
    }

    /**
     * Gets the profile.
     *
     * @return An optional profile URI
     */
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_EMPTY)
    public Optional<String> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Gets the resource type.
     *
     * @return The resource type
     */
    public String getType() {
        return myType;
    }

    /**
     * Gets a hash code for this property.
     *
     * @return A hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(myID, myType, myFormat, myProfile, myLabel, getLanguages());
    }

    /**
     * Sets format.
     *
     * @param aFormat A resource's format
     * @return The resource whose format is being set
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setFormat(final MediaType aFormat) {
        myFormat = aFormat;
        return (T) this;
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The resource whose ID is being set
     */
    @JsonSetter(JsonKeys.ID)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setID(final String aID) {
        myID = UriUtils.checkID(aID, false);
        return (T) this;
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The resource whose label is being set
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setLabel(final Label aLabel) {
        myLabel = Objects.requireNonNull(aLabel);
        return (T) this;
    }

    /**
     * Sets the profile.
     *
     * @param aProfile A profile
     * @return The resource whose profile is being set
     */
    @JsonSetter(JsonKeys.PROFILE)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setProfile(final String aProfile) {
        myProfile = UriUtils.checkID(aProfile, false);
        return (T) this;
    }

    /**
     * Sets the resource type.
     *
     * @param aType A resource type
     * @return The resource whose type is being set
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setType(final String aType) {
        myType = Objects.requireNonNull(aType);
        return (T) this;
    }

    /**
     * Returns a JSON string representing this resource.
     *
     * @return A JSON string representing this resource
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(getClass()).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Used by Jackson's deserialization processes.
     *
     * @param aObject An object to be deserialized
     * @return This resource
     * @throws IllegalArgumentException If the object supplied is unsupported
     */
    @JsonSetter(JsonKeys.LANGUAGE)
    private AbstractLinkProperty<T> setLanguageProperty(final Object aObject) {
        final List<?> languageList;

        if (aObject instanceof final String string) {
            return setLanguages(string);
        }

        if (aObject instanceof final String[] array) {
            return setLanguages(array);
        }

        if (!(aObject instanceof List)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_052, aObject.getClass().getName()));
        }

        languageList = (List<?>) aObject;

        if (!languageList.isEmpty() && languageList.get(0) instanceof String) {
            return setLanguages(languageList.toArray(new String[0]));
        }

        return this;
    }
}
