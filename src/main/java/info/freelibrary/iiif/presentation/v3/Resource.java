
package info.freelibrary.iiif.presentation.v3; // NOPMD

import java.util.List;

import info.freelibrary.util.warnings.PMD;

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
 * An interface that defines methods relevant to all resources.
 *
 * @param <T> The class that implements {@code Resource}
 */
@SuppressWarnings(PMD.EXCESSIVE_PUBLIC_COUNT)
public interface Resource<T extends Resource<T>> {

    /**
     * Gets the resource label.
     *
     * @return The resource's label
     */
    Label getLabel();

    /**
     * Sets the resource label from the supplied string.
     *
     * @param aLabel The string form of the label to set
     * @return The resource
     */
    T setLabel(String aLabel);

    /**
     * Sets the resource label.
     *
     * @param aLabel The resource's label
     * @return The resource
     */
    T setLabel(Label aLabel);

    /**
     * Gets the resource metadata.
     *
     * @return The resource's metadata
     */
    List<Metadata> getMetadata();

    /**
     * Sets the resource metadata.
     *
     * @param aMetadataArray An array of metadata properties
     * @return The resource
     */
    T setMetadata(Metadata... aMetadataArray);

    /**
     * Sets the resource metadata.
     *
     * @param aMetadataList A list of metadata properties
     * @return The resource
     */
    T setMetadata(List<Metadata> aMetadataList);

    /**
     * Gets the resource summary.
     *
     * @return The resource's summary
     */
    Summary getSummary();

    /**
     * Sets the resource summary.
     *
     * @param aSummary A summary in string form
     * @return The resource
     */
    T setSummary(String aSummary);

    /**
     * Sets the resource summary.
     *
     * @param aSummary A resource's summary
     * @return The resource
     */
    T setSummary(Summary aSummary);

    /**
     * Gets a list of resource thumbnails. A thumbnail can be any type of content resource, not just
     * {@link ImageContent}.
     *
     * @return The resource's thumbnails
     */
    List<ContentResource<?>> getThumbnails();

    /**
     * Sets the thumbnails for this resource. A thumbnail can be any type of content resource, not just
     * {@link ImageContent}.
     *
     * @param aThumbnailArray The thumbnails to set for this resource
     * @return The resource
     */
    T setThumbnails(ContentResource<?>... aThumbnailArray);

    /**
     * Sets the thumbnails for this resource. A thumbnail can be any type of content resource, not just
     * {@link ImageContent}.
     *
     * @param aThumbnailList The thumbnails to set for this resource
     * @return The resource
     */
    T setThumbnails(List<ContentResource<?>> aThumbnailList);

    /**
     * Gets the resource's required statement.
     *
     * @return The required statement of the resource
     */
    RequiredStatement getRequiredStatement();

    /**
     * Sets the resource's required statement.
     *
     * @param aStatement A required statement
     * @return The resource
     */
    T setRequiredStatement(RequiredStatement aStatement);

    /**
     * Gets the resource's rights ID.
     *
     * @return The rights ID
     */
    String getRights();

    /**
     * Sets the resource's rights ID from the supplied string.
     *
     * @param aRights A resource's rights ID in string form
     * @return The resource
     */
    T setRights(String aRights);

    /**
     * Gets a list of resource homepages.
     *
     * @return The resource's homepages
     */
    List<Homepage> getHomepages();

    /**
     * Sets the homepages for this resource.
     *
     * @param aHomepageArray The homepages to set for this resource
     * @return The resource
     */
    T setHomepages(Homepage... aHomepageArray);

    /**
     * Sets the homepages for this resource.
     *
     * @param aHomepageList The homepages to set for this resource
     * @return The resource
     */
    T setHomepages(List<Homepage> aHomepageList);

    /**
     * Gets a list of resource providers.
     *
     * @return The resource's providers
     */
    List<Provider> getProviders();

    /**
     * Sets the providers for this resource.
     *
     * @param aProviderArray The providers to set for this resource
     * @return The resource
     */
    T setProviders(Provider... aProviderArray);

    /**
     * Sets the providers for this resource.
     *
     * @param aProviderList The providers to set for this resource
     * @return The resource
     */
    T setProviders(List<Provider> aProviderList);

    /**
     * Gets a list of resource renderings.
     *
     * @return The resource's renderings
     */
    List<Rendering> getRenderings();

