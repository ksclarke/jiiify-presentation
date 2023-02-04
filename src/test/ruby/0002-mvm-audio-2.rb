
module JiiifyPresentation0002_2
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.annotations'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'
  include_package 'com.google.common.net'

  manifestID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest'
  canvasID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1'
  soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'
  annoID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image'
  annoPageID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1'

  manifest = Manifest.new(manifestID, Label.new('en', 'Simplest Audio Example 1'))
  canvas = Canvas.new(canvasID).setDuration(1985.024)
  soundContent = SoundContent.new(soundID).setDuration(1985.024)
  annoPage = AnnotationPage.new(annoPageID)
  anno = PaintingAnnotation.new(annoID, canvas)

  target = Target.new(canvasID)
  annoPage.addAnnotations(anno.setBody(soundContent).setTarget(target))
  manifest.setCanvases(canvas.setPaintingPages(annoPage))

  puts manifest.toString()
end
