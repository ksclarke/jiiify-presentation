
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;

/**
 * A handler for annotation pages.
 */
public class AnnotationPageHandler extends AbstractHandler<Annotation<?>, List<Annotation<?>>> {

    /**
     * The annotation page handler's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPageHandler.class, MessageCodes.BUNDLE);

    /**
     * The annotation page's parent canvas.
     */
    private final Canvas myCanvas;

    /**
     * Creates an annotation page handler.
     *
     * @param aCanvas The parent canvas of the annotation page
     */
    public AnnotationPageHandler(final Canvas aCanvas) {
        myCanvas = aCanvas;
    }

    @Override
    public void endPropertyName(final Annotation<?> aAnnotation, final String aName) {
        if (JsonKeys.ITEMS.equals(aName)) {
            final AnnotationHandler handler = new AnnotationHandler(myCanvas);

            myParser.addHandler(handler);
        }
    }

    @Override
    public void endArray(final List<Annotation<?>> aList) {
        myParser.removeHandler();
    }
}
