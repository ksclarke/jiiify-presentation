
class JiiifyPresentationDemo:

    def __init__(self):
        # We pass in the version of the uber package to use when running this test
        import sys; sys.path.append('../../../target/jiiify-presentation-v3-' + sys.argv[0] + '-uber.jar')

        from info.freelibrary.iiif.presentation.v3 import Canvas, ImageContent, Manifest
        from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
        from info.freelibrary.iiif.presentation.v3.properties import Label
        from info.freelibrary.iiif.presentation.v3.services.image import ImageService2

        manifest = Manifest('http://example.com/1', Label('en', 'My Manifest'))
        minter = MinterFactory.getMinter(manifest)
        profile = ImageService2.Profile.LEVEL_TWO

        image1_id = 'https://iiif.library.ucla.edu/iiif/2/ark%3A%2F21198%2Fzz00254trx'
        image2_id = 'https://iiif.library.ucla.edu/iiif/2/ark%3A%2F21198%2Fzz00254tbq'

        image1 = ImageContent(image1_id + '/full/full/0/default.jpg') \
            .setLabel(Label('en', 'My Image 1')) \
            .setServices([ImageService2(profile, image1_id)]) \
            .setWidthHeight(6200, 4842)

        image2 = ImageContent(image2_id + '/full/full/0/default.jpg') \
            .setLabel(Label('en', 'My Image 2')) \
            .setServices([ImageService2(profile, image2_id)]) \
            .setWidthHeight(6200, 4885)

        canvas = Canvas(minter, Label('en', 'My Canvas')) \
            .setWidthHeight(6200, 4842 + 4885) \
            .paintWith(minter, 'xywh=0,0,6200,4842', [image1]) \
            .paintWith(minter, 'xywh=0,4842,6200,4885', [image2])

        manifest.addCanvases(canvas)

        print(manifest)

JiiifyPresentationDemo()
