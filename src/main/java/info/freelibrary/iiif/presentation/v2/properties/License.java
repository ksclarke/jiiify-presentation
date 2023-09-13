
package info.freelibrary.iiif.presentation.v2.properties;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * A link to an external resource that describes the license or rights statement under which the resource may be used.
 * The rationale for this being a URI and not a human readable label is that typically there is one license for many
 * resources, and the text is too long to be displayed to the user along with the object. If displaying the text is a
 * requirement, then it is recommended to include the information using the attribution property instead.
 */
public class License {

    /** The list padding for license. */
    private static final int LIST_PADDING = 2;

    /** The URLs associated with the license. */
    private final List<URL> myURLs;

    /**
     * Creates a license property from the supplied URL(s).
     *
     * @param aURL A URL (or URLs) for the license(s)
     * @throws MalformedURLException If the supplied URL string isn't a valid URL
     */
    public License(final String... aURL) throws MalformedURLException {
        myURLs = new ArrayList<>(aURL.length + LIST_PADDING);

        for (final String url : aURL) {
            myURLs.add(new URL(url));
        }
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
     * Creates a new license property.
     *
     * @param aURL A license's URL
     * @throws MalformedURLException If the supplied value isn't a valid URL
     */
    @SuppressWarnings("unused")
    private License(final String aURL) throws MalformedURLException {
        myURLs = new ArrayList<>(LIST_PADDING + 1);
        myURLs.add(new URL(aURL));
    }

    /**
     * Add URL value(s) to the License.
     *
     * @param aValue A string version of a license value URL
     * @return The license
     * @throws MalformedURLException If a supplied value isn't a valid URL
     */
    @JsonIgnore
    public License addValue(final String... aValue) throws MalformedURLException {
        final URL[] urls = new URL[aValue.length];
        int index = 0;

        // Check the string values before we start adding them
        for (final String value : aValue) {
            urls[index] = new URL(value);
            index++;
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
    @JsonIgnore
    public License setValue(final String... aValue) throws MalformedURLException {
        myURLs.clear();
        return addValue(aValue);
    }

    /**
     * Add additional license URL(s).
     *
     * @param aURL Additional license URL(s)
     * @return True if the supplied URL(s) were added
     * @throws UnsupportedOperationException If a supplied license URL could not be added
     */
    @JsonIgnore
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
    @JsonIgnore
    public License setValue(final URL... aURL) {
        myURLs.clear();
        return addValue(aURL);
    }

    /**
     * Gets the URLs for the licenses.
     *
     * @return The URLs for the licenses
     */
    @JsonIgnore
    public List<URL> getURLs() {
        return myURLs;
    }

    /**
     * Gets number of license URLs.
     *
     * @return The number of license URLs
     */
    @JsonIgnore
    public int count() {
        return getURLs().size();
    }

    /**
     * Gets the URLs for the licenses as strings.
     *
     * @return The URLs for the licenses as strings
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
     * Gets the first URL. Returns null if there are no license URLs.
     *
     * @return The first URL
     */
    @JsonIgnore
    public URL getURL() {
        if (myURLs.isEmpty()) {
            return null;
        }
        return myURLs.get(0);
    }

    /**
     * Gets the first string. Returns null if there are no license URLs.
     *
     * @return The first URL as a string
     */
    @JsonIgnore
    public String getString() {
        return getURL().toExternalForm();
    }

    /**
     * Return the value of the license property. It may be a single URL or may be an list of URLs.
     *
     * @return The value of the license property
     */
    @JsonValue
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private Object getValue() {
        final List<URL> urls = getURLs();

        if (!urls.isEmpty()) {
            if (urls.size() == Constants.SINGLE_INSTANCE) {
                return urls.get(0);
            }

            return urls;
        }

        return null;
    }

}
