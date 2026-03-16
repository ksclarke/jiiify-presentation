
package info.freelibrary.iiif.presentation.v3.content;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.AbstractResource;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Localized;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeKeySerializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeSerializer;
import info.freelibrary.util.ListUtils;
import info.freelibrary.util.warnings.JDK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * An abstract content resource class that specific content types can extend.
 *
 * @param <T> The concrete content resource type
 */
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public abstract class AbstractContentResource<T extends AbstractContentResource<T>> extends AbstractResource<T>
        implements Localized<T> {

    /** The content resource's media type. */
    protected MediaType myFormat;

    /** The content resource's Web annotations. */
    private List<AnnotationPage<WebAnnotation>> myAnnotations;

    /** The content resource's languages. */
    private List<String> myLanguages;

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
     * Creates a content resource.
     *
     * @param aType The type of resource
     * @param aID The resource ID
     * @param aHttpsID Whether the resource ID should be HTTPS
     * @param aBehaviorClass A class of behavior for this resource
     * @param aMediaTypeHint An optional hint as to the class of media type should be used
     */
    protected AbstractContentResource(final String aType, final String aID, final boolean aHttpsID,
            final Class<? extends Behavior> aBehaviorClass, final String aMediaTypeHint) {
        super(aType, aID, aHttpsID, aBehaviorClass);

        myFormat = aMediaTypeHint != null ? MediaType.parse(aID, aMediaTypeHint).orElse(null)
                : MediaType.parse(aID).orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object aObject) {
        final AbstractContentResource<T> other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (AbstractContentResource<T>) aObject;

        return Objects.equals(myFormat, other.myFormat) && ListUtils.equals(myAnnotations, other.myAnnotations) &&
                ListUtils.equals(myLanguages, other.myLanguages) && super.equals(other);
    }

    /**
     * Gets the content resource's annotations.
     *
     * @return The content resource's annotations
     */
    @JsonGetter(JsonKeys.ANNOTATIONS)
    public List<AnnotationPage<WebAnnotation>> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    /**
     * Gets the media type format of the content resource.
     *
     * @return The media type format of the content resource
     */
    @JsonSerialize(contentUsing = MediaTypeSerializer.class, keyUsing = MediaTypeKeySerializer.class)
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myFormat, myAnnotations, myLanguages);
    }

    /**
     * Sets the content resource's annotation pages from an array.
     *
     * @param aAnnotationArray An array of annotation pages
     * @return The content resource
     */
    @JsonIgnore
    @SafeVarargs
    public final T setAnnotations(final AnnotationPage<WebAnnotation>... aAnnotationArray) {
        return setAnnotations(Arrays.asList(aAnnotationArray));
    }

    /**
     * Sets the content resource's annotations.
     *
     * @param aAnnotationList A list of annotation pages
     * @return The content resource
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    @JsonSetter(JsonKeys.ANNOTATIONS)
    public T setAnnotations(final List<AnnotationPage<WebAnnotation>> aAnnotationList) {
        final List<AnnotationPage<WebAnnotation>> annotations = getAnnotations();

        Objects.requireNonNull(aAnnotationList);
        annotations.clear();
        annotations.addAll(aAnnotationList);

        return (T) this;
    }

    /**
     * A non-public way to set format from a media type.
     *
     * @param aMediaType A media type
     * @return This content resource
     */
    @JsonProperty(JsonKeys.FORMAT)
    @JsonDeserialize(using = MediaTypeDeserializer.class)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setFormat(final MediaType aMediaType) {
        myFormat = Objects.requireNonNull(aMediaType);
        return (T) this;
    }

    /**
     * Used by Jackson't serialization processes.
     *
     * @return A form of language ready to be serialized
     */
    @JsonGetter(JsonKeys.LANGUAGE)
    private Object getLanguage() {
        final List<String> languages = getLanguages();
        return languages.size() == SINGLE_INSTANCE ? languages.getFirst() : languages;
    }

    /**
     * Used by Jackson's deserialization processes.
     *
     * @param aObject An object to be deserialized
     * @return This resource
     */
    @JsonSetter(JsonKeys.LANGUAGE)
    private AbstractContentResource<T> setLanguage(final Object aObject) {
        if (aObject instanceof final String language) {
            return setLanguages(language);
        }

        if (aObject instanceof final String[] languages) {
            return setLanguages(languages);
        }

        return this;
    }

}
