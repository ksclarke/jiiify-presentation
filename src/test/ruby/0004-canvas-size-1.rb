
module JiiifyPresentation0004_1
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'
  
  manifestID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest'
  imageID = 'https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png'

  label = Label.new('en', 'Still image from an opera performance at Indiana University')
  manifest = Manifest.new(manifestID, label)
  minter = MinterFactory.getMinter(manifest)
  canvas = Canvas.new(minter).setWidthHeight(1920, 1080)
  imageContent = ImageContent.new(imageID).setWidthHeight(640, 360)

  canvas.paintWith(minter, imageContent)
  manifest.setCanvases(canvas)

  puts manifest.toString()
end
