
package info.freelibrary.iiif.presentation.v2;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
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
 * An ordered list of canvases, and/or further ranges. Ranges allow canvases, or parts thereof, to be grouped together
 * in some way. This could be for textual reasons, such as to distinguish books, chapters, verses, sections,
 * non-content-bearing pages, the table of contents or similar. Equally, physical features might be important such as
 * quires or gatherings, sections that have been added later and so forth.
 */
public class Range extends Resource<Range> {

    private static final String TYPE = "sc:Range";

    private static final int REQ_ARG_COUNT = 3;

    private ViewingDirection myViewingDirection;

    private Optional<URI> myStartCanvas;

    /**
     * Creates a IIIF presentation range.
     *
     * @param aID A range ID
     * @param aLabel A descriptive label for the range
     */
    public Range(final String aID, final String aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation range.
     *
     * @param aID A range ID
     * @param aLabel A descriptive label for the range
     */
    public Range(final URI aID, final Label aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Sets the optional start canvas.
     *
     * @param aStartCanvas A start canvas
     * @return The range
     */
    @JsonSetter(Constants.START_CANVAS)
    public Range setStartCanvas(final URI aStartCanvas) {
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
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The range
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Range setViewingDirection(final ViewingDirection aViewingDirection) {
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

    @Override
    public Range setLabel(final String aLabel) {
        return (Range) super.setLabel(aLabel);
    }

    @Override
    public Range setLabel(final Label aLabel) {
        return (Range) super.setLabel(aLabel);
    }

    @Override
    public Range setService(final Service<?> aService) {
        return (Range) super.setService(aService);
    }

    @Override
    public Range setMetadata(final Metadata aMetadata) {
        return (Range) super.setMetadata(aMetadata);
    }

    @Override
    public Range setDescription(final String aDescription) {
        return (Range) super.setDescription(aDescription);
    }

    @Override
    public Range setDescription(final Description aDescription) {
        return (Range) super.setDescription(aDescription);
    }

    @Override
    public Range setThumbnail(final Thumbnail aThumbnail) {
        return (Range) super.setThumbnail(aThumbnail);
    }

    @Override
    public Range setThumbnail(final String aURI) {
        return (Range) super.setThumbnail(aURI);
    }

    @Override
    public Range setAttribution(final String aAttribution) {
        return (Range) super.setAttribution(aAttribution);
    }

    @Override
    public Range setAttribution(final Attribution aAttribution) {
        return (Range) super.setAttribution(aAttribution);
    }

    @Override
    public Range setLicense(final License aLicense) {
        return (Range) super.setLicense(aLicense);
    }

    @Override
    public Range setLicense(final String aURL) throws MalformedURLException {
        return (Range) super.setLicense(aURL);
    }

    @Override
    public Range setLogo(final Logo aLogo) {
        return (Range) super.setLogo(aLogo);
    }

    @Override
    public Range setLogo(final String aURI) {
        return (Range) super.setLogo(aURI);
    }

    @Override
    public Range setID(final String aURI) {
        return (Range) super.setID(aURI);
    }

    @Override
    public Range setID(final URI aID) {
        return (Range) super.setID(aID);
    }

    @Override
    public Range setWithin(final String aWithin) {
        return (Range) super.setWithin(aWithin);
    }

    @Override
    public Range setWithin(final URI aWithin) {
        return (Range) super.setWithin(aWithin);
    }

    @Override
    public Range setViewingHint(final ViewingHint aViewingHint) {
        return (Range) super.setViewingHint(aViewingHint);
    }

    @Override
    public Range setViewingHint(final String aViewingHint) {
        return (Range) super.setViewingHint(aViewingHint);
    }

    @Override
    public Range setViewingHint(final Option aViewingHint) {
        return (Range) super.setViewingHint(aViewingHint);
    }

    @Override
    public Range setSeeAlso(final SeeAlso aSeeAlso) {
        return (Range) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public Range setSeeAlso(final String aSeeAlso) {
        return (Range) super.setSeeAlso(aSeeAlso);
    }
}
