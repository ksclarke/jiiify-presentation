
package info.freelibrary.iiif.presentation.v3.id;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.paukov.combinatorics3.Generator;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.Stopwatch;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.Range;
import info.freelibrary.iiif.presentation.v3.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Mints intra-manifest IDs using predictable ID templates.
 */
class DefaultMinter implements Minter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMinter.class, MessageCodes.BUNDLE);

    private static final String CANVAS_ID_TEMPLATE = "{}/canvas-{}";

    private static final String RANGE_ID_TEMPLATE = "{}/range-{}";

    private static final String ANNO_ID_TEMPLATE = "{}/annotations/anno-{}";

    private static final String PAGE_ID_TEMPLATE = "{}/anno-page-{}";

    // All the alpha-numeric characters we use in creating NOIDs; lower case L is not used because it looks like "1"
    private static final Character[] CHARS =
            new Character[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

    /* The maximum number of NOIDs, given the size of our character array. */
    private static final int MAX_NOID_COUNT = 1500625;

    private static final List<String> NOIDS; // Static array is initialized just once

    static {
        final Stopwatch stopwatch = new Stopwatch().start();
        final List<String> noids = new ArrayList<>();

        // Create a list of NOIDs for the manuscript to use in constructing IDs
        Generator.<Character>permutation(CHARS).withRepetitions(4).stream().forEach(charList -> {
            noids.add(charList.stream().map(String::valueOf).collect(Collectors.joining()));
        });

        // Shuffle them so they appear to be random
        Collections.shuffle(noids);

        // Finalize the NOIDs List for all future minters
        NOIDS = Collections.unmodifiableList(noids);
        LOGGER.debug(MessageCodes.JPA_101, stopwatch.stop().getSeconds());

        // Do a sanity check on the number of NOIDs in our list
        if (MAX_NOID_COUNT != NOIDS.size()) {
            throw new I18nRuntimeException(MessageCodes.BUNDLE, MessageCodes.JPA_102, NOIDS.size(), MAX_NOID_COUNT);
        }
    }

    private final Set<URI> myExistingIDs = new HashSet<>();

    private final Iterator<String> myIterator;

    private final URI myManifestID;

    private int myUsedNOIDs;

    /**
     * Creates a manifest component ID minter.
     *
     * @param aManifest The manifest for which to mint IDs
     */
    DefaultMinter(final Manifest aManifest) {
        myManifestID = aManifest.getID();
        myIterator = new NoidIterator();

        // Record which IDs are already in use by this manifest
        findPreexistingIDs(aManifest);
    }

    /**
     * A minter that just takes a manifest ID. It won't know about IDs that already exist in a manifest.
     *
     * @param aManifestID A manifest ID
     */
    DefaultMinter(final URI aManifestID) {
        myManifestID = aManifestID;
        myIterator = new NoidIterator();
    }

    /**
     * Gets the manifest ID associated with this minter.
     *
     * @return The manifest ID associated with this minter
     */
    @Override
    public URI getManifestID() {
        return myManifestID;
    }

    /**
     * Gets a new canvas ID.
     *
     * @return An ID to use on a canvas
     */
    @Override
    public URI getCanvasID() {
        try {
            final URI id = URI.create(StringUtils.format(CANVAS_ID_TEMPLATE, myManifestID, myIterator.next()));
            return myExistingIDs.contains(id) ? getCanvasID() : increment(id);
        } catch (final NoSuchElementException details) {
            throw new MintingException(MessageCodes.JPA_105, myManifestID, Canvas.class.getSimpleName());
        }
    }

    /**
     * Gets a new annotation ID.
     *
     * @return An ID to use on an annotation
     */
    @Override
    public URI getAnnotationID() {
        try {
            final URI id = URI.create(StringUtils.format(ANNO_ID_TEMPLATE, myManifestID, myIterator.next()));
            return myExistingIDs.contains(id) ? getAnnotationID() : increment(id);
        } catch (final NoSuchElementException details) {
            throw new MintingException(MessageCodes.JPA_105, myManifestID, Annotation.class.getSimpleName());
        }
    }

    // Could also do an annotation ID that lives under an AnnotationPage

    /**
     * Gets a new annotation page ID.
     *
     * @param <C> A type of canvas
     * @param aCanvasResource The canvas that the annotation page is going onto
     * @return An ID to use on the supplied annotation page
     */
    @Override
    public <C extends CanvasResource<C>> URI getAnnotationPageID(final CanvasResource<C> aCanvasResource) {
        try {
            final URI canvasID = aCanvasResource.getID();
            final URI id = URI.create(StringUtils.format(PAGE_ID_TEMPLATE, canvasID, myIterator.next()));

            return myExistingIDs.contains(id) ? getAnnotationPageID(aCanvasResource) : increment(id);
        } catch (final NoSuchElementException details) {
            throw new MintingException(MessageCodes.JPA_105, myManifestID, AnnotationPage.class.getSimpleName());
        }
    }

    /**
     * Gets a new range ID.
     *
     * @return An ID to use on a range
     */
    @Override
    public URI getRangeID() {
        try {
            final URI id = URI.create(StringUtils.format(RANGE_ID_TEMPLATE, myManifestID, myIterator.next()));
            return myExistingIDs.contains(id) ? getRangeID() : increment(id);
        } catch (final NoSuchElementException details) {
            throw new MintingException(MessageCodes.JPA_105, myManifestID, Range.class.getSimpleName());
        }
    }

    /**
     * Gets total number of IDs that this minter can mint.
     *
     * @return The number of IDs that this minter can mint
     */
    @Override
    public int size() {
        return NOIDS.size();
    }

    /**
     * Gets the number of IDs that are available for use.
     *
     * @return The number of IDs that are available for use
     */
    @Override
    public int remaining() {
        return NOIDS.size() - (myUsedNOIDs + myExistingIDs.size());
    }

    /**
     * Returns whether there is another ID available to be minted.
     *
     * @return Whether there is another ID available to be minted
     */
    @Override
    public boolean hasNext() {
        return myIterator.hasNext();
    }

    /**
     * Increments the ID count, returning the ID to be used.
     *
     * @param aID An ID to be used
     * @return The ID to be used
     */
    private URI increment(final URI aID) {
        myUsedNOIDs += 1;
        return aID;
    }

    /**
     * Find IDs already associated with this manifest. This doesn't look for all IDs, but just the ones that a minter
     * might create.
     *
     * @param aManifest A supplied manifest
     */
    private void findPreexistingIDs(final Manifest aManifest) {
        final List<Canvas> canvases = aManifest.getCanvases();
        final List<Range> ranges = aManifest.getRanges();

        for (final Canvas canvas : canvases) {
            if (!myExistingIDs.add(canvas.getID())) {
                throw new MintingException(MessageCodes.JPA_100, canvas.getID());
            }

            for (final AnnotationPage<PaintingAnnotation> paintingPage : canvas.getPaintingPages()) {
                if (!myExistingIDs.add(paintingPage.getID())) {
                    LOGGER.warn(MessageCodes.JPA_100, paintingPage.getID());
                }

                findAnnotationIDs(paintingPage.getAnnotations());
            }

            for (final AnnotationPage<SupplementingAnnotation> supplementingPage : canvas.getSupplementingPages()) {
                if (!myExistingIDs.add(supplementingPage.getID())) {
                    LOGGER.warn(MessageCodes.JPA_100, supplementingPage.getID());
                }

                findAnnotationIDs(supplementingPage.getAnnotations());
            }
        }

        ranges.forEach(range -> {
            if (!myExistingIDs.add(range.getID())) {
                LOGGER.warn(MessageCodes.JPA_100, range.getID());
            }
        });
    }

    /**
     * Gets the IDs from annotations.
     *
     * @param <A> A type of annotation
     * @param aAnnotationList A list of annotations
     */
    private <A extends Annotation<A>> void findAnnotationIDs(final List<A> aAnnotationList) {
        aAnnotationList.stream().forEach(annotation -> {
            if (!myExistingIDs.add(annotation.getID())) {
                LOGGER.warn(MessageCodes.JPA_100, annotation.getID());
            }
        });
    }

    /**
     * An iterator that returns NOIDs in a pseudo-random order.
     */
    private class NoidIterator implements Iterator<String> {

        /* The high end of an integer range used for randomization. */
        private static final int MAX_RANDOM_INT = 20;

        /* Where the iterator started cycling through the array. */
        private final int myStart;

        /* The number of NOIDs to skip in each iteration. */
        private int mySkipCount;

        /* The index position for the next available NOID. */
        private int myIndex;

        /* The number of NOIDs this iterator has returned. */
        private int myCount;

        /* The number of times we've cycled through the array. */
        private int myIteration;

        /**
         * Creates a new NOID iterator with a randomized start and skip count.
         */
        NoidIterator() {
            final ThreadLocalRandom randomizer = ThreadLocalRandom.current();

            // The MAX_RANDOM_INT + 1 makes the int range inclusive
            myStart = randomizer.nextInt(0, MAX_RANDOM_INT + 1);
            mySkipCount = randomizer.nextInt(1, MAX_RANDOM_INT + 1);
            myIndex = myStart;

            LOGGER.debug(MessageCodes.JPA_106, myManifestID, myStart, mySkipCount);
        }

        @Override
        public boolean hasNext() {
            return myCount < MAX_NOID_COUNT;
        }

        @Override
        public String next() {
            if (hasNext()) {
                final String noid = NOIDS.get(myIndex);

                // Check to see if we've retrieved all the possible NOIDs and increment if not
                if (++myCount < MAX_NOID_COUNT) {
                    myIndex += mySkipCount;

                    // When at the end of an iteration cycle, reset the index to a new start
                    if (myIndex >= MAX_NOID_COUNT) {
                        myIndex = ++myIteration + myStart;

                        // Loop around to get the remaining ones from the start of the array
                        if (myIndex >= mySkipCount + myStart) {
                            mySkipCount = 1;
                            myIndex = 0;
                        }
                    }
                }

                return noid;
            } else {
                throw new IndexOutOfBoundsException(myIndex);
            }
        }

    }
}
