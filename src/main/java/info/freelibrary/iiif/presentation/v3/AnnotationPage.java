
package info.freelibrary.iiif.presentation.v3; // NOPMD - ExcessiveImports

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.URIs;

/**
 * A collection of {@link Annotation}(s) included in the items property of the Canvas (and whose target is that Canvas).
 */
@SuppressWarnings(PMD.GOD_CLASS)
public class AnnotationPage<T extends Annotation<T>> extends AbstractResource<AnnotationPage<T>> // NOPMD
        implements Resource<AnnotationPage<T>> {

    /**
     * The logger used by the AnnotationPage.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPage.class, MessageCodes.BUNDLE);

    /**
     * The AnnotationPage's annotations.
     */
    private List<T> myAnnotations;

    /**
     * Whether the annotation page is intended to be used outside of a manifest (i.e. whether it needs its own context).
     */
    private boolean isExternal;

    /**
     * Creates a new annotation page.
     *
     * @param aID An annotation page ID in string form
     */
    public AnnotationPage(final String aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID);
    }

    /**
     * Creates a new annotation page.
     *
     * @param aID An annotation page ID
     */
    public AnnotationPage(final URI aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID);
    }

    /**
     * Creates a new annotation page for a supplied {@link CanvasResource}.
     *
     * @param <C> The type of CanvasResource
     * @param aMinter An ID minter
     * @param aCanvas A canvas resource
     */
    public <C extends CanvasResource<C>> AnnotationPage(final Minter aMinter, final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION_PAGE, aMinter.getAnnotationPageID(aCanvas));
    }

    /**
     * Allows Jackson to deserialize JSON.
     */
    private AnnotationPage() {
        super(ResourceTypes.ANNOTATION_PAGE);
    }

    /**
     * Sets the page as external to a manifest; this ensures it is serialized with its own context.
     *
     * @return This annotation page
     */
    @JsonIgnore
    public AnnotationPage<T> setExternalContext() {
        isExternal = true;
        return this;
    }

    /**
     * Removes the external context from an annotation page so that it can be used inside a manifest.
     *
     * @return This annotation page
     */
    public AnnotationPage<T> removeExternalContext() {
        isExternal = false;
        return this;
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public AnnotationPage<T> setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public AnnotationPage<T> setProviders(final List<Provider> aProviderList) {
        return (AnnotationPage<T>) super.setProviders(aProviderList);
    }

    /**
     * Sets the annotation page's annotations.
     *
     * @param aAnnotationArray An annotation array
     * @return The annotation page
     */
    @JsonSetter(JsonKeys.ITEMS)
    @SafeVarargs
    public final AnnotationPage<T> setAnnotations(final T... aAnnotationArray) {
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
    @JsonIgnore
    public final AnnotationPage<T> setAnnotations(final List<T> aAnnotationList) {
        if (myAnnotations != null) {
            myAnnotations.clear();
        }

        return addAnnotations(aAnnotationList);
    }

    /**
     * Adds annotations to the annotation page.
     *
     * @param aAnnotationArray Annotations to be added to the annotation page
     * @return The annotation page
     */
    @SafeVarargs
    public final AnnotationPage<T> addAnnotations(final T... aAnnotationArray) {
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
     */
    public final AnnotationPage<T> addAnnotations(final List<T> aAnnotationList) {
        if (!getAnnotations().addAll(Objects.requireNonNull(aAnnotationList))) {
            final String details = getListIDs(aAnnotationList);
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
    public List<T> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    @Override
    public AnnotationPage<T> clearBehaviors() {
        return (AnnotationPage<T>) super.clearBehaviors();
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public AnnotationPage<T> setBehaviors(final Behavior... aBehaviorArray) {
        return (AnnotationPage<T>) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public AnnotationPage<T> setBehaviors(final List<Behavior> aBehaviorList) {
        return (AnnotationPage<T>) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public AnnotationPage<T> addBehaviors(final Behavior... aBehaviorArray) {
        return (AnnotationPage<T>) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public AnnotationPage<T> addBehaviors(final List<Behavior> aBehaviorList) {
        return (AnnotationPage<T>) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    @Override
    public AnnotationPage<T> setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (AnnotationPage<T>) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public AnnotationPage<T> setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (AnnotationPage<T>) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public AnnotationPage<T> setServices(final Service<?>... aServiceArray) {
        return (AnnotationPage<T>) super.setServices(aServiceArray);
    }

    @Override
    public AnnotationPage<T> setServices(final List<Service<?>> aServiceList) {
        return (AnnotationPage<T>) super.setServices(aServiceList);
    }

    @Override
    public AnnotationPage<T> setPartOfs(final PartOf... aPartOfArray) {
        return (AnnotationPage<T>) super.setPartOfs(aPartOfArray);
    }

    @Override
    public AnnotationPage<T> setPartOfs(final List<PartOf> aPartOfList) {
        return (AnnotationPage<T>) super.setPartOfs(aPartOfList);
    }

    @Override
    public AnnotationPage<T> setRenderings(final Rendering... aRenderingArray) {
        return (AnnotationPage<T>) super.setRenderings(aRenderingArray);
    }

    @Override
    public AnnotationPage<T> setRenderings(final List<Rendering> aRenderingList) {
        return (AnnotationPage<T>) super.setRenderings(aRenderingList);
    }

    @Override
    public AnnotationPage<T> setHomepages(final Homepage... aHomepageArray) {
        return (AnnotationPage<T>) super.setHomepages(aHomepageArray);
    }

    @Override
    public AnnotationPage<T> setHomepages(final List<Homepage> aHomepageList) {
        return (AnnotationPage<T>) super.setHomepages(aHomepageList);
    }

    @Override
    public AnnotationPage<T> setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (AnnotationPage<T>) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public AnnotationPage<T> setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (AnnotationPage<T>) super.setThumbnails(aThumbnailList);
    }

    @Override
    public AnnotationPage<T> setID(final String aID) {
        return (AnnotationPage<T>) super.setID(aID);
    }

    @Override
    public AnnotationPage<T> setID(final URI aID) {
        return (AnnotationPage<T>) super.setID(aID);
    }

    @Override
    public AnnotationPage<T> setRights(final String aRights) {
        return (AnnotationPage<T>) super.setRights(aRights);
    }

    @Override
    public AnnotationPage<T> setRights(final URI aRights) {
        return (AnnotationPage<T>) super.setRights(aRights);
    }

    @Override
    public AnnotationPage<T> setRequiredStatement(final RequiredStatement aStatement) {
        return (AnnotationPage<T>) super.setRequiredStatement(aStatement);
    }

    @Override
    public AnnotationPage<T> setSummary(final String aSummary) {
        return (AnnotationPage<T>) super.setSummary(aSummary);
    }

    @Override
    public AnnotationPage<T> setSummary(final Summary aSummary) {
        return (AnnotationPage<T>) super.setSummary(aSummary);
    }

    @Override
    public AnnotationPage<T> setMetadata(final Metadata... aMetadataArray) {
        return (AnnotationPage<T>) super.setMetadata(aMetadataArray);
    }

    @Override
    public AnnotationPage<T> setMetadata(final List<Metadata> aMetadataList) {
        return (AnnotationPage<T>) super.setMetadata(aMetadataList);
    }

    @Override
    public AnnotationPage<T> setLabel(final String aLabel) {
        return (AnnotationPage<T>) super.setLabel(aLabel);
    }

    @Override
    public AnnotationPage<T> setLabel(final Label aLabel) {
        return (AnnotationPage<T>) super.setLabel(aLabel);
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
     * Returns an annotation from its JSON representation.
     *
     * @param <T> The type of annotation contained in this page
     * @param aJsonString An annotation in JSON form
     * @return The Annotation
     * @throws JsonParsingException If there is trouble parsing the annotation page from the supplied JSON string
     */
    public static <T extends Annotation<T>> AnnotationPage<T> from(final String aJsonString) {
        try {
            return JSON.getReader(new TypeReference<AnnotationPage<T>>() {}).readValue(aJsonString);
        } catch (final IOException details) {
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
        return isExternal ? URIs.CONTEXT_URI : null;
    }

    /**
     * Allows Jackson to set the external context flag.
     *
     * @param aContextURI A annotation page context URI
     * @return This annotation page
     */
    @JsonSetter(JsonKeys.CONTEXT)
    private AnnotationPage<T> setExternalContext(final String aContextURI) {
        if (URIs.CONTEXT_URI.toString().equalsIgnoreCase(aContextURI)) {
            setExternalContext();
        }

        return this;
    }

    /**
     * Get the IDs of the annotations in the supplied list and return them as a single string.
     *
     * @param aAnnotationList A list of annotations
     * @return A string containing the IDs
     */
    private String getListIDs(final List<T> aAnnotationList) {
        final StringBuilder builder = new StringBuilder();

        for (final T annotation : aAnnotationList) {
            builder.append(annotation.getID()).append('|');
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
