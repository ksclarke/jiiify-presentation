
package info.freelibrary.iiif.presentation.properties;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;

/**
 * An internationalized entry.
 */
abstract class I18nEntry {

    protected abstract Logger getLogger();

    /**
     * An entry with a label and value.
     */
    @JsonPropertyOrder({ "label", "value" })
    public class Entry {

        private final Label myLabel;

        private final Value myValue;

        /**
         * Creates an entry from the supplied label and value.
         *
         * @param aLabel A label in string form
         * @param aValue A value in string form
         */
        Entry(final String aLabel, final String aValue) {
            Objects.requireNonNull(aLabel, getLogger().getMessage(MessageCodes.JPA_002));
            Objects.requireNonNull(aValue, getLogger().getMessage(MessageCodes.JPA_022));

            myLabel = new Label(aLabel);
            myValue = new Value(aValue);
        }

        /**
         * Creates an entry from the supplied label and value.
         *
         * @param aLabel A label
         * @param aValue A value
         */
        Entry(final Label aLabel, final Value aValue) {
            Objects.requireNonNull(aLabel, getLogger().getMessage(MessageCodes.JPA_002));
            Objects.requireNonNull(aValue, getLogger().getMessage(MessageCodes.JPA_022));

            myLabel = aLabel;
            myValue = aValue;
        }

        /**
         * Gets the label for the entry.
         *
         * @return The label for the entry
         */
        @JsonGetter(Constants.LABEL)
        public Label getLabel() {
            return myLabel;
        }

        /**
         * Gets the entry's value.
         *
         * @return The entry's value
         */
        @JsonGetter(Constants.VALUE)
        public Value getValue() {
            return myValue;
        }

        /**
         * Gets the outer class of this inner class.
         *
         * @return The outer class
         */
        @JsonIgnore
        protected Class getOuterClass() {
            return I18nEntry.this.getClass();
        }
    }

}
