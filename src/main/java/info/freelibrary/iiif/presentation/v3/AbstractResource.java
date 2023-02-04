
package info.freelibrary.iiif.presentation.v3; // NOPMD

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
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
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.BehaviorsDeserializer;

/**
 * A resource that can be used as a base for more specific IIIF presentation resources.
 */
@SuppressWarnings({ PMD.EXCESSIVE_IMPORTS, "PMD.ExcessiveImports", PMD.ABSTRACT_CLASS_WITHOUT_ABSTRACT_METHOD,
    "PMD.AbstractClassWithoutAbstractMethod", PMD.GOD_CLASS, "PMD.GodClass", "PMD.TooManyFields" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.PROVIDER, JsonKeys.PART_OF,
    JsonKeys.BEHAVIOR, JsonKeys.HOMEPAGE, JsonKeys.THUMBNAIL, JsonKeys.SUMMARY, JsonKeys.METADATA, JsonKeys.START,
    JsonKeys.RIGHTS, JsonKeys.REQUIRED_STATEMENT, JsonKeys.VIEWING_DIRECTION, JsonKeys.RENDERING, JsonKeys.SEE_ALSO,
    JsonKeys.ITEMS, JsonKeys.SERVICE, JsonKeys.STRUCTURES, JsonKeys.SERVICES, JsonKeys.NAV_DATE, JsonKeys.ANNOTATIONS })
abstract class AbstractResource<T extends AbstractResource<T>> {

    /** The IIIF Presentation context URI. */
    protected static final URI PRESENTATION_CONTEXT_URI = URI.create("http://iiif.io/api/presentation/3/context.json");

    /** The logger used by abstract resources. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class, MessageCodes.BUNDLE);

    /** The resource type. */
    @JsonProperty(JsonKeys.TYPE)
    protected String myType;

    /** The resource's behaviors. */
    @JsonProperty(JsonKeys.BEHAVIOR)
    @JsonDeserialize(using = BehaviorsDeserializer.class)
    private List<Behavior> myBehaviors;

    /** The resource's homepage. */
    private List<Homepage> myHomepages;

    /** The resource ID. */
    @JsonProperty(JsonKeys.ID)
    private String myID;

    /** The resource label. */
    private Label myLabel;

    /** The resource's metadata. */
    private List<Metadata> myMetadata;

    /** The resource's partOfs. */
    private List<PartOf> myPartOfs;

    /** The resource's providers. */
    private List<Provider> myProviders;

    /** The resource renderings. */
    private List<Rendering> myRenderings;

    /** The resource's requiredStatement. */
    private RequiredStatement myRequiredStatement;

    /** The rights ID of the resource. */
    private String myRights;

    /** The resource's seeAlsos. */
    private List<SeeAlso> mySeeAlsoRefs;

    /** The resource's services. */
    @JsonDeserialize(contentUsing = ServiceDeserializer.class)
    private List<Service<?>> myServices;

    /** The resource summary. */
    private Summary mySummary;

    /** The resource's thumbnails. */
    @JsonProperty(JsonKeys.THUMBNAIL)
    @JsonDeserialize(contentUsing = ContentResourceDeserializer.class)
    private List<ContentResource<?>> myThumbnails;

    /** A class of behaviors supported by this resource. */
    private final Class<? extends Behavior> myBehaviorClass;

