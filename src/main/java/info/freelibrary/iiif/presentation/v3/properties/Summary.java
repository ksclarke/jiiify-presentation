
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A short textual summary intended to be conveyed to the user when the metadata entries for the resource are not being
 * displayed. This could be used as a brief description for item level search results, for small-screen environments, or
 * as an alternative user interface when the metadata property is not currently being rendered.
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
    public Summary setStrings(final String... aStringArray) {
        return (Summary) super.setStrings(aStringArray);
    }

    @Override
    public Summary setI18ns(final I18n... aI18nArray) {
        return (Summary) super.setI18ns(aI18nArray);
    }

    @Override
    public Summary addStrings(final String... aStringArray) {
        return (Summary) super.addStrings(aStringArray);
    }

    @Override
    public Summary addI18ns(final I18n... aI18nArray) {
        return (Summary) super.addI18ns(aI18nArray);
    }

    @Override
    @JsonGetter(JsonKeys.SUMMARY)
    protected Object toMap() {
        return super.toMap();
    }

}
