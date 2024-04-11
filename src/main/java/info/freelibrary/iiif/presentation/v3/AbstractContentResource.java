
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Localized;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeKeySerializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeSerializer;

/**
 * An abstract content resource class that specific content types can extend.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
abstract class AbstractContentResource<T extends AbstractResource<AbstractContentResource<T>>>
        extends AbstractResource<AbstractContentResource<T>> implements Localized<T> {

    /** The content resource's Web annotations. */
    private List<AnnotationPage<WebAnnotation>> myAnnotations;

    /** The content resource's media type. */
    private MediaType myFormat;

    /** The content resource's languages. */
    private List<String> myLanguages;

    /**
     * Creates a content resource.
     *
     * @param aType The type of resource
     * @param aID The resource ID
     * @param aBehaviorClass A class of behavior for this resource
     */
    protected AbstractContentResource(final String aType, final String aID,
            final Class<? extends Behavior> aBehaviorClass) {
        super(aType, aID, aBehaviorClass);
        myFormat = MediaType.parse(aID).orElse(null);
    }

    /**
     * Creates a content resource.
     *
     * @param aType The type of resource
     * @param aBehaviorClass A class of behavior for this resource
     */
    protected AbstractContentResource(final String aType, final Class<? extends Behavior> aBehaviorClass) {
        super(aType, aBehaviorClass);
    }

    /**
     * Gets the content resource's languages.
     *
     * @return A list of languages
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
     * Gets the media type format of the content resource.
     *
     * @return The media type format of the content resource
     */
    @JsonInclude(Include.NON_EMPTY)
    @JsonSerialize(contentUsing = MediaTypeSerializer.class, keyUsing = MediaTypeKeySerializer.class)
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * A non-public way to set format from a media type.
     *
     * @param aMediaType A media type
     * @return This content resource
     */
    @JsonProperty(JsonKeys.FORMAT)
    @JsonDeserialize(using = MediaTypeDeserializer.class)
    protected AbstractContentResource<T> setFormat(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Gets the content resource's annotations.
     *
     * @return The content resource's annotations
     */
    @JsonGetter(JsonKeys.ANNOTATIONS)
    protected List<AnnotationPage<WebAnnotation>> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    /**
     * Sets the content resource's annotations.
     *
     * @param aAnnotationList A list of annotation pages
     * @return The content resource
     */
    @JsonSetter(JsonKeys.ANNOTATIONS)
    protected AbstractContentResource<T> setAnnotations(final List<AnnotationPage<WebAnnotation>> aAnnotationList) {
        final List<AnnotationPage<WebAnnotation>> annotations = getAnnotations();

        Objects.requireNonNull(aAnnotationList);
        annotations.clear();
        annotations.addAll(aAnnotationList);

        return this;
    }

    /**
     * Sets the content resource's annotation pages from an array.
     *
     * @param aAnnotationArray An array of annotation pages
     * @return The content resource
     */
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonIgnore
    protected AbstractContentResource<T> setAnnotations(final AnnotationPage<WebAnnotation>... aAnnotationArray) {
        return setAnnotations(Arrays.asList(aAnnotationArray));
    }

    /**
     * Used by Jackson't serialization processes.
     *
     * @return A form of language ready to be serialized
     */
    @JsonGetter(JsonKeys.LANGUAGE)
    @JsonInclude(Include.NON_EMPTY)
    private Object getLanguage() {
        // FIXME? Unclear if this should ALWAYS be an array
        final List<String> languages = getLanguages();
        return languages.size() == SINGLE_INSTANCE ? languages.get(0) : languages;
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
