
module JiiifyPresentation0002_1
  require 'jruby'

  # We pass in the version of the uber package to use when running this test
  require_relative '../../../target/jiiify-presentation-v3-' + ARGV[0] + '-uber.jar'

  include_package 'info.freelibrary.iiif.presentation.v3'
  include_package 'info.freelibrary.iiif.presentation.v3.ids'
  include_package 'info.freelibrary.iiif.presentation.v3.properties'
  include_package 'com.google.common.net'

  manifestID = 'https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest'
  soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'

  manifest = Manifest.new(manifestID, Label.new('en', 'Simplest Audio Example 1'))
  minter = MinterFactory.getMinter(manifest)
  canvas = Canvas.new(minter).setDuration(1985.024)
  soundContent = SoundContent.new(soundID).setDuration(1985.024)

  canvas.paintWith(minter, soundContent)
  manifest.setCanvases(canvas)

  puts manifest.toString()
end