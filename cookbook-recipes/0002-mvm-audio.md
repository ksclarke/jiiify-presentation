---
description: Simplest Manifest - Audio
---

# 0002-mvm-audio

Some introductory text goes here...

{% tabs %}
{% tab title="Java Code" %}
```text
String manifestID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest.json";
String soundID = "https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4";

Manifest manifest = new Manifest(manifestID, new Label("en", "Simplest Audio Example 1"));
Minter minter = MinterFactory.getMinter(manifest);
Canvas canvas = new Canvas(minter).setDuration(1985.024);
SoundContent soundContent = new SoundContent(soundID).setDuration(1985.024);

canvas.paintWith(minter, soundContent);
manifest.setCanvases(canvas);

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest'
soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'

manifest = Manifest.new(manifestID, Label.new('en', 'Simplest Audio Example 1'))
minter = MinterFactory.getMinter(manifest)
canvas = Canvas.new(minter).setDuration(1985.024)
soundContent = SoundContent.new(soundID).setDuration(1985.024)

canvas.paintWith(minter, soundContent)
manifest.setCanvases(canvas)

puts manifest.toString()
```
{% endtab %}

{% tab title="Python Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest'
soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'

manifest = Manifest(manifestID, Label('en', 'Simplest Audio Example 1'))
minter = MinterFactory.getMinter(manifest)
canvas = Canvas(minter).setDuration(1985.024)
soundContent = SoundContent(soundID).setDuration(1985.024)

canvas.paintWith(minter, [soundContent])
manifest.setCanvases([canvas])

print(manifest)
```
{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context" : "http://iiif.io/api/presentation/3/context.json",
  "id" : "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest",
  "type" : "Manifest",
  "label" : {
    "en" : [ "Simplest Audio Example 1" ]
  },
  "items" : [ {
    "id" : "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest/canvas-56av",
    "type" : "Canvas",
    "duration" : 1985.024,
    "items" : [ {
      "id" : "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest/canvas-56av/anno-page-69ok",
      "type" : "AnnotationPage",
      "items" : [ {
        "id" : "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest/annotations/anno-eo5h",
        "type" : "Annotation",
        "motivation" : "painting",
        "body" : {
          "id" : "https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4",
          "type" : "Sound",
          "format" : "audio/mp4",
          "duration" : 1985.024
        },
        "target" : "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest/canvas-56av"
      } ]
    } ]
  } ]
}
```
{% endtab %}
{% endtabs %}

{% tabs %}
{% tab title="Java Code" %}
```text
String manifestID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest.json";
String canvasID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas";
String soundID = "https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4";
String annoID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page/annotation";
String annoPageID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page";

Manifest manifest = new Manifest(manifestID, new Label("en", "Simplest Audio Example 1"));
Canvas canvas = new Canvas(canvasID).setDuration(1985.024);
SoundContent soundContent = new SoundContent(soundID).setDuration(1985.024);
AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

annoPage.addAnnotations(anno.setBodies(soundContent).setTarget(canvasID));
manifest.setCanvases(canvas.setPaintingPages(annoPage));

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest'
canvasID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/p1'
soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'
annoID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/annotation/p0001-image'
annoPageID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/page/p1/1'

manifest = Manifest.new(manifestID, Label.new('en', 'Simplest Audio Example 1'))
canvas = Canvas.new(canvasID).setDuration(1985.024)
soundContent = SoundContent.new(soundID).setDuration(1985.024)
annoPage = AnnotationPage.new(annoPageID)
anno = PaintingAnnotation.new(annoID, canvas)

annoPage.addAnnotations(anno.setBodies(soundContent).setTarget(canvasID))
manifest.setCanvases(canvas.setPaintingPages(annoPage))

puts manifest.toString()
```
{% endtab %}

{% tab title="Python Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest'
canvasID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/p1'
soundID = 'https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4'
annoID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/annotation/p0001-image'
annoPageID = 'https://iiif.io/api/cookbook/recipe/0002-mvm-audio/page/p1/1'

manifest = Manifest(manifestID, Label('en', 'Simplest Audio Example 1'))
canvas = Canvas(canvasID).setDuration(1985.024)
soundContent = SoundContent(soundID).setDuration(1985.024)
annoPage = AnnotationPage(annoPageID)
anno = PaintingAnnotation(annoID, canvas)

annoPage.addAnnotations(anno.setBodies([soundContent]).setTarget(canvasID))
manifest.setCanvases(canvas.setPaintingPages([annoPage]))

print(manifest)
```
{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context": "http://iiif.io/api/presentation/3/context.json",
  "id": "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest",
  "type": "Manifest",
  "label": {
    "en": [
      "Simplest Audio Example 1"
    ]
  },
  "items": [
    {
      "id": "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas",
      "type": "Canvas",
      "duration": 1985.024,
      "items": [
        {
          "id": "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page",
          "type": "AnnotationPage",
          "items": [
            {
              "id": "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page/annotation",
              "type": "Annotation",
              "motivation": "painting",
              "body": {
                "id": "https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4",
                "type": "Sound",
                "format": "audio/mp4",
                "duration": 1985.024
              },
              "target": "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page"
            }
          ]
        }
      ]
    }
  ]
}
```
{% endtab %}
{% endtabs %}

Some wrap-up text goes here...

