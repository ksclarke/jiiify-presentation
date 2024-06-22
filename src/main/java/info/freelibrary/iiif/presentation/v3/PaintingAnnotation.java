
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.Motivation;
import info.freelibrary.iiif.presentation.v3.annotations.Purpose;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.StylesheetDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.StylesheetSerializer;

/**
 * An annotation used for painting content resources onto a {@link Canvas}.
 */
@SuppressWarnings({ PMD.GOD_CLASS, PMD.EXCESSIVE_PUBLIC_COUNT, PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS })
public class PaintingAnnotation extends AbstractCanvasAnnotation<PaintingAnnotation>
        implements Resource<PaintingAnnotation>, Annotation<PaintingAnnotation> {

    /** The logger for painting annotations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PaintingAnnotation.class, MessageCodes.BUNDLE);

    /** The specific resource's stylesheet. */
    private Stylesheet myStylesheet;

    /**
     * Creates a painting annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the painting annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a painting annotation from the supplied canvas resource and media fragment selector, using the supplied
     * minter to create the painting annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the painting annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.PAINTING));
    }

    /**
     * Creates a painting annotation from the supplied canvas resource and canvas region, using the supplied minter to
     * create the painting annotation's ID.
     *
     * @param <C> A type of canvas to target
     * @param aMinter A minter from which to get the painting annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.PAINTING));
    }

    /**
     * Creates a painting annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.fromLabel(Purpose.PAINTING));
    }

    /**
     * Creates a painting annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.PAINTING));
    }

    /**
     * Creates a painting annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas to target
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> PaintingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.PAINTING));
    }

    /**
     * Creates a painting annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private PaintingAnnotation() {
        super();
    }

    /**
     * Gets the specific resource's CSS stylesheet.
     *
     * @return The specific resource's CSS stylesheet
     */
    public Optional<Stylesheet> getStylesheet() {
        return Optional.ofNullable(myStylesheet);
    }

    @Override
    public final PaintingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.PAINTING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_038, PaintingAnnotation.class.getSimpleName(), aMotivation));
        }

        return super.setMotivation(Motivation.fromLabel(Purpose.PAINTING));
    }

    /**
     * Sets the specific resource's CSS stylesheet.
     *
     * @param aStylesheet A CSS stylesheet
     * @return The specific resource
     */
    public PaintingAnnotation setStylesheet(final Stylesheet aStylesheet) {
        myStylesheet = aStylesheet;
        return this;
    }

    /**
     * A SpecificResource's CSS stylesheet. This may be represented by a single URI or a combination of type and value.
     */
    @JsonSerialize(using = StylesheetSerializer.class)
    @JsonDeserialize(using = StylesheetDeserializer.class)
    public static class Stylesheet {

        /** The specific resource's Stylesheet type. */
        public static final String TYPE = "CssStylesheet";

        /** Whether the value represents a URI to an external CSS or a CSS value. */
        private boolean isURI;

        /** The value of the stylesheet (either a URI to an external CSS stylesheet or a CSS value). */
        private String myValue;

        /**
         * Creates a new SpecificResource stylesheet from the supplied styling value.
         *
         * @param aValue A CSS value
         */
        public Stylesheet(final String aValue) {
            myValue = aValue;
            isURI = false;
        }

        /**
         * Creates a new SpecificResource stylesheet from the supplied URI.
         *
         * @param aURI A URI for an external CSS stylesheet
         */
        public Stylesheet(final URI aURI) {
            myValue = aURI.toString();
            isURI = true;
        }

        /**
         * Gets the URI for an external CSS stylesheet.
         *
         * @return A URI if one has been set
         */
        public Optional<URI> getURI() {
            return isURI ? Optional.of(URI.create(myValue)) : Optional.empty();
        }

        /**
         * Gets the CSS stylesheet value.
         *
         * @return A styling value
         */
        public Optional<String> getValue() {
            return isURI ? Optional.empty() : Optional.of(myValue);
        }

        /**
         * Sets a URI for an external CSS stylesheet, zeroing out the internal stylesheet value.
         *
         * @param aURI A URI to an external CSS stylesheet
         * @return This stylesheet
         */
        public Stylesheet setURI(final URI aURI) {
            myValue = aURI.toString();
            isURI = true;

            return this;
        }

        /**
         * Sets an internal stylesheet value, zeroing out the external CSS URI.
         *
         * @param aValue A styling value
         * @return This stylesheet
         */
        public Stylesheet setValue(final String aValue) {
            myValue = aValue;
            isURI = false;

            return this;
        }
    }
}
