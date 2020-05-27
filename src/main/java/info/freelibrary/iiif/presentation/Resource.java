
package info.freelibrary.iiif.presentation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Homepage;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.PartOf;
import info.freelibrary.iiif.presentation.properties.Rendering;
import info.freelibrary.iiif.presentation.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.properties.Rights;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A resource that can be used as a base for more specific IIIF presentation resources.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.TYPE, Constants.ID, Constants.LABEL, Constants.SUMMARY,
    Constants.REQUIRED_STATEMENT, Constants.RIGHTS, Constants.PART_OF, Constants.HOMEPAGE, Constants.LOGO,
    Constants.THUMBNAIL, Constants.METADATA, Constants.ITEMS, Constants.SERVICE })
class Resource<T extends Resource<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Resource.class, Constants.BUNDLE_NAME);

    // We initialize this here so it loads for manifests and collections
    static {
        DatabindCodec.mapper().findAndRegisterModules();
    }

    @JsonProperty(Constants.TYPE)
    protected String myType;

    @JsonProperty(Constants.ID)
    private URI myID;

    private Label myLabel;

    private List<PartOf> myPartOfs;

    @JsonProperty(Constants.METADATA)
    private Metadata myMetadata;

    private Summary mySummary;

    private Thumbnail myThumbnail;

    @JsonProperty(Constants.REQUIRED_STATEMENT)
    private RequiredStatement myRequiredStatement;

    private Rights myRights;

    private List<Homepage> myHomepages;

    private Logo myLogo;

    private List<Rendering> myRenderings;

    private List<Behavior> myBehaviors;

    private List<SeeAlso> mySeeAlsoRefs;

    /**
     * Creates a new resource from the supplied type.
     *
     * @param aType A resource type in string form
     */
    protected Resource(final String aType) {
        myType = checkNotNull(aType);
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aID An ID in string form
     * @param aType A resource type in string form
     */
    protected Resource(final String aType, final String aID) {
        myType = checkNotNull(aType);
        myID = URI.create(aID);
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aID An ID in string form
     * @param aType A resource type in string form
     */
    protected Resource(final String aType, final URI aID) {
        myType = checkNotNull(aType);
        myID = checkNotNull(aID);
    }

    /**
     * Creates a new resource from a supplied ID, type, and label.
     *
     * @param aID A URI in string form
     * @param aType A type of resource in string form
     * @param aLabel A label in string form
     */
    protected Resource(final String aType, final String aID, final String aLabel) {
        myType = checkNotNull(aType);
        myID = URI.create(aID);
        myLabel = new Label(aLabel);
    }

    /**
     * Creates a new resource from a supplied ID and label.
     *
     * @param aID A URI ID (preferably and HTTP based one)
     * @param aType A type of resource in string form
     * @param aLabel A label for the resource
     */
    protected Resource(final String aType, final URI aID, final Label aLabel) {
        myType = checkNotNull(aType);
        myID = checkNotNull(aID);
        myLabel = checkNotNull(aLabel);
    }

    /**
     * Creates a new resource from string values.
     *
     * @param aID An ID in string form
     * @param aType A type in string form
     * @param aLabel A label in string form
     * @param aMetadata A metadata property
     * @param aSummary A summary property in string form
     * @param aThumbnail A thumbnail property
     */
    protected Resource(final String aType, final String aID, final String aLabel, final Metadata aMetadata,
            final String aSummary, final Thumbnail aThumbnail) {
        this(aType, URI.create(aID), new Label(aLabel), aMetadata, new Summary(aSummary), aThumbnail);
    }

    /**
     * Creates a new resource from properties.
     *
     * @param aID An ID
     * @param aType A type in string form
     * @param aLabel A label is required
     * @param aMetadata A metadata property
     * @param aSummary A summary property
     * @param aThumbnail A thumbnail property
     */
    protected Resource(final String aType, final URI aID, final Label aLabel, final Metadata aMetadata,
            final Summary aSummary, final Thumbnail aThumbnail) {
        myType = checkNotNull(aType);
        myID = checkNotNull(aID);
        myLabel = checkNotNull(aLabel);
        myMetadata = checkNotNull(aMetadata);
        mySummary = checkNotNull(aSummary);
        myThumbnail = checkNotNull(aThumbnail);
    }

    /**
     * Gets the label.
     *
     * @return The label
     */
    @JsonUnwrapped
    public Label getLabel() {
        return myLabel;
    }

    /**
     * Sets the label from the supplied string.
     *
     * @param aLabel The string form of the label to set
     * @return The resource
     */
    @JsonIgnore
    public T setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return (T) this;
    }

    /**
     * Sets the label.
     *
     * @param aLabel The label
     * @return The resource
     */
    @JsonSetter(Constants.LABEL)
    public T setLabel(final Label aLabel) {
        myLabel = aLabel;
        return (T) this;
    }

    /**
     * Gets the metadata.
     *
     * @return The metadata
     */
    @JsonGetter(Constants.METADATA)
    public Metadata getMetadata() {
        return myMetadata;
    }

    /**
     * Sets the metadata.
     *
     * @param aMetadata A metadata
     * @return The resource
     */
    @JsonIgnore
    public T setMetadata(final Metadata aMetadata) {
        myMetadata = aMetadata;
        return (T) this;
    }

    /**
     * Gets the summary.
     *
     * @return The summary
     */
    @JsonUnwrapped
    public Summary getSummary() {
        return mySummary;
    }

    /**
     * Sets the summary.
     *
     * @param aSummary A summary in string form
     * @return The resource
     */
    @JsonIgnore
    public T setSummary(final String aSummary) {
        mySummary = new Summary(aSummary);
        return (T) this;
    }

    /**
     * Sets the summary.
     *
     * @param aSummary A summary
     * @return The resource
     */
    public T setSummary(final Summary aSummary) {
        mySummary = aSummary;
        return (T) this;
    }

    /**
     * Gets the thumbnail.
     *
     * @return The thumbnail
     */
    @JsonUnwrapped
    public Thumbnail getThumbnail() {
        return myThumbnail;
    }

    /**
     * Sets the thumbnail.
     *
     * @param aThumbnail A thumbnail
     * @return The resource
     */
    public T setThumbnail(final Thumbnail aThumbnail) {
        myThumbnail = aThumbnail;
        return (T) this;
    }

    /**
     * Sets the thumbnail.
     *
     * @param aURI A thumbnail URI ID in string form
     * @return The resource
     */
    @JsonIgnore
    public T setThumbnail(final String aURI) {
        myThumbnail = new Thumbnail(aURI);
        return (T) this;
    }

    /**
     * Gets the required statement.
     *
     * @return The required statement
     */
    @JsonGetter(Constants.REQUIRED_STATEMENT)
    public RequiredStatement getRequiredStatement() {
        return myRequiredStatement;
    }

    /**
     * Sets the required statement.
     *
     * @param aReqStatement A required statement
     * @return The resource
     */
    @JsonIgnore
    public T setRequiredStatement(final RequiredStatement aReqStatement) {
        myRequiredStatement = aReqStatement;
        return (T) this;
    }

    /**
     * Gets the rights.
     *
     * @return The rights
     */
    @JsonProperty
    public Rights getRights() {
        return myRights;
    }

    /**
     * Sets the rights.
     *
     * @param aRights A rights
     * @return The resource
     */
    @JsonProperty
    public T setRights(final Rights aRights) {
        myRights = aRights;
        return (T) this;
    }

    /**
     * Sets the rights from the supplied string.
     *
     * @param aRightsURL A rights URL in string form
     * @return The resource
     * @throws MalformedURLException If the supplied URL string isn't a valid URL
     */
    @JsonIgnore
    public T setRights(final String aRightsURL) throws MalformedURLException {
        myRights = new Rights(aRightsURL);
        return (T) this;
    }

    /**
     * Sets the homepages for this resource.
     *
     * @param aHomepageArray The homepages to set for this resource
     * @return The resource
     */
    @JsonSetter(Constants.HOMEPAGE)
    public T setHomepages(final Homepage... aHomepageArray) {
        final List<Homepage> homepages = getHomepages();

        homepages.clear();
        homepages.addAll(Arrays.asList(aHomepageArray));
        return (T) this;
    }

    /**
     * Gets a list of resource homepages, initializing the list if this hasn't been done already.
     *
     * @return The resource's homepages
     */
    @JsonGetter(Constants.HOMEPAGE)
    public List<Homepage> getHomepages() {
        if (myHomepages == null) {
            myHomepages = new ArrayList<>();
        }

        return myHomepages;
    }

    /**
     * Gets the logo.
     *
     * @return The logo
     */
    @JsonUnwrapped
    public Logo getLogo() {
        return myLogo;
    }

    /**
     * Sets the logo.
     *
     * @param aLogo A logo
     * @return The resource
     */
    @JsonSetter(Constants.LOGO)
    public T setLogo(final Logo aLogo) {
        myLogo = aLogo;
        return (T) this;
    }

    /**
     * Sets the logo from the supplied string.
     *
     * @param aLogo A logo URI ID in string form
     * @return The resource
     */
    @JsonIgnore
    public T setLogo(final String aLogo) {
        myLogo = new Logo(aLogo);
        return (T) this;
    }

    /**
     * Sets the renderings for this resource.
     *
     * @param aRenderingArray The renderings to set for this resource
     * @return The resource
     */
    @JsonSetter(Constants.RENDERING)
    public T setRenderings(final Rendering... aRenderingArray) {
        final List<Rendering> renderings = getRenderings();

        renderings.clear();
        renderings.addAll(Arrays.asList(aRenderingArray));
        return (T) this;
    }

    /**
     * Gets a list of resource renderings, initializing the list if this hasn't been done already.
     *
     * @return The resource's renderings
     */
    @JsonGetter(Constants.RENDERING)
    public List<Rendering> getRenderings() {
        if (myRenderings == null) {
            myRenderings = new ArrayList<>();
        }

        return myRenderings;
    }

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID from the supplied string.
     *
     * @param aID An URI ID in string form
     * @return The resource
     */
    @JsonIgnore
    public T setID(final String aID) {
        myID = URI.create(aID);
        return (T) this;
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The resource
     */
    @JsonIgnore
    public T setID(final URI aID) {
        myID = aID;
        return (T) this;
    }

    /**
     * Sets the partOfs for this resource.
     *
     * @param aPartOfArray The partOfs to set for this resource
     * @return The resource
     */
    @JsonSetter(Constants.PART_OF)
    public T setPartOfs(final PartOf... aPartOfArray) {
        final List<PartOf> partOfs = getPartOfs();

        partOfs.clear();
        partOfs.addAll(Arrays.asList(aPartOfArray));
        return (T) this;
    }

    /**
     * Gets a list of resource partOfs, initializing the list if this hasn't been done already.
     *
     * @return The resource's partOfs
     */
    @JsonGetter(Constants.PART_OF)
    public List<PartOf> getPartOfs() {
        if (myPartOfs == null) {
            myPartOfs = new ArrayList<>();
        }

        return myPartOfs;
    }

    /**
     * Gets the type.
     *
     * @return The type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return myType;
    }

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
    @JsonSetter(Constants.BEHAVIOR)
    protected T setBehaviors(final Behavior... aBehaviorArray) {
        final List<Behavior> behaviors = getBehaviorsList();

        behaviors.clear();
        behaviors.addAll(Arrays.asList(aBehaviorArray));
        return (T) this;
    }

    /**
     * Gets the resource's behaviors in an unmodifiable list.
     *
     * @return The resource's behaviors
     */
    @JsonGetter(Constants.BEHAVIOR)
    public List<Behavior> getBehaviors() {
        return Collections.unmodifiableList(getBehaviorsList());
    }

    /**
     * Adds behaviors to the resource.
     *
     * @param aBehaviorArray An array of behaviors to add to the resource
     * @return The resource
     */
    protected T addBehaviors(final Behavior... aBehaviorArray) {
        getBehaviorsList().addAll(Arrays.asList(aBehaviorArray));
        return (T) this;
    }

    /**
     * Removes the behaviors associated with this resource.
     *
     * @return The resource
     */
    public T clearBehaviors() {
        getBehaviorsList().clear();
        return (T) this;
    }

    /**
     * Gets see also reference(s).
     *
     * @return The see also reference(s)
     */
    @JsonGetter(Constants.SEE_ALSO)
    public List<SeeAlso> getSeeAlsoRefs() {
        if (mySeeAlsoRefs == null) {
            mySeeAlsoRefs = new ArrayList<>();
        }

        return mySeeAlsoRefs;
    }

    /**
     * Sets see also reference(s).
     *
     * @param aSeeAlsoArray See also reference(s)
     * @return The resource
     */
    @JsonIgnore
    public T setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        Collections.addAll(getSeeAlsoRefs(), aSeeAlsoArray);
        return (T) this;
    }

    /**
     * Sets see also reference(s).
     *
     * @param aSeeAlsoList See also reference(s)
     * @return The resource
     */
    @JsonSetter(Constants.SEE_ALSO)
    public T setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        getSeeAlsoRefs().addAll(aSeeAlsoList);
        return (T) this;
    }

    /**
     * Checks that the supplied behaviors are all implementations of a particular class.
     *
     * @param aClass A class that implements Behavior
     * @param aBehaviorArray An array of behaviors
     */
    protected Behavior[] checkBehaviors(final Class aClass, final Behavior... aBehaviorArray) {
        for (final Behavior behavior : aBehaviorArray) {
            if (!(aClass.isInstance(behavior))) {
                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_031, behavior.getClass()
                        .getSimpleName(), aClass.getSimpleName()));
            }
        }

        return aBehaviorArray;
    }

    /**
     * Gets a list of resource behaviors, initializing the list if this hasn't been done already.
     *
     * @return A list of resource behaviors
     */
    private List<Behavior> getBehaviorsList() {
        if (myBehaviors == null) {
            myBehaviors = new ArrayList<>();
        }

        return myBehaviors;
    }
}
