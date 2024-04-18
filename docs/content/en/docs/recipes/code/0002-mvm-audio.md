---
title: "Simplest Manifest - Audio"
linkTitle: "0002-mvn-audio"
weight: 0
---

### Use Case

The simplest viable manifest for audio content. This pattern presents a single audio file in a IIIF Presentation resource.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0002-mvm-audio/ |
| Manifest: | https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however,
create different IDs than are used in the cookbook recipe.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest",
  new Label("en", "Simplest Audio Example 1"));
var canvas = new Canvas(MinterFactory.getMinter(manifest)).setDuration(1985.024);
var soundContent = new SoundContent("https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4");

canvas.paintWith(soundContent.setDuration(1985.024));
manifest.setCanvases(canvas);

System.out.println(manifest);
```

### Method Two

The second method will create a manifest with the same IDs that the cookbook recipe uses. It involves setting all the IDs manually, which involves a little more code.

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest.json",
  new Label("en", "Simplest Audio Example 1"));
var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas");
var soundContent = new SoundContent("https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4");
var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page");
var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page/annotation", canvas);

canvas.setDuration(1985.024);
soundContent.setDuration(1985.024);
page.addAnnotations(annotation.setBody(soundContent).setTarget(new Target(canvas)));
manifest.setCanvases(canvas.setPaintingPages(page));

System.out.println(manifest);
```

{{% sandbox %}}
