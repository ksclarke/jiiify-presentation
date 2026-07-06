
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.AnnotationPageSerializer;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A page of {@link Annotation}(s) that associates different content resources with their respective {@link Canvas}(es).
 * An AnnotationPage may be included in the `items` property of the Canvas (and whose target is that Canvas) or on a
 * Manifest (and whose target is that Manifest).
 *
 * @param <A> The type of annotation encapsulated on the page
 */
@JsonInclude(Include.NON_EMPTY)
public class AnnotationPage<A extends Annotation<A>> extends AbstractResource<AnnotationPage<A>> {

    /** The logger used by the AnnotationPage. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPage.class, MessageCodes.BUNDLE);

    /** Whether the annotation page is intended to be used outside a manifest. */
    private boolean isExternal;

    /** The AnnotationPage's annotations. */
    private List<A> myAnnotations;

    /** The next annotation page in an {@link AnnotationCollection}. */
    @JsonProperty(JsonKeys.NEXT)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = AnnotationPageSerializer.class)
    private AnnotationPage<? extends Annotation<?>> myNextAnnotationPage;

    /** The previous annotation page in an {@link AnnotationCollection}. */
    @JsonProperty(JsonKeys.PREV)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = AnnotationPageSerializer.class)
    private AnnotationPage<? extends Annotation<?>> myPreviousAnnotationPage;

    /**
     * Creates a new annotation page for a supplied {@link CanvasResource}.
     *
     * @param <C> The type of CanvasResource
     * @param aMinter An ID minter
     * @param aCanvas A parent canvas resource
     */
    public <C extends CanvasResource<C>> AnnotationPage(final Minter aMinter, final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION_PAGE, aMinter.getAnnotationPageID(aCanvas), true, ResourceBehavior.class);
    }

    /**
     * Creates a new annotation page.
     *
     * @param aID An annotation page ID
     */
    public AnnotationPage(final String aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID, true, ResourceBehavior.class);
    }

    /**
     * Allows Jackson to deserialize JSON.
     */
    private AnnotationPage() {
        super(ResourceTypes.ANNOTATION_PAGE, ResourceBehavior.class);
    }

    /**
     * Creates a new annotation page from an existing one. Note that because this is a copy, the linked annotation pages
     * are also copied.
     *
     * @param aAnnotationPage The annotation page to copy
     */
    public AnnotationPage(final AnnotationPage<A> aAnnotationPage) {
        this();

        aAnnotationPage.copyTo(this);

        if (aAnnotationPage.isExternal) {
            isExternal = true;
        }

        if (aAnnotationPage.myNextAnnotationPage != null) {
            myNextAnnotationPage = new AnnotationPage<>(aAnnotationPage.myNextAnnotationPage);
        }

        if (aAnnotationPage.myPreviousAnnotationPage != null) {
            myPreviousAnnotationPage = new AnnotationPage<>(aAnnotationPage.myPreviousAnnotationPage);
        }

        if (aAnnotationPage.myAnnotations != null) {
            myAnnotations = aAnnotationPage.myAnnotations.stream().map(Annotation::copy)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    /**
     * Creates a copy of this annotation page.
     *
     * @return A copy of this annotation page
     */
    @Override
    public AnnotationPage<A> copy() {
        return new AnnotationPage<>(this);
    }

    @Override
    public boolean equals(final Object aObject) {
        final AnnotationPage<?> other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (AnnotationPage<?>) aObject;

        return Objects.equals(myAnnotations, other.myAnnotations) &&
                Objects.equals(myNextAnnotationPage, other.myNextAnnotationPage) && super.equals(other);
    }

    /**
     * Gets the annotation page's annotations.
     *
     * @return The annotation page's annotations
     */
    @JsonGetter(JsonKeys.ITEMS)
    public List<A> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    /**
     * Sets the annotation page's annotations.
     *
     * @param aAnnotationArray An annotation array
     * @return The annotation page
     */
    @JsonIgnore
    @SafeVarargs
    public final AnnotationPage<A> setAnnotations(final A... aAnnotationArray) {
        return setAnnotations(new ArrayList<>(Arrays.asList(aAnnotationArray))); // we want a mutable list
    }

    /**
     * Sets the annotation page's annotations.
     *
     * @param aAnnotationList A list of annotations
     * @return The annotation page
     */
    @JsonSetter(JsonKeys.ITEMS)
    public final AnnotationPage<A> setAnnotations(final List<A> aAnnotationList) {
        Objects.requireNonNull(aAnnotationList, LOGGER.getMessage(MessageCodes.JPA_200));

        for (final A annotation : aAnnotationList) {
            Objects.requireNonNull(annotation, LOGGER.getMessage(MessageCodes.JPA_201));
        }

        myAnnotations = aAnnotationList;
        return this;
    }

    /**
     * Gets the annotation page that should follow this one in an {@link AnnotationCollection}.
     *
     * @param <T> The type of annotation in the returned annotation page
     * @return The optional annotation page that follows this one
     */
    @JsonIgnore
    @SuppressWarnings({ JDK.UNCHECKED })
    public <T extends Annotation<T>> Optional<AnnotationPage<T>> getNextPage() {
        return Optional.ofNullable((AnnotationPage<T>) myNextAnnotationPage);
    }

    /**
     * Sets the annotation page that should follow this one in an {@link AnnotationCollection}.
     *
     * @param anAnnotationPage A next annotation page
     * @param <T> The type of annotation page set as the next one
     * @return This annotation page
     */
    @JsonSetter(JsonKeys.NEXT)
    public <T extends Annotation<T>> AnnotationPage<A> setNextPage(final AnnotationPage<T> anAnnotationPage) {
        myNextAnnotationPage = anAnnotationPage;
        return this;
    }

    /**
     * Gets the annotation page that should precede this one in an {@link AnnotationCollection}.
     *
     * @param <T> The type of annotation in the returned annotation page
     * @return The optional annotation page that follows this one
     */
    @JsonIgnore
    @SuppressWarnings({ JDK.UNCHECKED })
    public <T extends Annotation<T>> Optional<AnnotationPage<T>> getPrevPage() {
        return Optional.ofNullable((AnnotationPage<T>) myPreviousAnnotationPage);
    }

    /**
     * Sets the annotation page that should precede this one in an {@link AnnotationCollection}.
     *
     * @param anAnnotationPage A previous annotation page
     * @param <T> The type of annotation page set as the previous one
     * @return This annotation page
     */
    @JsonSetter(JsonKeys.PREV)
    public <T extends Annotation<T>> AnnotationPage<A> setPrevPage(final AnnotationPage<T> anAnnotationPage) {
        myPreviousAnnotationPage = anAnnotationPage;
        return this;
    }

    /**
     * Gets whether this page has an external context.
     *
     * @return True if the page has external context
     */
    public boolean hasExternalContext() {
        return isExternal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myAnnotations, myNextAnnotationPage, myPreviousAnnotationPage);
    }

    /**
     * Removes the external context from an annotation page so that it can be used inside a manifest.
     *
     * @return This annotation page
     */
    public AnnotationPage<A> removeExternalContext() {
        isExternal = false;
        return this;
    }

    @Override
    @JsonIgnore
    public AnnotationPage<A> setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public AnnotationPage<A> setBehaviors(final List<Behavior> aBehaviorList) {
        final AnnotationPage<A> page;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            page = super.setBehaviors(behaviorList);
        } else {
            page = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return page;
    }

    /**
     * Sets the page as external to a manifest; this ensures it is serialized with its own context.
     *
     * @return This annotation page
     */
    @JsonIgnore
    public AnnotationPage<A> setExternalContext() {
        isExternal = true;
        return this;
    }

    /**
     * Gets the context only when the annotation page is intended to be used outside a manifest, as indicated by using
     * {@code AnnotationPage#setExternalContext()}.
     *
     * @return The context URI
     */
    @JsonGetter(JsonKeys.CONTEXT)
    private Optional<URI> getExternalContext() {
        return isExternal ? Optional.of(ContextList.PRESENTATION_CONTEXT_URI) : Optional.empty();
    }

    /**
     * Allows Jackson to set the external context flag.
     *
     * @param aContextURI An annotation page context URI
     * @return This annotation page
     */
    @JsonSetter(JsonKeys.CONTEXT)
    private AnnotationPage<A> setExternalContext(final String aContextURI) {
        if (ContextList.PRESENTATION_CONTEXT_URI.toString().equalsIgnoreCase(aContextURI)) {
            setExternalContext();
        }

        return this;
    }
}
