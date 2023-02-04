
class JiiifyPresentation0004_1:

  def __init__(self):
    # We pass in the version of the uber package to use when running this test
    import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

    from info.freelibrary.iiif.presentation.v3 import Canvas, ImageContent, Manifest
    from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
    from info.freelibrary.iiif.presentation.v3.properties import Label

    manifestID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest'
    imageID = 'https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png'

    label = Label('en', 'Still image from an opera performance at Indiana University')
    manifest = Manifest(manifestID, label)
    minter = MinterFactory.getMinter(manifest)
    canvas = Canvas(minter).setWidthHeight(1920, 1080)
    imageContent = ImageContent(imageID).setWidthHeight(640, 360)

    canvas.paintWith(minter, [imageContent])
    manifest.setCanvases([canvas])

    print(manifest)

JiiifyPresentation0004_1()
