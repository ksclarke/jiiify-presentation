
package info.freelibrary.iiif.presentation.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.properties.Localized;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract content resource class that specific content types can extend.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
abstract class AbstractContentResource<T extends AbstractResource<AbstractContentResource<T>>>
        extends AbstractResource<AbstractContentResource<T>> implements Localized<T> {

    /**
     * The number of languages for a single (non-array) value.
     */
    private static final int SINGLE_LANGUAGE_COUNT = 1;

    /**
     * The content resource's media type.
     */
    private MediaType myFormat;

    /**
     * The content resource's languages.
     */
    private List<String> myLanguages;

    /**
     * Creates a content resource.
     *
     * @param aType The type of resource
     * @param aID The resource ID in string form
     */
    protected AbstractContentResource(final String aType, final String aID) {
        super(aType, aID);
        myFormat = MediaType.parse(aID).orElse(null);
    }

    /**
     * Creates a content resource.
     *
     * @param aType The type of resource
     */
    protected AbstractContentResource(final String aType) {
        super(aType);
    }

    /**
     * Gets the content resource's languages.
     *
     * @return A list of languages
     */
    @Override
    public List<String> getLanguages() {
        if (myLanguages == null) {
            myLanguages = new ArrayList<>();
        }

        return myLanguages;
    }

    /**
     * Sets the format of the resource from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The content resource
     */
    @JsonSetter(JsonKeys.FORMAT)
    protected AbstractContentResource<T> setFormat(final String aMediaType) {
        myFormat = MediaType.fromString(aMediaType).orElse(null);
        return this;
    }

    /**
     * Sets the format of the content resource.
     *
     * @param aMediaType A media type
     * @return The content resource
     */
    @JsonIgnore
    protected AbstractContentResource<T> setFormat(final MediaType aMediaType) {
        return setFormatFromMediaType(aMediaType);
    }

    /**
     * Gets the format of the content resource.
     *
     * @return A string representation of the format
     */
    @JsonGetter(JsonKeys.FORMAT)
    private Optional<String> getFormatAsString() {
        if (myFormat != null) {
            return Optional.of(myFormat.toString());
        }
        return Optional.empty();
    }

    /**
     * Gets the media type format of the content resource.
     *
     * @return The media type format of the content resource
     */
    @JsonIgnore
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * A non-public way to set format from a media type.
     *
     * @param aMediaType A media type
     * @return This content resource
     */
    @JsonIgnore
    protected final AbstractContentResource<T> setFormatFromMediaType(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Used by Jackson't serialization processes.
     *
     * @return A form of language ready to be serialized
     */
    @JsonGetter(JsonKeys.LANGUAGE)
    @JsonInclude(Include.NON_EMPTY)
    private Object getLanguage() {
        final List<String> languages = getLanguages();

        if (languages.size() == SINGLE_LANGUAGE_COUNT) {
            return languages.get(0);
        }
        return languages;
    }

    /**
     * Used by Jackson's deserialization processes.
     *
     * @param aObject An object to be deserialized
     * @return This resource
     */
    @JsonSetter(JsonKeys.LANGUAGE)
    private AbstractContentResource<T> setLanguage(final Object aObject) {
        if (aObject instanceof String) {
            return (AbstractContentResource<T>) setLanguages((String) aObject);
        }
        if (aObject instanceof String[]) {
            return (AbstractContentResource<T>) setLanguages((String[]) aObject);
        }

        return this;
    }

}
