
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
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;
import info.freelibrary.iiif.presentation.v2.services.Service;

/**
 * An ordered list of annotations, typically associated with a single canvas.
 */
public class AnnotationList extends Resource<AnnotationList> {

    /**
     * The annotation list type.
     */
    private static final String TYPE = "sc:AnnotationList";

    /**
     * The required argument count for annotation lists.
     */
    private static final int REQ_ARG_COUNT = 2;

    /**
     * Creates an annotation list resource.
     *
     * @param aID An annotation list ID
     */
    public AnnotationList(final String aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates an annotation list resource.
     *
     * @param aID An annotation list ID
     */
    public AnnotationList(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    @Override
    public AnnotationList setLabel(final String aLabel) {
        return (AnnotationList) super.setLabel(aLabel);
    }

    @Override
    public AnnotationList setLabel(final Label aLabel) {
        return (AnnotationList) super.setLabel(aLabel);
    }

    @Override
    public AnnotationList setService(final Service<?> aService) {
        return (AnnotationList) super.setService(aService);
    }

    @Override
    public AnnotationList setMetadata(final Metadata aMetadata) {
        return (AnnotationList) super.setMetadata(aMetadata);
    }

    @Override
    public AnnotationList setDescription(final String aDescription) {
        return (AnnotationList) super.setDescription(aDescription);
    }

    @Override
    public AnnotationList setDescription(final Description aDescription) {
        return (AnnotationList) super.setDescription(aDescription);
    }

    @Override
    public AnnotationList setThumbnail(final Thumbnail aThumbnail) {
        return (AnnotationList) super.setThumbnail(aThumbnail);
    }

    @Override
    public AnnotationList setThumbnail(final String aURI) {
        return (AnnotationList) super.setThumbnail(aURI);
    }

    @Override
    public AnnotationList clearAttribution() {
        return (AnnotationList) super.clearAttribution();
    }

    @Override
    public AnnotationList setAttribution(final String aAttribution) {
        return (AnnotationList) super.setAttribution(aAttribution);
    }

    @Override
    public AnnotationList setAttribution(final Attribution aAttribution) {
        return (AnnotationList) super.setAttribution(aAttribution);
    }

    @Override
    public AnnotationList setLicense(final License aLicense) {
        return (AnnotationList) super.setLicense(aLicense);
    }

    @Override
    public AnnotationList setLicense(final String aURL) throws MalformedURLException {
        return (AnnotationList) super.setLicense(aURL);
    }

    @Override
    public AnnotationList setLogo(final Logo aLogo) {
        return (AnnotationList) super.setLogo(aLogo);
    }

    @Override
    public AnnotationList setLogo(final String aURI) {
        return (AnnotationList) super.setLogo(aURI);
    }

    @Override
    public AnnotationList setID(final String aURI) {
        return (AnnotationList) super.setID(aURI);
    }

    @Override
    public AnnotationList setID(final URI aID) {
        return (AnnotationList) super.setID(aID);
    }

    @Override
    public AnnotationList setWithin(final String aWithin) {
        return (AnnotationList) super.setWithin(aWithin);
    }

    @Override
    public AnnotationList setWithin(final URI aWithin) {
        return (AnnotationList) super.setWithin(aWithin);
    }

    @Override
    public AnnotationList clearViewingHint() {
        return (AnnotationList) super.clearViewingHint();
    }

    @Override
    public AnnotationList setViewingHint(final ViewingHint aViewingHint) {
        return (AnnotationList) super.setViewingHint(aViewingHint);
    }

    @Override
    public AnnotationList setViewingHint(final String aViewingHint) {
        return (AnnotationList) super.setViewingHint(aViewingHint);
    }

    @Override
    public AnnotationList setViewingHint(final Option aViewingHint) {
        return (AnnotationList) super.setViewingHint(aViewingHint);
    }

    @Override
    public AnnotationList setSeeAlso(final SeeAlso aSeeAlso) {
        return (AnnotationList) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public AnnotationList setSeeAlso(final String aSeeAlso) {
        return (AnnotationList) super.setSeeAlso(aSeeAlso);
    }
}
