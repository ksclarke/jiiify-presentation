
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.AbstractHandler;
import info.freelibrary.json.ParseException;

/**
 * A IIIF Presentation resource handler.
 */
public class ResourceHandler extends AbstractHandler<Resource<?>, List<Resource<?>>> {

    /**
     * The logger used by the resource handler.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceHandler.class, MessageCodes.BUNDLE);

    /**
     * The handler for the resource's canvases.
     */
    private CanvasHandler myCanvasHandler;

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
                setObject(new Manifest(aID, aLabel));
                break;
            case ResourceTypes.COLLECTION:
                setObject(new Collection(aID, aLabel));
                break;
            default:
                throw new ParseException(myParser.getLocation(), "Unexpected document type: {}", aType);
        }

        LOGGER.debug("Creating new resource: {}", getObject().getClass().getSimpleName());
    }

    @Override
    public Resource<?> startJsonObject() {
        return getObject();
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
        if (myParser.getLocation().getNestingLevel() == 1) {
            if (aName.equals(JsonKeys.ITEMS)) {
                ((Manifest) getObject()).setCanvases(myCanvasHandler.getIterable());
            }
        }
    }
}
