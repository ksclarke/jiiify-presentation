
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.JsonHandler;
import info.freelibrary.json.JsonParser;

/**
 * A handler for canvas parsing events.
 */
public class CanvasHandler implements JsonHandler<Canvas, List<Canvas>> {

    /**
     * The canvas handler's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasHandler.class, MessageCodes.BUNDLE);

    /**
     * A list of canvases.
     */
    private final List<Canvas> myCanvases;

    /**
     * The JSON parser that's parsing the canvas information.
     */
    private JsonParser myParser;

    /**
     * The name of the current property.
     */
    private String myPropertyName;

    /**
     * The value of the current property.
     */
    private String myPropertyValue;

    /**
     * Creates a new canvas handler.
     */
    public CanvasHandler() {
        myCanvases = new ArrayList<>();
    }

    @Override
    public <T> T getResult(final Class<T> aClass) {
        return aClass.cast(myCanvases);
    }

    @Override
    public void setJsonParser(final JsonParser aParser) {
        if (myParser != null) {
            throw new IllegalStateException(LOGGER.getMessage(MessageCodes.JPA_121));
        }

        myParser = aParser;
    }

    @Override
    public void endNumber(final String aNumber) {
        myPropertyValue = aNumber;
    }

    @Override
    public void endString(final String aValue) {
        myPropertyValue = aValue;
    }

    @Override
    public void startPropertyName(final Canvas aCanvas) {
        myPropertyValue = null;
    }

    @Override
    public void endPropertyName(final Canvas aCanvas, final String aName) {
        if (JsonKeys.ITEMS.equals(aName)) {
            final AnnotationPageHandler handler = new AnnotationPageHandler();

            myParser.addHandler(handler);
        }
    }

    @Override
    public void endPropertyValue(final Canvas aCanvas, final String aName) {
        if (JsonKeys.ID.equals(aName)) {
            aCanvas.setID(myPropertyValue);
        } else if (JsonKeys.DURATION.equals(aName)) {
            aCanvas.setDuration(Float.valueOf(myPropertyValue));
        }
    }

    @Override
    public List<Canvas> startArray() {
        return myCanvases;
    }

    @Override
    public Canvas startJsonObject() {
        return Canvas.empty();
    }

    @Override
    public void endJsonObject(final Canvas aCanvas) {
        myCanvases.add(aCanvas);
    }

    @Override
    public void endArray(final List<Canvas> aList) {
        myParser.removeHandler();
    }
}
