
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;

public class AnnotationHandler extends AbstractHandler<Annotation<?>, List<Annotation<?>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationHandler.class, MessageCodes.BUNDLE);

    @Override
    public void endPropertyName(final Annotation<?> aAnnotation, final String aName) {
        if (JsonKeys.BODY.equals(aName)) {
            final AnnotationBodyHandler handler = new AnnotationBodyHandler();

            myParser.addHandler(handler);
        }
    }

    @Override
    public List<Annotation<?>> startArray() {
        return new ArrayList<>();
    }

    @Override
    public void endArray(final List<Annotation<?>> aList) {
        myParser.removeHandler();
    }

}
