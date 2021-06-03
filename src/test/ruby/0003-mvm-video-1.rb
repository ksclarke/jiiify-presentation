
module JiiifyPresentation0003_1
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'

  manifestID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest'
  videoID = 'https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4'

  manifest = Manifest.new(manifestID, Label.new('en', 'Video Example 3'))
  minter = MinterFactory.getMinter(manifest)
  canvas = Canvas.new(minter).setWidthHeight(640, 360).setDuration(572.034)
  videoContent = VideoContent.new(videoID).setWidthHeight(480, 360)

  videoContent.setDuration(572.034)
  canvas.paintWith(minter, videoContent)
  manifest.setCanvases(canvas)

  puts manifest.toString()
end
