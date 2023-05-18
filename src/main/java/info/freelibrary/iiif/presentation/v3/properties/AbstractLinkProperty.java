
package info.freelibrary.iiif.presentation.v3.properties;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /** The link property ID. */
    private String myID;

    /** The link property type. */
    private String myType;

    /** The link property format. */
    @JsonDeserialize(using = MediaTypeDeserializer.class)
    @JsonProperty(JsonKeys.FORMAT)
    private MediaType myFormat;

    /** The link property profile. */
    private String myProfile;

    /** The link property label. */
    private Label myLabel;

    /** The link property languages. */
    private List<String> myLanguages;

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
     * Sets the ID.
     *
     * @param aID An ID
     * @return The resource whose ID is being set
     */
    @JsonSetter(JsonKeys.ID)
    protected AbstractLinkProperty<T> setID(final String aID) {
        myID = UriUtils.checkID(aID, false);
        return this;
    }

    /**
     * Gets the ID.
     *
     * @return An ID
     */
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return myID;
    }

    /**
     * Sets the resource type.
     *
     * @param aType A resource type
     * @return The resource whose type is being set
     */
    protected AbstractLinkProperty<T> setType(final String aType) {
        myType = Objects.requireNonNull(aType);
        return this;
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
     * Sets format.
     *
     * @param aFormat A resource's format
     * @return The resource whose format is being set
     */
    protected AbstractLinkProperty<T> setFormat(final MediaType aFormat) {
        myFormat = Objects.requireNonNull(aFormat);
        return this;
    }

    /**
     * Gets the resource's languages.
     *
     * @return A list of the resource's languages
     */
    @Override
    @JsonIgnore
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
    public Optional<String> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Sets the profile.
     *
     * @param aProfile A profile
     * @return The resource whose profile is being set
     */
    @JsonSetter(JsonKeys.PROFILE)
    protected AbstractLinkProperty<T> setProfile(final String aProfile) {
        myProfile = UriUtils.checkID(aProfile, false);
        return this;
    }

    /**
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    @JsonGetter(JsonKeys.LABEL)
    @JsonInclude(Include.NON_EMPTY)
    protected Label getNullableLabel() {
        return myLabel;
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The resource whose label is being set
     */
    @JsonSetter(JsonKeys.LABEL)
    protected AbstractLinkProperty<T> setLabel(final Label aLabel) {
        myLabel = Objects.requireNonNull(aLabel);
        return this;
    }

    /**
     * Gets a hash code for this property.
     *
     * @return A hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(myID, myType, myFormat, myProfile, myLabel);
    }

    /**
     * Tests whether the supplied object is equal to this one.
     *
     * @return True if the objects are equal; else, false
     */
    @Override
    public boolean equals(final Object aObject) {
        if (aObject instanceof AbstractLinkProperty) {
            final AbstractLinkProperty<?> otherLink = (AbstractLinkProperty<?>) aObject;

            return Objects.equals(myID, otherLink.myID) && Objects.equals(myType, otherLink.myType) &&
                    Objects.equals(myFormat, otherLink.myFormat) && Objects.equals(myProfile, otherLink.myProfile) &&
                    Objects.equals(myLabel, otherLink.myLabel);
        }

        return false;
    }

    /**
     * Returns a JSON string representing this resource.
     *
     * @return A JSON string representing this resource
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(this.getClass()).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Used by Jackson's serialization processes.
     *
     * @return A form of language ready to be serialized
     */
    @JsonGetter(JsonKeys.LANGUAGE)
    @JsonInclude(Include.NON_EMPTY)
    protected Object getLanguageProperty() {
        final List<String> languages = getLanguages();

        if (languages.size() == SINGLE_INSTANCE) {
            return languages.get(0);
        }

        return languages;
    }

    /**
     * Used by Jackson's deserialization processes.
     *
     * @param aObject An object to be deserialized
     * @return This resource
     * @throws IllegalArgumentException If the object supplied is unsupported
     */
    @JsonSetter(JsonKeys.LANGUAGE)
    protected AbstractLinkProperty<T> setLanguageProperty(final Object aObject) {
        final List<?> languageList;

        if (aObject instanceof String) {
            return (AbstractLinkProperty<T>) setLanguages((String) aObject);
        }

        if (aObject instanceof String[]) {
            return (AbstractLinkProperty<T>) setLanguages((String[]) aObject);
        }

        if (!(aObject instanceof List)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_052, aObject.getClass().getName()));
        }

        languageList = (List<?>) aObject;

        if (!languageList.isEmpty() && languageList.get(0) instanceof String) {
            return (AbstractLinkProperty<T>) setLanguages(languageList.toArray(new String[0]));
        }

        return this;
    }
}
