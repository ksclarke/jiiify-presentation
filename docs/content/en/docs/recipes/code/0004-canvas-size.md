---
title: "Image and Canvas with Differing Dimensions"
linkTitle: "0004-canvas-size"
weight: 0
---

### Use Case

You have an image ready for annotating that is expected to be replaced later by a higher resolution image. You would like to provide a sufficiently high-resolution 
coordinate space to position the annotations precisely. IIIF Presentation v3.0 allows you to describe a Canvas with the dimensions of the larger image to come, fill it 
with the smaller image you have ready now, and capture annotations on the smaller image in confidence that they will be positioned appropriately on the larger image when 
it is swapped in.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0004-canvas-size/ |
| JSON: | https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however,
create different IDs than are used in the cookbook recipe.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest",
  new Label("en", "Still image from an opera performance at Indiana University"));
var canvas = new Canvas(MinterFactory.getMinter(manifest)).setWidthHeight(1920, 1080);
var imageContent = new ImageContent("https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png");

canvas.paintWith(imageContent.setWidthHeight(640, 360));
manifest.setCanvases(canvas);

System.out.println(manifest);
```

### Method Two

The second method will create a manifest with the same IDs that the cookbook recipe uses. It involves setting all the IDs manually, which involves a little more code.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest",
  new Label("en", "Still image from an opera performance at Indiana University"));
var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1");
var imageContent = new ImageContent("https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png");
var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1");
var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image", canvas);

canvas.setWidthHeight(1920, 1080);
imageContent.setWidthHeight(640, 360);
page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
manifest.setCanvases(canvas.setPaintingPages(page));

System.out.println(manifest);
```

{{% sandbox %}}
