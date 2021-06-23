---
description: Setting different canvas and image dimensions
---

# 0004-canvas-size

Recipe four demonstrates that image dimensions need not be the same as the canvas dimensions. There are two examples below illustrating this. The first shows this while using a minter to create manifest component IDs. On this example, the canvas width is set to 1920 and the height is set to 1080. The image on the other hand has its width set to 640 and its height set to 360.

{% tabs %}
{% tab title="Java Code" %}
```text
String manifestID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest";
String imageID = "https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png";

Label label = new Label("en", "Still image from an opera performance at Indiana University");
Manifest manifest = new Manifest(manifestID, label);
Minter minter = MinterFactory.getMinter(manifest);
Canvas canvas = new Canvas(minter).setWidthHeight(1920, 1080);
ImageContent imageContent = new ImageContent(imageID).setWidthHeight(640, 360);

canvas.paintWith(minter, imageContent);
manifest.setCanvases(canvas);

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}
```text
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
```
{% endtab %}

{% tab title="Python Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest'
imageID = 'https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png'

label = Label('en', 'Still image from an opera performance at Indiana University')
manifest = Manifest(manifestID, label)
minter = MinterFactory.getMinter(manifest)
canvas = Canvas(minter).setWidthHeight(1920, 1080)
imageContent = ImageContent(imageID).setWidthHeight(640, 360)

canvas.paintWith(minter, [imageContent])
manifest.setCanvases([canvas])

print(manifest)
```
{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context" : "http://iiif.io/api/presentation/3/context.json",
  "id" : "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest",
  "type" : "Manifest",
  "label" : {
    "en" : [ "Still image from an opera performance at Indiana University" ]
  },
  "items" : [ {
    "id" : "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest/canvas-ii70",
    "type" : "Canvas",
    "height" : 1080,
    "width" : 1920,
    "items" : [ {
      "id" : "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest/canvas-ii70/anno-page-dz9s",
      "type" : "AnnotationPage",
      "items" : [ {
        "id" : "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest/annotations/anno-ap7x",
        "type" : "Annotation",
        "motivation" : "painting",
        "body" : {
          "id" : "https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png",
          "type" : "Image",
          "format" : "image/png",
          "height" : 360,
          "width" : 640
        },
        "target" : "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest/canvas-ii70"
      } ]
    } ]
  } ]
}

```
{% endtab %}
{% endtabs %}

The second example shows a canvas and image with the same dimensions as above, but the minter isn't used to create component IDs. Instead, the IDs \(which are those from the cookbook recipe\) are manually supplied when constructing the components.

{% tabs %}
{% tab title="Java Code" %}
```text
String manifestID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest";
String canvasID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1";
String imageID = "https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png";
String annoID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image";
String annoPageID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1";

Label label = new Label("en", "Still image from an opera performance at Indiana University");
Manifest manifest = new Manifest(manifestID, label);
Canvas canvas = new Canvas(canvasID).setWidthHeight(1920, 1080);
ImageContent imageContent = new ImageContent(imageID).setWidthHeight(640, 360);
AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

annoPage.addAnnotations(anno.setBodies(imageContent).setTarget(canvasID));
manifest.setCanvases(canvas.setPaintingPages(annoPage));

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}
```text
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
```
{% endtab %}

{% tab title="Python Code" %}
```text
manifestID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest'
canvasID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1'
imageID = 'https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png'
annoID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image'
annoPageID = 'https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1'

label = Label('en', 'Still image from an opera performance at Indiana University')
manifest = Manifest(manifestID, label)
canvas = Canvas(canvasID).setWidthHeight(1920, 1080)
imageContent = ImageContent(imageID).setWidthHeight(640, 360)
annoPage = AnnotationPage(annoPageID)
anno = PaintingAnnotation(annoID, canvas)

annoPage.addAnnotations(anno.setBodies([imageContent]).setTarget(canvasID))
manifest.setCanvases(canvas.setPaintingPages([annoPage]))

print(manifest)
```
{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context": "http://iiif.io/api/presentation/3/context.json",
  "id": "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest.json",
  "type": "Manifest",
  "label": {
    "en": [
      "Still image from an opera performance at Indiana University"
    ]
  },
  "items": [
    {
      "id": "https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1",
      "type": "Canvas",
      "height": 1080,
      "width": 1920,
      "items": [
        {
          "id": "https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1",
          "type": "AnnotationPage",
          "items": [
            {
              "id": "https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image",
              "type": "Annotation",
              "motivation": "painting",
              "body": {
                "id": "https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png",
                "type": "Image",
                "format": "image/png",
                "height": 360,
                "width": 640
              },
              "target": "https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1"
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

The next examples shows how to use an image service in a manifest.

