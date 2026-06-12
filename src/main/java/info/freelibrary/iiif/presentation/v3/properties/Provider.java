
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.content.ImageContent;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.util.IllegalArgumentI18nException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * An organization or person that contributed to providing the content of the resource.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.HOMEPAGE, JsonKeys.LOGO, JsonKeys.SEE_ALSO })
public class Provider {

    /** The provider's homepages. */
    private List<Homepage> myHomepages;

    /** The provider's ID. */
    private String myID;

    /** The provider's label. */
    private Label myLabel;

    /** The provider's logos. */
    private List<ImageContent> myLogos;

    /** The provider's seeAlso references. */
    private List<SeeAlso> mySeeAlsoRefs;

    /**
     * Creates a new resource provider from the supplied ID and label.
     *
     * @param aID An ID
     * @param aLabel A label
     */
    public Provider(final String aID, final Label aLabel) {
        myID = UriUtils.checkID(aID, false);
        myLabel = Objects.requireNonNull(aLabel);
    }

    /**
     * Creates a new resource provider from the supplied ID and label.
     *
     * @param aID An ID
     * @param aLabel A label
     * @param aHomepage A homepage
     * @param aLogo A logo
     */
    public Provider(final String aID, final Label aLabel, final Homepage aHomepage, final ImageContent aLogo) {
        this(aID, aLabel);

        getLogos().add(Objects.requireNonNull(aLogo));
        getHomepages().add(Objects.requireNonNull(aHomepage));
    }

    /**
     * Creates a new resource provider from the supplied provider.
     *
     * @param aProvider A provider
     */
    public Provider(final Provider aProvider) {
        this();

        if (aProvider.myID != null) {
            myID = aProvider.myID;
        }

        if (aProvider.myLabel != null) {
            myLabel = aProvider.myLabel.copy();
        }

        if (aProvider.myHomepages != null) {
            myHomepages = aProvider.myHomepages.stream().map(Homepage::new).collect(Collectors.toList());
        }

        if (aProvider.myLogos != null) {
            myLogos = aProvider.myLogos.stream().map(ImageContent::copy).collect(Collectors.toList());
        }

        if (aProvider.mySeeAlsoRefs != null) {
            mySeeAlsoRefs = aProvider.mySeeAlsoRefs.stream().map(SeeAlso::copy).collect(Collectors.toList());
        }
    }

    /**
     * Creates a new provider for Jackson's deserialization process.
     */
    private Provider() {
        // This is intentionally empty
    }

    /**
     * Creates a copy of this provider.
     *
     * @return A copy of this provider
     */
    public Provider copy() {
        return new Provider(this);
    }

    /**
     * Tests whether the supplied object equals this provider.
     *
     * @return True if the objects are equal; else, false
     */
    @Override
    public boolean equals(final Object aObject) {
        final Provider other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (Provider) aObject;

        return Objects.equals(myID, other.myID) && Objects.equals(myLabel, other.myLabel) &&
                Objects.equals(myHomepages, other.myHomepages) && Objects.equals(myLogos, other.myLogos) &&
                Objects.equals(mySeeAlsoRefs, other.mySeeAlsoRefs);
    }

    /**
     * Gets a list of provider homepages, initializing the list if this hasn't been done already.
     *
     * @return The provider's homepages
     */
    @JsonGetter(JsonKeys.HOMEPAGE)
    public final List<Homepage> getHomepages() {
        if (myHomepages == null) {
            myHomepages = new ArrayList<>();
        }

        return myHomepages;
    }

    /**
     * Gets the ID.
     *
     * @return An ID
     */
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return myID;
    }

    /**
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    @JsonGetter(JsonKeys.LABEL)
    public Label getLabel() {
        return myLabel;
    }

    /**
     * Gets a list of provider logos, initializing the list if this hasn't been done already.
     *
     * @return The provider's logos
     */
    @JsonGetter(JsonKeys.LOGO)
    public final List<ImageContent> getLogos() {
        if (myLogos == null) {
            myLogos = new ArrayList<>();
        }

        return myLogos;
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
     * Gets the provider type.
     *
     * @return The provider type
     */
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return ResourceTypes.AGENT;
    }

    /**
     * Gets the hash code for the provider.
     *
     * @return The provider's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(myID, myLabel, myHomepages, myLogos, mySeeAlsoRefs);
    }

    /**
     * Sets the provider homepages.
     *
     * @param aHomepageArray An array of homepages
     * @return The provider
     */
    @JsonSetter(JsonKeys.HOMEPAGE)
    public Provider setHomepages(final Homepage... aHomepageArray) {
        return setHomepages(Arrays.asList(aHomepageArray));
    }

    /**
     * Sets the provider homepages.
     *
     * @param aHomepageList An array of homepages
     * @return The provider
     */
    @JsonIgnore
    public Provider setHomepages(final List<Homepage> aHomepageList) {
        final List<Homepage> homepages = getHomepages();

        Objects.requireNonNull(aHomepageList);
        homepages.clear();
        homepages.addAll(aHomepageList);

        return this;
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The provider
     */
    @JsonSetter(JsonKeys.ID)
    public Provider setID(final String aID) {
        myID = UriUtils.checkID(aID, false);
        return this;
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The provider
     */
    @JsonSetter(JsonKeys.LABEL)
    public Provider setLabel(final Label aLabel) {
        myLabel = Objects.requireNonNull(aLabel);
        return this;
    }

    /**
     * Sets the provider logos.
     *
     * @param aLogoArray An array of logos
     * @return The provider
     */
    @JsonSetter(JsonKeys.LOGO)
    public final Provider setLogos(final ImageContent... aLogoArray) {
        return setLogos(Arrays.asList(aLogoArray));
    }

    /**
     * Sets the provider logo(s).
     *
     * @param aLogoList An array of logos
     * @return The provider
     */
    @JsonIgnore
    public Provider setLogos(final List<ImageContent> aLogoList) {
        final List<ImageContent> logos = getLogos();

        Objects.requireNonNull(aLogoList);
        logos.clear();
        logos.addAll(aLogoList);

        return this;
    }

    /**
     * Sets the provider's see also references.
     *
     * @param aSeeAlsoList A list of see also references
     * @return The provider
     */
    @JsonSetter(JsonKeys.SEE_ALSO)
    public Provider setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        getSeeAlsoRefs().addAll(aSeeAlsoList);
        return this;
    }

    /**
     * Sets the provider's see also references.
     *
     * @param aSeeAlsoArray An array of see also references
     * @return The provider
     */
    @JsonIgnore
    public Provider setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        Collections.addAll(getSeeAlsoRefs(), aSeeAlsoArray);
        return this;
    }

    /**
     * Gets a JSON string representation of the provider.
     *
     * @return A JSON string representation of this provider
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(Provider.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Necessary for Jackson to be able to deserializer the provider.
     *
     * @param aType A provider type
     * @return The provider
     */
    @JsonSetter(JsonKeys.TYPE)
    private Provider setType(final String aType) {
        if (!ResourceTypes.AGENT.equals(aType)) {
            throw new IllegalArgumentI18nException(aType);
        }

        return this;
    }
}
