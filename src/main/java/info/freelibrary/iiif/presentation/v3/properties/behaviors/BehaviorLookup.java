
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;

import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for looking up behaviors by label.
 */
final class BehaviorLookup {

    /**
     * Private constructor to prevent instantiation.
     */
    private BehaviorLookup() {
        // This is intentionally left empty
    }

    /**
     * Looks up a behavior by its label.
     *
     * @param <T> The type of behavior
     * @param aType The type of behavior
     * @param aLabel The label of the behavior
     * @return An optional behavior, or empty if not found
     */
    static <T extends Enum<T> & Behavior> Optional<T> fromLabel(final Class<T> aType, final String aLabel) {
        for (final T behavior : Objects.requireNonNull(aType).getEnumConstants()) {
            if (behavior.label().equalsIgnoreCase(aLabel)) {
                return Optional.of(behavior);
            }
        }

        return Optional.empty();
    }
}
