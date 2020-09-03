
package info.freelibrary.iiif.presentation.v3;

import static com.google.common.base.Preconditions.*;

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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
import info.freelibrary.iiif.presentation.v3.properties.behaviors.DisjointChecker;
import info.freelibrary.iiif.presentation.v3.services.Service;
import info.freelibrary.iiif.presentation.v3.services.ServiceDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A resource that can be used as a base for more specific IIIF presentation resources.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.TYPE, Constants.ID, Constants.LABEL, Constants.SUMMARY,
    Constants.REQUIRED_STATEMENT, Constants.PROVIDER, Constants.RIGHTS, Constants.PART_OF, Constants.HOMEPAGE,
    Constants.THUMBNAIL, Constants.METADATA, Constants.ITEMS, Constants.SERVICE })
abstract class AbstractResource<T extends AbstractResource<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class, MessageCodes.BUNDLE);

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

    private List<Metadata> myMetadata;

    private Summary mySummary;

    private List<Provider> myProviders;

    @JsonProperty(Constants.THUMBNAIL)
    @JsonDeserialize(contentUsing = ThumbnailDeserializer.class)
    private List<Thumbnail> myThumbnails;

    private RequiredStatement myRequiredStatement;

    private URI myRights;

    private List<Homepage> myHomepages;

    private List<Rendering> myRenderings;

    private List<Behavior> myBehaviors;

    private List<SeeAlso> mySeeAlsoRefs;

    @JsonDeserialize(contentUsing = ServiceDeserializer.class)
    private List<Service> myServices;

    /**
     * Creates a new resource from the supplied type.
     *
     * @param aType A resource type
     */
    protected AbstractResource(final String aType) {
        myType = checkNotNull(aType);
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aType A resource type
     * @param aID A URI ID in string form
     */
    protected AbstractResource(final String aType, final String aID) {
        myType = checkNotNull(aType);
        myID = URI.create(aID);
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aType A resource type
     * @param aID A URI ID
     */
    protected AbstractResource(final String aType, final URI aID) {
        myType = checkNotNull(aType);
        myID = checkNotNull(aID);
    }

    /**
     * Creates a new resource from a supplied ID, type, and label.
     *
     * @param aType A type of resource
     * @param aID A URI ID in string form
     * @param aLabel A label in string form
     */
    protected AbstractResource(final String aType, final String aID, final String aLabel) {
        myType = checkNotNull(aType);
        myID = URI.create(aID);
        myLabel = new Label(aLabel);
    }

    /**
     * Creates a new resource from a supplied ID and label.
     *
     * @param aType A type of resource
     * @param aID A URI ID
     * @param aLabel A label for the resource
     */
    protected AbstractResource(final String aType, final URI aID, final Label aLabel) {
        myType = checkNotNull(aType);
        myID = checkNotNull(aID);
        myLabel = checkNotNull(aLabel);
    }

    /**
     * Creates a new resource from string values.
     *
     * @param aType A type in string form
     * @param aID An ID in string form
     * @param aLabel A label in string form
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary property in string form
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    protected AbstractResource(final String aType, final String aID, final String aLabel,
            final List<Metadata> aMetadataList, final String aSummary, final Thumbnail aThumbnail,
            final Provider aProvider) {
        this(aType, URI.create(aID), new Label(aLabel), aMetadataList, new Summary(aSummary), aThumbnail, aProvider);
    }

    /**
     * Creates a new resource from properties.
     *
     * @param aType A resource type
     * @param aID A URI ID
     * @param aLabel A label
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary property
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    protected AbstractResource(final String aType, final URI aID, final Label aLabel,
            final List<Metadata> aMetadataList, final Summary aSummary, final Thumbnail aThumbnail,
            final Provider aProvider) {
        myType = checkNotNull(aType);
        myID = checkNotNull(aID);
        myLabel = checkNotNull(aLabel);
        myMetadata = checkNotNull(aMetadataList);
        mySummary = checkNotNull(aSummary);
        setThumbnails(checkNotNull(aThumbnail));
        setProviders(checkNotNull(aProvider));
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

    @JsonIgnore
    protected AbstractResource<T> setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
    }

    @JsonSetter(Constants.LABEL)
    protected AbstractResource<T> setLabel(final Label aLabel) {
        checkNotNull(aLabel);
        myLabel = aLabel;
        return this;
    }

    /**
     * Gets the metadata.
     *
     * @return The metadata
     */
    @JsonGetter(Constants.METADATA)
    public List<Metadata> getMetadata() {
        if (myMetadata == null) {
            myMetadata = new ArrayList<>();
        }

        return myMetadata;
    }

    @JsonSetter(Constants.METADATA)
    protected AbstractResource<T> setMetadata(final Metadata... aMetadataArray) {
        return setMetadata(Arrays.asList(aMetadataArray));
    }

    @JsonIgnore
    protected AbstractResource<T> setMetadata(final List<Metadata> aMetadataList) {
        final List<Metadata> metadata = getMetadata();

        checkNotNull(aMetadataList);
        metadata.clear();
        metadata.addAll(aMetadataList);

        return this;
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

    @JsonIgnore
    protected AbstractResource<T> setSummary(final String aSummary) {
        mySummary = new Summary(aSummary);
        return this;
    }

    protected AbstractResource<T> setSummary(final Summary aSummary) {
        checkNotNull(aSummary);
        mySummary = aSummary;
        return this;
    }

    /**
     * Gets a list of resource thumbnails, initializing the list if this hasn't been done already.
     *
     * @return The resource's thumbnails
     */
    @JsonGetter(Constants.THUMBNAIL)
    public List<Thumbnail> getThumbnails() {
        if (myThumbnails == null) {
            myThumbnails = new ArrayList<>();
        }

        return myThumbnails;
    }

    @JsonSetter(Constants.THUMBNAIL)
    protected AbstractResource<T> setThumbnails(final Thumbnail... aThumbnailArray) {
        return setThumbnails(Arrays.asList(aThumbnailArray));
    }

    @JsonIgnore
    protected AbstractResource<T> setThumbnails(final List<Thumbnail> aThumbnailList) {
        final List<Thumbnail> thumbnails = getThumbnails();

        checkNotNull(aThumbnailList);
        thumbnails.clear();
        thumbnails.addAll(aThumbnailList);

        return this;
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

    @JsonSetter(Constants.REQUIRED_STATEMENT)
    protected AbstractResource<T> setRequiredStatement(final RequiredStatement aStatement) {
        checkNotNull(aStatement);
        myRequiredStatement = aStatement;
        return this;
    }

    /**
     * Gets the rights.
     *
     * @return The rights
     */
    @JsonProperty
    public URI getRights() {
        return myRights;
    }

    @JsonProperty
    protected AbstractResource<T> setRights(final URI aRights) {
        checkNotNull(aRights);
        myRights = aRights;
        return this;
    }

    @JsonIgnore
    protected AbstractResource<T> setRights(final String aRights) {
        return setRights(URI.create(aRights));
    }

    @JsonSetter(Constants.HOMEPAGE)
    protected AbstractResource<T> setHomepages(final Homepage... aHomepageArray) {
        return setHomepages(Arrays.asList(aHomepageArray));
    }

    @JsonIgnore
    protected AbstractResource<T> setHomepages(final List<Homepage> aHomepageList) {
        final List<Homepage> homepages = getHomepages();

        checkNotNull(aHomepageList);
        homepages.clear();
        homepages.addAll(aHomepageList);

        return this;
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

    @JsonSetter(Constants.PROVIDER)
    protected AbstractResource<T> setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @JsonIgnore
    protected AbstractResource<T> setProviders(final List<Provider> aProviderList) {
        final List<Provider> providers = getProviders();

        checkNotNull(aProviderList);
        providers.clear();
        providers.addAll(aProviderList);

        return this;
    }

    /**
     * Gets a list of resource providers, initializing the list if this hasn't been done already.
     *
     * @return The resource's providers
     */
    @JsonGetter(Constants.PROVIDER)
    public List<Provider> getProviders() {
        if (myProviders == null) {
            myProviders = new ArrayList<>();
        }

        return myProviders;
    }

    @JsonSetter(Constants.RENDERING)
    protected AbstractResource<T> setRenderings(final Rendering... aRenderingArray) {
        return setRenderings(Arrays.asList(aRenderingArray));
    }

    @JsonIgnore
    protected AbstractResource<T> setRenderings(final List<Rendering> aRenderingList) {
        final List<Rendering> renderings = getRenderings();

        checkNotNull(renderings);
        renderings.clear();
        renderings.addAll(aRenderingList);

        return this;
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

    @JsonIgnore
    protected AbstractResource<T> setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    @JsonIgnore
    protected AbstractResource<T> setID(final URI aID) {
        checkNotNull(aID);
        myID = aID;
        return this;
    }

    @JsonSetter(Constants.PART_OF)
    protected AbstractResource<T> setPartOfs(final PartOf... aPartOfArray) {
        return setPartOfs(Arrays.asList(aPartOfArray));
    }

    @JsonIgnore
    protected AbstractResource<T> setPartOfs(final List<PartOf> aPartOfList) {
        final List<PartOf> partOfs = getPartOfs();

        checkNotNull(aPartOfList);
        partOfs.clear();
        partOfs.addAll(aPartOfList);

        return this;
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

    @JsonSetter(Constants.BEHAVIOR)
    protected AbstractResource<T> setBehaviors(final Behavior... aBehaviorArray) {
        final List<Behavior> behaviors = getBehaviorsList();

        checkNotNull(aBehaviorArray);
        behaviors.clear();
        behaviors.addAll(Arrays.asList(aBehaviorArray));

        return this;
    }

    @JsonIgnore
    protected AbstractResource<T> setBehaviors(final List<Behavior> aBehaviorList) {
        // We implement this so it can be overridden, but it should never actually be called because all behavior
        // setting should go through checkBehaviors() first, which returns an array for setBehaviors(Behavior...)
        throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_057));
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

    protected AbstractResource<T> addBehaviors(final Behavior... aBehaviorArray) {
        getBehaviorsList().addAll(Arrays.asList(aBehaviorArray));
        return this;
    }

    protected AbstractResource<T> addBehaviors(final List<Behavior> aBehaviorList) {
        // We implement this so it can be overridden, but it should never actually be called because all behavior
        // adding should go through checkBehaviors() first, which returns an array for addBehaviors(Behavior...)
        throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_058));
    }

    protected AbstractResource<T> clearBehaviors() {
        getBehaviorsList().clear();
        return this;
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

    @JsonIgnore
    protected AbstractResource<T> setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        Collections.addAll(getSeeAlsoRefs(), aSeeAlsoArray);
        return this;
    }

    @JsonSetter(Constants.SEE_ALSO)
    protected AbstractResource<T> setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        getSeeAlsoRefs().addAll(aSeeAlsoList);
        return this;
    }

    /**
     * Gets a list of resource services, initializing the list if this hasn't been done already.
     *
     * @return The resource's services
     */
    @JsonGetter(Constants.SERVICE)
    public List<Service> getServices() {
        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        return myServices;
    }

    @JsonIgnore
    protected AbstractResource<T> setServices(final Service... aServiceArray) {
        return setServices(Arrays.asList(aServiceArray));
    }

    @JsonSetter(Constants.SERVICE)
    protected AbstractResource<T> setServices(final List<Service> aServiceList) {
        final List<Service> services = getServices();

        checkNotNull(aServiceList);
        services.clear();
        services.addAll(aServiceList);

        return this;
    }

    /**
     * Checks that the supplied behaviors are all implementations of a particular class.
     *
     * @param aClass A class that implements Behavior
     * @param aCleanComparison If the comparison does not check pre-existing behaviors
     * @param aBehaviorList A list of behaviors
     */
    protected Behavior[] checkBehaviors(final Class<?> aClass, final boolean aCleanComparison,
            final List<Behavior> aBehaviorList) {
        return checkBehaviors(aClass, aCleanComparison, aBehaviorList.toArray(new Behavior[0]));
    }

    /**
     * Checks that the supplied behaviors are all implementations of a particular class.
     *
     * @param aClass A class that implements Behavior
     * @param aCleanComparison If the comparison does not check pre-existing behaviors
     * @param aBehaviorArray An array of behaviors
     */
    protected Behavior[] checkBehaviors(final Class<?> aClass, final boolean aCleanComparison,
            final Behavior... aBehaviorArray) {
        if (aCleanComparison) {
            new DisjointChecker().check(aClass, aBehaviorArray);
        } else {
            new DisjointChecker(getBehaviorsList()).check(aClass, aBehaviorArray);
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
