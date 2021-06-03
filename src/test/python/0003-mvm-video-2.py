
class JiiifyPresentation0003_2:

  def __init__(self):
    # We pass in the version of the uber package to use when running this test
    import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

    from info.freelibrary.iiif.presentation.v3 import Canvas, VideoContent, Manifest, \
           AnnotationPage, PaintingAnnotation
    from info.freelibrary.iiif.presentation.v3.properties import Label

    manifestID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest'
    canvasID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas'
    videoID = 'https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4'
    annoID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation'
    annoPageID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page'

    manifest = Manifest(manifestID, Label('en', 'Video Example 3'))
    canvas = Canvas(canvasID).setDuration(572.034).setWidthHeight(640, 360)
    videoContent = VideoContent(videoID).setDuration(572.034).setWidthHeight(480, 360)
    annoPage = AnnotationPage(annoPageID)
    anno = PaintingAnnotation(annoID, canvas)

    annoPage.addAnnotations(anno.setBodies([videoContent]).setTarget(canvasID))
    manifest.setCanvases(canvas.setPaintingPages([annoPage]))

    print(manifest)

JiiifyPresentation0003_2()
