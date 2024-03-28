---
title: "Simplest Manifest - Single Image File"
linkTitle: "0001-mvm-image"
weight: 0
---

The simplest viable manifest for image content. If all you have for an object is one image on the web and a label to go along with it, this pattern turns it into a IIIF 
Presentation resource.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0001-mvm-image/ |
| Manifest: | https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest.json |

```java
Manifest manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
    new Label("en", "Single Image Example"));
Minter minter = MinterFactory.getMinter(manifest);
Canvas canvas = new Canvas(minter).setWidthHeight(1200, 1800);
ImageContent image =
    new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png")
        .setWidthHeight(1200, 1800);

canvas.paintWith(minter, image);
manifest.setCanvases(canvas);

System.out.println(manifest);
```
