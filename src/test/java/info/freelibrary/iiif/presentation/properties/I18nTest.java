
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Tests internationalizations.
 */
public class I18nTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nTest.class, Constants.BUNDLE_NAME);

    private static final String AR_LATN = "ar-Latn";

    private static final String UNDEFINED = "und";

    private LoremIpsum myLorem;

    private String myTestHTML;

    private String myTestString;

    /**
     * Sets up the tests.
     */
    @Before
    public void setUp() {
        myLorem = LoremIpsum.getInstance();
        myTestHTML = myLorem.getHtmlParagraphs(1, 1);
        myTestString = myLorem.getWords(3, 6);
    }

    /**
     * Tests a hyphenated language tag value.
     */
    @Test
    public final void testHyphenatedLangTag() {
        final I18n i18n = new I18n(AR_LATN.toLowerCase(), myTestString);
        final List<String> strings = Arrays.asList(myTestString);

        assertEquals(AR_LATN, i18n.getLang());
        assertEquals(strings, i18n.getStrings());
    }

    /**
     * Tests the use of the "none" language tag.
     */
    @Test
    public final void testNoneLangTag() {
        assertEquals(I18n.DEFAULT_LANG, new I18n(I18n.DEFAULT_LANG, myTestString).getLang());
    }

    /**
     * Tests that an exception is thrown if an unknown language tag is supplied.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNonsenseLangTag() {
        new I18n("THIS_SHOULD_THROW_EXCEPTION", myTestString);
    }

    /**
     * Tests that an exception is thrown when HTML is supplied but not allowed.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testExplicitlyDisallowedHTML() {
        new I18n(I18n.DEFAULT_LANG, myTestHTML, false);
    }

    /**
     * Tests that HTML is allowed when appropriate.
     */
    @Test
    public final void testAllowedHTML() {
        new I18n(I18n.DEFAULT_LANG, myTestHTML, true);
    }

    /**
     * Test that checks that angle brackets alone don't get recognized as HTML
     */
    @Test
    public final void testHtmlLikeString() {
        new I18n(I18n.DEFAULT_LANG, "If A<B then B>A", false);
    }

    /**
     * Tests the clearStrings() method.
     */
    @Test
    public final void testClearStrings() {
        final List<String> strings = Arrays.asList(myTestString, myTestString);

        assertEquals(0, new I18n(I18n.DEFAULT_LANG, strings).clear().size());
    }

    /**
     * Tests getting and setting language tags.
     */
    @Test
    public final void testSetLanguageTag() {
        assertEquals(I18n.DEFAULT_LANG, new I18n(AR_LATN, myTestString).setLang(I18n.DEFAULT_LANG).getLang());
    }

    /**
     * Tests setting undefined language tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetUndefinedLanguageTag() {
        new I18n(UNDEFINED, myTestString);
    }

    /**
     * Tests setting an undefined language tag via the setLanguageTag() method.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetUndefinedLanguageTagInMethod() {
        new I18n(I18n.DEFAULT_LANG, myTestString).setLang(UNDEFINED);
    }

    /**
     * Tests getStrings() from a single string source.
     */
    @Test
    public final void testGetStringsSingleStringSource() {
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, myTestString);
        final List<String> strings = Arrays.asList(myTestString);

        assertEquals(strings, i18n.getStrings());
    }

    /**
     * Tests getStrings() from an array string source.
     */
    @Test
    public final void testGetStringsListSource() {
        final List<String> strings = Arrays.asList(myTestString);
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, strings);

        assertEquals(strings, i18n.getStrings());
    }

    /**
     * Tests addString() really does add a new string to the list.
     */
    @Test
    public final void testAddString() {
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, myTestString);

        if (!i18n.addString(myTestString)) {
            fail(LOGGER.getMessage(MessageCodes.JPA_021));
        }

        assertEquals(2, i18n.size());
    }

    /**
     * Tests addStrings() really does add a new string to the list.
     */
    @Test
    public final void testAddStrings() {
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, myTestString);
        final List<String> strings = Arrays.asList(myTestString, myTestString);

        if (!i18n.addStrings(strings)) {
            fail(LOGGER.getMessage(MessageCodes.JPA_021));
        }

        assertEquals(3, i18n.size());
    }

    /**
     * Tests the string iterator.
     */
    @Test
    public final void testIterator() {
        final List<String> strings = Arrays.asList(myTestString, myTestString, myTestString);
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, strings);
        final Iterator<String> iterator = i18n.iterator();

        int count = 0;

        while (iterator.hasNext()) {
            assertEquals(myTestString, iterator.next());
            count += 1;
        }

        assertEquals(3, count);
    }

    /**
     * Tests the value spliterator.
     */
    @Test
    public final void testSpliterator() {
        final List<String> strings = Arrays.asList(myTestString, myTestString, myTestString);
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, strings);
        final Spliterator<String> spliterator = i18n.spliterator();

        spliterator.forEachRemaining(string -> {
            assertEquals(myTestString, string);
        });
    }

    /**
     * Tests the value forEach() method.
     */
    @Test
    public final void testForEach() {
        final List<String> strings = Arrays.asList(myTestString, myTestString, myTestString);
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, strings);

        i18n.forEach(string -> {
            assertEquals(myTestString, string);
        });
    }

    /**
     * Tests toJSON() method.
     */
    @Test
    public final void testToJSON() {
        final List<String> strings = Arrays.asList("one two", "three four", "five six");
        final I18n i18n = new I18n(I18n.DEFAULT_LANG, strings);

        assertEquals("{\"none\":[\"one two\",\"three four\",\"five six\"]}", i18n.toJSON().encode());
    }
}
