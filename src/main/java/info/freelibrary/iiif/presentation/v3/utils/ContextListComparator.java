
package info.freelibrary.iiif.presentation.v3.utils;

import java.util.Comparator;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * A context list comparator that makes sure the required context is always last in the list.
 * <p>
 * Cf. https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions
 * </p>
 */
public class ContextListComparator<U> implements Comparator<U> {

    @Override
    public int compare(final U aFirstURI, final U aSecondURI) {
        if (Constants.CONTEXT_URI.equals(aFirstURI) && Constants.CONTEXT_URI.equals(aSecondURI)) {
            return 0;
        } else if (Constants.CONTEXT_URI.equals(aFirstURI)) {
            return 1;
        } else if (Constants.CONTEXT_URI.equals(aSecondURI)) {
            return -1;
        } else {
            return 0; // We leave all non-required contexts where they are
        }
    }

}
