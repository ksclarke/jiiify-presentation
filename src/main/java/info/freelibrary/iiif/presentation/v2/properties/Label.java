
package info.freelibrary.iiif.presentation.v2.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.LabelDeserializer;

/**
 * A human readable label, name or title for the resource. This property is intended to be displayed as a short, textual
 * surrogate for the resource if a human needs to make a distinction between it and similar resources, for example
 * between pages or between a choice of images to display.
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
    public Label addValue(final String... aValueArray) {
        return (Label) super.addValue(aValueArray);
    }

    @Override
    public Label addValue(final Value... aValueArray) {
        return (Label) super.addValue(aValueArray);
    }

    @Override
    public Label setValue(final String... aValueArray) {
        return (Label) super.setValue(aValueArray);
    }

    @Override
    @JsonGetter(Constants.LABEL)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }

}
