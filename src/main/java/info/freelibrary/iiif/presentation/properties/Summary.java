
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A short textual summary intended to be conveyed to the user when the metadata entries for the resource are not
 * being displayed. This could be used as a brief description for item level search results, for small-screen
 * environments, or as an alternative user interface when the metadata property is not currently being rendered.
 */
@JsonDeserialize(using = SummaryDeserializer.class)
public class Summary extends I18nProperty<Summary> {

    /**
     * Creates a summary from the supplied internationalization(s).
     *
     * @param aI18nArray An array of internationalizations for the summary
     */
    public Summary(final I18n... aI18nArray) {
        super(aI18nArray);
    }

    /**
     * Creates a summary from the supplied string(s).
     *
     * @param aStringArray An array of strings for the summary
     */
    public Summary(final String... aStringArray) {
        super(aStringArray);
    }

    @Override
    @JsonGetter(Constants.SUMMARY)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }

}
