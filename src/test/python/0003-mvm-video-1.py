
class JiiifyPresentation0003_1:

  def __init__(self):
    # We pass in the version of the uber package to use when running this test
    import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

    from info.freelibrary.iiif.presentation.v3 import Canvas, VideoContent, Manifest
    from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
    from info.freelibrary.iiif.presentation.v3.properties import Label

    manifestID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest'
    videoID = 'https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4'

    manifest = Manifest(manifestID, Label('en', 'Video Example 3'))
    minter = MinterFactory.getMinter(manifest)
    canvas = Canvas(minter).setDuration(572.034).setWidthHeight(640, 360)
    videoContent = VideoContent(videoID).setDuration(572.034).setWidthHeight(480, 360)

    canvas.paintWith(minter, [videoContent])
    manifest.setCanvases([canvas])

    print(manifest)

JiiifyPresentation0003_1()
