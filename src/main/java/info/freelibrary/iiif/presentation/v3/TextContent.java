
package info.freelibrary.iiif.presentation.v3;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

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
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Text content that can be associated with an annotation or used as a thumbnail.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.THUMBNAIL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class TextContent extends AbstractContentResource<TextContent>
        implements AnnotationBody<TextContent>, ContentResource<TextContent>, Resource<TextContent> {

    /**
     * Creates a text content resource.
     *
     * @param aID An text content resource ID in string form
     */
    public TextContent(final String aID) {
        super(ResourceTypes.TEXT, aID);
    }

    /**
     * Creates a text content resource.
     */
    private TextContent() {
        super(ResourceTypes.TEXT);
    }

    @Override
    @JsonIgnore
    public TextContent setFormat(final MediaType aMediaType) {
        return (TextContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public TextContent setFormat(final String aMediaType) {
        return (TextContent) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public TextContent setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public TextContent setProviders(final List<Provider> aProviderList) {
        return (TextContent) super.setProviders(aProviderList);
    }

    @Override
    public TextContent clearBehaviors() {
        return (TextContent) super.clearBehaviors();
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public TextContent setBehaviors(final Behavior... aBehaviorArray) {
        return (TextContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public TextContent setBehaviors(final List<Behavior> aBehaviorList) {
        return (TextContent) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    public TextContent addBehaviors(final Behavior... aBehaviorArray) {
        return (TextContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public TextContent addBehaviors(final List<Behavior> aBehaviorList) {
        return (TextContent) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    @Override
    public TextContent setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (TextContent) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public TextContent setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (TextContent) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    @SafeVarargs
    public final TextContent setServices(final Service<?>... aServiceArray) {
        return (TextContent) super.setServices(aServiceArray);
    }

    @Override
    public TextContent setServices(final List<Service<?>> aServiceList) {
        return (TextContent) super.setServices(aServiceList);
    }

    @Override
    public TextContent setPartOfs(final PartOf... aPartOfArray) {
        return (TextContent) super.setPartOfs(aPartOfArray);
    }

    @Override
    public TextContent setPartOfs(final List<PartOf> aPartOfList) {
        return (TextContent) super.setPartOfs(aPartOfList);
    }

    @Override
    public TextContent setRenderings(final Rendering... aRenderingArray) {
        return (TextContent) super.setRenderings(aRenderingArray);
    }

    @Override
    public TextContent setRenderings(final List<Rendering> aRenderingList) {
        return (TextContent) super.setRenderings(aRenderingList);
    }

    @Override
    public TextContent setHomepages(final Homepage... aHomepageArray) {
        return (TextContent) super.setHomepages(aHomepageArray);
    }

    @Override
    public TextContent setHomepages(final List<Homepage> aHomepageList) {
        return (TextContent) super.setHomepages(aHomepageList);
    }

    @Override
    public TextContent setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (TextContent) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public TextContent setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (TextContent) super.setThumbnails(aThumbnailList);
    }

    @Override
    public TextContent setID(final String aID) {
        return (TextContent) super.setID(aID);
    }

    @Override
    public TextContent setRights(final String aRights) {
        return (TextContent) super.setRights(aRights);
    }

    @Override
    public TextContent setRequiredStatement(final RequiredStatement aStatement) {
        return (TextContent) super.setRequiredStatement(aStatement);
    }

    @Override
    public TextContent setSummary(final String aSummary) {
        return (TextContent) super.setSummary(aSummary);
    }

    @Override
    public TextContent setSummary(final Summary aSummary) {
        return (TextContent) super.setSummary(aSummary);
    }

    @Override
    public TextContent setMetadata(final Metadata... aMetadataArray) {
        return (TextContent) super.setMetadata(aMetadataArray);
    }

    @Override
    public TextContent setMetadata(final List<Metadata> aMetadataList) {
        return (TextContent) super.setMetadata(aMetadataList);
    }

    @Override
    public TextContent setLabel(final String aLabel) {
        return (TextContent) super.setLabel(aLabel);
    }

    @Override
    public TextContent setLabel(final Label aLabel) {
        return (TextContent) super.setLabel(aLabel);
    }

}
