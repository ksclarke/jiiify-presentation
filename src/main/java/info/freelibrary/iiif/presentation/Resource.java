
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.List;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Homepage;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.PartOf;
import info.freelibrary.iiif.presentation.properties.Rendering;
import info.freelibrary.iiif.presentation.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;

/**
 * An interface that defines methods related to all resources.
 *
 * @param <T> The class that implements {@code Resource}
 */
public interface Resource<T extends Resource<T>> {

    /**
     * Gets the label.
     *
     * @return The label
     */
    Label getLabel();

    /**
     * Sets the label from the supplied string.
     *
     * @param aLabel The string form of the label to set
     * @return The resource
     */
    T setLabel(String aLabel);

    /**
     * Sets the label.
     *
     * @param aLabel The label
     * @return The resource
     */
    T setLabel(Label aLabel);

    /**
     * Gets the metadata.
     *
     * @return The metadata
     */
    Metadata getMetadata();

    /**
     * Sets the metadata.
     *
     * @param aMetadata A metadata
     * @return The resource
     */
    T setMetadata(Metadata aMetadata);

    /**
     * Gets the summary.
     *
     * @return The summary
     */
    Summary getSummary();

    /**
     * Sets the summary.
     *
     * @param aSummary A summary in string form
     * @return The resource
     */
    T setSummary(String aSummary);

    /**
     * Sets the summary.
     *
     * @param aSummary A summary
     * @return The resource
     */
    T setSummary(Summary aSummary);

    /**
     * Gets a list of resource thumbnails, initializing the list if this hasn't been done already.
     *
     * @return The resource's thumbnails
     */
    List<Thumbnail> getThumbnails();

    /**
     * Sets the thumbnails for this resource.
     *
     * @param aThumbnailArray The thumbnails to set for this resource
     * @return The resource
     */
    T setThumbnails(Thumbnail... aThumbnailArray);

    /**
     * Sets the thumbnails for this resource.
     *
     * @param aThumbnailList The thumbnails to set for this resource
     * @return The resource
     */
    T setThumbnails(List<Thumbnail> aThumbnailList);

    /**
     * Gets the required statement.
     *
     * @return The required statement
     */
    RequiredStatement getRequiredStatement();

    /**
     * Sets the required statement.
     *
     * @param aStatement A required statement
     * @return The resource
     */
    T setRequiredStatement(RequiredStatement aStatement);

    /**
     * Gets the rights.
     *
     * @return The rights
     */
    List<URI> getRights();

    /**
     * Sets the rights.
     *
     * @param aRightsList A list of rights URIs
     * @return The resource
     */
    T setRights(List<URI> aRightsList);

    /**
     * Sets the rights from the supplied strings.
     *
     * @param aRightsArray Rights URIs in string form
     * @return The resource
     */
    T setRights(String... aRightsArray);

    /**
     * Sets the rights from the supplied strings.
     *
     * @param aRightsArray Rights URIs
     * @return The resource
     */
    T setRights(URI... aRightsArray);

    /**
     * Gets a list of resource homepages, initializing the list if this hasn't been done already.
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
     * Gets the logo.
     *
     * @return The logo
     */
    Logo getLogo();

    /**
     * Sets the logo.
     *
     * @param aLogo A logo
     * @return The resource
     */
    T setLogo(Logo aLogo);

    /**
     * Sets the logo from the supplied string.
     *
     * @param aLogo A logo URI ID in string form
     * @return The resource
     */
    T setLogo(String aLogo);

    /**
     * Gets a list of resource renderings, initializing the list if this hasn't been done already.
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
     * Gets the ID.
     *
     * @return The ID
     */
    URI getID();

    /**
     * Sets the ID from the supplied string.
     *
     * @param aID An URI ID in string form
     * @return The resource
     */
    T setID(String aID);

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The resource
     */
    T setID(URI aID);

    /**
     * Gets a list of resource partOfs, initializing the list if this hasn't been done already.
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
     * Gets the type.
     *
     * @return The type
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
     * <code>IllegalArgumentException</code> will be thrown. Manifests, collections, canvases, and ranges have their
     * own behaviors. Other resources use the <code>ResourceBehavior</code> class.
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
     * <code>IllegalArgumentException</code> will be thrown. Manifests, collections, canvases, and ranges have their
     * own behaviors. Other resources use the <code>ResourceBehavior</code> class.
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

}
