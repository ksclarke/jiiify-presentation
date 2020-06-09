
package info.freelibrary.iiif.presentation;

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

import info.freelibrary.iiif.presentation.properties.Localized;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * An abstract content resource class that specific content types can extend.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.FORMAT, Constants.LANGUAGE })
abstract class AbstractContentResource<T extends Resource<T>> extends Resource<T> implements Localized<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractContentResource.class,
            Constants.BUNDLE_NAME);

    private Optional<MediaType> myFormat;

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
    @JsonGetter(Constants.LANGUAGE)
    @JsonInclude(Include.NON_EMPTY)
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
    @JsonSetter(Constants.FORMAT)
    public T setFormat(final String aMediaType) {
        setMediaTypeFromExt(aMediaType);
        return (T) this;
    }

    /**
     * Sets the format of the content resource.
     *
     * @param aMediaType A media type
     * @return The content resource
     */
    @JsonIgnore
    public T setFormatMediaType(final MediaType aMediaType) {
        myFormat = Optional.ofNullable(aMediaType);
        return (T) this;
    }

    /**
     * Gets the format of the content resource.
     *
     * @return A string representation of the format
     */
    @JsonGetter(Constants.FORMAT)
    public Optional<String> getFormat() {
        if (myFormat.isPresent()) {
            final MediaType mediaType = myFormat.get();
            return Optional.of(mediaType.type() + "/" + mediaType.subtype()); // skip encoding
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
    public Optional<MediaType> getFormatMediaType() {
        return myFormat;
    }

    @JsonIgnore
    protected final void setMediaTypeFromExt(final String aURI) {
        final String fragment = '#' + URI.create(aURI).getFragment();
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
                myFormat = Optional.ofNullable(MediaType.parse(mimeType));
            } else {
                myFormat = Optional.ofNullable(MediaType.parse(aURI));
            }
        } catch (final IllegalArgumentException details) {
            LOGGER.debug(MessageCodes.JPA_013, aURI); // This is fine
            myFormat = Optional.empty();
        }
    }
}
