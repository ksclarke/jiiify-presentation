
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.JsonHandler;
import info.freelibrary.json.JsonParser;

/**
 * A IIIF Presentation resource handler.
 */
public class ResourceHandler implements JsonHandler<Resource<?>, List<?>, Resource<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceHandler.class, MessageCodes.BUNDLE);

    /**
     * The parser used by this handler.
     */
    protected JsonParser myParser;

    /**
     * My IIIF presentation resource.
     */
    private Resource<?> myResource;

    @Override
    public void endArray(final List<?> aList) {

    }

    @Override
    public void endArrayValue(final List<?> aArrayValue) {

    }

    @Override
    public void endBoolean(final boolean aBool) {

    }

    @Override
    public void endJsonObject(final Resource<?> aResource) {

    }

    @Override
    public void endNull() {

    }

    @Override
    public void endNumber(final String aNumber) {

    }

    @Override
    public void endPropertyName(final Resource<?> aResource, final String aName) {

    }

    @Override
    public void endPropertyValue(final Resource<?> aResource, final String aName) {

    }

    @Override
    public void endString(final String aString) {

    }

    @Override
    public Consumer<Resource<?>> getObjectConsumer() {
        return resource -> {
            myResource = resource;
            LOGGER.debug("Setting parser's base resource: {}", myResource.getClass().getSimpleName());
        };
    }

    @Override
    public void setObjectConsumer(final Consumer<Resource<?>> aConsumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setJsonParser(final JsonParser aParser) {
        if (myParser != null) {
            throw new IllegalStateException("parser is already set");
        }

        myParser = aParser;
    }

    @Override
    public List<?> startArray() {
        return new ArrayList<>();
    }

    @Override
    public void startArrayValue(final List<?> aList) {

    }

    @Override
    public void startBoolean() {

    }

    @Override
    public Resource<?> startJsonObject() {
        return myResource;
    }

    @Override
    public void startNull() {

    }

    @Override
    public void startNumber() {

    }

    @Override
    public void startPropertyName(final Resource<?> aResource) {

    }

    @Override
    public void startPropertyValue(final Resource<?> aResource, final String aName) {
        if (aName.equals(JsonKeys.ITEMS)) {
            LOGGER.debug("Items found");
        }
    }

    @Override
    public void startString() {

    }

}
