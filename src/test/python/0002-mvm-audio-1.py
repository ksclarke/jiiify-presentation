
class JiiifyPresentation0002_1:

  def __init__(self):
    # We pass in the version of the uber package to use when running this test
    import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

    from info.freelibrary.iiif.presentation.v3 import Canvas, SoundContent, Manifest
    from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
    from info.freelibrary.iiif.presentation.v3.properties import Label

    manifestID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest'
    soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'

    manifest = Manifest(manifestID, Label('en', 'Simplest Audio Example 1'))
    minter = MinterFactory.getMinter(manifest)
    canvas = Canvas(minter).setDuration(1985.024)
    soundContent = SoundContent(soundID).setDuration(1985.024)

    canvas.paintWith(minter, [soundContent])
    manifest.setCanvases([canvas])

    print(manifest)

JiiifyPresentation0002_1()
