
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A containing resource that includes the resource that has the <code>partOf</code> property. For example, the
 * <code>partOf</code> property on a Canvas can be used to reference an external Manifest in order to enable the
 * discovery of further relevant information. Similarly, a Manifest can reference a containing Collection using
 * <code>partOf</code> to aid in navigation.
 */
@JsonInclude(Include.NON_EMPTY)
public class PartOf extends AbstractLinkProperty<PartOf> {

    /**
     * Creates a partOf reference.
     *
     * @param aID A partOf ID
     * @param aType A partOf type
     */
    public PartOf(final String aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Creates a new partOf for Jackson's deserialization.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private PartOf() {
        super();
    }

    /**
     * Sets the ID.
     *
     * @param aID The ID
     * @return The resource whose ID is being set
     */
    @Override
    @JsonSetter(JsonKeys.ID)
    public PartOf setID(final String aID) {
        return (PartOf) super.setID(aID);
    }

    /**
     * Sets the partOf type.
     *
     * @param aType A type
     * @return The partOf
     */
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
    public Optional<MediaType> getFormat() {
        return super.getFormat();
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The property whose label is being set
     */
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
        if (!super.equals(aObject) || getClass() != aObject.getClass()) {
            return false;
        }

        return getLanguages().equals(((PartOf) aObject).getLanguages());
    }

    /**
     * Returns a PartOf from its JSON representation.
     *
     * @param aJsonString A PartOf in string form
     * @throws JsonParsingException If the supplied JSON string cannot be successfully parsed
     * @return This PartOf
     */
    public static PartOf from(final String aJsonString) {
        try {
            return JSON.getReader(PartOf.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
