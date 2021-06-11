
package info.freelibrary.iiif.presentation.v3.utils;

import java.util.Comparator;
import java.util.Objects;

/**
 * A comparator that returns the sort order of the {@link Annotation} properties.
 */
public class ContentResourceComparator implements Comparator<String> {

    /**
     * Defines the desired content resource sort order.
     */
    private static final String[] KEYS = { JsonKeys.ID, JsonKeys.TYPE, JsonKeys.DEFAULT, JsonKeys.ITEMS,
        JsonKeys.FORMAT, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.LABEL, JsonKeys.SERVICE };

    @Override
    public int compare(final String aFirstKey, final String aSecondKey) {
        final int firstKeyIndex = getIndex(KEYS, aFirstKey);
        final int secondKeyIndex = getIndex(KEYS, aSecondKey);

        return ((Integer) firstKeyIndex).compareTo(secondKeyIndex);
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
