
package info.freelibrary.iiif.presentation.v3.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.freelibrary.iiif.presentation.v3.AbstractResource;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.util.ListUtils;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A resource that associates content resources and commentary with a IIIF canvas. This provides a single, coherent
 * method for aligning information and provides a standards-based framework for distinguishing parts of resources and
 * parts of canvases. As annotations can be added later, it promotes a distributed system in which publishers can align
 * their content with the descriptions created by others.
 *
 * @param <A> The type of canvas annotation
 */
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public abstract class AbstractCanvasAnnotation<A extends AbstractCanvasAnnotation<A>> extends AbstractResource<A> {

    /** A boolean flag indicating whether the annotation body contains a choice. */
    private boolean myBodyHasChoice;

    /** The annotation's body optional ID. */
    private String myBodyID;

    /** The annotation's motivation. */
    private Motivation myMotivation;

    /** The annotation's resources. */
    private List<ContentResource<?>> myResources;

    /** The target of the annotation. */
    private List<Target> myTargets;

    /** The annotation's time mode. */
    private TimeMode myTimeMode;

    /**
     * Creates an annotation resource.
     */
    protected AbstractCanvasAnnotation() {
        super(ResourceTypes.ANNOTATION, ResourceBehavior.class);
        myTargets = new ArrayList<>(1);
    }

    /**
     * Creates a new canvas annotation from the supplied annotation.
     *
     * @param aAnnotation An annotation to copy
     */
    protected AbstractCanvasAnnotation(final AbstractCanvasAnnotation<A> aAnnotation) {
        this();

        aAnnotation.copyTo(this);

        myBodyHasChoice = aAnnotation.myBodyHasChoice;
        myBodyID = aAnnotation.myBodyID;
        myMotivation = aAnnotation.myMotivation;
        myResources = aAnnotation.myResources.stream().map(ContentResource::copy)
                .collect(Collectors.toCollection(ArrayList::new));
        myTargets = aAnnotation.myTargets.stream().map(Target::copy).collect(Collectors.toCollection(ArrayList::new));
        myTimeMode = aAnnotation.myTimeMode;
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final boolean aHttpsID,
            final CanvasResource<C> aCanvas) {
        this(aID, aHttpsID, null, aCanvas);
    }

    /**
     * Creates an annotation resource.
     *
     * @param aMinter An annotation ID minter
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final Minter aMinter, final boolean aHttpsID,
            final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aHttpsID, aMinter, aCanvas);
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final boolean aHttpsID,
            final CanvasResource<C> aCanvas, final MediaFragmentSelector aCanvasRegion) {
        this(aID, aHttpsID, null, aCanvas, aCanvasRegion);
    }

    /**
     * Creates an annotation resource.
     *
     * @param aMinter An annotation ID minter
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final Minter aMinter, final boolean aHttpsID,
            final CanvasResource<C> aCanvas, final MediaFragmentSelector aCanvasRegion) {
        this(aMinter.getAnnotationID(), aHttpsID, aMinter, aCanvas, aCanvasRegion);
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final boolean aHttpsID,
            final CanvasResource<C> aCanvas, final String aCanvasRegion) {
        this(aID, aHttpsID, null, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates an annotation resource.
     *
     * @param aMinter An annotation ID
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final Minter aMinter, final boolean aHttpsID,
            final CanvasResource<C> aCanvas, final String aCanvasRegion) {
        this(aMinter.getAnnotationID(), aHttpsID, aMinter, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates a new canvas annotation from a list of targets.
     *
     * @param aID An annotation ID
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aTargetList An annotation target list
     */
    protected AbstractCanvasAnnotation(final String aID, final boolean aHttpsID, final List<Target> aTargetList) {
        super(ResourceTypes.ANNOTATION, aID, aHttpsID, ResourceBehavior.class);
        myTargets = new ArrayList<>();
        myTargets.addAll(aTargetList);
    }

    /**
     * Creates a new canvas annotation from an array of targets.
     *
     * @param aID An annotation ID
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aTargetArray An array of annotation targets
     */
    protected AbstractCanvasAnnotation(final String aID, final boolean aHttpsID, final Target... aTargetArray) {
        super(ResourceTypes.ANNOTATION, aID, aHttpsID, ResourceBehavior.class);
        myTargets = new ArrayList<>();
        myTargets.addAll(Arrays.asList(aTargetArray));
    }

    /**
     * Creates a new canvas annotation.
     *
     * @param <C> A type of canvas resource
     * @param aID An annotation ID
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aMinter An annotation ID minter
     * @param aCanvas A canvas to target
     */
    private <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final boolean aHttpsID,
            final Minter aMinter, final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION, aID, aHttpsID, ResourceBehavior.class);

        if (aMinter != null) {
            myBodyID = aMinter.getAnnotationBodyID(aID);
        }

        myTargets = new ArrayList<>(1);
        myTargets.add(new Target(aCanvas.getID()));
    }

    /**
     * Constructs an instance of AbstractCanvasAnnotation.
     *
     * @param <C> The type of CanvasResource that extends CanvasResource&lt;C&gt;
     * @param aID The unique identifier for this annotation
     * @param aHttpsID Whether the ID should be an HTTPS ID
     * @param aMinter The minter used to generate annotation body IDs; may be null
     * @param aCanvas The canvas resource associated with this annotation
     * @param aCanvasRegion The media fragment selector that specifies the region of the canvas
     */
    private <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final boolean aHttpsID,
            final Minter aMinter, final CanvasResource<C> aCanvas, final MediaFragmentSelector aCanvasRegion) {
        super(ResourceTypes.ANNOTATION, aID, aHttpsID, ResourceBehavior.class);

        if (aMinter != null) {
            myBodyID = aMinter.getAnnotationBodyID(aID);
        }

        myTargets = new ArrayList<>(1);
        myTargets.add(new SpecificResource(UriUtils.checkID(aCanvas.getID(), aHttpsID), aCanvasRegion));
    }

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if the body contains a choice; else, false
     */
    public boolean bodyHasChoice() {
        return myBodyHasChoice;
    }

    @Override
    public boolean equals(final Object aObject) {
        final AbstractCanvasAnnotation<?> other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (AbstractCanvasAnnotation<?>) aObject;

        return Objects.equals(myBodyHasChoice, other.myBodyHasChoice) && Objects.equals(myBodyID, other.myBodyID) &&
                Objects.equals(myMotivation, other.myMotivation) && ListUtils.equals(myResources, other.myResources) &&
                ListUtils.equals(myTargets, other.myTargets) && Objects.equals(myTimeMode, other.myTimeMode) &&
                super.equals(other);
    }

    /**
     * Gets the content resources associated with this annotation.
     *
     * @return The content resources associated with this annotation
     */
    public List<ContentResource<?>> getBody() {
        if (myResources == null) {
            myResources = new ArrayList<>();
        }

        return myResources;
    }

    /**
     * Sets an array of content resources for this annotation.
     *
     * @param aResourceArray An array of content resources
     * @return This annotation
     */
    @JsonIgnore
    @SuppressWarnings(JDK.UNCHECKED)
    public A setBody(final ContentResource<?>... aResourceArray) {
        final List<ContentResource<?>> resources = getBody();

        resources.clear();
        resources.addAll(Arrays.asList(aResourceArray));

        return (A) this;
    }

    /**
     * Sets a list of content resources for this annotation.
     *
     * @param aResourceList A list of content resources
     * @return This annotation
     */
    public A setBody(final List<ContentResource<?>> aResourceList) {
        return setBody(aResourceList.toArray(new ContentResource[0]));
    }

    /**
     * Gets the annotation body's optional ID.
     *
     * @return The annotation body's optional ID
     */
    public Optional<String> getBodyID() {
        return Optional.ofNullable(myBodyID);
    }

    /**
     * Sets the annotation body's optional ID.
     *
     * @param aBodyID An optional annotation body ID.
     * @return The annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    public A setBodyID(final String aBodyID) {
        myBodyID = aBodyID;
        return (A) this;
    }

    /**
     * Gets the annotation's motivation.
     *
     * @return The annotation's motivation
     */
    public Optional<Motivation> getMotivation() {
        return Optional.of(myMotivation);
    }

    /**
     * Sets the motivation of the annotation.
     *
     * @param aMotivation A motivation
     * @return The annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    protected A setMotivation(final Motivation aMotivation) {
        myMotivation = aMotivation;
        return (A) this;
    }

    /**
     * Gets the annotation's target.
     *
     * @return The annotation's target
     */
    public List<Target> getTargets() {
        return myTargets;
    }

    /**
     * Sets the target(s) of the annotation.
     *
     * @param aTargetList A list of targets
     * @return The annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    public A setTargets(final List<Target> aTargetList) {
        myTargets.clear();
        myTargets.addAll(aTargetList);
        return (A) this;
    }

    /**
     * Sets the target(s) of the annotation.
     *
     * @param aTargetArray An array of targets
     * @return The annotation
     */
    @JsonIgnore
    @SuppressWarnings(JDK.UNCHECKED)
    public A setTargets(final Target... aTargetArray) {
        myTargets.clear();
        myTargets.addAll(Arrays.asList(aTargetArray));
        return (A) this;
    }

    /**
     * Gets the annotation's time mode.
     *
     * @return The annotation's optional time mode
     */
    public Optional<TimeMode> getTimeMode() {
        return Optional.ofNullable(myTimeMode);
    }

    /**
     * Sets the time mode of the annotation.
     *
     * @param aTimeMode A time mode
     * @return The annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    public A setTimeMode(final TimeMode aTimeMode) {
        myTimeMode = aTimeMode;
        return (A) this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myBodyHasChoice, myMotivation, myResources, myTargets, myTimeMode);
    }

    /**
     * Sets the annotation resource's behaviors. The supplied behaviors are checked for compatibility with the resource.
     *
     * @param aBehaviorArray An array of annotation resource behaviors
     * @return This annotation
     */
    @Override
    public A setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    /**
     * Sets the annotation resource's behaviors. The supplied behaviors are checked for compatibility with the resource.
     *
     * @param aBehaviorList A list of annotation resource behaviors
     * @return This annotation
     */
    @Override
    public A setBehaviors(final List<Behavior> aBehaviorList) {
        final A canvasAnno;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            canvasAnno = super.setBehaviors(behaviorList);
        } else {
            canvasAnno = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return canvasAnno;
    }

    /**
     * Sets whether there is a choice between resources or just individual resources on the annotation.
     *
     * @param aChoice A flag indicating whether the annotation contains a choice between resources
     * @return This annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    public A setChoice(final boolean aChoice) {
        myBodyHasChoice = aChoice;
        return (A) this;
    }

    /**
     * A comparator that returns the sort order of the {@link AbstractCanvasAnnotation} properties.
     */
    static class ContentResourceComparator implements Comparator<String> {

        /**
         * Defines the desired content resource sort order.
         */
        private static final String[] KEYS = { JsonKeys.ID, JsonKeys.TYPE, JsonKeys.DEFAULT, JsonKeys.ITEMS,
            JsonKeys.FORMAT, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.LABEL, JsonKeys.SERVICE };

        @Override
        public int compare(final String aFirstKey, final String aSecondKey) {
            final int firstKeyIndex = getIndex(KEYS, aFirstKey);
            final int secondKeyIndex = getIndex(KEYS, aSecondKey);

            return Integer.compare(firstKeyIndex, secondKeyIndex);
        }

        /**
         * Gets a key index position.
         *
         * @param aKeyArray An array of keys
         * @param aKey A particular key
         * @return The index position of the particular key in the array or -1 if the key isn't found in the array
         */
        private int getIndex(final String[] aKeyArray, final String aKey) {
            Objects.requireNonNull(aKey);

            for (int index = 0; index < aKeyArray.length; index++) {
                if (aKey.equals(aKeyArray[index])) {
                    return index;
                }
            }

            return -1;
        }
    }
}
