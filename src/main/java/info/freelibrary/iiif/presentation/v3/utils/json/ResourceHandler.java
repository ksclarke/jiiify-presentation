
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.JsonHandler;
import info.freelibrary.json.JsonParser;
import info.freelibrary.json.ParseException;

/**
 * A IIIF Presentation resource handler.
 */
public class ResourceHandler implements JsonHandler<Resource<?>, List<Resource<?>>> {

    /**
     * The logger used by the resource handler.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceHandler.class, MessageCodes.BUNDLE);

    /**
     * The handler for the resource's canvases.
     */
    private CanvasHandler myCanvasHandler;

    /**
     * The parser used by this handler.
     */
    protected JsonParser myParser;

    /**
     * My IIIF presentation resource.
     */
    private Resource<?> myResource;

    /**
     * Create a resource handler.
     *
     * @param aID A resource ID
     * @param aType A resource type
     * @param aLabel A resource label
     */
    public ResourceHandler(final URI aID, final String aType, final Label aLabel) {
        switch (aType) {
            case ResourceTypes.MANIFEST:
                myResource = new Manifest(aID, aLabel);
                break;
            case ResourceTypes.COLLECTION:
                myResource = new Collection(aID, aLabel);
                break;
            default:
                throw new ParseException(myParser.getLocation(), "Unexpected document type: {}", aType);
        }

        LOGGER.debug("Creating new resource: {}", myResource.getClass().getSimpleName());
    }

    @Override
    public <T> T getResult(final Class<T> aClass) {
        return aClass.cast(myResource);
    }

    @Override
    public void setJsonParser(final JsonParser aParser) {
        if (myParser != null) {
            throw new IllegalStateException("parser is already set");
        }

        myParser = aParser;
    }

    @Override
    public Resource<?> startJsonObject() {
        return myResource;
    }

    @Override
    public List<Resource<?>> startArray() {
        return new ArrayList<>();
    }

    @Override
    public void endPropertyName(final Resource<?> aResource, final String aName) {
        if (myParser.getLocation().getNestingLevel() == 1) {
            if (aName.equals(JsonKeys.ITEMS)) {
                myCanvasHandler = new CanvasHandler();
                myParser.addHandler(myCanvasHandler);
            } else if (aName.equals(JsonKeys.STRUCTURES)) {
                // do something
            }
        }
    }

    @Override
    public void endPropertyValue(final Resource<?> aResource, final String aName) {
        if (aName.equals(JsonKeys.ITEMS)) {
            final List<Canvas> canvases = castElements(myCanvasHandler.getResult(List.class), Canvas.class);
            getResource(Manifest.class).setCanvases(canvases);
        }
    }

    /**
     * Casts a generic list to a list of the supplied class type.
     *
     * @param <T> A type of list to output
     * @param aList A list to cast to the supplied class type
     * @param aClass A class to which to cast the list
     * @return A list of the supplied typeF
     */
    private <T> List<T> castElements(final List<?> aList, final Class<T> aClass) {
        return aList.stream().map(obj -> aClass.cast(obj)).collect(Collectors.toList());
    }

    /**
     * Gets the handler's resource, cast appropriately.
     *
     * @param <T> The type of resource we want returned
     * @param aClass The class of resource we want
     * @return The resource object, cast appropriately
     */
    private <T> T getResource(final Class<T> aClass) {
        return aClass.cast(myResource);
    }
}
