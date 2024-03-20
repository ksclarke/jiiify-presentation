
package info.freelibrary.iiif.presentation.v3; // NOPMD - ExcessiveImports

import java.io.IOException;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A page of {@link Annotation}(s) that associates different content resources with their respective {@link Canvas}(es).
 * An AnnotationPage may included in the items property of the Canvas (and whose target is that Canvas) or on a Manifest
 * (and whose target is that Manifest).
 */
@SuppressWarnings({ PMD.GOD_CLASS, "PMD.GodClass" })
public class AnnotationPage<A extends Annotation<A>> extends AbstractResource<AnnotationPage<A>>
        implements Resource<AnnotationPage<A>> {

    /** The logger used by the AnnotationPage. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPage.class, MessageCodes.BUNDLE);

    /** Whether the annotation page is intended to be used outside of a manifest. */
    private boolean isExternal;

    /** The AnnotationPage's annotations. */
    private List<A> myAnnotations;

    /** The next annotation page in an {@link AnnotationCollection}. */
    private AnnotationPage<?> myNextAnnotationPage;

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
     * @return The optional annotation page that follows this one
     */
    public Optional<AnnotationPage<?>> getNextPage() {
        return Optional.ofNullable(myNextAnnotationPage);
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

    @Override
    @JsonIgnore
    public AnnotationPage<A> setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public AnnotationPage<A> setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ResourceBehavior.class, this.getClass());
        }

        return (AnnotationPage<A>) super.setBehaviors(aBehaviorList);
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

    @Override
    public AnnotationPage<A> setHomepages(final Homepage... aHomepageArray) {
        return (AnnotationPage<A>) super.setHomepages(aHomepageArray);
    }

    @Override
    public AnnotationPage<A> setHomepages(final List<Homepage> aHomepageList) {
        return (AnnotationPage<A>) super.setHomepages(aHomepageList);
    }

    @Override
    public AnnotationPage<A> setID(final String aID) {
        return (AnnotationPage<A>) super.setID(aID);
    }

    @Override
    public AnnotationPage<A> setLabel(final Label aLabel) {
        return (AnnotationPage<A>) super.setLabel(aLabel);
    }

    @Override
    public AnnotationPage<A> setMetadata(final List<Metadata> aMetadataList) {
        return (AnnotationPage<A>) super.setMetadata(aMetadataList);
    }

    @Override
    public AnnotationPage<A> setMetadata(final Metadata... aMetadataArray) {
        return (AnnotationPage<A>) super.setMetadata(aMetadataArray);
    }

    /**
     * Sets the annotation page that should follow this one in an {@link AnnotationCollection}.
     *
     * @param anAnnotationPage A next annotation page
     * @return This annotation page
     */
    public AnnotationPage<?> setNextPage(final AnnotationPage<?> anAnnotationPage) {
        myNextAnnotationPage = anAnnotationPage;
        return this;
    }

    @Override
    public AnnotationPage<A> setPartOfs(final List<PartOf> aPartOfList) {
        return (AnnotationPage<A>) super.setPartOfs(aPartOfList);
    }

    @Override
    public AnnotationPage<A> setPartOfs(final PartOf... aPartOfArray) {
        return (AnnotationPage<A>) super.setPartOfs(aPartOfArray);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public AnnotationPage<A> setProviders(final List<Provider> aProviderList) {
        return (AnnotationPage<A>) super.setProviders(aProviderList);
    }

    @Override
    @JsonIgnore
    public AnnotationPage<A> setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    public AnnotationPage<A> setRenderings(final List<Rendering> aRenderingList) {
        return (AnnotationPage<A>) super.setRenderings(aRenderingList);
    }

    @Override
    public AnnotationPage<A> setRenderings(final Rendering... aRenderingArray) {
        return (AnnotationPage<A>) super.setRenderings(aRenderingArray);
    }

    @Override
    public AnnotationPage<A> setRequiredStatement(final RequiredStatement aStatement) {
        return (AnnotationPage<A>) super.setRequiredStatement(aStatement);
    }

    @Override
    public AnnotationPage<A> setRights(final String aRights) {
        return (AnnotationPage<A>) super.setRights(aRights);
    }

    @Override
    public AnnotationPage<A> setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (AnnotationPage<A>) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public AnnotationPage<A> setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (AnnotationPage<A>) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public AnnotationPage<A> setServices(final List<Service<?>> aServiceList) {
        return (AnnotationPage<A>) super.setServices(aServiceList);
    }

    @Override
    public AnnotationPage<A> setServices(final Service<?>... aServiceArray) {
        return (AnnotationPage<A>) super.setServices(aServiceArray);
    }

    @Override
    public AnnotationPage<A> setSummary(final Summary aSummary) {
        return (AnnotationPage<A>) super.setSummary(aSummary);
    }

    @Override
    public AnnotationPage<A> setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (AnnotationPage<A>) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public AnnotationPage<A> setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (AnnotationPage<A>) super.setThumbnails(aThumbnailList);
    }

    /**
     * Gets a string representation of the annotation page.
     *
     * @return A string representation of the annotation page
     * @throws JsonParsingException If the annotation page cannot be serialized to valid JSON
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(AnnotationPage.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
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

    /**
     * Returns an annotation from its JSON representation.
     *
     * @param <A> The type of annotation contained in this page
     * @param aJsonString An annotation in JSON form
     * @return The Annotation
     * @throws JsonParsingException If there is trouble parsing the annotation page from the supplied JSON string
     */
    public static <A extends Annotation<A>> AnnotationPage<A> fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(new TypeReference<AnnotationPage<A>>() {}).readValue(aJsonString);
        } catch (final IOException details) {
            throw new JsonParsingException(details);
        }
    }
}
