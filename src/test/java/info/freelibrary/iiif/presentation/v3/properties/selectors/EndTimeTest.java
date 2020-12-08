package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.tkurz.media.fragments.temporal.Clocktime;

import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector.EndTime;

/**
 * Tests for end time.
 */
public class EndTimeTest {

    /**
     * Tests that passing different kinds of numbers to the constructor results in the same internal representation.
     */
    @Test
    public final void testConstructor() {
        final Number[] numbers = { 0, 0.0f, 0.0d };

        for (final Number number : numbers) {
            assertEquals(0, new EndTime(number).getClocktime().compareTo(Clocktime.ZERO));
        }
    }

}