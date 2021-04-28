
package info.freelibrary.iiif.presentation.v3.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.v3.Constants;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A resource that is an alternative, non-IIIF representation of the resource that has the <code>rendering</code>
 * property. Examples include a rendering of a book as a PDF or EPUB, a slide deck with images of a building, or a 3D
 * model of a statue.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.LABEL, Constants.FORMAT, Constants.LANGUAGE })
public class Rendering extends AbstractLinkProperty<Rendering> implements Localized<Rendering> {

    private List<String> myLanguages;

    /**
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID
     * @param aType A rendering type
     * @param aLabel A rendering label
     */
    public Rendering(final URI aID, final String aType, final Label aLabel) {
        super(aID, aType, aLabel);
    }

    /**
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID in string form
     * @param aType A rendering type
     * @param aLabel A rendering label in string form
     */
    public Rendering(final String aID, final String aType, final String aLabel) {
        this(URI.create(aID), aType, new Label(aLabel));
    }

    /**
     * Constructs the rendering for Jackson's deserialization process.
     */
    @SuppressWarnings("unused")
    private Rendering() {
    }

    @Override
    public Rendering setID(final URI aID) {
        return (Rendering) super.setID(aID);
    }

    /**
     * Sets the ID in string form.
     *
     * @param aID An ID in string form
     * @return The resource whose ID is being set
     */
    @JsonIgnore
    public Rendering setID(final String aID) {
        return (Rendering) super.setID(URI.create(aID));
    }

    @Override
    public Rendering setType(final String aType) {
        return (Rendering) super.setType(aType);
    }

    /**
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    public Label getLabel() {
        return super.getNullableLabel();
    }

    @Override
    public Rendering setLabel(final Label aLabel) {
        return (Rendering) super.setLabel(aLabel);
    }

    /**
     * Sets the label of the rendering.
     *
     * @param aLabel A rendering's label
     * @return The rendering
     */
    @JsonIgnore
    public Rendering setLabel(final String aLabel) {
        return (Rendering) super.setLabel(new Label(aLabel));
    }

    @Override
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return super.getFormatMediaType();
    }

    @Override
    public Optional<String> getFormat() {
        return super.getFormat();
    }

    @Override
    @JsonIgnore
    public Rendering setFormat(final MediaType aMediaType) {
        return (Rendering) super.setFormat(aMediaType);
    }

    @Override
    public Rendering setFormat(final String aFormat) {
        return (Rendering) super.setFormat(aFormat);
    }

    @Override
    public List<String> getLanguages() {
        if (myLanguages == null) {
            myLanguages = new ArrayList<>();
        }

        return myLanguages;
    }

    @Override
    public Rendering setLanguages(final String... aLangArray) {
        return (Rendering) Localized.super.setLanguages(aLangArray);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(getLanguages());
    }

    @Override
    public boolean equals(final Object aObject) {
        if (!super.equals(aObject)) {
            return false;
        }

        if (getClass() != aObject.getClass()) {
            return false;
        }

        return getLanguages().equals(((Rendering) aObject).getLanguages());
    }

    /**
     * Returns a JsonObject of this resource.
     *
     * @return A JsonObject of this resource
     */
    @Override
    public JsonObject toJSON() {
        return JsonObject.mapFrom(this);
    }

    /**
     * Returns a rendering from its JSON representation.
     *
     * @param aJsonObject A rendering in JSON form
     * @return This rendering
     */
    public static Rendering fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Rendering.class);
    }

    /**
     * Returns a rendering from its JSON representation.
     *
     * @param aJsonString A rendering in string form
     * @return This rendering
     */
    public static Rendering fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
