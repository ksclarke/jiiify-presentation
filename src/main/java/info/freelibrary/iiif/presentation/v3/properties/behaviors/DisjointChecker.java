
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

// BEGIN GENERATED CODE
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorConstants.*;
// END GENERATED CODE

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A disjoint behavior checker.
 */
public class DisjointChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisjointChecker.class, MessageCodes.BUNDLE);

    // A map of behavior disjoints
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

    private final List<Behavior> myBehaviors;

    /**
     * Creates a new disjoint checker.
     */
    public DisjointChecker() {
        myBehaviors = List.of();
    }

    /**
     * Creates a new disjoint checker.
     *
     * @param aBehaviorList A list of pre-existing behaviors
     */
    public DisjointChecker(final List<Behavior> aBehaviorList) {
        myBehaviors = aBehaviorList;
    }

    /**
     * Check to see if the behaviors are disjointed.
     *
     * @param aClass A class of behaviors being set
     * @param aBehaviorArray An array of behaviors to be set
     * @throws IllegalArgumentException If the behaviors are disjointed
     */
    public void check(final Class<?> aClass, final Behavior... aBehaviorArray) throws IllegalArgumentException {
        for (int index = 0; index < aBehaviorArray.length; index++) {
            final Behavior behavior = aBehaviorArray[index];
            final Set<String> disjoints;

            // Check that the supplied behaviors are valid
            if (!aClass.isInstance(behavior)) {
                final String className = aClass.getSimpleName();
                final String behaviorName = behavior.getClass().getSimpleName();

                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_031, behaviorName, className));
            }

            disjoints = DISJOINTS.get(behavior.toString());

            // Check the existing behaviors to see if we have any conflicts
            for (final Behavior existing : myBehaviors) {
                if (disjoints.contains(existing.toString())) {
                    throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_054, behavior, existing));
                }
            }

            // Check the new behaviors to see if we have any conflicts
            for (int position = 0; position < aBehaviorArray.length; position++) {
                final Behavior supplied = aBehaviorArray[position];

                // Skip the one we're looking at, but check the rest for disjoints
                if (position != index && disjoints.contains(supplied.toString())) {
                    throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_055, behavior, supplied));
                }
            }
        }
    }

    @Override
    public String toString() {
        final List<String> keyList = new ArrayList<>(DISJOINTS.keySet());
        final StringBuilder builder = new StringBuilder("{");

        // Sort behavior keys for consistency
        Collections.sort(keyList);

        for (final String key : keyList) {
            final List<String> values = new ArrayList<>(DISJOINTS.get(key));

            // Sort disjoint behaviors for consistency
            Collections.sort(values);

            builder.append(key).append('=').append(values).append(", ");
        }

        return builder.delete(builder.length() - 2, builder.length()).append('}').toString();
    }
}
