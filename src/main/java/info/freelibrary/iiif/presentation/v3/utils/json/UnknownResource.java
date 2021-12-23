
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.net.URI;
import java.util.List;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.ContentResource;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.Service;
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

/**
 * A stub resource only used when deserializing from a JSON document.
 *
 * @param <T> A type of a resource whose type is yet to be determined
 */
public class UnknownResource<T extends Resource<T>> implements Resource<T> {

    /**
     * The type of the <code>UnknownResource</code>.
     */
    private final String myType;

    /**
     * The ID of the <code>UnknownResource</code>.
     */
    private URI myID;

    /**
     * The label of the <code>UnknownResource</code>.
     */
    private Label myLabel;

    /**
     * Creates a new stub for a resource whose type is unknown.
     */
    public UnknownResource() {
        myType = ResourceTypes.UNKNOWN;
    }

    @Override
    public URI getID() {
        return myID;
    }

    @Override
    public String getType() {
        return myType;
    }

    @Override
    public Label getLabel() {
        return myLabel;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setID(final String aID) {
        myID = URI.create(aID);
        return (T) this;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setLabel(final Label aLabel) {
        myLabel = aLabel;
        return (T) this;
    }

    @Override
    public List<Metadata> getMetadata() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setMetadata(final Metadata... aMetadataArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setMetadata(final List<Metadata> aMetadataList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Summary getSummary() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setSummary(final String aSummary) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setSummary(final Summary aSummary) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ContentResource<?>> getThumbnails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setThumbnails(final ContentResource<?>... aThumbnailArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequiredStatement getRequiredStatement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setRequiredStatement(final RequiredStatement aStatement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public URI getRights() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setRights(final URI aRights) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setRights(final String aRights) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Homepage> getHomepages() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setHomepages(final Homepage... aHomepageArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setHomepages(final List<Homepage> aHomepageList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Provider> getProviders() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setProviders(final Provider... aProviderArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setProviders(final List<Provider> aProviderList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Rendering> getRenderings() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setRenderings(final Rendering... aRenderingArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setRenderings(final List<Rendering> aRenderingList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setID(final URI aID) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setLabel(final String aLabel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PartOf> getPartOfs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setPartOfs(final PartOf... aPartOfArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setPartOfs(final List<PartOf> aPartOfList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Behavior> getBehaviors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setBehaviors(final Behavior... aBehaviorArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setBehaviors(final List<Behavior> aBehaviorList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T addBehaviors(final Behavior... aBehaviorArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T addBehaviors(final List<Behavior> aBehaviorList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T clearBehaviors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SeeAlso> getSeeAlsoRefs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Service<?>> getServices() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setServices(final Service<?>... aServiceArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T setServices(final List<Service<?>> aServiceList) {
        throw new UnsupportedOperationException();
    }

}
