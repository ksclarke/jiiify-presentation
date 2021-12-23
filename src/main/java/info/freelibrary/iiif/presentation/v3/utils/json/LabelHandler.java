/**
 *
 */

package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.JsonHandler;
import info.freelibrary.json.JsonParser;

/**
 *
 */
public class LabelHandler implements JsonHandler<Resource<?>, List<?>, Resource<?>> {

    /**
     * The logger used by the label handler.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LabelHandler.class, MessageCodes.BUNDLE);

    /**
     * The Label handler's parser.
     */
    private JsonParser myParser;

    /**
     * The label handler's resource.
     */
    private Resource<?> myResource;

    @Override
    public Consumer<Resource<?>> getObjectConsumer() {
        return resource -> {
            String name = resource.getClass().getSimpleName();

            myResource = resource;

            // If we don't have a concrete class, we're using an anonymous subclass of UnknownResource
            name = StringUtils.trimToNull(name) == null ? UnknownResource.class.getSimpleName() : name;
            LOGGER.debug("Setting the label handler's resource: {}", name);
        };
    }

    @Override
    public void setJsonParser(final JsonParser aParser) {
        if (myParser != null) {
            throw new IllegalStateException("parser is already set");
        }

        myParser = aParser;
    }

    @Override
    public void startPropertyValue(final Resource<?> aResource, final String aName) {
        LOGGER.debug("HERE");
    }

    @Override
    public List<?> startArray() {
        return new ArrayList<>();
    }

    @Override
    public Resource<?> startJsonObject() {
        return myResource;
    }

}
