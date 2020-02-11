
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.SummaryDeserializer;

/**
 * A longer-form prose description of the object or resource that the property is attached to, intended to be conveyed
 * to the user as a full text description, rather than a simple label and value. It may be in simple HTML or plain
 * text. It can duplicate any of the information from the metadata fields, along with additional information required
 * to understand what is being displayed. Clients should have a way to display the descriptions of manifests and
 * canvases, and may have a way to view the information about other resources.
 */
@JsonDeserialize(using = SummaryDeserializer.class)
public class Summary extends I18nProperty<Summary> {

    /**
     * Creates a description from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for description
     */
    public Summary(final Value... aValue) {
        super(aValue);
    }

    /**
     * Creates a description from the supplied String(s).
     *
     * @param aValue A list of string values for description
     */
    public Summary(final String... aValue) {
        super(aValue);
    }

    @Override
    @JsonGetter(Constants.SUMMARY)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }
}
