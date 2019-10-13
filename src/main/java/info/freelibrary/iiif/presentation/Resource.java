
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
import info.freelibrary.iiif.presentation.properties.Description;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.License;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.properties.ViewingHint;
import info.freelibrary.iiif.presentation.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A resource that can be used as a base for more specific IIIF presentation resources.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.LABEL, Constants.ID, Constants.TYPE, Constants.DESCRIPTION,
    Constants.LOGO, Constants.THUMBNAIL, Constants.METADATA, Constants.SEQUENCES })
public class Resource<T extends Resource<T>> {

    @JsonProperty("@type")
    protected final Type myType;

    @JsonProperty("@id")
    private URI myID;

    private Label myLabel;

    @JsonProperty("metadata")
    private Metadata myMetadata;

    private Description myDescription;

    private Thumbnail myThumbnail;

    private Attribution myAttribution;

    private License myLicense;

    private Logo myLogo;

    private ViewingHint myViewingHint;

    private SeeAlso mySeeAlso;

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
        checkArgs(new Object[] { aType, aID, aLabel, aMetadata, aDescription, aThumbnail }, new String[] {
            Constants.TYPE, Constants.ID, Constants.LABEL, Constants.METADATA, Constants.DESCRIPTION,
            Constants.THUMBNAIL }, aNumber);
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
        checkArgs(new Object[] { aType, aID, aLabel, aMetadata, aDescription, aThumbnail }, new String[] {
            Constants.TYPE, Constants.ID, Constants.LABEL, Constants.METADATA, Constants.DESCRIPTION,
            Constants.THUMBNAIL }, aNumber);
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
    @JsonSetter(Constants.LABEL)
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
    @JsonIgnore
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
    public T setDescription(final String aDescription) {
        myDescription = new Description(aDescription);
        return (T) this;
    }

    /**
     * Sets the description.
     *
     * @param aDescription A description
     * @return The resource
     */
    @JsonIgnore
    public T setDescription(final Description aDescription) {
        myDescription = aDescription;
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
     * @param aURI A thumbnail ID
     * @return The resource
     */
    public T setThumbnail(final String aURI) {
        myThumbnail = new Thumbnail(aURI);
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
     * @param aAttribution An attribution
     * @return The resource
     */
    @JsonIgnore
    public T setAttribution(final String aAttribution) {
        myAttribution = new Attribution(aAttribution);
        return (T) this;
    }

    /**
     * Sets the attribution.
     *
     * @param aAttribution An attribution
     * @return The resource
     */
    @JsonIgnore
    public T setAttribution(final Attribution aAttribution) {
        myAttribution = aAttribution;
        return (T) this;
    }

    /**
     * Gets the license.
     *
     * @return The license
     */
    @JsonGetter(Constants.LICENSE)
    public License getLicense() {
        return myLicense;
    }

    /**
     * Sets the license.
     *
     * @param aLicense A license
     * @return The resource
     */
    @JsonIgnore
    public T setLicense(final License aLicense) {
        myLicense = aLicense;
        return (T) this;
    }

    /**
     * Sets the license.
     *
     * @param aURL A license URL
     * @return The resource
     * @throws MalformedURLException If the supplied URL string isn't a valid URL
     */
    @JsonIgnore
    public T setLicense(final String aURL) throws MalformedURLException {
        myLicense = new License(aURL);
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
     * Sets the logo.
     *
     * @param aURI A logo ID
     * @return The resource
     */
    public T setLogo(final String aURI) {
        myLogo = new Logo(aURI);
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
     * Sets the ID.
     *
     * @param aURI An ID
     * @return The resource
     */
    @JsonIgnore
    public T setID(final String aURI) {
        myID = URI.create(aURI);
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
     * Gets the type.
     *
     * @return The type
     */
    @JsonGetter(Constants.TYPE)
    public Type getType() {
        return myType;
    }

    /**
     * Sets the viewing hint.
     *
     * @param aViewingHint The viewing hint
     * @return The resource
     */
    @JsonIgnore
    public T setViewingHint(final ViewingHint aViewingHint) {
        myViewingHint = aViewingHint;
        return (T) this;
    }

    /**
     * Sets the viewing hint.
     *
     * @param aViewingHint The viewing hint
     * @return The resource
     */
    public T setViewingHint(final String aViewingHint) {
        myViewingHint = new ViewingHint(aViewingHint);
        return (T) this;
    }

    /**
     * Sets the viewing hint.
     *
     * @param aViewingHint The viewing hint
     * @return The resource
     */
    public T setViewingHint(final Option aViewingHint) {
        myViewingHint = new ViewingHint(aViewingHint);
        return (T) this;
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
    public T setSeeAlso(final SeeAlso aSeeAlso) {
        mySeeAlso = aSeeAlso;
        return (T) this;
    }

    /**
     * Sets see also reference(s).
     *
     * @param aSeeAlso See also reference(s)
     * @return The resource
     */
    @JsonSetter(Constants.SEE_ALSO)
    public T setSeeAlso(final String aSeeAlso) {
        mySeeAlso = new SeeAlso(aSeeAlso);
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
            Objects.requireNonNull(aArgs[index], getLogger().getMessage(MessageCodes.EXC_012, aNames[index]));
        }
    }

}
