
package info.freelibrary.iiif.presentation.v2;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v2.properties.Attribution;
import info.freelibrary.iiif.presentation.v2.properties.Description;
import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.License;
import info.freelibrary.iiif.presentation.v2.properties.Logo;
import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v2.properties.Thumbnail;
import info.freelibrary.iiif.presentation.v2.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.v2.services.Service;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * The order of the views of the object. Multiple sequences are allowed to cover situations when there are multiple
 * equally valid orders through the content, such as when a manuscript’s pages are rebound or archival collections are
 * reordered.
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.LABEL, Constants.ID, Constants.TYPE })
public class Sequence extends Resource<Sequence> {

    private static final String TYPE = "sc:Sequence";

    private static final int REQ_ARG_COUNT = 1;

    private ViewingDirection myViewingDirection;

    private final List<Canvas> myCanvases;

    private Optional<URI> myStartCanvas;

    /**
     * Creates a IIIF presentation sequence resource.
     */
    public Sequence() {
        super(TYPE, REQ_ARG_COUNT);
        myCanvases = new ArrayList<>();
    }

    /**
     * Creates a IIIF presentation sequence resource with the supplied ID.
     *
     * @param aID An ID
     */
    public Sequence(final String aID) {
        super(TYPE, REQ_ARG_COUNT);
        myCanvases = new ArrayList<>();
        setID(aID);
    }

    /**
     * Creates a IIIF presentation sequence resource with the supplied ID.
     *
     * @param aID An ID
     */
    public Sequence(final URI aID) {
        super(TYPE, REQ_ARG_COUNT);
        myCanvases = new ArrayList<>();
        setID(aID);
    }

    /**
     * Sets the optional start canvas.
     *
     * @param aStartCanvas A start canvas
     * @return The sequence
     */
    @JsonSetter(Constants.START_CANVAS)
    public Sequence setStartCanvas(final URI aStartCanvas) {
        myStartCanvas = Optional.ofNullable(aStartCanvas);
        return this;
    }

    /**
     * Gets the optional start canvas.
     *
     * @return The optional start canvas
     */
    @JsonGetter(Constants.START_CANVAS)
    public Optional<URI> getStartCanvas() {
        return myStartCanvas;
    }

    /**
     * Clears the viewing direction.
     *
     * @return The sequence
     */
    public Sequence clearViewingDirection() {
        myViewingDirection = null;
        return this;
    }

    /**
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The sequence
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Sequence setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(Constants.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    /**
     * Sets this sequence's canvases to the supplied list of canvases.
     *
     * @param aCanvases A list of canvases
     * @return The sequence
     */
    @JsonIgnore
    public Sequence setCanvases(final List<Canvas> aCanvases) {
        myCanvases.clear();
        myCanvases.addAll(aCanvases);
        return this;
    }

    /**
     * Sets this sequence's canvases to the supplied list of canvases.
     *
     * @param aCanvas A list of canvases
     * @return The sequence
     */
    @JsonIgnore
    public Sequence setCanvases(final Canvas... aCanvas) {
        myCanvases.clear();
        return addCanvas(aCanvas);
    }

    /**
     * Adds a canvas to this sequence.
     *
     * @param aCanvas A canvas to add to this sequence
     * @return The sequence
     */
    public Sequence addCanvas(final Canvas... aCanvas) {
        if (!Collections.addAll(myCanvases, aCanvas)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the canvases associated with this sequence.
     *
     * @return The canvases associated with this sequence
     */
    @JsonGetter(Constants.CANVASES)
    public List<Canvas> getCanvases() {
        return myCanvases;
    }

    @Override
    public Sequence setLabel(final String aLabel) {
        return (Sequence) super.setLabel(aLabel);
    }

    @Override
    public Sequence setLabel(final Label aLabel) {
        return (Sequence) super.setLabel(aLabel);
    }

    @Override
    public Sequence setService(final Service<?> aService) {
        return (Sequence) super.setService(aService);
    }

    @Override
    public Sequence setMetadata(final Metadata aMetadata) {
        return (Sequence) super.setMetadata(aMetadata);
    }

    @Override
    public Sequence setDescription(final String aDescription) {
        return (Sequence) super.setDescription(aDescription);
    }

    @Override
    public Sequence setDescription(final Description aDescription) {
        return (Sequence) super.setDescription(aDescription);
    }

    @Override
    public Sequence setThumbnail(final Thumbnail aThumbnail) {
        return (Sequence) super.setThumbnail(aThumbnail);
    }

    @Override
    public Sequence setThumbnail(final String aURI) {
        return (Sequence) super.setThumbnail(aURI);
    }

    @Override
    public Sequence clearAttribution() {
        return (Sequence) super.clearAttribution();
    }

    @Override
    public Sequence setAttribution(final String aAttribution) {
        return (Sequence) super.setAttribution(aAttribution);
    }

    @Override
    public Sequence setAttribution(final Attribution aAttribution) {
        return (Sequence) super.setAttribution(aAttribution);
    }

    @Override
    public Sequence setLicense(final License aLicense) {
        return (Sequence) super.setLicense(aLicense);
    }

    @Override
    public Sequence setLicense(final String aURL) throws MalformedURLException {
        return (Sequence) super.setLicense(aURL);
    }

    @Override
    public Sequence setLogo(final Logo aLogo) {
        return (Sequence) super.setLogo(aLogo);
    }

    @Override
    public Sequence setLogo(final String aURI) {
        return (Sequence) super.setLogo(aURI);
    }

    @Override
    public Sequence setID(final String aURI) {
        return (Sequence) super.setID(aURI);
    }

    @Override
    public Sequence setID(final URI aID) {
        return (Sequence) super.setID(aID);
    }

    @Override
    public Sequence setWithin(final String aWithin) {
        return (Sequence) super.setWithin(aWithin);
    }

    @Override
    public Sequence setWithin(final URI aWithin) {
        return (Sequence) super.setWithin(aWithin);
    }

    @Override
    public Sequence clearViewingHint() {
        return (Sequence) super.clearViewingHint();
    }

    @Override
    public Sequence setViewingHint(final ViewingHint aViewingHint) {
        return (Sequence) super.setViewingHint(aViewingHint);
    }

    @Override
    public Sequence setViewingHint(final String aViewingHint) {
        return (Sequence) super.setViewingHint(aViewingHint);
    }

    @Override
    public Sequence setViewingHint(final Option aViewingHint) {
        return (Sequence) super.setViewingHint(aViewingHint);
    }

    @Override
    public Sequence setSeeAlso(final SeeAlso aSeeAlso) {
        return (Sequence) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public Sequence setSeeAlso(final String aSeeAlso) {
        return (Sequence) super.setSeeAlso(aSeeAlso);
    }

}
