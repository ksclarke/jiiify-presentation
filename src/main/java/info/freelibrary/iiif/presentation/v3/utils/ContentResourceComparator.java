
package info.freelibrary.iiif.presentation.v3.utils;

import java.util.Comparator;
import java.util.Objects;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * A comparator that returns the sort order of the {@code ImageAnnotation} properties.
 */
public class ContentResourceComparator implements Comparator<String> {

    // This is the sort order we want
    private static final String[] KEYS = new String[] { Constants.ID, Constants.TYPE, Constants.DEFAULT,
        Constants.ITEMS, Constants.WIDTH, Constants.HEIGHT, Constants.FORMAT, Constants.LABEL, Constants.SERVICE };

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
