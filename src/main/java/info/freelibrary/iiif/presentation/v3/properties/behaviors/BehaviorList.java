
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.AUTO_ADVANCE;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.CONTINUOUS;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.FACING_PAGES;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.INDIVIDUALS;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.MULTI_PART;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.NON_PAGED;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.NO_AUTO_ADVANCE;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.NO_NAV;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.NO_REPEAT;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.PAGED;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.REPEAT;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.SEQUENCE;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.THUMBNAIL_NAV;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.TOGETHER;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.UNORDERED;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An implementation of <code>List&lt;Behavior&gt;</code> that checks for disjointed behaviors. This ensures that
 * behaviors in the list are valid together and, also, valid for the resource with which they're being associated.
 */
@SuppressWarnings({ PMD.TOO_MANY_STATIC_IMPORTS, "PMD.TooManyStaticImports" })
public class BehaviorList extends ArrayList<Behavior> implements List<Behavior> {

    /** The behavior list's <code>serialVersionUID</code>. */
    private static final long serialVersionUID = -877432424934929199L;

    /**
     * A map of behavior disjoints.
     */
    private static final Map<String, Set<String>> DISJOINTS = Map.ofEntries( //
            //
            // Temporal behaviors
            //
            new AbstractMap.SimpleEntry<>(AUTO_ADVANCE, Set.of(NO_AUTO_ADVANCE)), //
            new AbstractMap.SimpleEntry<>(NO_AUTO_ADVANCE, Set.of(AUTO_ADVANCE)), //
            new AbstractMap.SimpleEntry<>(REPEAT, Set.of(NO_REPEAT)), //
            new AbstractMap.SimpleEntry<>(NO_REPEAT, Set.of(REPEAT)), //
            //
            // Layout behaviors
            //
            new AbstractMap.SimpleEntry<>(UNORDERED, Set.of(INDIVIDUALS, CONTINUOUS, PAGED)),
            new AbstractMap.SimpleEntry<>(INDIVIDUALS, Set.of(UNORDERED, CONTINUOUS, PAGED)),
            new AbstractMap.SimpleEntry<>(CONTINUOUS, Set.of(UNORDERED, INDIVIDUALS, PAGED)),
            new AbstractMap.SimpleEntry<>(PAGED, Set.of(UNORDERED, INDIVIDUALS, CONTINUOUS, FACING_PAGES, NON_PAGED)),
            new AbstractMap.SimpleEntry<>(FACING_PAGES, Set.of(PAGED, NON_PAGED)), //
            new AbstractMap.SimpleEntry<>(NON_PAGED, Set.of(PAGED, FACING_PAGES)), //
            //
            // Collection behaviors
            //
            new AbstractMap.SimpleEntry<>(MULTI_PART, Set.of(TOGETHER)), //
            new AbstractMap.SimpleEntry<>(TOGETHER, Set.of(MULTI_PART)), //
            //
            // Range behaviors
            //
            new AbstractMap.SimpleEntry<>(SEQUENCE, Set.of(THUMBNAIL_NAV, NO_NAV)), //
            new AbstractMap.SimpleEntry<>(THUMBNAIL_NAV, Set.of(SEQUENCE, NO_NAV)), //
            new AbstractMap.SimpleEntry<>(NO_NAV, Set.of(SEQUENCE, THUMBNAIL_NAV)) //
    );

    /** The list's type of <code>Behavior</code>. */
    private final Class<? extends Behavior> myBehaviorClass;

    /**
     * Creates a new behavior list from the supplied behavior class.
     *
     * @param aBehaviorClass A type of behavior
     */
    public BehaviorList(final Class<? extends Behavior> aBehaviorClass) {
        myBehaviorClass = Objects.requireNonNull(aBehaviorClass);
    }

    /**
     * Creates a new behavior list from the supplied behavior class and array of behaviors.
     *
     * @param aBehaviorClass A type of behavior
     * @param aBehaviorArray An array of behaviors
     * @throws InvalidBehaviorException If there is a disjoint in the supplied behavior list
     */
    @SuppressWarnings({ "PMD.ConstructorCallsOverridableMethod" })
    public BehaviorList(final Class<? extends Behavior> aBehaviorClass, final Behavior... aBehaviorArray) {
        this(aBehaviorClass);

        addBehaviors(Arrays.asList(Objects.requireNonNull(aBehaviorArray)));
    }

