package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.JsonHandler;
import info.freelibrary.json.JsonParser;


public class AnnotationPageHandler implements JsonHandler<Annotation<?>, List<Annotation<?>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPageHandler.class, MessageCodes.BUNDLE);

    private JsonParser myParser;

    public AnnotationPageHandler() {
    }

    @Override
    public <T> T getResult(final Class<T> aResult) {
        return null;
    }

    @Override
    public void setJsonParser(final JsonParser aParser) {
        if (myParser != null) {
            throw new IllegalStateException(LOGGER.getMessage(MessageCodes.JPA_121));
        }

        myParser = aParser;
    }

    @Override
    public void endPropertyName(final Annotation<?> aAnnotation, final String aName) {
        if (JsonKeys.ITEMS.equals(aName)) {
            final AnnotationHandler handler = new AnnotationHandler();

            myParser.addHandler(handler);
        }
    }

    @Override
    public List<Annotation<?>> startArray() {
        return new ArrayList<>();
    }

    @Override
    public Annotation<?> startJsonObject() {
        return null;
    }

    @Override
    public void endJsonObject(final Annotation<?> aAnnotation) {
        myParser.removeHandler();
    }
}
