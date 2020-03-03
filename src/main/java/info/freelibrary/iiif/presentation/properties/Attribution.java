
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * Text that must be shown when the resource it is associated with is displayed or used. For example, this could be
 * used to present copyright or ownership statements, or simply an acknowledgement of the owning and/or publishing
 * institution. Clients should try to match the language preferred by the user, and if the preferred language is
 * unknown or unavailable, then the client may choose which value to display. If there are multiple values of the same
 * or unspecified language, then all of those values must be displayed.
 */
@JsonDeserialize(using = AttributionDeserializer.class)
public class Attribution extends I18nProperty<Attribution> {

    /**
     * Creates an attribution from the supplied internationalizations.
     *
     * @param aI18nArray An array of internationalizations for attribution
     */
    public Attribution(final I18n... aI18nArray) {
        super(aI18nArray);
    }

    /**
     * Creates an attribution from the supplied String(s).
     *
     * @param aStringArray An array of string values for attribution
     */
    public Attribution(final String... aStringArray) {
        super(aStringArray);
    }

    @Override
    @JsonGetter(Constants.ATTRIBUTION)
    protected Object toMap() {
        return super.toMap();
    }

}
