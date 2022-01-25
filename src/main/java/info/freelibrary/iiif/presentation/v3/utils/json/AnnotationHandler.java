
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;

/**
 * Creates an handler for annotations.
 */
public class AnnotationHandler<T extends Annotation<T>> extends AbstractHandler<Annotation<?>, List<Annotation<?>>> {

    /**
     * The annotation handler's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationHandler.class, MessageCodes.BUNDLE);

    /**
     * The parent canvas of the annotation.
     */
    private final Canvas myCanvas;

    /**
     * A property value.
     */
    private String myPropertyValue;

    /**
     * The annotation's motivation.
     */
    private String myMotivation;

    /**
     * The annotation's ID.
     */
    private String myID;

    /**
     * Creates a new annotation handler for the supplied canvas.
     *
     * @param aCanvas A parent canvas
     */
    public AnnotationHandler(final Canvas aCanvas) {
        myCanvas = aCanvas;
    }

    @Override
    public void endString(final String aValue) {
        myPropertyValue = aValue;
    }

    @Override
    public void endPropertyValue(final Annotation<?> aAnnotation, final String aName) {
        if (JsonKeys.MOTIVATION.equals(aName)) {
            myMotivation = myPropertyValue;
        } else if (JsonKeys.ID.equals(aName)) {
            myID = myPropertyValue;
        }

        if (requiredMetadataFound()) {
            switch (myPropertyValue) {
                case PaintingAnnotation.MOTIVATION:
                    setObject(new PaintingAnnotation(myID, myCanvas));
                    break;
                case SupplementingAnnotation.MOTIVATION:
                    setObject(new SupplementingAnnotation(myID, myCanvas));
                    break;
                default:
                    setObject(new Annotation<T>(myID, myCanvas) {});
            }
        }
    }

    @Override
    public void endPropertyName(final Annotation<?> aAnnotation, final String aName) {
        if (JsonKeys.BODY.equals(aName)) {
            final AnnotationBodyHandler handler = new AnnotationBodyHandler();

            myParser.addHandler(handler);
        }
    }

    @Override
    public void endArray(final List<Annotation<?>> aList) {
        LOGGER.debug(getObject().toString());
        myParser.removeHandler();
    }

    private boolean requiredMetadataFound() {
        return myID != null && myMotivation != null;
    }
}
