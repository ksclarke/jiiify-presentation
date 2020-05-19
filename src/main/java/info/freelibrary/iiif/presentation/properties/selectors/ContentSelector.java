
package info.freelibrary.iiif.presentation.properties.selectors;

/**
 * A content selector selects a particular type of content from a resource that has more than one type: for instance,
 * it might select just the sound from a multi-media stream.
 */
public interface ContentSelector extends Selector {

    // ContentAnnotation selectors have a hard-coded type; nothing really needs to be done with them.

}
