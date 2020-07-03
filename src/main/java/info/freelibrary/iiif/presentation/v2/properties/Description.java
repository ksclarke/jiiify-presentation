
package info.freelibrary.iiif.presentation.v2.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.DescriptionDeserializer;

/**
 * A longer-form prose description of the object or resource that the property is attached to, intended to be conveyed
 * to the user as a full text description, rather than a simple label and value. It may be in simple HTML or plain text.
 * It can duplicate any of the information from the metadata fields, along with additional information required to
 * understand what is being displayed. Clients should have a way to display the descriptions of manifests and canvases,
 * and may have a way to view the information about other resources.
 */
@JsonDeserialize(using = DescriptionDeserializer.class)
public class Description extends I18nProperty<Description> {

    /**
     * Creates a description from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for description
     */
    public Description(final Value... aValue) {
        super(aValue);
    }

    /**
     * Creates a description from the supplied String(s).
     *
     * @param aValue A list of string values for description
     */
    public Description(final String... aValue) {
        super(aValue);
    }

    @Override
    public Description addValue(final String... aValueArray) {
        return (Description) super.addValue(aValueArray);
    }

    @Override
    public Description addValue(final Value... aValueArray) {
        return (Description) super.addValue(aValueArray);
    }

    @Override
    public Description setValue(final String... aValueArray) {
        return (Description) super.setValue(aValueArray);
    }

    @Override
    @JsonGetter(Constants.DESCRIPTION)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }
}
