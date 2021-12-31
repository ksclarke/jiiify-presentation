
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.util.function.Consumer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.DefaultHandler;
import info.freelibrary.json.JsonObject;
import info.freelibrary.json.ParseException;

/**
 * A preliminary handler for IIIF Presentation JSON events; it that determines what sort of IIIF Presentation document
 * we're parsing and then hands the base resource off to a more specialized handler.
 * <p>
 * We perhaps sacrifice some performance here for simplicity, but the hope is that the final result is faster than the
 * Jackson parsing that we currently use. JSON documents with the <code>Type</code>, <code>ID</code>, and
 * <code>Label</code> properties at the top of the document should be parsed faster and more efficiently than those with
 * those properties at the bottom of the document.
 * </p>
 */
public class Jpv3JsonHandler extends DefaultHandler<Resource<?>> {

    /**
     * The JSON handler's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Jpv3JsonHandler.class, MessageCodes.BUNDLE);

    /**
     * The type of IIIF Presentation document the document represents.
     */
    private Consumer<Resource<?>> myConsumer;

    /**
     * A bare-bones generic resource.
     */
    private Resource<?> myResource;

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public void endPropertyName(final JsonObject aObject, final String aName) {
        super.endPropertyName(aObject, aName);

        if (JsonKeys.LABEL.equals(aName) && isDocLevel()) {
            LOGGER.debug("Updating parser's handler to: {}", LabelHandler.class.getSimpleName());

            final Consumer<Label> consumer = (Consumer<Label>) myParser.changeHandler(LabelHandler.class);
            final Label label = Label.empty();

            consumer.accept(label);
            myResource.setLabel(label);
        }
    }

    @Override
    public void endPropertyValue(final JsonObject aObject, final String aName) {
        super.endPropertyValue(aObject, aName);

        if (JsonKeys.TYPE.equals(aName) && isDocLevel()) {
            final String type = aObject.getString(aName).orElseThrow(
                    () -> new ParseException(myParser.getLocation(), "Presentation document type is missing"));

            switch (type) {
                case ResourceTypes.MANIFEST:
                    myResource = populateResource(Manifest.empty());
                    break;
                case ResourceTypes.COLLECTION:
                    myResource = populateResource(Collection.empty());
                    break;
                default:
                    throw new ParseException(myParser.getLocation(), "Unexpected document type: {}", type);
            }

            LOGGER.debug("Found top level resource by type: {}", type);
            checkCompletion();
        } else if (JsonKeys.ID.equals(aName) && isDocLevel()) {
            getResource().setID(aObject.getString(aName).orElseThrow(
                    () -> new ParseException(myParser.getLocation(), "Presentation document ID is missing")));

            LOGGER.debug("Found resource ID: {}", myResource.getID());
            checkCompletion();
        }
    }

    /**
     * Populates a supplied resource with any missing basic metadata properties.
     *
     * @param aResource An empty, but strongly typed, resource
     * @return A populated, fully typed resource
     */
    private Resource<?> populateResource(final Resource<?> aResource) {
        if (myResource.getID() != null) {
            aResource.setID(myResource.getID());
        }

        if (myResource.getLabel() != null) {
            aResource.setLabel(myResource.getLabel());
        }

        return aResource;
    }

    /**
     * Check that we have all the information needed to determine what type of IIIF Presentation document we have.
     */
    @SuppressWarnings(JDK.UNCHECKED)
    private void checkCompletion() {
        if (basicMetadataFound()) {
            LOGGER.debug("Updating parser's handler to: {}", ResourceHandler.class.getSimpleName());
            myConsumer = (Consumer<Resource<?>>) myParser.changeHandler(ResourceHandler.class);

            switch (myResource.getType()) {
                case ResourceTypes.MANIFEST:
                    myConsumer.accept(new Manifest(myResource.getID(), myResource.getLabel()));
                    break;
                case ResourceTypes.COLLECTION:
                    myConsumer.accept(new Collection(myResource.getID(), myResource.getLabel()));
                    break;
                default:
                    throw new RuntimeException();
            }

            myParser.resetParsing();
        } // else, just ignore for now
    }

    /**
     * Returns true if the parser's nesting level is at the top level of the JSON document.
     *
     * @return True if the nesting level is at the top level of the document; else, false.
     */
    private boolean isDocLevel() {
        return myParser.getLocation().getNestingLevel() == 1;
    }

    /**
     * Gets the JSON document's basic metadata.
     *
     * @return The JSON document's basic metadata
     */
    private Resource<?> getResource() {
        if (myResource == null) {
            myResource = new UnknownResource<>();
        }

        return myResource;
    }

    /**
     * Determines whether the resource's basic metadata has been parsed.
     *
     * @return True if enough of the resource's metadata has been parsed to create the resource; else, false
     */
    private boolean basicMetadataFound() {
        final Resource<?> resource = getResource();
        return resource.getType() != null && resource.getID() != null && resource.getLabel() != null;
    }
}
