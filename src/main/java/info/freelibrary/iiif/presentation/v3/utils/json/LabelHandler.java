/**
 *
 */

package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.I18n;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;
import info.freelibrary.json.JsonParser;
import info.freelibrary.json.Location;
import info.freelibrary.json.ParseException;

/**
 * A handler that parses resource labels.
 */
public class LabelHandler extends AbstractHandler<Label, List<String>> {

    /**
     * The logger used by the label handler.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LabelHandler.class, MessageCodes.BUNDLE);

    /**
     * An I18n language tag.
     */
    private String myI18nLang;

    /**
     * A value for the I18n values array.
     */
    private String myI18nValue;

    /**
     * The nesting level at which we started parsing.
     */
    private int myLevel;

    /**
     * Creates a new label handler.
     */
    public LabelHandler() {
        setObject(Label.empty());
    }

    @Override
    public void setJsonParser(final JsonParser aParser) {
        super.setJsonParser(aParser);
        myLevel = myParser.getLocation().getNestingLevel();
    }

    @Override
    public void endPropertyName(final Label aLabel, final String aName) {
        myI18nLang = aName; // Record the I18n language tag
    }

    @Override
    public List<String> startArray() {
        return new ArrayList<>(); // Create a new I18n value array
    }

    @Override
    public void endString(final String aValue) {
        myI18nValue = aValue; // Record the current I18n array value
    }

    @Override
    public void endArrayValue(final List<String> aList) {
        aList.add(myI18nValue); // Write current array value to the list
    }

    @Override
    public void endArray(final List<String> aList) {
        getObject().addI18ns(new I18n(myI18nLang, aList));
    }

    @Override
    public void endJsonObject(final Label aLabel) {
        LOGGER.debug("Finished parsing: {}", aLabel.getClass().getSimpleName());
        myParser.removeHandler();
    }

    @Override
    public Label startJsonObject() {
        final Location location = myParser.getLocation();

        if (location.getNestingLevel() != myLevel) {
            throw new ParseException(location, "Unexpected JSON object in Label");
        }

        return getObject();
    }
}
