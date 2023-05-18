
class JiiifyPresentation0002_2:

  def __init__(self):
    # We pass in the version of the uber package to use when running this test
    import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

    from info.freelibrary.iiif.presentation.v3 import Canvas, SoundContent, Manifest, AnnotationPage, PaintingAnnotation
    from info.freelibrary.iiif.presentation.v3.annotations import Target
    from info.freelibrary.iiif.presentation.v3.properties import Label

    manifestID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest'
    canvasID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/p1'
    soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'
    annoID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/annotation/p0001-image'
    annoPageID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/page/p1/1'

    manifest = Manifest(manifestID, Label('en', 'Simplest Audio Example 1'))
    canvas = Canvas(canvasID).setDuration(1985.024)
    soundContent = SoundContent(soundID).setDuration(1985.024)
    annoPage = AnnotationPage(annoPageID)
    anno = PaintingAnnotation(annoID, canvas)

    target = Target(canvasID)
    annoPage.addAnnotations(anno.setBody([soundContent]).setTarget(target))
    manifest.setCanvases(canvas.setPaintingPages([annoPage]))

    print(manifest)

JiiifyPresentation0002_2()
