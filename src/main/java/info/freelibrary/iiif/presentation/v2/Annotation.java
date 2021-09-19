
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
 * Content resources and commentary are associated with a canvas via an annotation. This provides a single, coherent
 * method for aligning information, and provides a standards based framework for distinguishing parts of resources and
 * parts of canvases. As annotations can be added later, it promotes a distributed system in which publishers can align
 * their content with the descriptions created by others.
 */
public class Annotation extends Resource<Annotation> {

    /**
     * The annotation type.
     */
    private static final String TYPE = "sc:Annotation";

    /**
     * The required argument count for annotations.
     */
    private static final int REQ_ARG_COUNT = 1;

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An annotation ID
     */
    public Annotation(final URI aID) {
        super(TYPE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An annotation ID
     */
    public Annotation(final String aID) {
        super(TYPE, URI.create(aID), REQ_ARG_COUNT);
    }

    @Override
    public Annotation setLabel(final String aLabel) {
        return (Annotation) super.setLabel(aLabel);
    }

    @Override
    public Annotation setLabel(final Label aLabel) {
        return (Annotation) super.setLabel(aLabel);
    }

    @Override
    public Annotation setService(final Service<?> aService) {
        return (Annotation) super.setService(aService);
    }

    @Override
    public Annotation setMetadata(final Metadata aMetadata) {
        return (Annotation) super.setMetadata(aMetadata);
    }

    @Override
    public Annotation setDescription(final String aDescription) {
        return (Annotation) super.setDescription(aDescription);
    }

    @Override
    public Annotation setDescription(final Description aDescription) {
        return (Annotation) super.setDescription(aDescription);
    }

    @Override
    public Annotation setThumbnail(final Thumbnail aThumbnail) {
        return (Annotation) super.setThumbnail(aThumbnail);
    }

    @Override
    public Annotation setThumbnail(final String aURI) {
        return (Annotation) super.setThumbnail(aURI);
    }

    @Override
    public Annotation clearAttribution() {
        return (Annotation) super.clearAttribution();
    }

    @Override
    public Annotation setAttribution(final String aAttribution) {
        return (Annotation) super.setAttribution(aAttribution);
    }

    @Override
    public Annotation setAttribution(final Attribution aAttribution) {
        return (Annotation) super.setAttribution(aAttribution);
    }

    @Override
    public Annotation setLicense(final License aLicense) {
        return (Annotation) super.setLicense(aLicense);
    }

    @Override
    public Annotation setLicense(final String aURL) throws MalformedURLException {
        return (Annotation) super.setLicense(aURL);
    }

    @Override
    public Annotation setLogo(final Logo aLogo) {
        return (Annotation) super.setLogo(aLogo);
    }

    @Override
    public Annotation setLogo(final String aURI) {
        return (Annotation) super.setLogo(aURI);
    }

    @Override
    public Annotation setID(final String aURI) {
        return (Annotation) super.setID(aURI);
    }

    @Override
    public Annotation setID(final URI aID) {
        return (Annotation) super.setID(aID);
    }

    @Override
    public Annotation setWithin(final String aWithin) {
        return (Annotation) super.setWithin(aWithin);
    }

    @Override
    public Annotation setWithin(final URI aWithin) {
        return (Annotation) super.setWithin(aWithin);
    }

    @Override
    public Annotation clearViewingHint() {
        return (Annotation) super.clearViewingHint();
    }

    @Override
    public Annotation setViewingHint(final ViewingHint aViewingHint) {
        return (Annotation) super.setViewingHint(aViewingHint);
    }

    @Override
    public Annotation setViewingHint(final String aViewingHint) {
        return (Annotation) super.setViewingHint(aViewingHint);
    }

    @Override
    public Annotation setViewingHint(final Option aViewingHint) {
        return (Annotation) super.setViewingHint(aViewingHint);
    }

    @Override
    public Annotation setSeeAlso(final SeeAlso aSeeAlso) {
        return (Annotation) super.setSeeAlso(aSeeAlso);
    }

    @Override
    public Annotation setSeeAlso(final String aSeeAlso) {
        return (Annotation) super.setSeeAlso(aSeeAlso);
    }
}
