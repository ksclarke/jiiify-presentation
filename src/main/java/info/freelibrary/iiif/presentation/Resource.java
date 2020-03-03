
package info.freelibrary.iiif.presentation;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import info.freelibrary.iiif.presentation.properties.Attribution;
import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Behavior.Option;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.Rights;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.services.Service;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A resource that can be used as a base for more specific IIIF presentation resources.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.TYPE, Constants.ID, Constants.LABEL, Constants.SUMMARY,
    Constants.ATTRIBUTION, Constants.RIGHTS, Constants.PART_OF, Constants.LOGO, Constants.THUMBNAIL,
    Constants.METADATA, Constants.SEQUENCES, Constants.SERVICE })
class Resource<T extends Resource<T>> {

    @JsonProperty(Constants.TYPE)
    protected final Type myType;

    @JsonProperty(Constants.ID)
    private URI myID;

    private Label myLabel;

    private URI myPartOfLink;

    @JsonProperty(Constants.METADATA)
    private Metadata myMetadata;

    private Summary mySummary;

    private Thumbnail myThumbnail;

    private Attribution myAttribution;

    private Rights myRights;

    private Logo myLogo;

    private Behavior myBehavior;

    private SeeAlso mySeeAlso;

    private Service myService;

    /**
     * Creates a new resource from the supplied type.
     *
     * @param aTypeString A resource type in string form
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aTypeString, final int aNumber) {
        checkArgs(new Object[] { aTypeString }, new String[] { Constants.TYPE }, aNumber);
        myType = new Type(aTypeString); // always required
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aIdString An ID in string form
     * @param aTypeString A resource type in string form
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aTypeString, final String aIdString, final int aNumber) {
        checkArgs(new Object[] { aTypeString, aIdString }, new String[] { Constants.TYPE, Constants.ID }, aNumber);
        myType = new Type(aTypeString); // always required

        if (aIdString != null) {
            myID = URI.create(aIdString);
        }
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aID An ID in string form
     * @param aTypeString A resource type in string form
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aTypeString, final URI aID, final int aNumber) {
        checkArgs(new Object[] { aTypeString, aID }, new String[] { Constants.TYPE, Constants.ID }, aNumber);
        myType = new Type(aTypeString); // always required

        myID = aID;
    }

    /**
     * Creates a new resource from a supplied ID, type, and label.
     *
     * @param aIdString A URI in string form
     * @param aTypeString A type of resource in string form
     * @param aLabelString A label in string form
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aTypeString, final String aIdString, final String aLabelString,
            final int aNumber) {
        final Object[] argsArray = new Object[] { aTypeString, aIdString, aLabelString };
        final String[] namesArray = new String[] { Constants.TYPE, Constants.ID, Constants.LABEL };

        checkArgs(argsArray, namesArray, aNumber);

        myType = new Type(aTypeString); // always required

        if (aIdString != null) {
            myID = URI.create(aIdString);
        }

        if (aLabelString != null) {
            myLabel = new Label(aLabelString);
        }
    }

    /**
     * Creates a new resource from a supplied ID and label.
     *
     * @param aID A URI ID (preferably and HTTP based one)
     * @param aTypeString A type of resource in string form
     * @param aLabel A label for the resource
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aTypeString, final URI aID, final Label aLabel, final int aNumber) {
        final Object[] argsArray = new Object[] { aTypeString, aID, aLabel };
        final String[] namesArray = new String[] { Constants.TYPE, Constants.ID, Constants.LABEL };

        checkArgs(argsArray, namesArray, aNumber);

        myType = new Type(aTypeString);
        myID = aID;
        myLabel = aLabel;
    }

    /**
     * Creates a new resource from string values.
     *
     * @param aIdString An ID in string form
     * @param aTypeString A type in string form
     * @param aLabelString A label in string form
     * @param aMetadata A metadata property
     * @param aSummaryString A summary property in string form
     * @param aThumbnail A thumbnail property
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aTypeString, final String aIdString, final String aLabelString,
            final Metadata aMetadata, final String aSummaryString, final Thumbnail aThumbnail, final int aNumber) {
        final Object[] argsArray = new Object[] { aTypeString, aIdString, aLabelString, aMetadata, aSummaryString,
            aThumbnail };
        final String[] namesArray = new String[] { Constants.TYPE, Constants.ID, Constants.LABEL, Constants.METADATA,
            Constants.SUMMARY, Constants.THUMBNAIL };

        checkArgs(argsArray, namesArray, aNumber);

        myType = new Type(aTypeString); // always required

        if (aIdString != null) {
            myID = URI.create(aIdString);
        }

        if (aLabelString != null) {
            myLabel = new Label(aLabelString);
        }

        myMetadata = aMetadata;
        myThumbnail = aThumbnail;

        if (aSummaryString != null) {
            mySummary = new Summary(aSummaryString);
        }
    }

    /**
     * Creates a new resource from properties.
     *
     * @param aID An ID
     * @param aTypeString A type in string form
     * @param aLabel A label is required
     * @param aMetadata A metadata property
     * @param aSummary A summary property
     * @param aThumbnail A thumbnail property
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aTypeString, final URI aID, final Label aLabel, final Metadata aMetadata,
            final Summary aSummary, final Thumbnail aThumbnail, final int aNumber) {
        final Object[] argsArray = new Object[] { aTypeString, aID, aLabel, aMetadata, aSummary, aThumbnail };
        final String[] namesArray = new String[] { Constants.TYPE, Constants.ID, Constants.LABEL, Constants.METADATA,
            Constants.SUMMARY, Constants.THUMBNAIL };

        checkArgs(argsArray, namesArray, aNumber);

        myType = new Type(aTypeString); // always required
        myID = aID;
        myLabel = aLabel;
        myMetadata = aMetadata;
        mySummary = aSummary;
        myThumbnail = aThumbnail;
    }

    /**
     * Creates a new resource from the supplied type.
     *
     * @param aType A type of resource
     */
    protected Resource(final Type aType) {
        myType = aType;
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
     * @param aLabelString The string form of the label to set
     * @return The resource
     */
    @JsonIgnore
    public T setLabel(final String aLabelString) {
        myLabel = new Label(aLabelString);
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
     * Gets a service.
     *
     * @return A service
     */
    @JsonGetter(Constants.SERVICE)
    public Service getService() {
        return myService;
    }

    /**
     * Sets a service.
     *
     * @param aService A service
     * @return The resource
     */
    @JsonSetter(Constants.SERVICE)
    public T setService(final Service aService) {
        myService = aService;
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
     * @param aSummaryString A summary in string form
     * @return The resource
     */
    @JsonIgnore
    public T setSummary(final String aSummaryString) {
        mySummary = new Summary(aSummaryString);
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
    @JsonIgnore
    public T setThumbnail(final Thumbnail aThumbnail) {
        myThumbnail = aThumbnail;
        return (T) this;
    }

    /**
     * Sets the thumbnail.
     *
     * @param aUriString A thumbnail URI ID in string form
     * @return The resource
     */
    public T setThumbnail(final String aUriString) {
        myThumbnail = new Thumbnail(aUriString);
        return (T) this;
    }

    /**
     * Gets the attribution.
     *
     * @return The attribution
     */
    @JsonUnwrapped
    public Attribution getAttribution() {
        return myAttribution;
    }

    /**
     * Sets the attribution.
     *
     * @param aAttributionString An attribution in string form
     * @return The resource
     */
    @JsonIgnore
    public T setAttribution(final String aAttributionString) {
        myAttribution = new Attribution(aAttributionString);
        return (T) this;
    }

    /**
     * Sets the attribution.
     *
     * @param aAttribution An attribution
     * @return The resource
     */
    @JsonProperty
    public T setAttribution(final Attribution aAttribution) {
        myAttribution = aAttribution;
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
     * @param aRightsString A rights URL in string form
     * @return The resource
     * @throws MalformedURLException If the supplied URL string isn't a valid URL
     */
    @JsonIgnore
    public T setRights(final String aRightsString) throws MalformedURLException {
        myRights = new Rights(aRightsString);
        return (T) this;
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
    @JsonIgnore
    public T setLogo(final Logo aLogo) {
        myLogo = aLogo;
        return (T) this;
    }

    /**
     * Sets the logo from the supplied string.
     *
     * @param aLogoString A logo URI ID in string form
     * @return The resource
     */
    public T setLogo(final String aLogoString) {
        myLogo = new Logo(aLogoString);
        return (T) this;
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
     * @param aIdString An URI ID in string form
     * @return The resource
     */
    @JsonIgnore
    public T setID(final String aIdString) {
        myID = URI.create(aIdString);
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
     * Gets the partOf link.
     *
     * @return The partOf link
     */
    @JsonGetter(Constants.PART_OF)
    public URI getPartOfLink() {
        return myPartOfLink;
    }

    /**
     * Sets the partOf link.
     *
     * @param aPartOfLink A partOf link
     * @return The resource
     */
    @JsonIgnore
    public T setPartOfLink(final String aPartOfLink) {
        myPartOfLink = URI.create(aPartOfLink);
        return (T) this;
    }

    /**
     * Sets the partOf link.
     *
     * @param aPartOfLink A partOf link
     * @return The resource
     */
    @JsonSetter(Constants.PART_OF)
    public T setPartOfLink(final URI aPartOfLink) {
        myPartOfLink = aPartOfLink;
        return (T) this;
    }

    /**
     * Gets the type.
     *
     * @return The type
     */
    @JsonGetter(Constants.TYPE)
    public Type getType() {
        return myType;
    }

    /**
     * Sets the behavior.
     *
     * @param aBehavior The behavior
     * @return The resource
     */
    @JsonIgnore
    public T setBehavior(final Behavior aBehavior) {
        myBehavior = aBehavior;
        return (T) this;
    }

    /**
     * Sets the behavior from the supplied string.
     *
     * @param aBehaviorString The behavior in string form
     * @return The resource
     */
    public T setBehavior(final String aBehaviorString) {
        myBehavior = new Behavior(aBehaviorString);
        return (T) this;
    }

    /**
     * Sets the behavior.
     *
     * @param aBehavior The behavior
     * @return The resource
     */
    public T setBehavior(final Option aBehavior) {
        myBehavior = new Behavior(aBehavior);
        return (T) this;
    }

    /**
     * Gets the behavior.
     *
     * @return The behavior
     */
    @JsonUnwrapped
    public Behavior getBehavior() {
        return myBehavior;
    }

    /**
     * Gets see also reference(s).
     *
     * @return The see also reference
     */
    @JsonGetter(Constants.SEE_ALSO)
    public SeeAlso getSeeAlso() {
        return mySeeAlso;
    }

    /**
     * Sets see also reference(s).
     *
     * @param aSeeAlso See also reference(s)
     * @return The resource
     */
    @JsonIgnore
    public T setSeeAlso(final SeeAlso aSeeAlso) {
        mySeeAlso = aSeeAlso;
        return (T) this;
    }

    /**
     * Sets see also reference from the supplied string.
     *
     * @param aSeeAlsoString See also reference supplied as a string
     * @return The resource
     */
    @JsonSetter(Constants.SEE_ALSO)
    public T setSeeAlso(final String aSeeAlsoString) {
        mySeeAlso = new SeeAlso(aSeeAlsoString);
        return (T) this;
    }

    /**
     * Gets a logger for the resource.
     *
     * @return A logger
     */
    @JsonIgnore
    protected Logger getLogger() {
        return LoggerFactory.getLogger(getClass(), Constants.BUNDLE_NAME);
    }

    /**
     * This lets us define required parameters at the subclass level with a minimal amount of code.
     *
     * @param aArgsArray An array of arguments passed to the constructor
     * @param aNamesArray An array of names corresponding to the arguments passed to the constructor
     * @param aNumber The number of required arguments
     */
    private void checkArgs(final Object[] aArgsArray, final String[] aNamesArray, final int aNumber) {
        if (aArgsArray.length < aNumber) {
            throw new IndexOutOfBoundsException(String.valueOf(aNumber));
        } else if (aArgsArray.length != aNamesArray.length) {
            throw new IllegalArgumentException("Number of arguments is not equal to the number of names");
        }

        for (int index = 0; index < aNumber; index++) {
            final String message = getLogger().getMessage(MessageCodes.JPA_012, aNamesArray[index]);

            Objects.requireNonNull(aArgsArray[index], message);
        }
    }

}
