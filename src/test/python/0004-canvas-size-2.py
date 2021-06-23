
class JiiifyPresentation0004_2:

    def __init__(self):
        # We pass in the version of the uber package to use when running this test
        import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

        from info.freelibrary.iiif.presentation.v3 import Canvas, ImageContent, Manifest, AnnotationPage, \
          PaintingAnnotation
        from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
        from info.freelibrary.iiif.presentation.v3.properties import Label

        manifestID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest'
        canvasID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1'
        imageID = 'https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png'
        annoID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image'
        annoPageID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1'

        label = Label('en', 'Still image from an opera performance at Indiana University')
        manifest = Manifest(manifestID, label)
        canvas = Canvas(canvasID).setWidthHeight(1920, 1080)
        imageContent = ImageContent(imageID).setWidthHeight(640, 360)
        annoPage = AnnotationPage(annoPageID)
        anno = PaintingAnnotation(annoID, canvas)

        annoPage.addAnnotations(anno.setBodies([imageContent]).setTarget(canvasID))
        manifest.setCanvases(canvas.setPaintingPages([annoPage]))

        print(manifest)

JiiifyPresentation0004_2()
