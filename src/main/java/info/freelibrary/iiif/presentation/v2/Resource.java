
package info.freelibrary.iiif.presentation.v2;

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

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v2.properties.Attribution;
import info.freelibrary.iiif.presentation.v2.properties.Description;
import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.License;
import info.freelibrary.iiif.presentation.v2.properties.Logo;
import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v2.properties.Thumbnail;
import info.freelibrary.iiif.presentation.v2.properties.Type;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.v2.services.Service;
import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;

/**
 * A resource that can be used as a base for more specific IIIF presentation resources.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.LABEL, Constants.ID, Constants.TYPE, Constants.DESCRIPTION,
    Constants.ATTRIBUTION, Constants.LICENSE, Constants.WITHIN, Constants.LOGO, Constants.THUMBNAIL, Constants.METADATA,
    Constants.SEQUENCES, Constants.SERVICE })
class Resource<T extends Resource<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Resource.class, MessageCodes.BUNDLE);

    @JsonProperty(Constants.TYPE)
    protected final Type myType;

    @JsonProperty(Constants.ID)
    private URI myID;

    private Label myLabel;

    private URI myWithin;

    @JsonProperty(Constants.METADATA)
    private Metadata myMetadata;

    private Description myDescription;

    private Thumbnail myThumbnail;

    private Attribution myAttribution;

    private License myLicense;

    private Logo myLogo;

    private ViewingHint myViewingHint;

    private SeeAlso mySeeAlso;

    private Service<?> myService;

    /**
     * Creates a new resource from the supplied type.
     *
     * @param aType A resource type
     * @param aNumber the number of required arguments
     */
    protected Resource(final String aType, final int aNumber) {
        checkArgs(new Object[] { aType }, new String[] { Constants.TYPE }, aNumber);
        myType = new Type(aType); // always required
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aID An ID
     * @param aType A resource type
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aType, final String aID, final int aNumber) {
        checkArgs(new Object[] { aType, aID }, new String[] { Constants.TYPE, Constants.ID }, aNumber);
        myType = new Type(aType); // always required

        if (aID != null) {
            myID = URI.create(aID);
        }
    }

    /**
     * Creates a new resource from the supplied ID and type.
     *
     * @param aID An ID
     * @param aType A resource type
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aType, final URI aID, final int aNumber) {
        checkArgs(new Object[] { aType, aID }, new String[] { Constants.TYPE, Constants.ID }, aNumber);
        myType = new Type(aType); // always required

        myID = aID;
    }

    /**
     * Creates a new resource from a supplied ID, type, and label.
     *
     * @param aID A URI
     * @param aType A type of resources
     * @param aLabel A label
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aType, final String aID, final String aLabel, final int aNumber) {
        checkArgs(new Object[] { aType, aID, aLabel }, new String[] { Constants.TYPE, Constants.ID, Constants.LABEL },
                aNumber);
        myType = new Type(aType); // always required

        if (aID != null) {
            myID = URI.create(aID);
        }

        if (aLabel != null) {
            myLabel = new Label(aLabel);
        }
    }

    /**
     * Creates a new resource from a supplied ID and label.
     *
     * @param aID A URI ID (preferably and HTTP based one)
     * @param aType A type of resource
     * @param aLabel A label for the resource
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aType, final URI aID, final Label aLabel, final int aNumber) {
        checkArgs(new Object[] { aType, aID, aLabel }, new String[] { Constants.TYPE, Constants.ID, Constants.LABEL },
                aNumber);
        myType = new Type(aType);

        myID = aID;
        myLabel = aLabel;
    }

    /**
     * Creates a new resource from string values.
     *
     * @param aID An ID
     * @param aType A type
     * @param aLabel A label is required
     * @param aMetadata A metadata property
     * @param aDescription A description is recommended
     * @param aThumbnail A thumbnail property
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aType, final String aID, final String aLabel, final Metadata aMetadata,
            final String aDescription, final Thumbnail aThumbnail, final int aNumber) {
        checkArgs(
                new Object[] { aType, aID, aLabel, aMetadata, aDescription, aThumbnail }, new String[] { Constants.TYPE,
                    Constants.ID, Constants.LABEL, Constants.METADATA, Constants.DESCRIPTION, Constants.THUMBNAIL },
                aNumber);
        myType = new Type(aType); // always required

        if (aID != null) {
            myID = URI.create(aID);
        }

        if (aLabel != null) {
            myLabel = new Label(aLabel);
        }

        myMetadata = aMetadata;
        myThumbnail = aThumbnail;

        if (aDescription != null) {
            myDescription = new Description(aDescription);
        }
    }

    /**
     * Creates a new resource from properties.
     *
     * @param aID An ID
     * @param aType A type
     * @param aLabel A label is required
     * @param aMetadata A metadata property
     * @param aDescription A description is recommended
     * @param aThumbnail A thumbnail property
     * @param aNumber The number of required arguments
     */
    protected Resource(final String aType, final URI aID, final Label aLabel, final Metadata aMetadata,
            final Description aDescription, final Thumbnail aThumbnail, final int aNumber) {
        checkArgs(
                new Object[] { aType, aID, aLabel, aMetadata, aDescription, aThumbnail }, new String[] { Constants.TYPE,
                    Constants.ID, Constants.LABEL, Constants.METADATA, Constants.DESCRIPTION, Constants.THUMBNAIL },
                aNumber);
        myType = new Type(aType); // always required

        myID = aID;
        myLabel = aLabel;
        myMetadata = aMetadata;
        myDescription = aDescription;
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
     * Sets the label.
     *
     * @param aLabel The label to set
     * @return The resource
     */
    @JsonIgnore
    protected Resource<T> setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
    }

    /**
     * Sets the label.
     *
     * @param aLabel The label
     * @return The resource
     */
    @JsonSetter(Constants.LABEL)
    protected Resource<T> setLabel(final Label aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Gets a service.
     *
     * @return A service
     */
    @JsonGetter(Constants.SERVICE)
    public Service<?> getService() {
        return myService;
    }

    /**
     * Sets a service.
     *
     * @param aService A service
     * @return The resource
     */
    @JsonSetter(Constants.SERVICE)
    protected Resource<T> setService(final Service<?> aService) {
        myService = aService;
        return this;
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
    protected Resource<T> setMetadata(final Metadata aMetadata) {
        myMetadata = aMetadata;
        return this;
    }

    /**
     * Gets the description.
     *
     * @return The description
     */
    @JsonUnwrapped
    public Description getDescription() {
        return myDescription;
    }

    /**
     * Sets the description.
     *
     * @param aDescription A description
     * @return The resource
     */
    @JsonIgnore
    protected Resource<T> setDescription(final String aDescription) {
        myDescription = new Description(aDescription);
        return this;
    }

    /**
     * Sets the description.
     *
     * @param aDescription A description
     * @return The resource
     */
    protected Resource<T> setDescription(final Description aDescription) {
        myDescription = aDescription;
        return this;
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
    protected Resource<T> setThumbnail(final Thumbnail aThumbnail) {
        myThumbnail = aThumbnail;
        return this;
    }

    /**
     * Sets the thumbnail.
     *
     * @param aURI A thumbnail ID
     * @return The resource
     */
    protected Resource<T> setThumbnail(final String aURI) {
        myThumbnail = new Thumbnail(aURI);
        return this;
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
     * Clears the attribution.
     *
     * @return The resource
     */
    protected Resource<T> clearAttribution() {
        myAttribution = null;
        return this;
    }

    /**
     * Sets the attribution.
     *
     * @param aAttribution An attribution
     * @return The resource
     */
    @JsonIgnore
    protected Resource<T> setAttribution(final String aAttribution) {
        myAttribution = new Attribution(aAttribution);
        return this;
    }

    /**
     * Sets the attribution.
     *
     * @param aAttribution An attribution
     * @return The resource
     */
    @JsonProperty
    protected Resource<T> setAttribution(final Attribution aAttribution) {
        myAttribution = aAttribution;
        return this;
    }

    /**
     * Gets the license.
     *
     * @return The license
     */
    @JsonProperty
    public License getLicense() {
        return myLicense;
    }

    /**
     * Sets the license.
     *
     * @param aLicense A license
     * @return The resource
     */
    @JsonProperty
    protected Resource<T> setLicense(final License aLicense) {
        myLicense = aLicense;
        return this;
    }

    /**
     * Sets the license.
     *
     * @param aURL A license URL
     * @return The resource
     * @throws MalformedURLException If the supplied URL string isn't a valid URL
     */
    @JsonIgnore
    protected Resource<T> setLicense(final String aURL) throws MalformedURLException {
        myLicense = new License(aURL);
        return this;
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
    protected Resource<T> setLogo(final Logo aLogo) {
        myLogo = aLogo;
        return this;
    }

    /**
     * Sets the logo.
     *
     * @param aURI A logo ID
     * @return The resource
     */
    protected Resource<T> setLogo(final String aURI) {
        myLogo = new Logo(aURI);
        return this;
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
     * Sets the ID.
     *
     * @param aURI An ID
     * @return The resource
     */
    @JsonIgnore
    protected Resource<T> setID(final String aURI) {
        myID = URI.create(aURI);
        return this;
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The resource
     */
    @JsonIgnore
    protected Resource<T> setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Gets the within link.
     *
     * @return The within link
     */
    @JsonGetter(Constants.WITHIN)
    public URI getWithin() {
        return myWithin;
    }

    /**
     * Sets the within link.
     *
     * @param aWithin A within link
     * @return The resource
     */
    @JsonIgnore
    protected Resource<T> setWithin(final String aWithin) {
        myWithin = URI.create(aWithin);
        return this;
    }

    /**
     * Sets the within link.
     *
     * @param aWithin A within link
     * @return The resource
     */
    @JsonSetter(Constants.WITHIN)
    protected Resource<T> setWithin(final URI aWithin) {
        myWithin = aWithin;
        return this;
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
     * Clears the viewing hint.
     *
     * @return The resource
     */
    protected Resource<T> clearViewingHint() {
        myViewingHint = null;
        return this;
    }

    /**
     * Sets the viewing hint.
     *
     * @param aViewingHint The viewing hint
     * @return The resource
     */
    @JsonIgnore
    protected Resource<T> setViewingHint(final ViewingHint aViewingHint) {
        myViewingHint = aViewingHint;
        return this;
    }

    /**
     * Sets the viewing hint.
     *
     * @param aViewingHint The viewing hint
     * @return The resource
     */
    protected Resource<T> setViewingHint(final String aViewingHint) {
        myViewingHint = new ViewingHint(aViewingHint);
        return this;
    }

    /**
     * Sets the viewing hint.
     *
     * @param aViewingHint The viewing hint
     * @return The resource
     */
    protected Resource<T> setViewingHint(final Option aViewingHint) {
        myViewingHint = new ViewingHint(aViewingHint);
        return this;
    }

    /**
     * Gets the viewing hint.
     *
     * @return The viewing hint
     */
    @JsonUnwrapped
    public ViewingHint getViewingHint() {
        return myViewingHint;
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
    protected Resource<T> setSeeAlso(final SeeAlso aSeeAlso) {
        mySeeAlso = aSeeAlso;
        return this;
    }

    /**
     * Sets see also reference(s).
     *
     * @param aSeeAlso See also reference(s)
     * @return The resource
     */
    @JsonSetter(Constants.SEE_ALSO)
    protected Resource<T> setSeeAlso(final String aSeeAlso) {
        mySeeAlso = new SeeAlso(aSeeAlso);
        return this;
    }

    /**
     * This lets us define required parameters at the subclass level with a minimal amount of code.
     *
     * @param aArgArray An array of arguments passed to the constructor
     * @param aNameArray An array of names corresponding to the arguments passed to the constructor
     * @param aNumber The number of required arguments
     */
    private void checkArgs(final Object[] aArgs, final String[] aNames, final int aNumber) {
        if (aArgs.length < aNumber) {
            throw new IndexOutOfBoundsException(String.valueOf(aNumber));
        } else if (aArgs.length != aNames.length) {
            throw new IllegalArgumentException("Number of arguments is not equal to the number of names");
        }

        for (int index = 0; index < aNumber; index++) {
            Objects.requireNonNull(aArgs[index], LOGGER.getMessage(MessageCodes.JPA_012, aNames[index]));
        }
    }

}
