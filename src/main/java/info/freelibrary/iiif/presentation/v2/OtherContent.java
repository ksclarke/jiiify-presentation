
package info.freelibrary.iiif.presentation.v2;

import java.net.MalformedURLException;
import java.net.URI;

import info.freelibrary.iiif.presentation.v2.properties.Attribution;
import info.freelibrary.iiif.presentation.v2.properties.Description;
import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.License;
import info.freelibrary.iiif.presentation.v2.properties.Logo;
import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v2.properties.Thumbnail;
import info.freelibrary.iiif.presentation.v2.properties.Type;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.v2.services.Service;

/**
 * Other, non-image, resources that are associated with a {@link Canvas}.
 */
public class OtherContent extends Content<OtherContent> {

    private static final String TYPE = "sc:AnnotationList";

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID A other content ID
     * @param aCanvas A canvas for other content
     */
    public OtherContent(final String aID, final Canvas aCanvas) {
        super(TYPE, aID, aCanvas);
    }

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID A other content ID
     * @param aCanvas A canvas for other content
     */
    public OtherContent(final URI aID, final Canvas aCanvas) {
        super(TYPE, aID, aCanvas);
    }

    /**
     * Creates a IIIF presentation content resource.
     */
    private OtherContent() {
        super(new Type(TYPE));
    }

    @Override
    public OtherContent setLabel(final String aLabel) {
        return (OtherContent) super.setLabel(aLabel);
    }

    @Override
    public OtherContent setLabel(final Label aLabel) {
        return (OtherContent) super.setLabel(aLabel);
    }

    @Override
    public OtherContent setService(final Service<?> aService) {
        return (OtherContent) super.setService(aService);
    }

    @Override
    public OtherContent setMetadata(final Metadata aMetadata) {
        return (OtherContent) super.setMetadata(aMetadata);
    }

    @Override
    public OtherContent setDescription(final String aDescription) {
        return (OtherContent) super.setDescription(aDescription);
    }

    @Override
    public OtherContent setDescription(final Description aDescription) {
        return (OtherContent) super.setDescription(aDescription);
    }

    @Override
    public OtherContent setThumbnail(final Thumbnail aThumbnail) {
        return (OtherContent) super.setThumbnail(aThumbnail);
    }

    @Override
    public OtherContent setThumbnail(final String aURI) {
        return (OtherContent) super.setThumbnail(aURI);
    }

    @Override
    public OtherContent setAttribution(final String aAttribution) {
        return (OtherContent) super.setAttribution(aAttribution);
    }

    @Override
    public OtherContent setAttribution(final Attribution aAttribution) {
        return (OtherContent) super.setAttribution(aAttribution);
    }

    @Override
    public OtherContent setLicense(final License aLicense) {
        return (OtherContent) super.setLicense(aLicense);
    }

    @Override
    public OtherContent setLicense(final String aURL) throws MalformedURLException {
        return (OtherContent) super.setLicense(aURL);
    }

    @Override
    public OtherContent setLogo(final Logo aLogo) {
        return (OtherContent) super.setLogo(aLogo);
    }

    @Override
    public OtherContent setLogo(final String aURI) {
        return (OtherContent) super.setLogo(aURI);
    }

    @Override
    public OtherContent setID(final String aURI) {
        return (OtherContent) super.setID(aURI);
    }

    @Override
    public OtherContent setID(final URI aID) {
        return (OtherContent) super.setID(aID);
    }

    @Override
    public OtherContent setWithin(final String aWithin) {
        return (OtherContent) super.setWithin(aWithin);
    }

    @Override
    public OtherContent setWithin(final URI aWithin) {
        return (OtherContent) super.setWithin(aWithin);
    }

    @Override
    public OtherContent setViewingHint(final ViewingHint aViewingHint) {
        return (OtherContent) super.setViewingHint(aViewingHint);
    }

    @Override
    public OtherContent setViewingHint(final String aViewingHint) {
        return (OtherContent) super.setViewingHint(aViewingHint);
    }

    @Override
    public OtherContent setViewingHint(final Option aViewingHint) {
        return (OtherContent) super.setViewingHint(aViewingHint);
    }

    @Override
    public OtherContent setSeeAlso(final SeeAlso aSeeAlso) {
        return (OtherContent) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public OtherContent setSeeAlso(final String aSeeAlso) {
        return (OtherContent) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public OtherContent setOn(final URI aURI) {
        return (OtherContent) super.setOn(aURI);
    }

    @Override
    public OtherContent setOn(final String aURI) {
        return (OtherContent) super.setOn(aURI);
    }
}
