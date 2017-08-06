
package info.freelibrary.iiif.presentation.properties;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A license property.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class License {

    private static final int LIST_PADDING = 2;

    private List<URL> myURLs;

    /**
     * Creates a license property from the supplied URL(s).
     *
     * @param aURL A URL (or URLs) for the license(s)
     */
    public License(final String... aURL) throws MalformedURLException {
        final List<URL> urls = new ArrayList<>(aURL.length + LIST_PADDING);

        for (final String url : aURL) {
            urls.add(new URL(url));
        }

        myURLs = urls;
    }

    /**
     * Creates a license property from the supplied URL(s).
     *
     * @param aURL A URL (or URLs) for the license(s)
     */
    public License(final URL... aURL) {
        myURLs = new ArrayList<>(aURL.length + LIST_PADDING);
        Collections.addAll(myURLs, aURL);
    }

    /**
     * Add URL value(s) to the License.
     *
     * @param aValue A string version of a license value URL
     * @return The license
     * @throws MalformedURLException If a supplied value isn't a valid URL
     */
    public License addValue(final String... aValue) throws MalformedURLException {
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
     * Sets the value of the license to the supplied values, deleting the rest.
     *
     * @param aValue A list of string URLs
     * @return The license
     * @throws MalformedURLException If a supplied value isn't a valid URL
     */
    public License setValue(final String... aValue) throws MalformedURLException {
        myURLs.clear();
        return addValue(aValue);
    }

    /**
     * Add additional license URL(s).
     *
     * @param aURL Additional license URL(s)
     * @return True if the supplied URL(s) were added
     */
    public License addValue(final URL... aURL) {
        if (!Collections.addAll(myURLs, aURL)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Sets the value of license to the supplied values, deleting the rest.
     *
     * @param aURL A list of license URLs
     * @return The license
     */
    public License setValue(final URL... aURL) {
        myURLs.clear();
        return addValue(aURL);
    }

    /**
     * Gets the URLs for the licenses.
     *
     * @return The URLs for the licenses
     */
    public List<URL> getURLs() {
        if (myURLs == null) {
            myURLs = new ArrayList<URL>();
        }

        return myURLs;
    }

    /**
     * Gets the URLs for the licenses as strings.
     *
     * @return The URLs for the licenses as strings
     */
    public List<String> getStrings() {
        final List<String> list = new ArrayList<String>(myURLs.size());

        for (final URL url : getURLs()) {
            list.add(url.toExternalForm());
        }

        return list;
    }

    /**
     * Gets the first URL. Returns null if there are no license URLs.
     *
     * @return The first URL
     */
    public URL getURL() {
        final List<URL> urls = getURLs();

        if (urls.size() > 1) {
            return urls.get(0);
        } else {
            return null;
        }
    }

    /**
     * Gets the first string. Returns null if there are no license URLs.
     *
     * @return The first URL as a string
     */
    public String getString() {
        return getURL().toExternalForm();
    }

    /**
     * Return the value of the license property. It may be a single URL or may be an list of URLs.
     *
     * @return The value of the license property
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
