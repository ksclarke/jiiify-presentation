
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;

/**
 * A handler that can read annotation bodies.
 */
public class AnnotationBodyHandler extends AbstractHandler<Object, List<?>> {

    /**
     * The annotation body handler's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationBodyHandler.class, MessageCodes.BUNDLE);

    @Override
    public void endJsonObject(final Object aObject) {
        myParser.removeHandler();
    }
}
