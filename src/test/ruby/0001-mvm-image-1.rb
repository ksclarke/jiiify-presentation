# A Ruby test of 0001-mvm-image
module JiiifyPresentation0001_1
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'

  manifestID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest'
  imageID = 'http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png'

  manifest = Manifest.new(manifestID, Label.new('en', 'Image 1'))
  minter = MinterFactory.getMinter(manifest)
  canvas = Canvas.new(minter).setWidthHeight(1200, 1800)
  imageContent = ImageContent.new(imageID).setWidthHeight(1200, 1800)

  canvas.paintWith(minter, imageContent)
  manifest.setCanvases(canvas)

  puts manifest.toString()
end