
class JiiifyPresentation0001_1:

  def __init__(self):
    # We pass in the version of the uber package to use when running this test
    import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

    from info.freelibrary.iiif.presentation.v3 import Canvas, ImageContent, Manifest
    from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
    from info.freelibrary.iiif.presentation.v3.properties import Label

    manifestID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest'
    imageID = 'https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png'

    manifest = Manifest(manifestID, Label('en', 'Image 1'))
    minter = MinterFactory.getMinter(manifest)
    canvas = Canvas(minter).setWidthHeight(1200, 1800)
    imageContent = ImageContent(imageID).setWidthHeight(1200, 1800)

    canvas.paintWith(minter, [imageContent])
    manifest.setCanvases([canvas])

    print(manifest)

JiiifyPresentation0001_1()
