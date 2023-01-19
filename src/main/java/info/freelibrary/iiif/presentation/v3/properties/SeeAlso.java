
package info.freelibrary.iiif.presentation.v3.properties;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A link to a machine readable document that semantically describes the resource with the seeAlso property, such as an
 * XML or RDF description. This document could be used for search and discovery or inferencing purposes, or just to
 * provide a longer description of the resource. The profile and format properties of the document should be given to
 * help the client to make appropriate use of the document.
 */
public class SeeAlso extends AbstractLinkProperty<SeeAlso> {

    /**
     * Creates a new see also value from the supplied string ID and string type. Constant values for type can be found
     * in {@link ResourceTypes}.
     *
     * @param aID An ID in string form
     * @param aType A type
     */
    public SeeAlso(final String aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Creates a new see also value from the supplied ID and string type. Constant values for type can be found in
     * {@link ResourceTypes}.
     *
     * @param aID An ID
     * @param aType A type
     */
    public SeeAlso(final URI aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Constructs the see also reference for Jackson's deserialization process.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SeeAlso() {
        super();
    }

    /**
     * Sets the ID in string form.
     *
     * @param aID An ID in string form
     * @return The resource whose ID is being set
     */
    @JsonSetter(JsonKeys.ID)
    public SeeAlso setID(final String aID) {
        return (SeeAlso) super.setID(URI.create(aID));
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The resource whose ID is being set
     */
    @Override
    @JsonIgnore
    public SeeAlso setID(final URI aID) {
        return (SeeAlso) super.setID(aID);
    }

    /**
     * Sets the resource type.
     *
     * @param aType A resource type
     * @return The resource whose type is being set
     */
    @Override
    @JsonSetter(JsonKeys.TYPE)
    public SeeAlso setType(final String aType) {
        return (SeeAlso) super.setType(aType);
    }

    /**
     * Gets format as a media type.
     *
     * @return An optional media type format
     */
    @Override
    @JsonIgnore
    public Optional<MediaType> getFormat() {
        return super.getFormat();
    }

    /**
     * Sets format in string form.
     *
     * @param aFormat A resource's format
     * @return The resource whose format is being set
     */
    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public SeeAlso setFormat(final String aFormat) {
        return (SeeAlso) super.setFormat(aFormat);
    }

    /**
     * Sets format.
     *
     * @param aMediaType A resource's format
     * @return The resource whose format is being set
     */
    @Override
    @JsonIgnore
    public SeeAlso setFormat(final MediaType aMediaType) {
        return (SeeAlso) super.setFormat(aMediaType);
    }

    /**
     * Sets the profile.
     *
     * @param aProfile A profile
     * @return The resource whose profile is being set
     */
    @Override
    @JsonIgnore
    public SeeAlso setProfile(final URI aProfile) {
        return (SeeAlso) super.setProfile(aProfile);
    }

    /**
     * Sets the profile in string form.
     *
     * @param aProfile A profile in string form
     * @return The resource whose profile is being set
     */
    @JsonSetter(JsonKeys.PROFILE)
    public SeeAlso setProfile(final String aProfile) {
        return (SeeAlso) super.setProfile(URI.create(aProfile));
    }

    /**
     * Gets an optional descriptive label.
     *
     * @return An optional descriptive label
     */
    @JsonGetter(JsonKeys.LABEL)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Label> getLabel() {
        return Optional.ofNullable(super.getNullableLabel());
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The resource whose label is being set
     */
    @Override
    @JsonSetter(JsonKeys.LABEL)
    public SeeAlso setLabel(final Label aLabel) {
        return (SeeAlso) super.setLabel(aLabel);
    }

    /**
     * Sets the label of the SeeAlso.
     *
     * @param aLabel A SeeAlso's label
     * @return The SeeAlso
     */
    @JsonIgnore
    public SeeAlso setLabel(final String aLabel) {
        return (SeeAlso) super.setLabel(new Label(aLabel));
    }

    @Override
    @JsonIgnore
    public SeeAlso setLanguages(final String... aLangArray) {
        return (SeeAlso) super.setLanguages(aLangArray);
    }

    /**
     * A private getter that Jackson uses in deserialization.
     *
     * @return The SeeAlso's profile URI as a string
     */
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_NULL)
    private String getProfileAsString() {
        final Optional<URI> profile = super.getProfile();
        return profile.isEmpty() ? null : profile.get().toString();
    }

    /**
     * Returns a SeeAlso from its JSON representation.
     *
     * @param aJsonString A SeeAlso in string form
     * @return A SeeAlso
     */
    public static SeeAlso from(final String aJsonString) {
        try {
            return JSON.getReader(SeeAlso.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
