---
description: Simplest Manifest - Image
---

# 0001-mvm-image

There are two different ways to approach the [simplest manifest example](https://iiif.io/api/cookbook/recipe/0001-mvm-image/). One would be to preserve the intra-manifest IDs found in the example JSON file. The other would be just to create the IDs at the point of creating the manifest. I'm not sure of the use case for using existing IDs for a manifest that does not yet exist \(which is why we're creating it\), but both examples are included below.

The first example creates the example manifest using Jiiify Presentation's ID minter. The [MinterFactory](https://javadoc.io/doc/info.freelibrary/jiiify-presentation-v3/latest/info/freelibrary/iiif/presentation/v3/ids/MinterFactory.html) comes with a default ID minter implementation, but this can be overridden to use a different minter if desired \(for instance, one that might generate IDs more like those found in the example JSON file\). Below is the code using the ID minter:

{% tabs %}
{% tab title="Java Code" %}
```text
String manifestID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest";
String imageID = "http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png";

Manifest manifest = new Manifest(manifestID, new Label("en", "Image 1"));
Minter minter = MinterFactory.getMinter(manifest);
Canvas canvas = new Canvas(minter).setWidthHeight(1200, 1800);
ImageContent imageContent = new ImageContent(imageID).setWidthHeight(1200, 1800);

canvas.paintWith(minter, imageContent);
manifest.setCanvases(canvas);

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}

{% endtab %}

{% tab title="Python Code" %}

{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context" : "http://iiif.io/api/presentation/3/context.json",
  "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
  "type" : "Manifest",
  "label" : {
    "en" : [ "Image 1" ]
  },
  "items" : [ {
    "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest/canvas-b5ey",
    "type" : "Canvas",
    "height" : 1800,
    "width" : 1200,
    "items" : [ {
      "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest/canvas-b5ey/anno-page-vm31",
      "type" : "AnnotationPage",
      "items" : [ {
        "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest/annotations/anno-rj4u",
        "type" : "Annotation",
        "motivation" : "painting",
        "body" : {
          "id" : "http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png",
          "type" : "Image",
          "format" : "image/png",
          "height" : 1800,
          "width" : 1200
        },
        "target" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest/canvas-b5ey"
      } ]
    } ]
  } ]
}
```
{% endtab %}
{% endtabs %}

The next example uses the example JSON file's IDs instead of relying on the Jiiify Presentation's minter to generate IDs. This requires the canvas' annotation and annotation page to be explicitly created:

{% tabs %}
{% tab title="Java Code" %}
```text
String manifestID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest";
String canvasID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1";
String imageID = "http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png";
String annoID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image";
String annoPageID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1";

Manifest manifest = new Manifest(manifestID, new Label("en", "Image 1"));
Canvas canvas = new Canvas(canvasID).setWidthHeight(1200, 1800);
ImageContent imageContent = new ImageContent(imageID).setWidthHeight(1200, 1800);
AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

annoPage.addAnnotations(anno.setBodies(imageContent).setTarget(canvasID));
manifest.setCanvases(canvas.setPaintingPages(annoPage));

System.out.println(manifest);
```
{% endtab %}

{% tab title="Ruby Code" %}

{% endtab %}

{% tab title="Python Code" %}

{% endtab %}

{% tab title="JSON Result" %}
```text
{
  "@context" : "http://iiif.io/api/presentation/3/context.json",
  "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
  "type" : "Manifest",
  "label" : {
    "en" : [ "Image 1" ]
  },
  "items" : [ {
    "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1",
    "type" : "Canvas",
    "height" : 1800,
    "width" : 1200,
    "items" : [ {
      "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1",
      "type" : "AnnotationPage",
      "items" : [ {
        "id" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image",
        "type" : "Annotation",
        "motivation" : "painting",
        "body" : {
          "id" : "http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png",
          "type" : "Image",
          "format" : "image/png",
          "height" : 1800,
          "width" : 1200
        },
        "target" : "https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1"
      } ]
    } ]
  } ]
}
```
{% endtab %}
{% endtabs %}

It's worth noting that Jiiify Presentation's API uses a "fluent" programming style, allowing methods to be chained. One can also split the methods up onto individual lines if this is desired \(e.g., some people might think this makes it easier to read and debug\).

The JSON output across the two examples is identical, with the exception of the different internal manifest IDs \(the IDs for Canvas, AnnotationPage, and Annotation\).

