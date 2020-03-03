
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * The overall description of the structure and properties of the digital representation of an object. It carries
 * information needed for the viewer to present the digitized content to the user, such as a title and other
 * descriptive information about the object or the intellectual work that it conveys. Each manifest describes how to
 * present a single object such as a book, a photograph, or a statue.
 */
public class Manifest extends Resource<Manifest> {

    static {
        DatabindCodec.mapper().findAndRegisterModules();
    }

    private static final String TYPE = "sc:Manifest";

    private static final URI CONTEXT = URI.create("http://iiif.io/api/presentation/3/context.json");

    private static final int REQ_ARG_COUNT = 3;

    private final List<URI> myContexts = Stream.of(CONTEXT).collect(Collectors.toList());

    private final List<Sequence> mySequences = new ArrayList<>();

    private NavDate myNavDate;

    private ViewingDirection myViewingDirection;

    /**
     * Creates a IIIF presentation manifest.
     *
     * @param aIdString A manifest ID in string form
     * @param aLabelString A manifest label in string form
     * @throws IllegalArgumentException If the supplied ID is not a valid URI
     */
    public Manifest(final String aIdString, final String aLabelString) {
        super(TYPE, aIdString, aLabelString, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation manifest.
     *
     * @param aID A manifest ID
     * @param aLabel A manifest label
     */
    public Manifest(final URI aID, final Label aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation manifest.
     *
     * @param aIdString A manifest ID in string form
     * @param aLabelString A manifest label in string form
     * @param aMetadata A manifest's metadata
     * @param aSummaryString A manifest summary in string form
     * @param aThumbnail A manifest thumbnail
     * @throws URISyntaxException If the supplied ID is not a valid URI
     */
    public Manifest(final String aIdString, final String aLabelString, final Metadata aMetadata,
            final String aSummaryString, final Thumbnail aThumbnail) throws URISyntaxException {
        super(TYPE, aIdString, aLabelString, aMetadata, aSummaryString, aThumbnail, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation manifest.
     *
     * @param aID A manifest ID
     * @param aLabel A manifest label
     * @param aMetadata A manifest's metadata
     * @param aSummary A manifest summary
     * @param aThumbnail A manifest thumbnail
     */
    public Manifest(final URI aID, final Label aLabel, final Metadata aMetadata, final Summary aSummary,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, aMetadata, aSummary, aThumbnail, REQ_ARG_COUNT);
    }

    /**
     * A private constructor used for serialization purposes.
     */
    private Manifest() {
        super(new Type(TYPE));
    }

    /**
     * Gets the manifest context.
     *
     * @return The manifest context
     */
    @JsonIgnore
    public List<URI> getContexts() {
        if (myContexts.isEmpty()) {
            return null;
        } else {
            return myContexts;
        }
    }

    /**
     * Gets the first manifest context.
     *
     * @return The manifest context
     */
    @JsonIgnore
    public URI getContext() {
        return CONTEXT;
    }

    /**
     * Adds an array of new context URIs to the manifest.
     *
     * @param aUriArray Manifest context URIs(s)
     * @return The manifest
     */
    public Manifest addContext(final URI... aUriArray) {
        Collections.addAll(myContexts, aUriArray);
        return this;
    }

    /**
     * Adds an array of new context URIs, in string form, to the manifest.
     *
     * @param aStringArray Manifest context URI(s) in string form
     * @return The manifest
     */
    public Manifest addContext(final String... aStringArray) {
        Objects.requireNonNull(aStringArray, MessageCodes.JPA_007);

        for (final String uri : aStringArray) {
            Objects.requireNonNull(aStringArray, MessageCodes.JPA_007);

            myContexts.add(URI.create(uri));
        }

        return this;
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The manifest
     */
    @JsonSetter(Constants.NAV_DATE)
    public Manifest setNavDate(final NavDate aNavDate) {
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
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The manifest
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Manifest setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(Constants.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    /**
     * Adds one or more sequences to the manifest.
     *
     * @param aSequence Sequence(s) to add to the manifest
     * @return The manifest
     */
    public Manifest addSequence(final Sequence... aSequence) {
        Objects.requireNonNull(aSequence, MessageCodes.JPA_008);

        for (final Sequence sequence : aSequence) {
            Objects.requireNonNull(sequence, MessageCodes.JPA_008);

            if (!mySequences.add(sequence)) {
                throw new UnsupportedOperationException();
            }
        }

        return this;
    }

    /**
     * Gets the manifest's sequences.
     *
     * @return The manifest's sequences
     */
    @JsonGetter(Constants.SEQUENCES)
    public List<Sequence> getSequences() {
        return mySequences;
    }

    /**
     * Sets the manifest sequences to the supplied one(s).
     *
     * @param aSequence The manifest's sequence(s)
     * @return The manifest
     */
    @JsonGetter(Constants.SEQUENCES)
    public Manifest setSequences(final Sequence... aSequence) {
        mySequences.clear();
        return addSequence(aSequence);
    }

    /**
     * Returns a JsonObject of the Manifest.
     *
     * @return A JsonObject of the Manifest
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
     * Returns a Manifest from its JSON representation.
     *
     * @param aJsonObject A Manifest in JSON form
     * @return The manifest
     */
    @JsonIgnore
    public static Manifest fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Manifest.class);
    }

    /**
     * Returns a Manifest from its JSON representation.
     *
     * @param aJsonString A manifest in string form
     * @return The manifest
     */
    @JsonIgnore
    public static Manifest fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }

    /**
     * Method used internally to set context from JSON.
     *
     * @param aContextString A manifest context in string form
     */
    @JsonSetter(Constants.CONTEXT)
    private void setContext(final String aContextString) {
        if (!CONTEXT.equals(URI.create(aContextString))) {
            throw new I18nRuntimeException();
        }
    }

    /**
     * Gets the manifest context.
     *
     * @return The manifest context
     */
    @JsonGetter(Constants.CONTEXT)
    private Object getContextJson() {
        if (myContexts.size() == 1) {
            return myContexts.get(0);
        } else if (myContexts.size() > 1) {
            return myContexts;
        } else {
            return null;
        }
    }
}
