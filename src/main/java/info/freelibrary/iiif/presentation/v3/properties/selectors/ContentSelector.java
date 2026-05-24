
package info.freelibrary.iiif.presentation.v3.properties.selectors;

/**
 * A content selector selects a particular type of content from a resource that has more than one type: for instance, it
 * might select just the sound from a multimedia stream.
 */
@FunctionalInterface
public interface ContentSelector extends Selector {
    // ContentAnnotation selectors have a hard-coded type; nothing really needs to be done with them
}
