---
title: "Simplest Manifest - Single Image File"
linkTitle: "0001-mvm-image"
weight: 0
---

### Use Case

The simplest viable manifest for image content. If all you have for an object is one image on the web and a label to go along with it, this pattern turns it into a IIIF 
Presentation resource.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0001-mvm-image/ |
| JSON: | https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however, 
create different IDs than are used in the cookbook recipe.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
  new Label("en", "Single Image Example"));
var canvas = new Canvas(MinterFactory.getMinter(manifest));
var imageContent = new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png");

canvas.paintWith(imageContent.setWidthHeight(1200, 1800));
manifest.setCanvases(canvas.setWidthHeight(1200, 1800));

System.out.println(manifest);
```

### Method Two

The second method will create a manifest with the same IDs that the cookbook recipe uses. It involves setting all the IDs manually, which involves a little more code,
but may be necessary if specific IDs need to be used.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
  new Label("en", "Single Image Example"));
var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1");
var imageContent = new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png");
var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1");
var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image",
  canvas).setBody(imageContent.setWidthHeight(1200, 1800));

canvas.setPaintingPages(page.setAnnotations(annotation));
manifest.setCanvases(canvas.setWidthHeight(1200, 1800));

System.out.println(manifest);
```

{{% sandbox %}}
