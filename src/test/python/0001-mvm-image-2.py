
class JiiifyPresentation0001_2:

    def __init__(self):
        # We pass in the version of the uber package to use when running this test
        import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

        from info.freelibrary.iiif.presentation.v3 import Canvas, ImageContent, Manifest, AnnotationPage, PaintingAnnotation
        from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
        from info.freelibrary.iiif.presentation.v3.properties import Label

        manifestID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest'
        canvasID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1'
        imageID = 'http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png'
        annoID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image'
        annoPageID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1'

        manifest = Manifest(manifestID, Label('en', 'Image 1'))
        canvas = Canvas(canvasID).setWidthHeight(1200, 1800)
        imageContent = ImageContent(imageID).setWidthHeight(1200, 1800)
        annoPage = AnnotationPage(annoPageID)
        anno = PaintingAnnotation(annoID, canvas)

        annoPage.addAnnotations(anno.setBodies([imageContent]).setTarget(canvasID))
        manifest.setCanvases(canvas.setPaintingPages([annoPage]))

        print(manifest)

JiiifyPresentation0001_2()
