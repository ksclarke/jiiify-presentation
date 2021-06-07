---
description: A human-friendly description goes here
---

# 0003-mvm-video

Some introductory text goes here...

{% tabs %}
{% tab title="Java Code" %}
```text
String manifestID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest";
String videoID = "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4";

Manifest manifest = new Manifest(manifestID, new Label("en", "Video Example 3"));
Minter minter = MinterFactory.getMinter(manifest);
Canvas canvas = new Canvas(minter).setWidthHeight(640, 360).setDuration(572.034);
VideoContent videoContent = new VideoContent(videoID).setWidthHeight(480, 360);

videoContent.setDuration(572.034);
canvas.paintWith(minter, videoContent);
manifest.setCanvases(canvas);

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}
```text
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
```
{% endtab %}

{% tab title="Python Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest'
videoID = 'https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4'

manifest = Manifest(manifestID, Label('en', 'Video Example 3'))
minter = MinterFactory.getMinter(manifest)
canvas = Canvas(minter).setDuration(572.034).setWidthHeight(640, 360)
videoContent = VideoContent(videoID).setDuration(572.034)

videoContent.setWidthHeight(480, 360)
canvas.paintWith(minter, [videoContent])
manifest.setCanvases([canvas])

print(manifest)
```
{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context" : "http://iiif.io/api/presentation/3/context.json",
  "id" : "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest",
  "type" : "Manifest",
  "label" : {
    "en" : [ "Video Example 3" ]
  },
  "items" : [ {
    "id" : "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest/canvas-oanc",
    "type" : "Canvas",
    "height" : 360,
    "width" : 640,
    "duration" : 572.034,
    "items" : [ {
      "id" : "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest/canvas-oanc/anno-page-280x",
      "type" : "AnnotationPage",
      "items" : [ {
        "id" : "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest/annotations/anno-rucz",
        "type" : "Annotation",
        "motivation" : "painting",
        "body" : {
          "id" : "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4",
          "type" : "Video",
          "height" : 360,
          "width" : 480,
          "duration" : 572.034,
          "format" : "video/mp4"
        },
        "target" : "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest/canvas-oanc"
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
String manifestID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest.json";
String canvasID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas";
String videoID = "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4";
String annoID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation";
String annoPageID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page";

Manifest manifest = new Manifest(manifestID, new Label("en", "Video Example 3"));
Canvas canvas = new Canvas(canvasID).setDuration(572.034).setWidthHeight(640, 360);
VideoContent videoContent = new VideoContent(videoID).setWidthHeight(480, 360);
AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

videoContent.setDuration(572.034);
annoPage.addAnnotations(anno.setBodies(videoContent).setTarget(canvasID));
manifest.setCanvases(canvas.setPaintingPages(annoPage));

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}
```text
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
```
{% endtab %}

{% tab title="Python Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest'
canvasID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas'
videoID = 'https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4'
annoID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation'
annoPageID = 'https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page'

manifest = Manifest(manifestID, Label('en', 'Video Example 3'))
canvas = Canvas(canvasID).setDuration(572.034).setWidthHeight(640, 360)
videoContent = VideoContent(videoID).setDuration(572.034).setWidthHeight(480, 360)
annoPage = AnnotationPage(annoPageID)
anno = PaintingAnnotation(annoID, canvas)

annoPage.addAnnotations(anno.setBodies([videoContent]).setTarget(canvasID))
manifest.setCanvases(canvas.setPaintingPages([annoPage]))

print(manifest)
```
{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context": "http://iiif.io/api/presentation/3/context.json",
  "id": "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest",
  "type": "Manifest",
  "label": {
    "en": [
      "Video Example 3"
    ]
  },
  "items": [
    {
      "id": "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas",
      "type": "Canvas",
      "height": 360,
      "width": 640,
      "duration": 572.034,
      "items": [
        {
          "id": "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page",
          "type": "AnnotationPage",
          "items": [
            {
              "id": "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation",
              "type": "Annotation",
              "motivation": "painting",
              "body": {
                "id": "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4",
                "type": "Video",
                "height": 360,
                "width": 480,
                "duration": 572.034,
                "format": "video/mp4"
              },
              "target": "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas"
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

