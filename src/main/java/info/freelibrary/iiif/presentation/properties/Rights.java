
package info.freelibrary.iiif.presentation.properties;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A string that identifies a license or rights statement that applies to the content of the resource, such as the
 * JSON of a Manifest or the pixels of an image. The value must be drawn from the set of Creative Commons license
 * URIs, the RightsStatements.org rights statement URIs, or those added via the extension mechanism. The inclusion of
 * this property is informative, and for example could be used to display an icon representing the rights assertions.
 */
public class Rights {

    private static final int LIST_PADDING = 2;

    private final List<URL> myURLs;

    /**
     * Creates a rights property from the supplied URL(s).
     *
     * @param aURL A URL (or URLs) for the rights(s)
     * @throws MalformedURLException If the supplied URL string isn't a valid URL
     */
    public Rights(final String... aURL) throws MalformedURLException {
        myURLs = new ArrayList<>(aURL.length + LIST_PADDING);

        for (final String url : aURL) {
            myURLs.add(new URL(url));
        }
    }

    /**
     * Creates a rights property from the supplied URL(s).
     *
     * @param aURL A URL (or URLs) for the rights(s)
     */
    public Rights(final URL... aURL) {
        myURLs = new ArrayList<>(aURL.length + LIST_PADDING);
        Collections.addAll(myURLs, aURL);
    }

    /**
     * Creates a new rights property.
     */
    @SuppressWarnings("unused")
    private Rights(final String aURL) throws MalformedURLException {
        myURLs = new ArrayList<>(LIST_PADDING + 1);
        myURLs.add(new URL(aURL));
    }

    /**
     * Add URL value(s) to the rights.
     *
     * @param aValue A string version of a rights value URL
     * @return The rights
     * @throws MalformedURLException If a supplied value isn't a valid URL
     */
    @JsonIgnore
    public Rights addValue(final String... aValue) throws MalformedURLException {
        final URL[] urls = new URL[aValue.length];
        int index = 0;

        // Check the string values before we start adding them
        for (final String value : aValue) {
            urls[index++] = new URL(value);
        }

        Collections.addAll(myURLs, urls);

        return this;
    }

    /**
     * Sets the value of the rights to the supplied values, deleting the rest.
     *
     * @param aValue A list of string URLs
     * @return The rights
     * @throws MalformedURLException If a supplied value isn't a valid URL
     */
    @JsonIgnore
    public Rights setValue(final String... aValue) throws MalformedURLException {
        myURLs.clear();
        return addValue(aValue);
    }

    /**
     * Add additional rights URL(s).
     *
     * @param aURL Additional rights URL(s)
     * @return True if the supplied URL(s) were added
     */
    @JsonIgnore
    public Rights addValue(final URL... aURL) {
        if (!Collections.addAll(myURLs, aURL)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Sets the value of rights to the supplied values, deleting the rest.
     *
     * @param aURL A list of rights URLs
     * @return The rights
     */
    @JsonIgnore
    public Rights setValue(final URL... aURL) {
        myURLs.clear();
        return addValue(aURL);
    }

    /**
     * Gets the URLs for the rights.
     *
     * @return The URLs for the rights
     */
    @JsonIgnore
    public List<URL> getURLs() {
        return myURLs;
    }

    /**
     * Gets number of rights URLs.
     *
     * @return The number of rights URLs
     */
    @JsonIgnore
    public int count() {
        return getURLs().size();
    }

    /**
     * Gets the URLs for the rights as strings.
     *
     * @return The URLs for the rights as strings
     */
    @JsonIgnore
    public List<String> getStrings() {
        final List<String> list = new ArrayList<>(myURLs.size());

        for (final URL url : getURLs()) {
            list.add(url.toExternalForm());
        }

        return list;
    }

    /**
     * Gets the first URL. Returns null if there are no rights URLs.
     *
     * @return The first URL
     */
    @JsonIgnore
    public URL getURL() {
        if (myURLs.isEmpty()) {
            return null;
        } else {
            return myURLs.get(0);
        }
    }

    /**
     * Gets the first string. Returns null if there are no rights URLs.
     *
     * @return The first URL as a string
     */
    @JsonIgnore
    public String getString() {
        return getURL().toExternalForm();
    }

    /**
     * Return the value of the rights property. It may be a single URL or may be an list of URLs.
     *
     * @return The value of the rights property
     */
    @JsonValue
    private Object getValue() {
        final List<URL> urls = getURLs();
        final int size = urls.size();

        if (size == 1) {
            return urls.get(0);
        } else if (size > 1) {
            return urls;
        } else {
            return null;
        }
    }

}