    /**
     * Creates a new behavior list from the supplied behavior class and a list of behaviors.
     *
     * @param aBehaviorClass A type of behavior
     * @param aBehaviorList A list of behaviors
     * @throws InvalidBehaviorException If there is a disjoint in the supplied behavior list
     */
    @SuppressWarnings({ "PMD.ConstructorCallsOverridableMethod" })
    public BehaviorList(final Class<? extends Behavior> aBehaviorClass, final List<Behavior> aBehaviorList) {
        myBehaviorClass = Objects.requireNonNull(aBehaviorClass);
        addBehaviors(Objects.requireNonNull(aBehaviorList));
    }

    @Override
    public boolean add(final Behavior aBehavior) {
        return addBehavior(aBehavior);
    }

    @Override
    public void add(final int aIndex, final Behavior aBehavior) {
        super.add(aIndex, check(Objects.requireNonNull(aBehavior)));
    }

    @Override
    public boolean addAll(final Collection<? extends Behavior> aCollection) {
        return addBehaviors(aCollection);
    }

    @Override
    public boolean addAll(final int aIndex, final Collection<? extends Behavior> aCollection) {
        int index = aIndex;

        for (final Behavior behavior : Objects.requireNonNull(aCollection)) {
            super.add(index, check(Objects.requireNonNull(behavior)));
            index += 1;
        }

        return true;
    }

    /**
     * Checks that the supplied resource type is a match for the behaviors in this list.
     *
     * @param aBehaviorType A type of behavior that the list holds
     * @param aResourceType The type of resource that contains the behaviors list
     * @throws InvalidBehaviorException If the behaviors in this list aren't the expected type
     */
    public void checkType(final Class<? extends Behavior> aBehaviorType, final Class<?> aResourceType) {
        if (!myBehaviorClass.equals(aBehaviorType)) {
            throw new InvalidBehaviorException(MessageCodes.JPA_010, myBehaviorClass, aResourceType);
        }
    }

    /**
     * Gets the list's behavior type.
     *
     * @return The list's behavior type
     */
    public Class<? extends Behavior> getBehaviorType() {
        return myBehaviorClass;
    }

    /**
     * Adds a single behavior to this list.
     *
     * @param aBehavior A behaviors
     * @return True if behavior was added; else, false
     * @throws InvalidBehaviorException If the behavior cannot be added
     */
    private boolean addBehavior(final Behavior aBehavior) {
        return super.add(check(Objects.requireNonNull(aBehavior)));
    }

    /**
     * Adds a list of behaviors to this list.
     *
     * @param aBehaviorCollection A collection of behaviors
     * @return True if behaviors were added; else, false
     * @throws InvalidBehaviorException If the behaviors cannot be added
     */
    private boolean addBehaviors(final Collection<? extends Behavior> aBehaviorCollection) {
        for (final Behavior behavior : aBehaviorCollection) {
            if (!addBehavior(behavior)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check to see if the supplied behavior is disjointed.
     *
     * @param aBehavior A behavior to be checked
     * @return A checked behavior
     * @throws InvalidBehaviorException If the behavior is disjointed
     */
    private Behavior check(final Behavior aBehavior) {
        final String disjointsKey = aBehavior.toString();
        final Set<String> disjoints;

        // Check that the supplied behavior is valid
        checkBehaviorValidity(myBehaviorClass, aBehavior);
        disjoints = DISJOINTS.get(disjointsKey);

        // Check the existing behaviors to see if we have any conflicts
        for (final Behavior existing : this) {
            if (disjoints.contains(existing.toString())) {
                throw new InvalidBehaviorException(MessageCodes.JPA_054, aBehavior, existing);
            }
        }

        return aBehavior;
    }

    /**
     * Checks that the supplied behavior is valid for the supplied class.
     *
     * @param aClass The class that has the behavior
     * @param aBehavior A behavior associated with the supplied class
     * @throws InvalidBehaviorException If an invalid behavior for the supplied class is passed
     */
    private void checkBehaviorValidity(final Class<?> aClass, final Behavior aBehavior) {
        if (!aClass.isInstance(aBehavior)) {
            final String className = aClass.getSimpleName();
            final String behaviorName = aBehavior.getClass().getSimpleName();

            throw new InvalidBehaviorException(MessageCodes.JPA_031, behaviorName, className);
        }
    }
}
