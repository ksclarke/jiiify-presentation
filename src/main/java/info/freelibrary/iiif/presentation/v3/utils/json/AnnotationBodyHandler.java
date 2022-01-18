package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;


public class AnnotationBodyHandler extends AbstractHandler<Object, List<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationBodyHandler.class, MessageCodes.BUNDLE);

    public AnnotationBodyHandler() {
    }

    @Override
    public List<?> startArray() {
        return new ArrayList<>();
    }

    @Override
    public void endJsonObject(final Object aObject) {
        myParser.removeHandler();
    }
}
