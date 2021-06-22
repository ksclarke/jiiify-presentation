---
description: Simplest Manifest - Audio
---

# 0002-mvm-audio

Like with the 0001-mvm-image example, this audio example shows both ways of creating manifests: 1\) using the Minter with the paintWith syntax, and 2\) explicitly creating all the manifest components by hand. For this example, either route is acceptable. Which path is taken might just depend on the level of control one wants over the manifest's component IDs.

In this example, the manifest and canvas are created first. The minter is then initialized with the newly created manifest. The audio resource is created, and then it is painted onto the canvas. The canvas gets added to the manifest before its JSON serialization is printed out.

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

The same thing can be accomplished while using the IDs that are used in the Cookbook recipe. In this example, the minter isn't created; the IDs at the top of the block are used instead. The outer structure's elements are the same, but this time an AnnotationPage and PaintingAnnotation are created. The audio resource is added to the annotation and the annotation is added to the annotation page.

The annotation page is then set on the canvas, and the canvas on the manifest. Lastly, like with the example above, the JSON serialization of the manifest is printed out.

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

As we can see, this example is very similar to the first example. The only real difference is the type of content resource that's being annotated onto the canvas.

