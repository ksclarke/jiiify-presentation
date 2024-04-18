
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A short textual summary intended to be conveyed to the user when the metadata entries for the resource are not being
 * displayed. This could be used as a brief description for item level search results, for small-screen environments, or
 * as an alternative user interface when the metadata property is not currently being rendered.
 */
@JsonDeserialize(using = SummaryDeserializer.class)
public class Summary extends I18nProperty<Summary> {

    /**
     * Creates a summary using the 'none' language tag.
     *
     * @param aValue A value
     */
    public Summary(final String aValue) {
        this(new I18n(I18n.DEFAULT_LANG, aValue, true));
    }

    /**
     * Creates a summary using the supplied language tag and value.
     *
     * @param aLangTag A language tag
     * @param aValue A value of the summary
     */
    public Summary(final String aLangTag, final String aValue) {
        this(new I18n(aLangTag, aValue, true));
    }

    /**
     * Creates a summary from the supplied internationalization(s).
     *
     * @param aI18nArray An array of internationalizations for the summary
     */
    public Summary(final I18n... aI18nArray) {
        super(aI18nArray);
    }

    /**
     * Creates a summary from the supplied internationalization(s).
     *
     * @param aI18nList A list of internationalizations for the summary
     */
    public Summary(final List<I18n> aI18nList) {
        super(aI18nList);
    }

    /**
     * Creates a summary from the supplied internationalization(s). The submitted array should alternate between
     * language code and string value.
     *
     * @param aDataArray An array of language codes and string values
     */
    public Summary(final String... aDataArray) {
        super(I18nUtils.parseArray(true, aDataArray));
    }

    /**
     * Sets the internationalizations of the property, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of I18n(s).
     * @return This summary
     */
    @Override
    public Summary setI18ns(final I18n... aI18nArray) {
        return (Summary) super.setI18ns(aI18nArray);
    }

    /**
     * Sets the internationalizations of the property, removing all other previous internationalizations.
     *
     * @param aI18nList A list of I18n(s).
     * @return This summary
     */
    @Override
    public Summary setI18ns(final List<I18n> aI18nList) {
        return (Summary) super.setI18ns(aI18nList);
    }

    @Override
    @JsonGetter(JsonKeys.SUMMARY)
    protected Object toMap() {
        return super.toMap();
    }

}
