
package info.freelibrary.iiif.presentation.v2.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v2.utils.AttributionDeserializer;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * Text that must be shown when the resource it is associated with is displayed or used. For example, this could be used
 * to present copyright or ownership statements, or simply an acknowledgement of the owning and/or publishing
 * institution. Clients should try to match the language preferred by the user, and if the preferred language is unknown
 * or unavailable, then the client may choose which value to display. If there are multiple values of the same or
 * unspecified language, then all of those values must be displayed.
 */
@JsonDeserialize(using = AttributionDeserializer.class)
public class Attribution extends I18nProperty<Attribution> {

    /**
     * Creates an attribution from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for attribution
     */
    public Attribution(final Value... aValue) {
        super(aValue);
    }

    /**
     * Creates an attribution from the supplied String(s).
     *
     * @param aValue A list of string values for attribution
     */
    public Attribution(final String... aValue) {
        super(aValue);
    }

    @Override
    public Attribution addValue(final String... aValueArray) {
        return (Attribution) super.addValue(aValueArray);
    }

    @Override
    public Attribution addValue(final Value... aValueArray) {
        return (Attribution) super.addValue(aValueArray);
    }

    @Override
    public Attribution setValue(final String... aValueArray) {
        return (Attribution) super.setValue(aValueArray);
    }

    @Override
    @JsonGetter(Constants.ATTRIBUTION)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }

}