    /**
     * Sets the renderings for this resource.
     *
     * @param aRenderingArray The renderings to set for this resource
     * @return The resource
     */
    T setRenderings(Rendering... aRenderingArray);

    /**
     * Sets the renderings for this resource.
     *
     * @param aRenderingList The renderings to set for this resource
     * @return The resource
     */
    T setRenderings(List<Rendering> aRenderingList);

    /**
     * Gets the resource ID.
     *
     * @return The resource's ID
     */
    String getID();

    /**
     * Sets the resource ID from the supplied string.
     *
     * @param aID A resource ID in string form
     * @return The resource
     */
    T setID(String aID);

    /**
     * Gets a list of resource partOfs.
     *
     * @return The resource's partOfs
     */
    List<PartOf> getPartOfs();

    /**
     * Sets the partOfs for this resource.
     *
     * @param aPartOfArray The partOfs to set for this resource
     * @return The resource
     */
    T setPartOfs(PartOf... aPartOfArray);

    /**
     * Sets the partOfs for this resource.
     *
     * @param aPartOfList The partOfs to set for this resource
     * @return The resource
     */
    T setPartOfs(List<PartOf> aPartOfList);

    /**
     * Gets the resource type.
     *
     * @return The resource's type
     */
    String getType();

    /**
     * Gets the resource's behaviors in an unmodifiable list.
     *
     * @return The resource's behaviors
     */
    List<Behavior> getBehaviors();

    /**
     * Sets the behaviors for this resource. Different types of resources allow different types of behaviors. For
     * instance, on a <code>Manifest</code> resource the <code>setBehaviors(Behavior aBehavior)</code> method only
     * allows a ManifestBehavior to be passed. If a CollectionBehavior, for instance, is passed, an
     * <code>IllegalArgumentException</code> will be thrown. Manifests, collections, canvases, and ranges have their own
     * behaviors. Other resources use the <code>ResourceBehavior</code> class.
     *
     * @param aBehaviorArray The behaviors to set for this resource
     * @return The resource
     * @throws IllegalArgumentException If a passed behavior is not appropriate for the type of resource in hand
     */
    T setBehaviors(Behavior... aBehaviorArray);

    /**
     * Sets the behaviors for this resource. Different types of resources allow different types of behaviors. For
     * instance, on a <code>Manifest</code> resource the <code>setBehaviors(List&lt;Behavior&gt; aBehaviorList)</code>
     * method only allows a ManifestBehavior to be passed. If a CollectionBehavior, for instance, is passed, an
     * <code>IllegalArgumentException</code> will be thrown. Manifests, collections, canvases, and ranges have their own
     * behaviors. Other resources use the <code>ResourceBehavior</code> class.
     *
     * @param aBehaviorList The behaviors to set for this resource
     * @return The resource
     * @throws IllegalArgumentException If a passed behavior is not appropriate for the type of resource in hand
     */
    T setBehaviors(List<Behavior> aBehaviorList);

    /**
     * Adds behaviors to the resource.
     *
     * @param aBehaviorArray An array of behaviors to add to the resource
     * @return The resource
     */
    T addBehaviors(Behavior... aBehaviorArray);

    /**
     * Adds behaviors to the resource.
     *
     * @param aBehaviorList A list of behaviors to add to the resource
     * @return The resource
     */
    T addBehaviors(List<Behavior> aBehaviorList);

    /**
     * Removes the behaviors associated with this resource.
     *
     * @return The resource
     */
    T clearBehaviors();

    /**
     * Gets see also reference(s).
     *
     * @return The see also reference(s)
     */
    List<SeeAlso> getSeeAlsoRefs();

    /**
     * Sets see also reference(s).
     *
     * @param aSeeAlsoArray See also reference(s)
     * @return The resource
     */
    T setSeeAlsoRefs(SeeAlso... aSeeAlsoArray);

    /**
     * Sets see also reference(s).
     *
     * @param aSeeAlsoList See also reference(s)
     * @return The resource
     */
    T setSeeAlsoRefs(List<SeeAlso> aSeeAlsoList);

    /**
     * Gets a list of resource services.
     *
     * @return The resource's services
     */
    List<Service<?>> getServices();

    /**
     * Sets the services for this resource.
     *
     * @param aServiceArray The services to set for this resource
     * @return The resource
     */
    T setServices(Service<?>... aServiceArray);

    /**
     * Sets the services for this resource.
     *
     * @param aServiceList The services to set for this resource
     * @return The resource
     */
    T setServices(List<Service<?>> aServiceList);

}
