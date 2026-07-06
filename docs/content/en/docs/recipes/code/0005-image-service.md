---
title: "Support Deep Viewing with Basic Use of a IIIF Image Service"
linkTitle: "0005-image-service"
weight: 0
---

### Use Case

You have a rare or special object in your collection that you’d like to make available for research to a large audience, including those without the ability to be 
present at your institution to examine the object in person. Presenting a medium-resolution flat digital image of the object using IIIF is possible, but if you have 
implemented a IIIF Image API service, you have significantly enhanced interaction possibilities for research and engagement. Specifying a IIIF Image API service in your 
presentation manifest allows for, among other features, proper deep zooming of large high-resolution images, client generation of derivatives, annotation of and external 
reference to image fragments, image rotation, choice of colors, creation of image fragments for annotation, and more. In turn, this permits researchers more 
sophisticated inspection of the object and more possibilities for stable, durable, and discoverable image-based scholarship.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0005-image-service/ |
| JSON: | https://iiif.io/api/cookbook/recipe/0005-image-service/manifest.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however,
create different IDs than are used in the cookbook recipe.

_Note that `LEVEL_ONE` and `IMAGE_JPEG` are static imports whose classes (`ImageService3.Profile` and `MediaType`) are predefined in this site's code sandbox._

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0005-image-service/manifest.json",
  new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
var canvas = new Canvas(MinterFactory.getMinter(manifest), new Label("en", "Canvas with a single IIIF image"));
var imageContent = new ImageContent("https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
var service = new ImageService3("https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
  LEVEL_ONE);

imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
canvas.setWidthHeight(4032, 3024).paintWith(imageContent);
manifest.setCanvases(canvas);

System.out.println(manifest);
```

### Method Two

The second method will create a manifest with the same IDs that the cookbook recipe uses. It involves setting all the IDs manually, which involves a little more code.

_The same note above about `LEVEL_ONE` and `IMAGE_JPEG` also applies to this example._

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0005-image-service/manifest.json",
  new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
var canvas = new Canvas(MinterFactory.getMinter(manifest), new Label("en", "Canvas with a single IIIF image"));
var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0005-image-service/page/p1/1");
var imageContent = new ImageContent("https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
var service = new ImageService3("https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
  LEVEL_ONE);
var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0005-image-service/annotation/p0001-image",
  canvas);

imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
annotation.setBody(imageContent);
page.setAnnotations(annotation);
canvas.setWidthHeight(4032, 3024).setPaintingPages(page);
manifest.setCanvases(canvas);

System.out.println(manifest);
```

{{% sandbox %}}
