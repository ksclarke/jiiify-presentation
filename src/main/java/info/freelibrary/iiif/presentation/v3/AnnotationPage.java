
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A page of {@link Annotation}(s) that associates different content resources with their respective {@link Canvas}(es).
 * An AnnotationPage may included in the items property of the Canvas (and whose target is that Canvas) or on a Manifest
 * (and whose target is that Manifest).
 *
 * @param <A> The type of annotation encapsulated on the page
 */
@SuppressWarnings({ PMD.GOD_CLASS, PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS })
public class AnnotationPage<A extends Annotation<A>> extends AbstractResource<AnnotationPage<A>>
        implements Resource<AnnotationPage<A>> {

    /** The logger used by the AnnotationPage. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPage.class, MessageCodes.BUNDLE);

    /** Whether the annotation page is intended to be used outside of a manifest. */
    private boolean isExternal;

    /** The AnnotationPage's annotations. */
    private List<A> myAnnotations;

    /** The next annotation page in an {@link AnnotationCollection}. */
    private AnnotationPage<? extends Annotation<?>> myNextAnnotationPage;

    /**
     * Creates a new annotation page for a supplied {@link CanvasResource}.
     *
     * @param <C> The type of CanvasResource
     * @param aMinter An ID minter
     * @param aCanvas A parent canvas resource
     */
    public <C extends CanvasResource<C>> AnnotationPage(final Minter aMinter, final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION_PAGE, aMinter.getAnnotationPageID(aCanvas), ResourceBehavior.class);
    }

    /**
     * Creates a new annotation page.
     *
     * @param aID An annotation page ID
     */
    public AnnotationPage(final String aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID, ResourceBehavior.class);
    }

    /**
     * Allows Jackson to deserialize JSON.
     */
    private AnnotationPage() {
        super(ResourceTypes.ANNOTATION_PAGE, ResourceBehavior.class);
    }

    /**
     * Adds annotations to the annotation page.
     *
     * @param aAnnotationArray Annotations to be added to the annotation page
     * @return The annotation page
     * @throws UnsupportedOperationException If the supplied annotations cannot be added to the page
     */
    @SafeVarargs
    public final AnnotationPage<A> addAnnotations(final A... aAnnotationArray) {
        if (!Collections.addAll(getAnnotations(), Objects.requireNonNull(aAnnotationArray))) {
            final String details = getListIDs(Arrays.asList(aAnnotationArray));
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_050, details));
        }

        return this;
    }

    /**
     * Adds annotations to the annotation page.
     *
     * @param aAnnotationList Annotations to be added to the annotation page
     * @return The annotation page
     * @throws UnsupportedOperationException If the supplied annotations cannot be added to the page
     */
    public final AnnotationPage<A> addAnnotations(final List<A> aAnnotationList) {
        if (!getAnnotations().addAll(Objects.requireNonNull(aAnnotationList))) {
            final String details = getListIDs(aAnnotationList);
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_050, details));
        }

        return this;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
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

        if (!myAnnotations.isEmpty()) {
            myAnnotations.get(0).getMotivation();
        }

        return myAnnotations;
    }

    /**
     * Gets the annotation page that should follow this one in an {@link AnnotationCollection}.
     *
     * @param <T> The type of annotation in the returned annotation page
     * @return The optional annotation page that follows this one
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public <T extends Annotation<T>> Optional<AnnotationPage<T>> getNextPage() {
        return Optional.ofNullable((AnnotationPage<T>) myNextAnnotationPage);
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
        return Objects.hash(super.hashCode(), myAnnotations, myNextAnnotationPage);
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

    /**
     * Sets the annotation page's annotations.
     *
     * @param aAnnotationArray An annotation array
     * @return The annotation page
     */
    @JsonIgnore
    @SafeVarargs
    public final AnnotationPage<A> setAnnotations(final A... aAnnotationArray) {
        if (myAnnotations != null) {
            myAnnotations.clear();
        }

        return addAnnotations(aAnnotationArray);
    }

    /**
     * Sets the annotation page's annotations.
     *
     * @param aAnnotationList A list of annotations
     * @return The annotation page
     */
    @JsonSetter(JsonKeys.ITEMS)
    public final AnnotationPage<A> setAnnotations(final List<A> aAnnotationList) {
        if (myAnnotations != null) {
            myAnnotations.clear();
        }

        return addAnnotations(aAnnotationList);
    }

    @Override
    @JsonIgnore
    public AnnotationPage<A> setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
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
     * Sets the annotation page that should follow this one in an {@link AnnotationCollection}.
     *
     * @param anAnnotationPage A next annotation page
     * @param <T> The type of annotation page set as the next one
     * @return This annotation page
     */
    public <T extends Annotation<T>> AnnotationPage<A> setNextPage(final AnnotationPage<T> anAnnotationPage) {
        myNextAnnotationPage = anAnnotationPage;
        return this;
    }

    /**
     * Gets the context when the annotation page is intended to be used outside of a manifest.
     *
     * @return The context URI
     */
    @JsonGetter(JsonKeys.CONTEXT)
    private URI getExternalContext() {
        return isExternal ? PRESENTATION_CONTEXT_URI : null;
    }

    /**
     * Get the IDs of the annotations in the supplied list and return them as a single string.
     *
     * @param aAnnotationList A list of annotations
     * @return A string containing the IDs
     */
    private String getListIDs(final List<A> aAnnotationList) {
        final StringBuilder builder = new StringBuilder();

        for (final A annotation : aAnnotationList) {
            builder.append(annotation.getID()).append('|');
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    /**
     * Allows Jackson to set the external context flag.
     *
     * @param aContextURI A annotation page context URI
     * @return This annotation page
     */
    @JsonSetter(JsonKeys.CONTEXT)
    private AnnotationPage<A> setExternalContext(final String aContextURI) {
        if (PRESENTATION_CONTEXT_URI.toString().equalsIgnoreCase(aContextURI)) {
            setExternalContext();
        }

        return this;
    }
}
