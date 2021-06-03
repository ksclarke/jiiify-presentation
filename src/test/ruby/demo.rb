
module JiiifyPresentationDemo
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'
  include_package 'info.freelibrary.iiif.presentation.v3.services.image'

  manifest = Manifest.new('http://example.com/1', Label.new('en', 'My Manifest'))
  minter = MinterFactory.getMinter(manifest)
  profile = ImageService2::Profile::LEVEL_TWO

  image1_id = 'https://iiif.library.ucla.edu/iiif/2/ark%3A%2F21198%2Fzz00254trx'
  image2_id = 'https://iiif.library.ucla.edu/iiif/2/ark%3A%2F21198%2Fzz00254tbq'

  image1 = ImageContent.new(image1_id + '/full/full/0/default.jpg')
    .setLabel(Label.new('en', 'My Image 1'))
    .setServices(ImageService2.new(profile, image1_id))
    .setWidthHeight(6200, 4842)
  
  image2 = ImageContent.new(image2_id + '/full/full/0/default.jpg')
    .setLabel(Label.new('en', 'My Image 2'))
    .setServices(ImageService2.new(profile, image2_id))
    .setWidthHeight(6200, 4885)

  canvas = Canvas.new(minter, Label.new('en', 'My Canvas'))
    .setWidthHeight(6200, 4842 + 4885)
    .paintWith(minter, 'xywh=0,0,6200,4842', image1)
    .paintWith(minter, 'xywh=0,4842,6200,4885', image2)

  manifest.addCanvases(canvas)

  puts manifest.toString()
end
