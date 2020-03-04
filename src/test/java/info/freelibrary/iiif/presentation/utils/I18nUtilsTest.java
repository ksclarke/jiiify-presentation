
package info.freelibrary.iiif.presentation.utils;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.iiif.presentation.properties.I18n;

/**
 * Tests for the I18nUtils class.
 */
public class I18nUtilsTest {

    private LoremIpsum myLorem;

    /**
     * Sets up the tests.
     */
    @Before
    public void setUp() {
        myLorem = LoremIpsum.getInstance();
    }

    /**
     * Tests detecting HTML.
     */
    @Test
    public final void testHasHTML() {
        new I18n(I18n.DEFAULT_LANG, myLorem.getHtmlParagraphs(1, 1), true);
    }

    /**
     * Tests detecting inappropriate HTML.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testHasHTMLFails() {
        new I18n(I18n.DEFAULT_LANG, myLorem.getHtmlParagraphs(1, 1), false);
    }

    /**
     * Tests validateI18ns().
     */
    @Test
    public final void testValidateI18ns() {
        I18nUtils.validateI18ns(false, new I18n(I18n.DEFAULT_LANG, myLorem.getWords(6)));
    }

    /**
     * Tests validateI18ns() with inappropriate HTML.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testValidateI18nsWithHTML() {
        I18nUtils.validateI18ns(false, new I18n(I18n.DEFAULT_LANG, myLorem.getHtmlParagraphs(1, 1)));
    }

    /**
     * Tests createI18ns().
     */
    @Test
    public final void testCreateI18ns() {
        I18nUtils.createI18ns(false, myLorem.getWords(6));
    }

    /**
     * Tests createI18ns() with inappropriate HTML.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testCreateI18nsWithHTML() {
        I18nUtils.createI18ns(false, myLorem.getHtmlParagraphs(1, 1));
    }

}
