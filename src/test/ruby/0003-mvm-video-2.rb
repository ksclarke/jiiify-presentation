
module JiiifyPresentation0003_2
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'

  manifestID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest'
  canvasID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas'
  videoID = 'https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4'
  annoID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation'
  annoPageID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page'

  manifest = Manifest.new(manifestID, Label.new('en', 'Video Example 3'))
  canvas = Canvas.new(canvasID).setDuration(572.034).setWidthHeight(640, 360)
  videoContent = VideoContent.new(videoID).setWidthHeight(480, 360)
  annoPage = AnnotationPage.new(annoPageID)
  anno = PaintingAnnotation.new(annoID, canvas)

  videoContent.setDuration(572.034)
  annoPage.addAnnotations(anno.setBodies(videoContent).setTarget(canvasID))
  manifest.setCanvases(canvas.setPaintingPages(annoPage))

  puts manifest.toString()
end