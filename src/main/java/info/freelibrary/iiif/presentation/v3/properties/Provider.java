
package info.freelibrary.iiif.presentation.v3.properties;

import static com.google.common.base.Preconditions.*;

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

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.ImageContent;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.util.IllegalArgumentI18nException;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * An organization or person that contributed to providing the content of the resource.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.LABEL, Constants.HOMEPAGE, Constants.LOGO,
    Constants.SEE_ALSO })
public class Provider {

    private URI myID;

    private Label myLabel;

    private List<Homepage> myHomepages;

    private List<ImageContent> myLogos;

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
    public Provider(final URI aID, final Label aLabel) {
        myID = checkNotNull(aID);
        myLabel = checkNotNull(aLabel);
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
        myID = checkNotNull(aID);
        myLabel = checkNotNull(aLabel);
        getLogos().add(checkNotNull(aLogo));
        getHomepages().add(checkNotNull(aHomepage));
    }

    /**
     * Creates a new provider for Jackson's deserialization process.
     */
    @SuppressWarnings("unused")
    private Provider() {
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The provider
     */
    public Provider setID(final URI aID) {
        myID = checkNotNull(aID);
        return this;
    }

    /**
     * Gets the ID.
     *
     * @return An ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Necessary for Jackson to be able to deserializer the provider.
     *
     * @param aType A provider type
     * @return The provider
     */
    @JsonSetter(Constants.TYPE)
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
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return ResourceTypes.AGENT;
    }

    /**
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    @JsonGetter(Constants.LABEL)
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
    @JsonSetter(Constants.LABEL)
    public Provider setLabel(final Label aLabel) {
        myLabel = checkNotNull(aLabel);
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
    @JsonSetter(Constants.HOMEPAGE)
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

        checkNotNull(aHomepageList);
        homepages.clear();
        homepages.addAll(aHomepageList);

        return this;
    }

    /**
     * Gets a list of provider homepages, initializing the list if this hasn't been done already.
     *
     * @return The provider's homepages
     */
    @JsonGetter(Constants.HOMEPAGE)
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
    @JsonSetter(Constants.LOGO)
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

        checkNotNull(aLogoList);
        logos.clear();
        logos.addAll(aLogoList);

        return this;
    }

    /**
     * Gets a list of provider logos, initializing the list if this hasn't been done already.
     *
     * @return The provider's logos
     */
    @JsonGetter(Constants.LOGO)
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
    @JsonGetter(Constants.SEE_ALSO)
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
    @JsonSetter(Constants.SEE_ALSO)
    public Provider setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        getSeeAlsoRefs().addAll(aSeeAlsoList);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myID, myLabel, myHomepages, myLogos, mySeeAlsoRefs);
    }

    @Override
    public boolean equals(final Object aObject) {
        if (aObject instanceof Provider) {
            final Provider otherProvider = (Provider) aObject;

            return Objects.equals(myID, otherProvider.myID) //
                    && Objects.equals(myLabel, otherProvider.myLabel) //
                    && Objects.equals(myHomepages, otherProvider.myHomepages) //
                    && Objects.equals(myLogos, otherProvider.myLogos) //
                    && Objects.equals(mySeeAlsoRefs, otherProvider.mySeeAlsoRefs);
        } else {
            return false;
        }
    }

    /**
     * Returns a JsonObject of this resource.
     *
     * @return A JsonObject of this resource
     */
    public JsonObject toJSON() {
        return JsonObject.mapFrom(this);
    }

    /**
     * Gets a string representation of the provider.
     */
    @Override
    public String toString() {
        return toJSON().encodePrettily();
    }

    /**
     * Returns a provider from its JSON representation.
     *
     * @param aJsonObject A provider in JSON form
     * @return This provider
     */
    public static Provider fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Provider.class);
    }

    /**
     * Returns a provider from its JSON representation.
     *
     * @param aJsonString A provider in string form
     * @return This provider
     */
    public static Provider fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
