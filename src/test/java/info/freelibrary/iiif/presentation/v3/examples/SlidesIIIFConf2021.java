
package info.freelibrary.iiif.presentation.v3.examples;

import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.ImageContent;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.SoundContent;
import info.freelibrary.iiif.presentation.v3.VideoContent;
import info.freelibrary.iiif.presentation.v3.annotations.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;

/**
 * Examples used from the 2021 IIIF Annual Conference.
 */
@SuppressWarnings("MultipleStringLiterals")
public class SlidesIIIFConf2021 {

    /**
     * First test runs the first example from the lightning talk.
     */
    @Test
    public final void testDemoingMinter() {
        final String id = UUID.randomUUID().toString();
        final String audioID = UUID.randomUUID().toString();

        final Manifest manifest = new Manifest(id, new Label("en", "Simple Audio"));
        final Minter minter = MinterFactory.getMinter(manifest);
        final Canvas canvas = new Canvas(minter).setDuration(1985.024);
        final SoundContent audio = new SoundContent(audioID).setDuration(1985.024);
        final AnnotationPage<PaintingAnnotation> page = new AnnotationPage<>(minter, canvas);
        final PaintingAnnotation annotation = new PaintingAnnotation(minter, canvas);

        page.addAnnotations(annotation.setBodies(audio));
        manifest.setCanvases(canvas.setPaintingPages(page));

        System.out.println(manifest);
    }

    /**
     * Tests the paintWith() demo.
     */
    @Test
    public final void testDemoingPaintWith() {
        final String id = UUID.randomUUID().toString();
        final String imageID = UUID.randomUUID().toString();

        final Manifest manifest = new Manifest(id, new Label("en", "Simple Image"));
        final Minter minter = MinterFactory.getMinter(manifest);
        final Canvas canvas = new Canvas(minter).setWidthHeight(1200, 1800);
        final ImageContent image = new ImageContent(imageID);

        canvas.paintWith(minter, image.setWidthHeight(1200, 1800));
        manifest.setCanvases(canvas);

        System.out.println(manifest);
    }

    /**
     * Tests the demo showing media type sniffing.
     */
    @Test
    public final void testMediaTypeSniffing() {
        final Manifest manifest = new Manifest(UUID.randomUUID().toString(), "Media Sniffing");
        final Minter minter = MinterFactory.getMinter(manifest);
        // Ignore above here

        final Canvas canvas = new Canvas(minter);
        final VideoContent video = new VideoContent("https://fixtures.iiif.io/video/indiana/sample.mp4");

        canvas.setWidthHeight(640, 360).setDuration(572.034);
        video.setWidthHeight(480, 360).setDuration(572.034);
        canvas.paintWith(minter, video);

        // Ignore below here
        manifest.setCanvases(canvas);
        System.out.println(manifest);
    }
}