    /**
     * Creates a new resource from the supplied type.
     *
     * @param aType A resource type
     * @param aBehaviorClass A behavior class for this resource
     */
    protected AbstractResource(final String aType, final Class<? extends Behavior> aBehaviorClass) {
        myBehaviorClass = Objects.requireNonNull(aBehaviorClass);
        myType = Objects.requireNonNull(aType);
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aType A resource type
     * @param aID A URI ID in string form
     * @param aBehaviorClass A behavior class for this resource
     */
    protected AbstractResource(final String aType, final String aID, final Class<? extends Behavior> aBehaviorClass) {
        myBehaviorClass = Objects.requireNonNull(aBehaviorClass);
        myType = Objects.requireNonNull(aType);
        myID = UriUtils.checkID(aID, true);
    }

    /**
     * Creates a new resource from a supplied ID and label.
     *
     * @param aType A type of resource
     * @param aID An ID in string form
     * @param aLabel A label for the resource
     * @param aBehaviorClass A behavior class for this resource
     */
    protected AbstractResource(final String aType, final String aID, final Label aLabel,
            final Class<? extends Behavior> aBehaviorClass) {
        myBehaviorClass = Objects.requireNonNull(aBehaviorClass);
        myType = Objects.requireNonNull(aType);
        myID = UriUtils.checkID(aID, true);
        myLabel = Objects.requireNonNull(aLabel);
    }

    /**
     * Gets the resource's behaviors in an unmodifiable list.
     *
     * @return The resource's behaviors
     */
    @JsonGetter(JsonKeys.BEHAVIOR)
    public List<Behavior> getBehaviors() {
        if (myBehaviors == null) {
            myBehaviors = new BehaviorList(myBehaviorClass);
        }

        return myBehaviors;
    }

    /**
     * Gets a list of resource homepages, initializing the list if this hasn't been done already.
     *
     * @return The resource's homepages
     */
    @JsonGetter(JsonKeys.HOMEPAGE)
    public List<Homepage> getHomepages() {
        if (myHomepages == null) {
            myHomepages = new ArrayList<>();
        }

        return myHomepages;
    }

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return myID;
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
     * Gets the metadata.
     *
     * @return The metadata
     */
    @JsonGetter(JsonKeys.METADATA)
    public List<Metadata> getMetadata() {
        if (myMetadata == null) {
            myMetadata = new ArrayList<>();
        }

        return myMetadata;
    }

    /**
     * Gets a list of resource partOfs, initializing the list if this hasn't been done already.
     *
     * @return The resource's partOfs
     */
    @JsonGetter(JsonKeys.PART_OF)
    public List<PartOf> getPartOfs() {
        if (myPartOfs == null) {
            myPartOfs = new ArrayList<>();
        }

        return myPartOfs;
    }

    /**
     * Gets a list of resource providers, initializing the list if this hasn't been done already.
     *
     * @return The resource's providers
     */
    @JsonGetter(JsonKeys.PROVIDER)
    public List<Provider> getProviders() {
        return getResourceProviders();
    }

    /**
     * Gets a list of resource renderings, initializing the list if this hasn't been done already.
     *
     * @return The resource's renderings
     */
    @JsonGetter(JsonKeys.RENDERING)
    public List<Rendering> getRenderings() {
        if (myRenderings == null) {
            myRenderings = new ArrayList<>();
        }

        return myRenderings;
    }

    /**
     * Gets the required statement.
     *
     * @return The required statement
     */
    @JsonGetter(JsonKeys.REQUIRED_STATEMENT)
    public RequiredStatement getRequiredStatement() {
        return myRequiredStatement;
    }

    /**
     * Gets the rights.
     *
     * @return The rights
     */
    @JsonProperty
    public String getRights() {
        return myRights;
    }

    /**
     * Gets see also reference(s).
     *
     * @return The see also reference(s)
     */
    @JsonGetter(JsonKeys.SEE_ALSO)
    public List<SeeAlso> getSeeAlsoRefs() {
        if (mySeeAlsoRefs == null) {
            mySeeAlsoRefs = new ArrayList<>();
        }

        return mySeeAlsoRefs;
    }

    /**
     * Gets a list of resource services, initializing the list if this hasn't been done already.
     *
     * @return The resource's services
     */
    @JsonGetter(JsonKeys.SERVICE)
    public List<Service<?>> getServices() {
        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        return myServices;
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
     * Gets a list of resource thumbnails, initializing the list if this hasn't been done already.
     *
     * @return The resource's thumbnails
     */
    @JsonGetter(JsonKeys.THUMBNAIL)
    public List<ContentResource<?>> getThumbnails() {
        return getResourceThumbnails();
    }

    /**
     * Gets the type.
     *
     * @return The type
     */
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return myType;
    }

    /**
     * Gets a JSON string representation of this object.
     *
     * @return A JSON string representation
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(this.getClass()).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * Converts a number to a float and throws an exception if the supplied number isn't positive or finite.
     *
     * @param aNumber A number
     * @return A float
     * @throws IllegalArgumentException If the supplied number isn't a finite positive float
     */
    protected float convertToFinitePositiveFloat(final Number aNumber) {
        final float floatValue = aNumber.floatValue();

        if (floatValue > 0 && Double.isFinite(floatValue)) {
            return floatValue;
        }

        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_024, floatValue));
    }

    /**
     * Sets the resource's behaviors.
     *
     * @param aBehaviorList A list of behaviors
     * @return The resource
     */
    @JsonSetter(JsonKeys.BEHAVIOR)
    protected AbstractResource<T> setBehaviors(final List<Behavior> aBehaviorList) {
        myBehaviors = Objects.requireNonNull(aBehaviorList);
        return this;
    }

    /**
     * Sets the resource's homepages.
     *
     * @param aHomepageArray An array of homepages
     * @return The resource
     */
    @JsonSetter(JsonKeys.HOMEPAGE)
    protected AbstractResource<T> setHomepages(final Homepage... aHomepageArray) {
        return setHomepages(Arrays.asList(aHomepageArray));
    }

    /**
     * Sets the resource's homepages.
     *
     * @param aHomepageList A list of homepages
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setHomepages(final List<Homepage> aHomepageList) {
        final List<Homepage> homepages = getHomepages();

        Objects.requireNonNull(aHomepageList);
        homepages.clear();
        homepages.addAll(aHomepageList);

        return this;
    }

    /**
     * Sets the resource ID.
     *
     * @param aID An ID
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setID(final String aID) {
        myID = UriUtils.checkID(aID, true);
        return this;
    }

    /**
     * Sets the resource label.
     *
     * @param aLabel A label to assign to the resource
     * @return The resource
     */
    @JsonSetter(JsonKeys.LABEL)
    protected AbstractResource<T> setLabel(final Label aLabel) {
        Objects.requireNonNull(aLabel);
        myLabel = aLabel;
        return this;
    }

    /**
     * Sets the resource label from its string form.
     *
     * @param aLabel A label to assign to the resource
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
    }

    /**
     * Sets the resource metadata from a list of metadata.
     *
     * @param aMetadataList A list of metadata
     * @return The resource
     */
    @JsonSetter(JsonKeys.METADATA)
    protected AbstractResource<T> setMetadata(final List<Metadata> aMetadataList) {
        final List<Metadata> metadata = getMetadata();

        Objects.requireNonNull(aMetadataList);
        metadata.clear();
        metadata.addAll(aMetadataList);

        return this;
    }

    /**
     * Sets the resource metadata.
     *
     * @param aMetadataArray The metadata to associate with the resource
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setMetadata(final Metadata... aMetadataArray) {
        return setMetadata(Arrays.asList(aMetadataArray));
    }

    /**
     * Sets the resource's partOfs.
     *
     * @param aPartOfList A list of partOfs
     * @return The resource
     */
    @JsonSetter(JsonKeys.PART_OF)
    protected AbstractResource<T> setPartOfs(final List<PartOf> aPartOfList) {
        final List<PartOf> partOfs = getPartOfs();

        Objects.requireNonNull(aPartOfList);
        partOfs.clear();
        partOfs.addAll(aPartOfList);

        return this;
    }

    /**
     * Sets the resource's partOfs.
     *
     * @param aPartOfArray An array of partOfs
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setPartOfs(final PartOf... aPartOfArray) {
        return setPartOfs(Arrays.asList(aPartOfArray));
    }

    /**
     * Sets the resource's providers.
     *
     * @param aProviderList A list of providers
     * @return The resource
     */
    @JsonSetter(JsonKeys.PROVIDER)
    protected AbstractResource<T> setProviders(final List<Provider> aProviderList) {
        return setResourceProviders(aProviderList);
    }

    /**
     * Sets the resource's providers.
     *
     * @param aProviderArray An array of providers
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    /**
     * Sets the resource's renderings.
     *
     * @param aRenderingList A list of renderings
     * @return The resource
     */
    @JsonSetter(JsonKeys.RENDERING)
    protected AbstractResource<T> setRenderings(final List<Rendering> aRenderingList) {
        final List<Rendering> renderings = getRenderings();

        Objects.requireNonNull(renderings);
        renderings.clear();
        renderings.addAll(aRenderingList);

        return this;
    }

    /**
     * Sets the resource's renderings.
     *
     * @param aRenderingArray An array of renderings
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setRenderings(final Rendering... aRenderingArray) {
        return setRenderings(Arrays.asList(aRenderingArray));
    }

    /**
     * Sets the resource's required statement.
     *
     * @param aStatement A required statement
     * @return The resource
     */
    @JsonSetter(JsonKeys.REQUIRED_STATEMENT)
    protected AbstractResource<T> setRequiredStatement(final RequiredStatement aStatement) {
        myRequiredStatement = aStatement;
        return this;
    }

    /**
     * Sets the resource's rights URI.
     *
     * @param aRights A rights URI
     * @return The resource
     */
    @JsonProperty
    protected AbstractResource<T> setRights(final String aRights) {
        myRights = UriUtils.checkID(aRights, false);
        return this;
    }

    /**
     * Sets the resource's seeAlso references.
     *
     * @param aSeeAlsoList A list of seeAlso references
     * @return The resource
     */
    @JsonSetter(JsonKeys.SEE_ALSO)
    protected AbstractResource<T> setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        getSeeAlsoRefs().addAll(aSeeAlsoList);
        return this;
    }

    /**
     * Sets the resource's seeAlso references.
     *
     * @param aSeeAlsoArray An array of seeAlso references
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        Collections.addAll(getSeeAlsoRefs(), aSeeAlsoArray);
        return this;
    }

    /**
     * Sets the resource's services.
     *
     * @param aServiceList A list of services
     * @return The resource
     */
    @JsonSetter(JsonKeys.SERVICE)
    protected AbstractResource<T> setServices(final List<Service<?>> aServiceList) {
        final List<Service<?>> services = getServices();

        Objects.requireNonNull(aServiceList);
        services.clear();
        services.addAll(aServiceList);

        return this;
    }

    /**
     * Sets the resource's services.
     *
     * @param aServiceArray An array of services
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setServices(final Service<?>... aServiceArray) {
        return setServices(Arrays.asList(aServiceArray));
    }

    /**
     * Sets the resource summary.
     *
     * @param aSummary The resource summary
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setSummary(final String aSummary) {
        mySummary = new Summary(aSummary);
        return this;
    }

    /**
     * Sets the resource summary.
     *
     * @param aSummary The resource summary
     * @return The resource
     */
    protected AbstractResource<T> setSummary(final Summary aSummary) {
        Objects.requireNonNull(aSummary);
        mySummary = aSummary;
        return this;
    }

    /**
     * Sets the resource's thumbnails.
     *
     * @param aThumbnailArray A thumbnails array
     * @return The resource
     */
    @JsonSetter(JsonKeys.THUMBNAIL)
    protected AbstractResource<T> setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return setResourceThumbnails(Arrays.asList(aThumbnailArray));
    }

    /**
     * Sets the resource's thumbnails.
     *
     * @param aThumbnailList A thumbnails list
     * @return The resource
     */
    @JsonIgnore
    protected AbstractResource<T> setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return setResourceThumbnails(aThumbnailList);
    }

    /**
     * Gets a list of resource providers, initializing the list if this hasn't been done already.
     *
     * @return The resource's providers
     */
    @JsonIgnore
    private List<Provider> getResourceProviders() {
        if (myProviders == null) {
            myProviders = new ArrayList<>();
        }

        return myProviders;
    }

    /**
     * Gets a list of resource thumbnails, initializing the list if this hasn't been done already.
     *
     * @return The resource's thumbnails
     */
    @JsonIgnore
    private List<ContentResource<?>> getResourceThumbnails() {
        if (myThumbnails == null) {
            myThumbnails = new ArrayList<>();
        }

        return myThumbnails;
    }

    /**
     * Sets the resource's provider.
     *
     * @param aProviderList A list of providers
     * @return This resource
     */
    @JsonIgnore
    private AbstractResource<T> setResourceProviders(final List<Provider> aProviderList) {
        final List<Provider> providers = getResourceProviders();

        Objects.requireNonNull(aProviderList);
        providers.clear();
        providers.addAll(aProviderList);

        return this;
    }

    /**
     * Sets the resource's thumbnails.
     *
     * @param aThumbnailList A list of thumbnails
     * @return This resource
     */
    @JsonIgnore
    private AbstractResource<T> setResourceThumbnails(final List<ContentResource<?>> aThumbnailList) {
        final List<ContentResource<?>> thumbnails = getResourceThumbnails();

        Objects.requireNonNull(aThumbnailList);
        thumbnails.clear();
        thumbnails.addAll(aThumbnailList);

        return this;
    }
}
