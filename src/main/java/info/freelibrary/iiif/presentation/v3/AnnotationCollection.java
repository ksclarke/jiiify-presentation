
package info.freelibrary.iiif.presentation.v3;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.GreaterThanOneSerializer;

/**
 * A grouping of {@link AnnotationPage}(s) that should be managed together as a collection of {@link Annotation}(s).
 */
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class AnnotationCollection extends AbstractResource<AnnotationCollection>
        implements Resource<AnnotationCollection> {

    /** The collection's first AnnotationPage. */
    private AnnotationPage<?> myFirstAnnotationPage;

    /** The collection's last AnnotationPage. */
    private AnnotationPage<?> myLastAnnotationPage;

    /** The total number of annotations in the collection. */
    private int myTotal;

    /** The collection's viewingDirection. */
    private ViewingDirection myViewingDirection;

    /**
     * Creates a collection of annotations from the supplied ID and label.
     *
     * @param aID A collection ID
     * @param aLabel A descriptive label for the collection
     */
    public AnnotationCollection(final String aID, final Label aLabel) {
        super(ResourceTypes.ANNOTATION_COLLECTION, aID, aLabel, ResourceBehavior.class);
    }

    /**
     * Creates a new {@code AnnotationCollection} for Jackson.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private AnnotationCollection() {
        super(ResourceTypes.ANNOTATION_COLLECTION, ResourceBehavior.class);
    }

    @Override
    public boolean equals(final Object aObject) {
        final AnnotationCollection other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (AnnotationCollection) aObject;

        return Objects.equals(myFirstAnnotationPage, other.myFirstAnnotationPage) &&
                Objects.equals(myLastAnnotationPage, other.myLastAnnotationPage) &&
                Objects.equals(myTotal, other.myTotal) &&
                Objects.equals(myViewingDirection, other.myViewingDirection) && super.equals(aObject);
    }

    /**
     * Gets the collection's first annotation page.
     *
     * @param <T> A type of annotation
     * @return An optional annotation page
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public <T extends Annotation<T>> Optional<AnnotationPage<T>> getFirstPage() {
        return Optional.ofNullable((AnnotationPage<T>) myFirstAnnotationPage);
    }

    /**
     * Gets the collection's last annotation page.
     *
     * @param <T> A type of annotation
     * @return An optional annotation page
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public <T extends Annotation<T>> Optional<AnnotationPage<T>> getLastPage() {
        return Optional.ofNullable((AnnotationPage<T>) myLastAnnotationPage);
    }

    /**
     * Gets the total number of annotations in the collection.
     *
     * @return The total number of annotations in the collection
     */
    @JsonGetter(JsonKeys.TOTAL)
    @JsonSerialize(using = GreaterThanOneSerializer.class)
    public int getTotal() {
        return myTotal;
    }

    /**
     * Gets the viewing direction of the annotation collection.
     *
     * @return The viewing direction
     */
    @JsonGetter(JsonKeys.VIEWING_DIRECTION)
    public Optional<ViewingDirection> getViewingDirection() {
        return Optional.ofNullable(myViewingDirection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myFirstAnnotationPage, myLastAnnotationPage, this.myViewingDirection);
    }

    @Override
    @JsonIgnore
    public AnnotationCollection setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public AnnotationCollection setBehaviors(final List<Behavior> aBehaviorList) {
        final AnnotationCollection collection;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ResourceBehavior.class, getClass());
            collection = super.setBehaviors(behaviorList);
        } else {
            collection = super.setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorList));
        }

        return collection;
    }

    /**
     * Sets the collection's first annotation page.
     *
     * @param anAnnotationPage The first annotation page
     * @return This collection
     */
    @JsonSetter(JsonKeys.FIRST)
    public AnnotationCollection setFirstPage(final AnnotationPage<?> anAnnotationPage) {
        myFirstAnnotationPage = anAnnotationPage;
        return this;
    }

    /**
     * Sets this collection's last annotation page.
     *
     * @param anAnnotationPage An annotation page
     * @return This collection
     */
    @JsonSetter(JsonKeys.LAST)
    public AnnotationCollection setLastPage(final AnnotationPage<?> anAnnotationPage) {
        myLastAnnotationPage = anAnnotationPage;
        return this;
    }

    /**
     * Sets the total number of annotations in the collection.
     *
     * @param aTotal
     * @return
     */
    @JsonSetter(JsonKeys.TOTAL)
    public AnnotationCollection setTotal(final int aTotal) {
        myTotal = aTotal;
        return this;
    }

    /**
     * Sets the viewing direction of the annotation collection.
     *
     * @param aViewingDirection A viewing direction
     * @return The annotation collection
     */
    @JsonSetter(JsonKeys.VIEWING_DIRECTION)
    public AnnotationCollection setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

}
