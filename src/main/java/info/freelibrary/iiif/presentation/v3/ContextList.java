
package info.freelibrary.iiif.presentation.v3;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A list of context URIs.
 */
public class ContextList extends ArrayList<URI> implements List<URI> {

    /** The IIIF Presentation context URI. */
    public static final URI PRESENTATION_CONTEXT_URI = URI.create("http://iiif.io/api/presentation/3/context.json");

    /** The {@code ContextList} logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextList.class, MessageCodes.BUNDLE);

    /** The context list's <code>serialVersionUID</code>. */
    private static final long serialVersionUID = -877433424933939199L;

    /** A simple sorter that only moves the required context to the first slot. */
    private final ContextListComparator myComparator;

    /**
     * Creates a new list of contexts. It includes the IIIF Presentation context by default.
     */
    public ContextList() {
        super();

        myComparator = new ContextListComparator();
        super.add(0, PRESENTATION_CONTEXT_URI);
    }

    /**
     * Creates a new list of contexts. A {@code ContextList} includes the IIIF Presentation context by default. If one
     * is included in the supplied list, it will be ignored so it's not duplicated.
     *
     * @param aUriList A list of context URIs
     */
    public ContextList(final List<URI> aUriList) {
        super();

        myComparator = new ContextListComparator();
        super.addAll(aUriList);

        if (!super.contains(PRESENTATION_CONTEXT_URI)) {
            super.add(PRESENTATION_CONTEXT_URI);
        } else {
            super.sort(myComparator);
        }
    }

    /**
     * Adds a new context URI at the supplied index position. The IIIF Presentation context URI cannot be added, because
     * it exists in the list already.
     *
     * @param aIndex An index position at which to add the supplied URI
     * @param aURI A URI to add at the supplied index position
     */
    @Override
    public void add(final int aIndex, final URI aURI) {
        if (!PRESENTATION_CONTEXT_URI.equals(aURI)) {
            super.add(aIndex, aURI);
        } else {
            LOGGER.warn(MessageCodes.JPA_150);
        }
    }

    /**
     * Adds the supplied context URI. This will return false if the supplied URI is the default IIIF Presentation
     * context or if the supplied context could not be added for any other reason.
     *
     * @param aURI A URI to add to the list
     * @return True if the supplied URI was successfully added to the list
     */
    @Override
    public boolean add(final URI aURI) {
        return !PRESENTATION_CONTEXT_URI.equals(aURI) && super.add(aURI);
    }

    /**
     * Adds all the supplied context URIs. If the supplied collection contains the default IIIF Presentation context,
     * all contexts except for that one will be added. The default context is ignored because it already exists in the
     * context list.
     *
     * @param aUriCollection A collection of context URIs
     * @return True if all the URIs were added to the list
     */
    @Override
    public boolean addAll(final Collection<? extends URI> aUriCollection) {
        return aUriCollection.stream().filter(uri -> !PRESENTATION_CONTEXT_URI.equals(uri)).allMatch(super::add);
    }

    /**
     * Adds all the supplied context URIs. If the supplied collection contains the default IIIF Presentation context,
     * all contexts except for that one will be added. The default context is ignored because it already exists in the
     * context list.
     *
     * @param aIndex An index position at which to add the URIs in the supplied collection
     * @param aCollection A collection of URIs to add at the supplied index position
     * @return If all the supplied URIs were successfully added
     */
    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public boolean addAll(final int aIndex, final Collection<? extends URI> aCollection) {
        return super.addAll(aIndex, aCollection.stream().filter(uri -> !PRESENTATION_CONTEXT_URI.equals(uri)).toList());
    }

