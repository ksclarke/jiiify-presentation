
package info.freelibrary.iiif.presentation.v3.properties;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.freelibrary.util.warnings.Eclipse;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A containing resource that includes the resource that has the <code>partOf</code> property. For example, the
 * <code>partOf</code> property on a Canvas can be used to reference an external Manifest in order to enable the
 * discovery of further relevant information. Similarly, a Manifest can reference a containing Collection using
 * <code>partOf</code> to aid in navigation.
 */
public class PartOf extends AbstractLinkProperty<PartOf> {

    /**
     * Creates a partOf reference.
     *
     * @param aID A partOf ID
     * @param aType A partOf type
     */
    public PartOf(final URI aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Creates a partOf reference.
     *
     * @param aID A partOf ID in string form
     * @param aType A partOf type
     */
    public PartOf(final String aID, final String aType) {
        this(URI.create(aID), aType);
    }

    /**
     * Creates a new partOf for Jackson's deserialization.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private PartOf() {
        super();
    }

    @Override
    public PartOf setID(final URI aID) {
        return (PartOf) super.setID(aID);
    }

    /**
     * Sets the ID from the supplied string.
     *
     * @param aID The ID in string form
     * @return The resource whose ID is being set
     */
    @JsonIgnore
    public PartOf setID(final String aID) {
        return (PartOf) super.setID(URI.create(aID));
    }

    @Override
    public PartOf setType(final String aType) {
        return (PartOf) super.setType(aType);
    }

    /**
     * Gets an optional descriptive label.
     *
     * @return An optional descriptive label
     */
    public Optional<Label> getLabel() {
        return Optional.ofNullable(super.getNullableLabel());
    }

    @Override
    public PartOf setLabel(final Label aLabel) {
        return (PartOf) super.setLabel(aLabel);
    }

    /**
     * Sets the label of the PartOf.
     *
     * @param aLabel A PartOf's label
     * @return The PartOf
     */
    @JsonIgnore
    public PartOf setLabel(final String aLabel) {
        return (PartOf) super.setLabel(new Label(aLabel));
    }

    @Override
    public PartOf setLanguages(final String... aLangArray) {
        return (PartOf) super.setLanguages(aLangArray);
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

        return getLanguages().equals(((PartOf) aObject).getLanguages());
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
     * Returns a PartOf from its JSON representation.
     *
     * @param aJsonObject A PartOf in JSON form
     * @return This PartOf
     */
    public static PartOf fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), PartOf.class);
    }

    /**
     * Returns a PartOf from its JSON representation.
     *
     * @param aJsonString A PartOf in string form
     * @return This PartOf
     */
    public static PartOf fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
