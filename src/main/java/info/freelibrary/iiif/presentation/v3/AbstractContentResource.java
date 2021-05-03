
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

import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.Localized;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An abstract content resource class that specific content types can extend.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.FORMAT, Constants.LANGUAGE })
abstract class AbstractContentResource<T extends AbstractResource<AbstractContentResource<T>>>
        extends AbstractResource<AbstractContentResource<T>> implements Localized<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractContentResource.class, MessageCodes.BUNDLE);

    private MediaType myFormat;

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
    protected AbstractContentResource<T> setFormatMediaType(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Gets the format of the content resource.
     *
     * @return A string representation of the format
     */
    @JsonGetter(Constants.FORMAT)
    public Optional<String> getFormat() {
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
    public Optional<MediaType> getFormatMediaType() {
        return Optional.ofNullable(myFormat);
    }

    @JsonIgnore
    protected final void setMediaTypeFromExt(final String aURI) throws IllegalArgumentException {
        final String fragment = Constants.FRAGMENT_DELIM + URI.create(aURI).getFragment();
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
            LOGGER.warn(MessageCodes.JPA_013, aURI);
            myFormat = null;
        }
    }
}
