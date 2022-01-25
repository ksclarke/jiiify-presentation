
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;

/**
 * A handler for canvas parsing events.
 */
public class CanvasHandler extends AbstractHandler<Canvas, List<Canvas>> {

    /**
     * The canvas handler's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasHandler.class, MessageCodes.BUNDLE);

    /**
     * The value of the current property.
     */
    private String myPropertyValue;

    /**
     * The canvas' width.
     */
    private int myWidth;

    /**
     * The canvas' height.
     */
    private int myHeight;

    /**
     * Creates a new canvas handler.
     */
    public CanvasHandler() {
        setIterable(new ArrayList<Canvas>());
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
    public void endPropertyName(final Canvas aCanvas, final String aName) {
        if (JsonKeys.ITEMS.equals(aName)) {
            final AnnotationPageHandler handler = new AnnotationPageHandler(aCanvas);

            myParser.addHandler(handler);
        }
    }

    @Override
    public void endPropertyValue(final Canvas aCanvas, final String aName) {
        if (JsonKeys.ID.equals(aName)) {
            aCanvas.setID(myPropertyValue);
        } else if (JsonKeys.DURATION.equals(aName)) {
            aCanvas.setDuration(Float.valueOf(myPropertyValue));
        } else if (JsonKeys.HEIGHT.equals(aName)) {
            myHeight = Integer.valueOf(myPropertyValue);
            setWidthHeightIfFound(aCanvas);
        } else if (JsonKeys.WIDTH.equals(aName)) {
            myWidth = Integer.valueOf(myPropertyValue);
            setWidthHeightIfFound(aCanvas);
        }
    }

    @Override
    public List<Canvas> startArray() {
        return getIterable();
    }

    @Override
    public Canvas startJsonObject() {
        return Canvas.empty();
    }

    @Override
    public void endJsonObject(final Canvas aCanvas) {
        getIterable().add(aCanvas);
    }

    @Override
    public void endArray(final List<Canvas> aList) {
        myParser.removeHandler();
    }

    private void setWidthHeightIfFound(final Canvas aCanvas) {
        if (myWidth != 0 && myHeight != 0) {
            aCanvas.setWidthHeight(myWidth, myHeight);
        }
    }
}
