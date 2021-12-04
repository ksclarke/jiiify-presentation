
package info.freelibrary.iiif.presentation.v3.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.IllegalArgumentI18nException;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.ImageContent;
import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An organization or person that contributed to providing the content of the resource.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.HOMEPAGE, JsonKeys.LOGO, JsonKeys.SEE_ALSO })
public class Provider {

    /**
     * The provider's ID.
     */
    private URI myID;

    /**
     * The provider's label.
     */
    private Label myLabel;

    /**
     * The provider's homepages.
     */
    private List<Homepage> myHomepages;

    /**
     * The provider's logos.
     */
    private List<ImageContent> myLogos;

    /**
     * The provider's seeAlso references.
     */
    private List<SeeAlso> mySeeAlsoRefs;

    /**
     * Creates a new resource provider from the supplied ID and label.
     *
     * @param aID An ID in string form
     * @param aLabel A label in string form
     */
    public Provider(final String aID, final String aLabel) {
        this(URI.create(aID), new Label(aLabel));
    }

    /**
     * Creates a new resource provider from the supplied ID and label.
     *
     * @param aID An ID
     * @param aLabel A label
     */
    public Provider(final String aID, final Label aLabel) {
        myID = URI.create(Objects.requireNonNull(aID));
        myLabel = Objects.requireNonNull(aLabel);
    }

    /**
     * Creates a new resource provider from the supplied ID and label.
     *
     * @param aID An ID
     * @param aLabel A label
     */
    public Provider(final URI aID, final Label aLabel) {
        myID = Objects.requireNonNull(aID);
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
        myID = URI.create(Objects.requireNonNull(aID));
        myLabel = Objects.requireNonNull(aLabel);
        getLogos().add(Objects.requireNonNull(aLogo));
        getHomepages().add(Objects.requireNonNull(aHomepage));
    }

    /**
     * Creates a new resource provider from the supplied ID and label.
     *
     * @param aID An ID
     * @param aLabel A label
     * @param aHomepage A homepage
     * @param aLogo A logo
     */
    public Provider(final URI aID, final Label aLabel, final Homepage aHomepage, final ImageContent aLogo) {
        myID = Objects.requireNonNull(aID);
        myLabel = Objects.requireNonNull(aLabel);
        getLogos().add(Objects.requireNonNull(aLogo));
        getHomepages().add(Objects.requireNonNull(aHomepage));
    }

    /**
     * Creates a new provider for Jackson's deserialization process.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private Provider() {
        // This is intentionally empty
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The provider
     */
    public Provider setID(final URI aID) {
        myID = Objects.requireNonNull(aID);
        return this;
    }

    /**
     * Gets the ID.
     *
     * @return An ID
     */
    @JsonGetter(JsonKeys.ID)
    public URI getID() {
        return myID;
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
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    @JsonGetter(JsonKeys.LABEL)
    @JsonInclude(Include.NON_EMPTY)
    public Label getLabel() {
        return myLabel;
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
     * Sets the descriptive label in string form.
     *
     * @param aLabel A descriptive label in string form
     * @return The provider
     */
    @JsonIgnore
    public Provider setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
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
     * Gets a list of provider homepages, initializing the list if this hasn't been done already.
     *
     * @return The provider's homepages
     */
    @JsonGetter(JsonKeys.HOMEPAGE)
    @JsonInclude(Include.NON_EMPTY)
    public List<Homepage> getHomepages() {
        if (myHomepages == null) {
            myHomepages = new ArrayList<>();
        }

        return myHomepages;
    }

    /**
     * Sets the provider logos.
     *
     * @param aLogoArray An array of logos
     * @return The provider
     */
    @JsonSetter(JsonKeys.LOGO)
    @SafeVarargs
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
     * Gets a list of provider logos, initializing the list if this hasn't been done already.
     *
     * @return The provider's logos
     */
    @JsonGetter(JsonKeys.LOGO)
    @JsonInclude(Include.NON_EMPTY)
    public List<ImageContent> getLogos() {
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
    @JsonInclude(Include.NON_EMPTY)
    public List<SeeAlso> getSeeAlsoRefs() {
        if (mySeeAlsoRefs == null) {
            mySeeAlsoRefs = new ArrayList<>();
        }

        return mySeeAlsoRefs;
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
     * Gets the hash code for the provider.
     *
     * @return The provider's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(myID, myLabel, myHomepages, myLogos, mySeeAlsoRefs);
    }

    /**
     * Tests whether the supplied object equals this provider.
     *
     * @return True if the objects are equal; else, false
     */
    @Override
    public boolean equals(final Object aObject) {
        if (aObject instanceof Provider) {
            final Provider otherProvider = (Provider) aObject;

            return Objects.equals(myID, otherProvider.myID) //
                    && Objects.equals(myLabel, otherProvider.myLabel) //
                    && Objects.equals(myHomepages, otherProvider.myHomepages) //
                    && Objects.equals(myLogos, otherProvider.myLogos) //
                    && Objects.equals(mySeeAlsoRefs, otherProvider.mySeeAlsoRefs);
        }

        return false;
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
     * Returns a provider from its JSON representation.
     *
     * @param aJsonString A provider in its JSON form
     * @return A provider
     */
    public static Provider from(final String aJsonString) {
        try {
            return JSON.getReader(Provider.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
