
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.LabelDeserializer;

/**
 * A label property.
 */
@JsonDeserialize(using = LabelDeserializer.class)
public class Label extends I18nProperty<Label> {

    /**
     * Creates a label from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for the label
     */
    public Label(final Value... aValue) {
        super(aValue);
    }

    /**
     * Creates a label from the supplied String(s).
     *
     * @param aValue A list of string values for label
     */
    public Label(final String... aValue) {
        super(aValue);
    }

    @Override
    @JsonGetter(Constants.LABEL)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }

}
