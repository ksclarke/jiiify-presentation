package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.JsonHandler;
import info.freelibrary.json.JsonParser;


public class AnnotationBodyHandler implements JsonHandler<Object, List<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationBodyHandler.class, MessageCodes.BUNDLE);

    private JsonParser myParser;

    public AnnotationBodyHandler() {
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
    public List<?> startArray() {
        return new ArrayList<>();
    }

    @Override
    public Object startJsonObject() {
        return null;
    }

    @Override
    public void endJsonObject(final Object aObject) {
        myParser.removeHandler();
    }
}
