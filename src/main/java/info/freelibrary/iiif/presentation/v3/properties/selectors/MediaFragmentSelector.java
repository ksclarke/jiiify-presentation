
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.github.tkurz.media.fragments.FragmentParser;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.spatial.SpatialFragment;
import com.github.tkurz.media.fragments.temporal.Clocktime;
import com.github.tkurz.media.fragments.temporal.NPTFragment;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A media fragment selector selects a region of interest in a resource with spatial and/or temporal dimensions.
 */
public class MediaFragmentSelector implements FragmentSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaFragmentSelector.class, MessageCodes.BUNDLE);

    private final MediaFragment myMediaFragment;

    /**
     * Creates a media fragment selector from the supplied string.
     *
     * @param aFragment A media fragment string
     * @throws IllegalArgumentException If the supplied string isn't a valid media fragment
     */
    public MediaFragmentSelector(final String aFragment) throws IllegalArgumentException {
        try {
            myMediaFragment = new FragmentParser(new StringReader(aFragment)).run(MediaFragment.Type.FRAGMENT);
        } catch (final ParseException details) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_042, aFragment, details));
        }
    }

    /**
     * Creates a media fragment selector from the supplied spatial dimensions.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     */
    public MediaFragmentSelector(final int aX, final int aY, final int aWidth, final int aHeight) {
        myMediaFragment = new MediaFragment();
        myMediaFragment.setSpatialFragment(createSpatialFragment(aX, aY, aWidth, aHeight));
    }

    /**
     * Creates a media fragment selector from the supplied temporal dimensions.
     * <p>
     * Users may pass either 1 or 2 temporal dimensions. Examples:
     * <ul>
     * <li><code>new MediaFragmentSelector(0.0f)</code> &#8594; <code>#t=0</code></li>
     * <li><code>new MediaFragmentSelector(0.0f, null)</code> &#8594; <code>#t=0</code></li>
     * <li><code>new MediaFragmentSelector(null, 1.0f)</code> &#8594; <code>#t=,1</code></li>
     * <li><code>new MediaFragmentSelector(0.0f, 1.0f)</code> &#8594; <code>#t=0,1</code></li>
     * </ul>
     *
     * @param aTemporalArray An array of time instants
     * @throws IllegalArgumentException If the supplied dimension cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final Float... aTemporalArray) throws IllegalArgumentException {
        myMediaFragment = new MediaFragment();
        myMediaFragment.setTemporalFragment(createTemporalFragment(aTemporalArray));
    }

    /**
     * Creates a media fragment selector from the supplied spatio-temporal dimensions.
     * <p>
     * Users may pass either 1 or 2 temporal dimensions in the same manner as with
     * {@link #MediaFragmentSelector(Float...)}.
     * <p>
     * All spatial dimensions are required.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     * @param aTemporalArray An array of time instants
     * @throws IllegalArgumentException If the supplied dimensions cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final int aX, final int aY, final int aWidth, final int aHeight,
            final Float... aTemporalArray) throws IllegalArgumentException {
        myMediaFragment = new MediaFragment();
        myMediaFragment.setSpatialFragment(createSpatialFragment(aX, aY, aWidth, aHeight));
        myMediaFragment.setTemporalFragment(createTemporalFragment(aTemporalArray));
    }

    /**
     * Creates the spatial component of a media fragment.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     * @return The spatial fragment
     */
    private SpatialFragment createSpatialFragment(final int aX, final int aY, final int aWidth, final int aHeight) {
        return new SpatialFragment(aX, aY, aWidth, aHeight);
    }

    /**
     * Creates the temporal component of a media fragment.
     *
     * @param aInstantsArray An array of time instants
     * @return The temporal fragment
     * @throws IllegalArgumentException If the supplied dimensions cannot be used to construct a valid media fragment
     */
    private NPTFragment createTemporalFragment(final Float... aInstantsArray) throws IllegalArgumentException {
        final NPTFragment temporalFragment = new NPTFragment();
        final Float start;
        final Float end;

        if (aInstantsArray.length == 1) {
            start = aInstantsArray[0];

            if (start != null) {
                temporalFragment.setStart(new Clocktime(aInstantsArray[0]));
            } else {
                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_063));
            }
        } else if (aInstantsArray.length == 2) {
            start = aInstantsArray[0];
            end = aInstantsArray[1];

            if (start != null || end != null) {
                if (start != null) {
                    temporalFragment.setStart(new Clocktime(start));
                }
                if (end != null) {
                    temporalFragment.setEnd(new Clocktime(end));
                }
            } else {
                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_063));
            }
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_062));
        }
        return temporalFragment;
    }

    @Override
    public URI getConformsTo() {
        return Constants.MEDIA_FRAGMENT_SPECIFICATION_URI;
    }

    /**
     * Gets the value of the media fragment selector, with the spatial part ordered before the temporal part.
     *
     * @return The value
     */
    @Override
    public String getValue() {
        final List<String> list = new ArrayList<>();

        if (myMediaFragment.hasSpatialFragment()) {
            list.add(myMediaFragment.getSpatialFragment().stringValue());
        }
        if (myMediaFragment.hasTemporalFragment()) {
            list.add(myMediaFragment.getTemporalFragment().stringValue());
        }
        return String.join("&", list);
    }

}
