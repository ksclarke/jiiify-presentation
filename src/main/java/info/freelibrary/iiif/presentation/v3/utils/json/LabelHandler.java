/**
 *
 */

package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.JsonHandler;
import info.freelibrary.json.JsonParser;

/**
 * A handler that parses resource labels.
 */
public class LabelHandler implements JsonHandler<Label, List<?>, Label> {

    /**
     * The logger used by the label handler.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LabelHandler.class, MessageCodes.BUNDLE);

    /**
     * The Label handler's parser.
     */
    private JsonParser myParser;

    /**
     * The label we're building.
     */
    private Label myLabel;

    /**
     * The level of the label.
     */
    private int myLevel;

    @Override
    public void setJsonParser(final JsonParser aParser) {
        if (myParser != null) {
            throw new IllegalStateException(LOGGER.getMessage(MessageCodes.JPA_121));
        }

        myParser = aParser;
    }

    @Override
    public Consumer<Label> getObjectConsumer() {
        return label -> {
            myLabel = label;
            myLevel = myParser.getLocation().getNestingLevel();
        };
    }

    @Override
    public void endPropertyName(final Label aLabel, final String aName) {
        LOGGER.debug("name: {}", aName);
        LOGGER.debug("=> " + myParser.getLocation().getNestingLevel());
    }

    @Override
    public List<?> startArray() {
        return new ArrayList<>();
    }

    @Override
    public void endArray(final List<?> aList) {
        LOGGER.debug("end list size: {}", aList.size());
    }

    @Override
    public Label startJsonObject() {
        return myLabel;
    }

    @Override
    public void endJsonObject(final Label aLabel) {
        if (myParser.getLocation().getNestingLevel() == myLevel) {

        }
    }
}
