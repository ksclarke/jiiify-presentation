
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.SummaryDeserializer;

/**
 * A short textual summary intended to be conveyed to the user when the metadata entries for the resource are not
 * being displayed. This could be used as a brief description for item level search results, for small-screen
 * environments, or as an alternative user interface when the metadata property is not currently being rendered.
 */
@JsonDeserialize(using = SummaryDeserializer.class)
public class Summary extends I18nProperty<Summary> {

    /**
     * Creates a summary from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for the summary
     */
    public Summary(final Value... aValue) {
        super(aValue);
    }

    /**
     * Creates a summary from the supplied String(s).
     *
     * @param aValue A list of string values for the summary
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
