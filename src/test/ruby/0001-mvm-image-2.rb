
module JiiifyPresentation0001_2
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'

  manifestID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest'
  canvasID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1'
  imageID = 'http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png'
  annoID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image'
  annoPageID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1'

  manifest = Manifest.new(manifestID, Label.new('en', 'Image 1'))
  canvas = Canvas.new(canvasID).setWidthHeight(1200, 1800)
  imageContent = ImageContent.new(imageID).setWidthHeight(1200, 1800)
  annoPage = AnnotationPage.new(annoPageID)
  anno = PaintingAnnotation.new(annoID, canvas)

  annoPage.addAnnotations(anno.setBodies(imageContent).setTarget(canvasID))
  manifest.setCanvases(canvas.setPaintingPages(annoPage))

  puts manifest.toString()
end