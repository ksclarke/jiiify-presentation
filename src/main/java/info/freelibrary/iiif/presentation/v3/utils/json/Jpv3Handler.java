
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.net.URI;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import info.freelibrary.json.DefaultHandler;
import info.freelibrary.json.JsonObject;
import info.freelibrary.json.JsonParser;
import info.freelibrary.json.ParseException;

/**
 * A preliminary handler for IIIF Presentation JSON events; it determines what sort of IIIF Presentation document we're
 * parsing and then hands the base resource off to more specialized handlers.
 * <p>
 * We perhaps sacrifice some performance here for simplicity, but the hope is that the final result is faster than the
 * Jackson parsing that we currently use. JSON documents with the <code>Type</code>, <code>ID</code>, and
 * <code>Label</code> properties at the top of the document should be parsed faster and more efficiently than those with
 * those properties at the bottom of the document.
 * </p>
 */
public class Jpv3Handler extends DefaultHandler {

    /**
     * The JSON handler's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Jpv3Handler.class, MessageCodes.BUNDLE);

    /**
     * The underlying resource handler.
     */
    private ResourceHandler myHandler;

    /**
     * The JSON document's ID.
     */
    private URI myID;

    /**
     * The JSON document's type.
     */
    private String myType;

    /**
     * The JSON document's label.
     */
    private Label myLabel;

    /**
     * A JSON parser.
     */
    private JsonParser myParser;

    @Override
    public void setJsonParser(final JsonParser aParser) {
        if (myParser != null) {
            throw new IllegalStateException(LOGGER.getMessage("parser is already set"));
        }

        myParser = aParser;
    }

    @Override
    public void endPropertyName(final JsonObject aObject, final String aName) {
        super.endPropertyName(aObject, aName);

        if (JsonKeys.LABEL.equals(aName) && myParser.getLocation().getNestingLevel() == 1) {
            final LabelHandler handler = new LabelHandler();

            myParser.addHandler(handler);
            myLabel = handler.getObject();
        }
    }

    @Override
    public void endPropertyValue(final JsonObject aObject, final String aName) {
        if (aObject != null) {
            super.endPropertyValue(aObject, aName);
        }

        if (myParser.getLocation().getNestingLevel() == 1) {
            if (JsonKeys.TYPE.equals(aName)) {
                myType = aObject.getString(aName).orElseThrow(
                        () -> new ParseException(myParser.getLocation(), "Presentation document type is missing"));

                LOGGER.debug("Found top level resource by type: {}", myType);
                checkRequiredMetadata();
            } else if (JsonKeys.ID.equals(aName)) {
                myID = URI.create(aObject.getString(aName).orElseThrow(
                        () -> new ParseException(myParser.getLocation(), "Presentation document ID is missing")));

                LOGGER.debug("Found resource ID: {}", myID);
                checkRequiredMetadata();
            } else if (JsonKeys.LABEL.equals(aName)) {
                LOGGER.debug("Found resource Label: {}", myLabel);
                checkRequiredMetadata();
            }
        }
    }

    /**
     * Gets the result of the JSON parsing. This will be a type of Resource (e.g., Manifest, Collection, etc.) The
     * passed in class will cast from a Resource to the supplied class to return a more strongly-typed resource object.
     *
     * @param aClass The class of the handler's result
     * @throws ClassCastException If the supplied class cannot cast the JSON parsing result
     */
    public <T> T getResult(final Class<T> aClass) {
        return aClass.cast(myHandler.getObject());
    }

    /**
     * Check that we have all the information needed to determine what type of IIIF Presentation document we have.
     */
    private void checkRequiredMetadata() {
        if (myType != null && myID != null && myLabel != null) {
            myHandler = new ResourceHandler(myID, myType, myLabel);
            myParser.addHandler(myHandler);
            myParser.reset();
        }
    }
}
