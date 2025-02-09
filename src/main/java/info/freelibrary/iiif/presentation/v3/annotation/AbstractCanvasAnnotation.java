
package info.freelibrary.iiif.presentation.v3.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.freelibrary.util.ListUtils;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.AbstractResource;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A resource that associates content resources and commentary with a IIIF canvas. This provides a single, coherent
 * method for aligning information, and provides a standards based framework for distinguishing parts of resources and
 * parts of canvases. As annotations can be added later, it promotes a distributed system in which publishers can align
 * their content with the descriptions created by others.
 */
@SuppressWarnings({ PMD.GOD_CLASS, PMD.EXCESSIVE_IMPORTS })
public abstract class AbstractCanvasAnnotation<A extends AbstractCanvasAnnotation<A>> extends AbstractResource<A> {

    /** A boolean flag indicating whether the annotation body contains a choice. */
    private boolean myBodyHasChoice;

    /** The annotation's motivation. */
    private Motivation myMotivation;

    /** The annotation's resources. */
    private List<ContentResource> myResources;

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
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID,
            final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION, aID, ResourceBehavior.class);
        myTargets = new ArrayList<>(1);
        myTargets.add(new Target(aCanvas.getID()));
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(ResourceTypes.ANNOTATION, aID, ResourceBehavior.class);
        myTargets = new ArrayList<>(1);
        myTargets.add(new SpecificResource(UriUtils.checkID(aCanvas.getID(), true), aCanvasRegion));
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates a new canvas annotation from a list of targets.
     *
     * @param aID An annotation ID
     * @param aTargetList An annotation target list
     */
    protected AbstractCanvasAnnotation(final String aID, final List<Target> aTargetList) {
        super(ResourceTypes.ANNOTATION, aID, ResourceBehavior.class);
        myTargets = new ArrayList<>();
        myTargets.addAll(aTargetList);
    }

    /**
     * Creates a new canvas annotation from an array of targets.
     *
     * @param aID An annotation ID
     * @param aTargetArray An array of annotation targets
     */
    protected AbstractCanvasAnnotation(final String aID, final Target... aTargetArray) {
        super(ResourceTypes.ANNOTATION, aID, ResourceBehavior.class);
        myTargets = new ArrayList<>();
        myTargets.addAll(Arrays.asList(aTargetArray));
    }

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if body contains a choice; else, false
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

        return Objects.equals(myBodyHasChoice, other.myBodyHasChoice) &&
                Objects.equals(myMotivation, other.myMotivation) && ListUtils.equals(myResources, other.myResources) &&
                ListUtils.equals(myTargets, other.myTargets) && Objects.equals(myTimeMode, other.myTimeMode) &&
                super.equals(other);
    }

    /**
     * Gets the content resources associated with this annotation.
     *
     * @return The content resources associated with this annotation
     */
    public List<ContentResource> getBody() {
        if (myResources == null) {
            myResources = new ArrayList<>();
        }

        return myResources;
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
     * Gets the annotation's target.
     *
     * @return The annotation's target
     */
    public List<Target> getTargets() {
        return myTargets;
    }

    /**
     * Gets the annotation's time mode.
     *
     * @return The annotation's optional time mode
     */
    public Optional<TimeMode> getTimeMode() {
        return Optional.ofNullable(myTimeMode);
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
    @SuppressWarnings(JDK.UNCHECKED)
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
     * Sets an array of content resources for this annotation.
     *
     * @param aResourceArray An array of content resources
     * @return This annotation
     */
    @JsonIgnore
    @SuppressWarnings(JDK.UNCHECKED)
    public A setBody(final ContentResource... aResourceArray) {
        final List<ContentResource> resources = getBody();

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
    public A setBody(final List<ContentResource> aResourceList) {
        return setBody(aResourceList.toArray(new ContentResource[0]));
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
