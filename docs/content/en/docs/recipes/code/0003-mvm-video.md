---
title: "Simplest Manifest - Video"
linkTitle: "0003-mvn-video"
weight: 0
---

### Use Case

The simplest viable manifest for video content. This pattern presents a single video file in a IIIF Presentation resource.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0003-mvm-video/ |
| JSON: | https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however,
create different IDs than are used in the cookbook recipe.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest",
  new Label("en", "Video Example 3"));
var minter = MinterFactory.getMinter(manifest);
var canvas = new Canvas(minter).setWidthHeight(480, 360).setDuration(572.034);
var videoContent = new VideoContent("https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4");

canvas.paintWith(videoContent.setWidthHeight(480, 360).setDuration(572.034));
manifest.setCanvases(canvas);

System.out.println(manifest);
```

### Method Two

The second method will create a manifest with the same IDs that the cookbook recipe uses. It involves setting all the IDs manually, which involves a little more code.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest.json",
  new Label("en", "Video Example 3"));
var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas");
var videoContent = new VideoContent("https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4");
var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page");
var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation", canvas);

canvas.setDuration(572.034).setWidthHeight(480, 360);
videoContent.setDuration(572.034).setWidthHeight(480, 360);
page.addAnnotations(annotation.setBody(videoContent).setTarget(new Target(canvas)));
manifest.setCanvases(canvas.setPaintingPages(page));

System.out.println(manifest);
```

{{% sandbox %}}
