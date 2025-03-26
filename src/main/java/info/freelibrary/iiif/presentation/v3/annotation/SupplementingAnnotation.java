
package info.freelibrary.iiif.presentation.v3.annotation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.TextGranularity;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.CanvasAnnotationDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.SupplementingAnnotationSerializer;

/**
 * An annotation used for associating supplementary content resources with a canvas resource.
 */
@JsonSerialize(using = SupplementingAnnotationSerializer.class)
@JsonDeserialize(using = CanvasAnnotationDeserializer.class)
@SuppressWarnings({ PMD.GOD_CLASS, PMD.EXCESSIVE_PUBLIC_COUNT, PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS })
public class SupplementingAnnotation extends AbstractCanvasAnnotation<SupplementingAnnotation>
        implements Resource<SupplementingAnnotation>, Annotation<SupplementingAnnotation> {

    /** The logger that SupplementingAnnotation uses. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplementingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * An optional text granularity, as specified by the <a href= "https://iiif.io/api/extension/text-granularity/">Text
     * Granularity Extension</a>.
     */
    @JsonProperty(JsonKeys.TEXT_GRANULARITY)
    private TextGranularity myTextGranularity;

    /**
     * Creates a supplementing annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter that's used to create the annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter,
            final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a supplementing annotation from the supplied canvas resource and media fragment selector, using the
     * supplied minter to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter used to create the annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        this(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a supplementing annotation from the supplied canvas resource and canvas region, using the supplied minter
     * to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter used to create the supplementing annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a supplementing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a supplementing annotation from the supplied ID, canvas resource, and media fragment selector.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a supplementing annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a new supplementing annotation from a list of targets.
     *
     * @param aID An annotation ID
     * @param aTargetList An annotation target list
     */
    public SupplementingAnnotation(final String aID, final List<Target> aTargetList) {
        super(UriUtils.checkID(aID, false), aTargetList);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a new supplementing annotation from an array of targets.
     *
     * @param aID An annotation ID
     * @param aTargetArray An array of annotation targets
     */
    public SupplementingAnnotation(final String aID, final Target... aTargetArray) {
        super(UriUtils.checkID(aID, false), aTargetArray);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a supplementing annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SupplementingAnnotation() {
        super();
    }

    @Override
    public boolean equals(final Object aObject) {
        final SupplementingAnnotation other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (SupplementingAnnotation) aObject;

        return Objects.equals(myTextGranularity, other.myTextGranularity) && super.equals(other);
    }

    /**
     * Gets the supplementing annotation's text granularity if it exists.
     *
     * @return An optional text granularity
     */
    public Optional<TextGranularity> getTextGranularity() {
        return Optional.ofNullable(myTextGranularity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myTextGranularity);
    }

    @Override
    public final SupplementingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.SUPPLEMENTING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038,
                    SupplementingAnnotation.class.getSimpleName(), Purpose.SUPPLEMENTING, aMotivation));
        }

        return super.setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Sets an optional text granularity for the supplementing annotation.
     *
     * @param aTextGranularity A text granularity
     * @return This supplementing annotation
     */
    public SupplementingAnnotation setTextGranularity(final TextGranularity aTextGranularity) {
        myTextGranularity = Objects.requireNonNull(aTextGranularity);
        return this;
    }
}
