---
title: "Embedding HTML in descriptive properties"
linkTitle: "0007-string-formats"
weight: 0
---

### Use Case

You want to have more control on how your metadata is displayed by adding links or simple formatting instructions to selected text blocks. For example, scientific names 
might need special formatting and links out to other sites benefit from being activatable. Legacy systems may also include rudimentary formatting in their output.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0007-string-formats/ |
| Manifest: | https://iiif.io/api/cookbook/recipe/0007-string-formats/manifest.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however,
create different IDs than are used in the cookbook recipe. This example also shows passing HTML markup to manifest properties that are allowed to contain it.

_Note that `LEVEL_ONE` and `IMAGE_JPEG` are static imports whose classes (`ImageService3.Profile` and `MediaType`) are predefined in this site’s code sandbox._

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0007-string-formats/manifest.json",
  new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
var canvas = new Canvas(MinterFactory.getMinter(manifest));
var imageContent = new ImageContent("https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
var service = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen");

manifest.setSummary(new Summary("en", "<p>Picture taken by the <a href=\"https://github.com/glenrobson\">IIIF Technical Coordinator</a></p>"));
manifest.setMetadata(new Metadata(new Label("en", "Author"),
  new Value("<span><a href='https://github.com/glenrobson'>Glen Robson</a></span>")));
manifest.setRights("http://creativecommons.org/licenses/by-sa/3.0/");
manifest.setRequiredStatement(new RequiredStatement(new Label("en", "Attribution"),
  new Value("en", "<span>Glen Robson, IIIF Technical Coordinator. <a href=\"https://creativecommons.org/licenses/by-sa/3.0\">CC BY-SA 3.0</a> <img src=\"https://licensebuttons.net/l/by-sa/3.0/88x31.png\"/></span>")));

imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
manifest.addCanvases(canvas.setWidthHeight(4032, 3024).paintWith(imageContent));

System.out.println(manifest);
```

### Method Two

The second method will create a manifest with the same IDs that the cookbook recipe uses. It involves setting all the IDs manually, which involves a little more code. 
This example also shows passing HTML markup to manifest properties that are allowed to contain it.

_Note that `LEVEL_ONE` and `IMAGE_JPEG` are static imports whose classes (`ImageService3.Profile` and `MediaType`) are predefined in this site’s code sandbox._

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0007-string-formats/manifest.json",
  new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0007-string-formats/canvas/p1");
var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0007-string-formats/page/p1/1");
var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0007-string-formats/annotation/p0001-image", canvas);
var imageContent = new ImageContent("https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
var service = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen");

manifest.setSummary(new Summary("en", "<p>Picture taken by the <a href=\"https://github.com/glenrobson\">IIIF Technical Coordinator</a></p>"));
manifest.setMetadata(new Metadata(new Label("en", "Author"),
  new Value("<span><a href='https://github.com/glenrobson'>Glen Robson</a></span>")));
manifest.setRights("http://creativecommons.org/licenses/by-sa/3.0/");
manifest.setRequiredStatement(new RequiredStatement(new Label("en", "Attribution"),
  new Value("en", "<span>Glen Robson, IIIF Technical Coordinator. <a href=\"https://creativecommons.org/licenses/by-sa/3.0\">CC BY-SA 3.0</a> <img src=\"https://licensebuttons.net/l/by-sa/3.0/88x31.png\"/></span>")));

imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
manifest.addCanvases(canvas.setWidthHeight(4032, 3024).setPaintingPages(page));

System.out.println(manifest);
```

{{% sandbox %}}