    /**
     * Adds a new context to the list.
     *
     * @param aURI A context URI to add at the beginning of the list
     * @throws UnsupportedOperationException If the default IIIF Presentation context URI was passed
     */
    @SuppressWarnings({ PMD.MISSING_OVERRIDE, Sonar.OVERRIDE_REQUIRED }) // JDK 21, but releasing with 17
    public void addFirst(final URI aURI) {
        if (PRESENTATION_CONTEXT_URI.equals(aURI)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_150));
        }

        super.add(0, aURI);
    }

    /**
     * Unsupported operation. The last context URI is hard-coded.
     *
     * @param aURI A context URI to add at the end of the list
     * @throws UnsupportedOperationException because the last context URI cannot be changed
     */
    @SuppressWarnings({ PMD.MISSING_OVERRIDE, Sonar.OVERRIDE_REQUIRED }) // JDK 21, but releasing with 17
    public void addLast(final URI aURI) {
        throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_150));
    }

    /**
     * Clears all contexts except for the IIIF Presentation context URI, which must always exist in the list.
     */
    @Override
    public void clear() {
        super.clear();
        super.add(PRESENTATION_CONTEXT_URI);
    }

    @Override
    public boolean equals(final Object aObject) {
        final ContextList other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (ContextList) aObject;

        return Arrays.equals(other.toArray(), super.toArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(super.toArray());
    }

    /**
     * Removes the context URI at the supplied index position, unless the position is zero. In this case, an
     * {@link IndexOutOfBoundsException} is thrown because it is out of bounds of the URIs that can be removed.
     *
     * @param aIndex The index position of the URI to remove from the list
     * @return The URI removed from the list
     * @throws IndexOutOfBoundsException If trying to remove the last context URI (which is required)
     */
    @Override
    public URI remove(final int aIndex) {
        if (aIndex == size() - 1) {
            throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.JPA_039, PRESENTATION_CONTEXT_URI));
        }

        return super.remove(aIndex);
    }

    /**
     * Removes the supplied context URI unless it: is the default IIIF Presentation context URI, is another type of
     * object, or is a context URI that doesn't exist in the list.
     *
     * @param aObj A URI to remove from the list
     * @return True if the supplied URI was removed
     */
    @Override
    public boolean remove(final Object aObj) {
        return !PRESENTATION_CONTEXT_URI.equals(aObj) && super.remove(aObj);
    }

    /**
     * Removes all the contexts in the supplied collection. If the IIIF Presentation context URI is included in the
     * collection, it is ignored. It cannot be removed.
     *
     * @param aCollection A collection of URIs to remove
     * @return True if the URIs were removed
     */
    @Override
    public boolean removeAll(final Collection<?> aCollection) {
        return super.removeAll(aCollection.stream().filter(uri -> !PRESENTATION_CONTEXT_URI.equals(uri)).toList());
    }

    /**
     * Removes a context that's selected by the supplied filter. If the default context is removed by the filter, it
     * will be automatically added back at index position zero.
     *
     * @param aFilter A filter to use to remove URIs from the list
     * @return True if the URIs were removed
     */
    @SuppressWarnings({ PMD.MISSING_OVERRIDE, Sonar.OVERRIDE_REQUIRED }) // JDK 21, but releasing with 17
    public boolean removeIf(final Predicate<? super URI> aFilter) {
        final boolean result = super.removeIf(aFilter);

        // If the filter removes our required default context, we add it back
        if (!PRESENTATION_CONTEXT_URI.equals(get(size() - 1))) {
            super.add(PRESENTATION_CONTEXT_URI);
        }

        return result;
    }

    /**
     * Unsupported operation. The last context URI (the URI for the IIIF Presentation context) cannot be removed.
     *
     * @return The context URI that was removed
     * @throws UnsupportedOperationException because the last context URI cannot be removed
     */
    @SuppressWarnings({ PMD.MISSING_OVERRIDE, Sonar.OVERRIDE_REQUIRED }) // JDK 21, but releasing with 17
    public URI removeLast() {
        throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_039, PRESENTATION_CONTEXT_URI));
    }

    /**
     * Replaces contexts in the list according to the supplied operator.
     *
     * @param anOperator A UnaryOperator to use in replacing URIs
     */
    @Override
    public void replaceAll(final UnaryOperator<URI> anOperator) {
        super.replaceAll(anOperator);

        // Remove all instances of our default context URI
        super.removeAll(List.of(PRESENTATION_CONTEXT_URI));

        // Add back a single instance of our default context URI
        super.add(PRESENTATION_CONTEXT_URI);
    }

    /**
     * Retains the contexts in the supplied collection and the default context (regardless of whether or not it exists
     * in the supplied collection).
     *
     * @param aCollection A collection of URIs to retain, removing the rest
     * @return Whether the URIs were successfully retained
     */
    @Override
    public boolean retainAll(final Collection<?> aCollection) {
        final boolean result = super.retainAll(aCollection);

        // If we haven't added retained the required context, we add it back
        if (!PRESENTATION_CONTEXT_URI.equals(get(size() - 1))) {
            super.add(PRESENTATION_CONTEXT_URI);
        }

        return result;
    }

    /**
     * Sets the supplied context URI at the supplied index position. The default context can not be set with this
     * method.
     *
     * @param aIndex An index position of the URI to set
     * @param aURI A URI to set at the supplied index position
     * @return The context URI that used to be at the supplied index position
     * @throws IndexOutOfBoundsException If an invalid index position is used
     */
    @Override
    public URI set(final int aIndex, final URI aURI) {
        if (!PRESENTATION_CONTEXT_URI.equals(aURI)) {
            if (aIndex != size() - 1) {
                return super.set(aIndex, aURI);
            }

            throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.JPA_149));
        }

        // We're trying to set the default IIIF Presentation context URI at the wrong index? It's supplied by default.
        if (aIndex != size() - 1) {
            throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.JPA_150));
        }

        // We're setting the IIIF Presentation in the index position where it already lives
        return PRESENTATION_CONTEXT_URI;
    }

    /**
     * This method is not supported because {@code ContextList} has a prescribed sort order that can not be overridden.
     *
     * @param aComparator A comparator that could be used to sort the list
     */
    @Override
    public void sort(final Comparator<? super URI> aComparator) {
        throw new UnsupportedOperationException();
    }

    /**
     * A context list comparator that makes sure the required context is always last in the list.
     * <p>
     * Cf. https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions
     * </p>
     */
    private static final class ContextListComparator implements Comparator<URI>, Serializable {

        /** The {@code serialVersionUID} for the {@code ContextListComparator}. */
        private static final long serialVersionUID = 5516185678973318858L;

        @Override
        public int compare(final URI aFirstURI, final URI aSecondURI) {
            int result = 0;

            if (!PRESENTATION_CONTEXT_URI.equals(aFirstURI) || !PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                if (PRESENTATION_CONTEXT_URI.equals(aFirstURI)) {
                    result = 1;
                } else if (PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                    result = -1;
                }
            }

            return result;
        }

    }
}
