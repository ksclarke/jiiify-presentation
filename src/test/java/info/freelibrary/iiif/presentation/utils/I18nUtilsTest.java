
package info.freelibrary.iiif.presentation.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.I18n;

/**
 * Tests for the I18nUtils class.
 */
public class I18nUtilsTest {

    /**
     * Tests stripping HTML tags from supplied text.
     */
    @Test
    public final void testStrippingHTML() {
        assertEquals("html to strip", I18nUtils.stripHTML("<p>html to strip</p>"));
    }

    /**
     * Tests stripping HTML from multiple internationalizations.
     */
    @Test
    public final void testStrippingI18ns() {
        final I18n[] cleanI18ns = new I18n[] { new I18n(I18n.DEFAULT_LANG, "something"), new I18n(I18n.DEFAULT_LANG,
                "everything iseverything"), new I18n(I18n.DEFAULT_LANG, "") };
        final I18n[] results = I18nUtils.stripHTML(new I18n[] { new I18n(I18n.DEFAULT_LANG, "<p>something</p>"),
            new I18n(I18n.DEFAULT_LANG, "</p>everything is<br/>everything"), new I18n(I18n.DEFAULT_LANG,
                    "<form></form>") });

        assertEquals(cleanI18ns.length, results.length);

        for (int index = 0; index < cleanI18ns.length; index++) {
            assertEquals(cleanI18ns[index].toJSON(), results[index].toJSON());
        }
    }

    /**
     * Tests stripping the script tag.
     */
    @Test
    public final void testCleanHtmlScriptTag() {
        final String html = "<p>some <script>alert('Hello');</script>text</p>";
        assertEquals("<p>some text</p>", I18nUtils.cleanHTML(html));
    }

    /**
     * Tests stripping the style tag.
     */
    @Test
    public final void testCleanHtmlStyleTag() {
        final String html = "<p><style>h1 {color:red;}</style>some style</p>";
        assertEquals("<p>some style</p>", I18nUtils.cleanHTML(html));
    }

    /**
     * Tests stripping the object tag.
     */
    @Test
    public final void testCleanHtmlObjectTag() {
        final String html = "<p>some object<object width='400' height='400' data='helloworld.swf'></object></p>";
        assertEquals("<p>some object</p>", I18nUtils.cleanHTML(html));
    }

    /**
     * Tests stripping the form and input tags.
     */
    @Test
    public final void testCleanHtmlFormTag() {
        final String html = "<p>form text<form><input type='text' name='fname'/></form></p>";
        assertEquals("<p>form text</p>", I18nUtils.cleanHTML(html));
    }

    /**
     * Tests stripping XML comments.
     */
    @Test
    public final void testCleanHtmlXmlComments() {
        final String html = "<p>some comment<!-- Hello world! --></p>";
        assertEquals("<p>some comment</p>", I18nUtils.cleanHTML(html));
    }

    /**
     * Tests stripping XML processing instructions.
     */
    @Test
    public final void testCleanHtmlXmlPIs() {
        final String html = "<p>some PI<?xml-stylesheet href='tutorialspointstyle.css' type='text/css'?></p>";
        assertEquals("<p>some PI</p>", I18nUtils.cleanHTML(html));
    }

    /**
     * Tests stripping XML CDATA.
     */
    @Test
    public final void testCleanHtmlXmlCDATA() {
        final String html = "<p>some CDATA<![CDATA[<div>TEXT!</div>]]></p>";
        assertEquals("<p>some CDATA</p>", I18nUtils.cleanHTML(html));
    }

    /**
     * Tests that the HTML check works.
     */
    @Test
    public final void testHasHTML() {
        assertTrue(I18nUtils.isHtmlFragment("<p>asdf<br/>test</p>"));
    }

    /**
     * Tests whether bad HTML is recognized. The spec says there can't be whitespace on the outside of the root level
     * element.
     */
    @Test
    public final void testHasBadHTMLBecauseSpace() {
        assertFalse(I18nUtils.isHtmlFragment("<p>asdf</p> "));
    }

    /**
     * Tests whether bad HTML is recognized. The spec says there must be a root level element that conforms to XML's
     * well-formedness rules.
     */
    @Test
    public final void testHasBadHTMLBecauseMissingTag() {
        assertFalse(I18nUtils.isHtmlFragment("<p>asdf"));
    }

    /**
     * Tests for the presence of a generic start tag.
     */
    @Test
    public final void testForAnyTag() {
        assertTrue(I18nUtils.hasHtml("<asdf>"));
    }

    /**
     * Tests for the presence of a spaced empty tag.
     */
    @Test
    public final void testForAnyTagSpaced() {
        assertTrue(I18nUtils.hasHtml("<asdf />"));
    }

    /**
     * Tests for the presence of a weirdly spaced empty tag.
     */
    @Test
    public final void testForAnyTagDoubleSpaced() {
        assertTrue(I18nUtils.hasHtml("<asdf / >"));
    }

    /**
     * Tests for the presence of a non-spaced empty tag.
     */
    @Test
    public final void testForAnyTagNoSpace() {
        assertTrue(I18nUtils.hasHtml("<asdf/>"));
    }
}
