
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

import info.freelibrary.util.Constants;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.Localized;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An abstract content resource class that specific content types can extend.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
abstract class AbstractContentResource<T extends AbstractResource<AbstractContentResource<T>>>
        extends AbstractResource<AbstractContentResource<T>> implements Localized<T> {

    /**
     * The logger used by the AbstractContentResource.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractContentResource.class, MessageCodes.BUNDLE);

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
        setMediaTypeFromExt(aID);
    }

    /**
     * Creates a content resource.
     *
     * @param aType The type of resource
     * @param aID The resource ID
     */
    protected AbstractContentResource(final String aType, final URI aID) {
        super(aType, aID);
        setMediaTypeFromExt(aID.toString());
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
        setMediaTypeFromExt(aMediaType);
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
            return Optional.of(myFormat.type() + "/" + myFormat.subtype()); // skip encoding
        } else {
            return Optional.empty();
        }
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
     * Sets the media type from the extension of the supplied URI.
     *
     * @param aURI A URI from which to glean a media type
     */
    @JsonIgnore
    protected final void setMediaTypeFromExt(final String aURI) {
        final String fragment = Constants.HASH + URI.create(aURI).getFragment();
        final String mimeType;
        final String uri;
        final int index;

        // If we have a fragment on our URI, remove it before checking media type
        if ((index = aURI.indexOf(fragment)) != -1) {
            uri = aURI.substring(0, index);
        } else {
            uri = aURI;
        }

        mimeType = FileUtils.getMimeType(uri);

        try {
            if (mimeType != null) {
                myFormat = MediaType.parse(mimeType);
            } else {
                myFormat = MediaType.parse(aURI);
            }
        } catch (final IllegalArgumentException details) {
            LOGGER.debug(MessageCodes.JPA_013, aURI); // It's okay to not have one if we don't know it
        }
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
        } else {
            return languages;
        }
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
        } else if (aObject instanceof String[]) {
            return (AbstractContentResource<T>) setLanguages((String[]) aObject);
        }

        return this;
    }

}
