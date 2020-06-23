
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Homepage;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Logo;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.PartOf;
import info.freelibrary.iiif.presentation.properties.Rendering;
import info.freelibrary.iiif.presentation.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.properties.SeeAlso;
import info.freelibrary.iiif.presentation.properties.Summary;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of
 * it. The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed
 * from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a
 * blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.WIDTH, Constants.HEIGHT,
    Constants.DURATION, Constants.THUMBNAIL, Constants.ITEMS, Constants.ANNOTATIONS })
public class Canvas extends AbstractCanvas<Canvas> implements Resource<Canvas>, CanvasResource<Canvas> {

    private Optional<AccompanyingCanvas> myAccompanyingCanvas;

    private Optional<PlaceholderCanvas> myPlaceholderCanvas;

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID
     */
    public Canvas(final URI aID) {
        super(aID);
    }

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID in string form
     */
    public Canvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     */
    public Canvas(final URI aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID in string form
     * @param aLabel A canvas label in string form
     */
    public Canvas(final String aID, final String aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a blank new canvas.
     */
    @SuppressWarnings("unused")
    private Canvas() {
        super();
    }

    /**
     * Gets the placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(Constants.PLACEHOLDER_CANVAS)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return myPlaceholderCanvas;
    }

    /**
     * Sets the placeholder canvas
     *
     * @param aCanvas A placeholder canvas
     * @return This canvas
     */
    @JsonSetter(Constants.PLACEHOLDER_CANVAS)
    public Canvas setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = Optional.ofNullable(aCanvas);
        return this;
    }

    /**
     * Gets the accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(Constants.ACCOMPANYING_CANVAS)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return myAccompanyingCanvas;
    }

    /**
     * Sets the accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This canvas
     */
    @JsonSetter(Constants.ACCOMPANYING_CANVAS)
    public Canvas setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = Optional.ofNullable(aCanvas);
        return this;
    }

    // begin AbstractCanvas

    @Override
    public Canvas setDuration(final float aDuration) {
        return (Canvas) super.setDuration(aDuration);
    }

    @Override
    public Canvas setWidthHeight(final int aWidth, final int aHeight) {
        return (Canvas) super.setWidthHeight(aWidth, aHeight);
    }

    @Override
    @SafeVarargs
    public final Canvas addSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        return (Canvas) super.addSupplementingPages(aPageArray);
    }

    @Override
    public final Canvas addSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        return (Canvas) super.addSupplementingPages(aPageList);
    }

    @Override
    @SafeVarargs
    public final Canvas setSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        return (Canvas) super.setSupplementingPages(aPageArray);
    }

    @Override
    public final Canvas setSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        return (Canvas) super.setSupplementingPages(aPageList);
    }

    @Override
    @SafeVarargs
    public final Canvas addPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        return (Canvas) super.addPaintingPages(aPageArray);
    }

    @Override
    public final Canvas addPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        return (Canvas) super.addPaintingPages(aPageList);
    }

    @Override
    @SafeVarargs
    public final Canvas setPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        return (Canvas) super.setPaintingPages(aPageArray);
    }

    @Override
    public final Canvas setPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        return (Canvas) super.setPaintingPages(aPageList);
    }

    // end AbstractCanvas

    @Override
    public Canvas clearBehaviors() {
        return (Canvas) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Canvas setBehaviors(final Behavior... aBehaviorArray) {
        return (Canvas) super.setBehaviors(aBehaviorArray);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Canvas setBehaviors(final List<Behavior> aBehaviorList) {
        return (Canvas) super.setBehaviors(aBehaviorList);
    }

    @Override
    public Canvas addBehaviors(final Behavior... aBehaviorArray) {
        return (Canvas) super.addBehaviors(aBehaviorArray);
    }

    @Override
    public Canvas addBehaviors(final List<Behavior> aBehaviorList) {
        return (Canvas) super.addBehaviors(aBehaviorList);
    }

    @Override
    public Canvas setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (Canvas) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public Canvas setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (Canvas) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public Canvas setPartOfs(final PartOf... aPartOfArray) {
        return (Canvas) super.setPartOfs(aPartOfArray);
    }

    @Override
    public Canvas setPartOfs(final List<PartOf> aPartOfList) {
        return (Canvas) super.setPartOfs(aPartOfList);
    }

    @Override
    public Canvas setRenderings(final Rendering... aRenderingArray) {
        return (Canvas) super.setRenderings(aRenderingArray);
    }

    @Override
    public Canvas setRenderings(final List<Rendering> aRenderingList) {
        return (Canvas) super.setRenderings(aRenderingList);
    }

    @Override
    public Canvas setHomepages(final Homepage... aHomepageArray) {
        return (Canvas) super.setHomepages(aHomepageArray);
    }

    @Override
    public Canvas setHomepages(final List<Homepage> aHomepageList) {
        return (Canvas) super.setHomepages(aHomepageList);
    }

    @Override
    public Canvas setThumbnails(final Thumbnail... aThumbnailArray) {
        return (Canvas) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Canvas setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (Canvas) super.setThumbnails(aThumbnailList);
    }

    @Override
    public Canvas setID(final String aID) {
        return (Canvas) super.setID(aID);
    }

    @Override
    public Canvas setID(final URI aID) {
        return (Canvas) super.setID(aID);
    }

    @Override
    public Canvas setLogo(final String aLogo) {
        return (Canvas) super.setLogo(aLogo);
    }

    @Override
    public Canvas setLogo(final Logo aLogo) {
        return (Canvas) super.setLogo(aLogo);
    }

    @Override
    public Canvas setRights(final String... aRightsArray) {
        return (Canvas) super.setRights(aRightsArray);
    }

    @Override
    public Canvas setRights(final URI... aRightsArray) {
        return (Canvas) super.setRights(aRightsArray);
    }

    @Override
    public Canvas setRights(final List<URI> aRightsList) {
        return (Canvas) super.setRights(aRightsList);
    }

    @Override
    public Canvas setRequiredStatement(final RequiredStatement aStatement) {
        return (Canvas) super.setRequiredStatement(aStatement);
    }

    @Override
    public Canvas setSummary(final String aSummary) {
        return (Canvas) super.setSummary(aSummary);
    }

    @Override
    public Canvas setSummary(final Summary aSummary) {
        return (Canvas) super.setSummary(aSummary);
    }

    @Override
    public Canvas setMetadata(final Metadata aMetadata) {
        return (Canvas) super.setMetadata(aMetadata);
    }

    @Override
    public Canvas setLabel(final String aLabel) {
        return (Canvas) super.setLabel(aLabel);
    }

    @Override
    public Canvas setLabel(final Label aLabel) {
        return (Canvas) super.setLabel(aLabel);
    }

    /**
     * Returns a Canvas from its JSON representation.
     *
     * @param aJsonObject A canvas in JSON form
     * @return The canvas
     */
    public static Canvas fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Canvas.class);
    }

    /**
     * Returns a Canvas from its JSON representation.
     *
     * @param aJsonString A canvas in string form
     * @return The canvas
     */
    public static Canvas fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
