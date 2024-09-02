
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

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
    private final Comparator<URI> myComparator;

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
            super.add(0, PRESENTATION_CONTEXT_URI);
        } else {
            super.sort(myComparator);
        }
    }

    /**
     * Adds a new context URI at the supplied index position. If the supplied context is not the IIIF Presentation
     * context and the supplied index position is zero, it will be added at position one instead. Only the default
     * context lives at position zero.
     *
     * @param aIndex An index position at which to add the supplied URI
     * @param aURI A URI to add at the supplied index position
     */
    @Override
    public void add(final int aIndex, final URI aURI) {
        if (!PRESENTATION_CONTEXT_URI.equals(aURI)) {
            if (aIndex == 0) {
                super.add(1, aURI);
                LOGGER.warn(MessageCodes.JPA_150, aURI);
            } else {
                super.add(aIndex, aURI);
            }
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
     * @param aUriCollection A collection of URIs to add at the supplied index position
     * @return If all the supplied URIs were successfully added
     */
    @Override
    public boolean addAll(final int aIndex, final Collection<? extends URI> aUriCollection) {
        if (aUriCollection.contains(PRESENTATION_CONTEXT_URI)) {
            aUriCollection.remove(PRESENTATION_CONTEXT_URI);
        }

        return super.addAll(aIndex, aUriCollection);
    }

    /**
     * If the supplied URI is not the default IIIF Presentation context, it is added at index position one. The first
     * index position (i.e., zero) is reserved for the default IIIF Presentation context.
     *
     * @param aURI A context URI to add at the beginning of the list
     */
    @SuppressWarnings(PMD.MISSING_OVERRIDE)
    public void addFirst(final URI aURI) {
        if (!PRESENTATION_CONTEXT_URI.equals(aURI)) {
            super.add(1, aURI);
        } // Ignore if passing the default URI, because that already lives at index position zero
    }

    /**
     * Adds the supplied context URI as the last in the list, unless the supplied context is the default IIIF
     * Presentation context (in which case it's ignored -- the list already contains the default context).
     *
     * @param aURI A context URI to add at the end of the list
     */
    @SuppressWarnings(PMD.MISSING_OVERRIDE)
    public void addLast(final URI aURI) {
        if (!PRESENTATION_CONTEXT_URI.equals(aURI)) {
            super.add(aURI); // Adding, by default, adds as the last item
        } else {
            LOGGER.warn(MessageCodes.JPA_149);
        }
    }

    /**
     * Clears all contexts except for the IIIF Presentation context URI, which must always exist in the list.
     */
    @Override
    public void clear() {
        super.clear();
        super.add(0, PRESENTATION_CONTEXT_URI);
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
     */
    @Override
    public URI remove(final int aIndex) {
        if (aIndex == 0) {
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
        if (aCollection.contains(PRESENTATION_CONTEXT_URI)) {
            final Stream<?> stream = aCollection.stream().filter(uri -> !PRESENTATION_CONTEXT_URI.equals(uri));
            final List<?> removables = stream.collect(Collectors.toList());

            return super.removeAll(removables);
        }

        return super.removeAll(aCollection);
    }

    /**
     * Removes a context that's selected by the supplied filter. If the default context is removed by the filter, it
     * will be automatically added back at index position zero.
     *
     * @param aFilter A filter to use to remove URIs from the list
     * @return True if the URIs were removed
     */
    @SuppressWarnings(PMD.MISSING_OVERRIDE)
    public boolean removeIf(final Predicate<? super URI> aFilter) {
        final boolean result = super.removeIf(aFilter);

        // If the filter removes our required default context, we add it back
        if (!PRESENTATION_CONTEXT_URI.equals(get(0))) {
            super.add(0, PRESENTATION_CONTEXT_URI);
        }

        return result;
    }

    /**
     * Removes the last context URI, unless the last URI is the default context. In that case, a
     * {@link NoSuchElementException} is thrown.
     *
     * @return The context URI that was removed
     * @throws NoSuchElementException If the list only has the required IIIF Presentation context
     */
    @SuppressWarnings(PMD.MISSING_OVERRIDE)
    public URI removeLast() {
        if (size() == SINGLE_INSTANCE) {
            throw new NoSuchElementException(LOGGER.getMessage(MessageCodes.JPA_039, PRESENTATION_CONTEXT_URI));
        }

        return remove(size() - 1);
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
        if (!PRESENTATION_CONTEXT_URI.equals(get(0))) {
            super.add(0, PRESENTATION_CONTEXT_URI);
        }
    }

    /**
     * Retains the contexts in the supplied collection and the default context (if it's also not included in the
     * supplied collection).
     *
     * @param aCollection A collection of URIs to retain, removing the rest
     * @return Whether the URIs were successfully retained
     */
    @Override
    public boolean retainAll(final Collection<?> aCollection) {
        final boolean result = super.retainAll(aCollection);

        // If we haven't added retained the required context, we add it back
        if (!PRESENTATION_CONTEXT_URI.equals(get(0))) {
            super.add(0, PRESENTATION_CONTEXT_URI);
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
     */
    @Override
    public URI set(final int aIndex, final URI aURI) {
        if (PRESENTATION_CONTEXT_URI.equals(aURI)) {
            if (aIndex != 0) {
                throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.JPA_149));
            } // else, just ignore -- this should be the current index of the default context
        } else if (aIndex != 0) {
            return super.set(aIndex, aURI);
        }

        throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.JPA_151, aURI));
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
    private static final class ContextListComparator implements Comparator<URI> {

        @Override
        public int compare(final URI aFirstURI, final URI aSecondURI) {
            int result = 0;

            if (!PRESENTATION_CONTEXT_URI.equals(aFirstURI) || !PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                if (PRESENTATION_CONTEXT_URI.equals(aFirstURI)) {
                    result = -1;
                } else if (PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                    result = 1;
                }
            }

            return result;
        }

    }
}
