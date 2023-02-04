
module JiiifyPresentation0004_2
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.annotations'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'

  manifestID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest'
  canvasID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1'
  imageID = 'https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png'
  annoID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image'
  annoPageID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1'

  label = Label.new('en', 'Still image from an opera performance at Indiana University')
  manifest = Manifest.new(manifestID, label)
  canvas = Canvas.new(canvasID).setWidthHeight(1920, 1080)
  imageContent = ImageContent.new(imageID).setWidthHeight(640, 360)
  annoPage = AnnotationPage.new(annoPageID)
  anno = PaintingAnnotation.new(annoID, canvas)

  annoPage.addAnnotations(anno.setBodies(imageContent).setTarget(canvasID))
  manifest.setCanvases(canvas.setPaintingPages(annoPage))

  puts manifest.toString()
end
