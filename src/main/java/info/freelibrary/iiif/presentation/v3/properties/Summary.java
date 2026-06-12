
package info.freelibrary.iiif.presentation.v3.properties;

import static java.util.stream.Collectors.toCollection;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.SummaryDeserializer;

import java.util.ArrayList;
import java.util.List;

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
     * Creates a summary from the supplied internationalization(s).
     *
     * @param aI18nList A list of internationalizations for the summary
     */
    public Summary(final List<I18n> aI18nList) {
        super(aI18nList);
    }

    /**
     * Creates a summary using the 'none' language tag.
     *
     * @param aValue A value
     */
    public Summary(final String aValue) {
        this(new I18n(I18n.DEFAULT_LANG, aValue, true));
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
     * Creates a summary using the supplied language tag and value.
     *
     * @param aLangTag A language tag
     * @param aValue A value of the summary
     */
    public Summary(final String aLangTag, final String aValue) {
        this(new I18n(aLangTag, aValue, true));
    }

    /**
     * Creates a summary from another summary.
     *
     * @param aSummary The summary to deep copy
     */
    public Summary(final Summary aSummary) {
        super(aSummary.getI18ns().stream().map(I18n::new).collect(toCollection(ArrayList::new)));
    }

    /**
     * Creates a copy of the summary.
     *
     * @return A copy of the summary
     */
    public Summary copy() {
        return new Summary(this);
    }

    @Override
    @JsonGetter(JsonKeys.SUMMARY)
    protected Object toMap() {
        return super.toMap();
    }

}
