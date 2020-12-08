
package info.freelibrary.iiif.presentation.v2;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.I18nRuntimeException;

import info.freelibrary.iiif.presentation.v2.properties.Attribution;
import info.freelibrary.iiif.presentation.v2.properties.Description;
import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.License;
import info.freelibrary.iiif.presentation.v2.properties.Logo;
import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.NavDate;
import info.freelibrary.iiif.presentation.v2.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v2.properties.Thumbnail;
import info.freelibrary.iiif.presentation.v2.properties.Type;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.v2.services.Service;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * An ordered list of manifests, and/or further collections. Collections allow easy advertising and browsing of the
 * manifests in a hierarchical structure, potentially with its own descriptive information. They can also provide
 * clients with a means to locate all of the manifests known to the publishing institution.
 */
public class Collection extends Resource<Collection> {

    static {
        DatabindCodec.mapper().findAndRegisterModules();
    }

    private static final URI CONTEXT = URI.create("http://iiif.io/api/presentation/2/context.json");

    private static final String TYPE = "sc:Collection";

    private static final int REQ_ARG_COUNT = 3;

    private NavDate myNavDate;

    private List<Manifest> myManifests;

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final String aID, final String aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final URI aID, final Label aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     * @param aMetadata A collection's metadata
     * @param aDescription A collection description
     * @param aThumbnail A collection thumbnail
     */
    public Collection(final String aID, final String aLabel, final Metadata aMetadata, final String aDescription,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, aMetadata, aDescription, aThumbnail, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     * @param aMetadata A collection's metadata
     * @param aDescription A collection description
     * @param aThumbnail A collection thumbnail
     */
    public Collection(final URI aID, final Label aLabel, final Metadata aMetadata, final Description aDescription,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, aMetadata, aDescription, aThumbnail, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation collection.
     */
    private Collection() {
        super(new Type(TYPE));
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The collection
     */
    @JsonSetter(Constants.NAV_DATE)
    public Collection setNavDate(final NavDate aNavDate) {
        myNavDate = aNavDate;
        return this;
    }

    /**
     * Gets a navigation date.
     *
     * @return The navigation date
     */
    @JsonGetter(Constants.NAV_DATE)
    public NavDate getNavDate() {
        return myNavDate;
    }

    /**
     * Gets the first manifest context.
     *
     * @return The manifest context
     */
    @JsonGetter(Constants.CONTEXT)
    public URI getContext() {
        return CONTEXT;
    }

    /**
     * Method used internally to set context from JSON.
     *
     * @param aContext A manifest context
     */
    @JsonSetter(Constants.CONTEXT)
    private void setContext(final String aContext) {
        if (!CONTEXT.equals(URI.create(aContext))) {
            throw new I18nRuntimeException();
        }
    }

    /**
     * Gets the manifests associated with this collection.
     *
     * @return The manifests associated with this collection
     */
    @JsonGetter(Constants.MANIFESTS)
    public List<Manifest> getManifests() {
        if (myManifests == null) {
            myManifests = new ArrayList<>();
        }

        return myManifests;
    }

    /**
     * Sets the manifests associated with this collection. 544
     *
     * @param aManifestList A list of manifests
     * @return This collection
     */
    @JsonSetter(Constants.MANIFESTS)
    public Collection setManifests(final List<Manifest> aManifestList) {
        myManifests = aManifestList;
        return this;
    }

    /**
     * Returns a JsonObject of the Collection manifest.
     *
     * @return A JsonObject of the Collection manifest
     */
    @JsonIgnore
    public JsonObject toJSON() {
        return JsonObject.mapFrom(this);
    }

    @Override
    @JsonIgnore
    public String toString() {
        return toJSON().encodePrettily();
    }

    /**
     * Returns a collection manifest from its JSON representation.
     *
     * @param aJsonObject A collection manifest in JSON form
     * @return The collection manifest
     */
    @JsonIgnore
    public static Collection fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Collection.class);
    }

    /**
     * Returns a collection manifest from its JSON representation.
     *
     * @param aJsonString A collection manifest in string form
     * @return The collection manifest
     */
    @JsonIgnore
    public static Collection fromString(final String aJsonString) {
        return Json.decodeValue(aJsonString, Collection.class);
    }

    @Override
    public Collection setLabel(final String aLabel) {
        return (Collection) super.setLabel(aLabel);
    }

    @Override
    public Collection setLabel(final Label aLabel) {
        return (Collection) super.setLabel(aLabel);
    }

    @Override
    public Collection setService(final Service<?> aService) {
        return (Collection) super.setService(aService);
    }

    @Override
    public Collection setMetadata(final Metadata aMetadata) {
        return (Collection) super.setMetadata(aMetadata);
    }

    @Override
    public Collection setDescription(final String aDescription) {
        return (Collection) super.setDescription(aDescription);
    }

    @Override
    public Collection setDescription(final Description aDescription) {
        return (Collection) super.setDescription(aDescription);
    }

    @Override
    public Collection setThumbnail(final Thumbnail aThumbnail) {
        return (Collection) super.setThumbnail(aThumbnail);
    }

    @Override
    public Collection setThumbnail(final String aURI) {
        return (Collection) super.setThumbnail(aURI);
    }

    @Override
    public Collection clearAttribution() {
        return (Collection) super.clearAttribution();
    }

    @Override
    public Collection setAttribution(final String aAttribution) {
        return (Collection) super.setAttribution(aAttribution);
    }

    @Override
    public Collection setAttribution(final Attribution aAttribution) {
        return (Collection) super.setAttribution(aAttribution);
    }

    @Override
    public Collection setLicense(final License aLicense) {
        return (Collection) super.setLicense(aLicense);
    }

    @Override
    public Collection setLicense(final String aURL) throws MalformedURLException {
        return (Collection) super.setLicense(aURL);
    }

    @Override
    public Collection setLogo(final Logo aLogo) {
        return (Collection) super.setLogo(aLogo);
    }

    @Override
    public Collection setLogo(final String aURI) {
        return (Collection) super.setLogo(aURI);
    }

    @Override
    public Collection setID(final String aURI) {
        return (Collection) super.setID(aURI);
    }

    @Override
    public Collection setID(final URI aID) {
        return (Collection) super.setID(aID);
    }

    @Override
    public Collection setWithin(final String aWithin) {
        return (Collection) super.setWithin(aWithin);
    }

    @Override
    public Collection setWithin(final URI aWithin) {
        return (Collection) super.setWithin(aWithin);
    }

    @Override
    public Collection clearViewingHint() {
        return (Collection) super.clearViewingHint();
    }

    @Override
    public Collection setViewingHint(final ViewingHint aViewingHint) {
        return (Collection) super.setViewingHint(aViewingHint);
    }

    @Override
    public Collection setViewingHint(final String aViewingHint) {
        return (Collection) super.setViewingHint(aViewingHint);
    }

    @Override
    public Collection setViewingHint(final Option aViewingHint) {
        return (Collection) super.setViewingHint(aViewingHint);
    }

    @Override
    public Collection setSeeAlso(final SeeAlso aSeeAlso) {
        return (Collection) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public Collection setSeeAlso(final String aSeeAlso) {
        return (Collection) super.setSeeAlso(aSeeAlso);
    }

    /**
     * The reference to a manifest that's included in a collection.
     */
    public static class Manifest {

        private static final String TYPE = "sc:Manifest";

        private URI myID;

        private Label myLabel;

        /**
         * Create a new collection manifest.
         */
        public Manifest() {
        }

        /**
         * Create a brief collection manifest from a full work manifest.
         *
         * @param aManifest A full manifest
         */
        public Manifest(final info.freelibrary.iiif.presentation.v2.Manifest aManifest) {
            setID(aManifest.getID());
            setLabel(aManifest.getLabel());
        }

        /**
         * Create a new collection manifest from the supplied ID and label.
         *
         * @param aID A manifest ID
         * @param aLabel A manifest label
         */
        public Manifest(final String aID, final String aLabel) {
            setID(aID);
            setLabel(aLabel);
        }

        /**
         * Create a new collection manifest from the supplied ID and label.
         *
         * @param aID A manifest ID
         * @param aLabel A manifest label
         */
        public Manifest(final URI aID, final Label aLabel) {
            myID = aID;
            myLabel = aLabel;
        }

        /**
         * Gets the collection manifest ID.
         *
         * @return The collection manifest ID
         */
        @JsonGetter(Constants.ID)
        public URI getID() {
            return myID;
        }

        /**
         * Sets the collection manifest ID.
         *
         * @param aID A collection manifest ID
         * @return The manifest
         */
        @JsonIgnore
        public Manifest setID(final URI aID) {
            myID = aID;
            return this;
        }

        /**
         * Sets the collection manifest ID.
         *
         * @param aID A collection manifest ID
         * @return The manifest
         */
        @JsonSetter(Constants.ID)
        public Manifest setID(final String aID) {
            myID = URI.create(aID);
            return this;
        }

        /**
         * Gets the collection manifest type.
         *
         * @return The collection manifest type
         */
        @JsonGetter(Constants.TYPE)
        public Type getType() {
            return new Type(TYPE);
        }

        /**
         * Gets the collection manifest label.
         *
         * @return The collection manifest label
         */
        @JsonGetter(Constants.LABEL)
        public Label getLabel() {
            return myLabel;
        }

        /**
         * Sets the collection manifest label.
         *
         * @param aLabel The collection manifest label
         * @return The manifest
         */
        @JsonSetter(Constants.LABEL)
        public Manifest setLabel(final Label aLabel) {
            myLabel = aLabel;
            return this;
        }

        /**
         * Sets the collection manifest label.
         *
         * @param aLabel The collection manifest label
         * @return The manifest
         */
        @JsonIgnore
        public Manifest setLabel(final String aLabel) {
            myLabel = new Label(aLabel);
            return this;
        }

        /**
         * Gives Jackson a way to "set" this, though it's a static string in this class.
         *
         * @return The collection manifest type
         */
        @JsonSetter(Constants.TYPE)
        private Manifest setType(final Type aType) {
            return this;
        }
    }

}