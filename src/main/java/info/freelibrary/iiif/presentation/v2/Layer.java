
package info.freelibrary.iiif.presentation.v2;

import java.net.MalformedURLException;
import java.net.URI;

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
 * An ordered list of annotation lists. Layers allow higher level groupings of annotations to be recorded. For example,
 * all of the English translation annotations of a medieval French document could be kept separate from the
 * transcription or an edition in modern French.
 */
public class Layer extends Resource<Layer> {

    /**
     * The layer's type.
     */
    private static final String TYPE = "sc:Layer";

    /**
     * The layer's required argument type.
     */
    private static final int REQ_ARG_COUNT = 3;

    /**
     * The layer's viewing direction.
     */
    private ViewingDirection myViewingDirection;

    /**
     * Creates a IIIF presentation layer resource.
     *
     * @param aID A layer ID
     * @param aLabel A descriptive label for the layer
     */
    public Layer(final String aID, final String aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation layer resource.
     *
     * @param aID A layer ID
     * @param aLabel A descriptive label for the layer
     */
    public Layer(final URI aID, final Label aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Clears the viewing direction.
     *
     * @return The layer
     */
    public Layer clearViewingDirection() {
        myViewingDirection = null; // NOPMD - assigning null, code smell
        return this;
    }

    /**
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The layer
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Layer setViewingDirection(final ViewingDirection aViewingDirection) {
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
    public Layer setLabel(final String aLabel) {
        return (Layer) super.setLabel(aLabel);
    }

    @Override
    public Layer setLabel(final Label aLabel) {
        return (Layer) super.setLabel(aLabel);
    }

    @Override
    public Layer setService(final Service<?> aService) {
        return (Layer) super.setService(aService);
    }

    @Override
    public Layer setMetadata(final Metadata aMetadata) {
        return (Layer) super.setMetadata(aMetadata);
    }

    @Override
    public Layer setDescription(final String aDescription) {
        return (Layer) super.setDescription(aDescription);
    }

    @Override
    public Layer setDescription(final Description aDescription) {
        return (Layer) super.setDescription(aDescription);
    }

    @Override
    public Layer setThumbnail(final Thumbnail aThumbnail) {
        return (Layer) super.setThumbnail(aThumbnail);
    }

    @Override
    public Layer setThumbnail(final String aURI) {
        return (Layer) super.setThumbnail(aURI);
    }

    @Override
    public Layer clearAttribution() {
        return (Layer) super.clearAttribution();
    }

    @Override
    public Layer setAttribution(final String aAttribution) {
        return (Layer) super.setAttribution(aAttribution);
    }

    @Override
    public Layer setAttribution(final Attribution aAttribution) {
        return (Layer) super.setAttribution(aAttribution);
    }

    @Override
    public Layer setLicense(final License aLicense) {
        return (Layer) super.setLicense(aLicense);
    }

    @Override
    public Layer setLicense(final String aURL) throws MalformedURLException {
        return (Layer) super.setLicense(aURL);
    }

    @Override
    public Layer setLogo(final Logo aLogo) {
        return (Layer) super.setLogo(aLogo);
    }

    @Override
    public Layer setLogo(final String aURI) {
        return (Layer) super.setLogo(aURI);
    }

    @Override
    public Layer setID(final String aURI) {
        return (Layer) super.setID(aURI);
    }

    @Override
    public Layer setID(final URI aID) {
        return (Layer) super.setID(aID);
    }

    @Override
    public Layer setWithin(final String aWithin) {
        return (Layer) super.setWithin(aWithin);
    }

    @Override
    public Layer setWithin(final URI aWithin) {
        return (Layer) super.setWithin(aWithin);
    }

    @Override
    public Layer clearViewingHint() {
        return (Layer) super.clearViewingHint();
    }

    @Override
    public Layer setViewingHint(final ViewingHint aViewingHint) {
        return (Layer) super.setViewingHint(aViewingHint);
    }

    @Override
    public Layer setViewingHint(final String aViewingHint) {
        return (Layer) super.setViewingHint(aViewingHint);
    }

    @Override
    public Layer setViewingHint(final Option aViewingHint) {
        return (Layer) super.setViewingHint(aViewingHint);
    }

    @Override
    public Layer setSeeAlso(final SeeAlso aSeeAlso) {
        return (Layer) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public Layer setSeeAlso(final String aSeeAlso) {
        return (Layer) super.setSeeAlso(aSeeAlso);
    }
}
