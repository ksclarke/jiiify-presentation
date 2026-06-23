---
title: "Internationalization and Multi-language Values"
linkTitle: "0006-text-language"
weight: 0
---

### Use Case

In some cases, a IIIF resource may have a title or description (label or summary) in more than one language; or, the publisher of the content may want to provide 
descriptive metadata field labels in multiple languages for different language audiences, for example supplying a label for a creator field in both English and French 
(“Creator”, “Auteur”).

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0006-text-language/ |
| JSON: | https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json |

### Method One

For the first method, we use a `Minter` to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, 
however, create different IDs than are used in the cookbook recipe. It also uses `I18n` objects to create the internationalizations.

_Note that `LEVEL_ONE` and `IMAGE_JPEG` are static imports whose classes (`ImageService3.Profile` and `MediaType`) are predefined in this site's code sandbox._

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json",
  new Label(new I18n("en", "Whistler's Mother"), new I18n("fr", "La Mère de Whistler")));

var canvas = new Canvas(MinterFactory.getMinter(manifest));
var imageContent = new ImageContent("https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother/full/max/0/default.jpg");
var service = new ImageService3("https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother",
  LEVEL_ONE);
var creator = new Metadata(new Label(new I18n("en", "Creator"), new I18n("fr", "Auteur")),
  new Value("Whistler, James Abbott McNeill"));

var subjectEN = new I18n("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)");
var subjectFR = new I18n("fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)");
var subject = new Metadata(new Label(new I18n("en", "Subject"), new I18n("fr", "Sujet")),
  new Value(subjectEN, subjectFR));

var summaryEN = new I18n("en", "Arrangement in Grey and Black No. 1, also called Portrait of the Artist's Mother.");
var summaryFR = new I18n("fr", "Arrangement en gris et noir n°1, also called Portrait de la mère de l'artiste.");

var reqStmtLabel = new Label(new I18n("en", "Held By"), new I18n("fr", "Détenu par"));
var reqStmt = new Value("Musée d'Orsay, Paris, France");

manifest.setMetadata(creator, subject);
manifest.setSummary(new Summary(summaryEN, summaryFR));
manifest.setRequiredStatement(new RequiredStatement(reqStmtLabel, reqStmt));

imageContent.setWidthHeight(1114, 991).setFormat(IMAGE_JPEG).setServices(service);
canvas.setWidthHeight(1114, 991).paintWith(imageContent);
manifest.setCanvases(canvas);

System.out.println(manifest);
```

### Method Two

The second method doesn't use a `Minter` and will create a manifest with the same IDs that the cookbook recipe uses. It sets all the IDs manually, which involves a 
little more code. Like the above, it still uses `I18n`(s) for the internationalizations.

_The same note above about `LEVEL_ONE` and `IMAGE_JPEG` also applies to this example._

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json",
  new Label(new I18n("en", "Whistler's Mother"), new I18n("fr", "La Mère de Whistler")));

var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0006-text-language/canvas/p1");
var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0006-text-language/page/p1/1");
var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0006-text-language/annotation/p0001-image",
  canvas);
var imageContent = new ImageContent("https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother/full/max/0/default.jpg");
var service = new ImageService3("https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother",
  LEVEL_ONE);

var creatorLabel = new Label(new I18n("en", "Creator"), new I18n("fr", "Auteur"));
var creator = new Metadata(creatorLabel, new Value("Whistler, James Abbott McNeill"));

var subjectEN = new I18n("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)");
var subjectFR = new I18n("fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)");
var subject = new Metadata(new Label(new I18n("en", "Subject"), new I18n("fr", "Sujet")),
  new Value(subjectEN, subjectFR));

var summaryEN = new I18n("en", "Arrangement in Grey and Black No. 1, also called Portrait of the Artist's Mother.");
var summaryFR = new I18n("fr", "Arrangement en gris et noir n°1, also called Portrait de la mère de l'artiste.");

var reqStmtLabel = new Label(new I18n("en", "Held By"), new I18n("fr", "Détenu par"));
var reqStmt = new Value("Musée d'Orsay, Paris, France");

manifest.setMetadata(creator, subject);
manifest.setSummary(new Summary(summaryEN, summaryFR));
manifest.setRequiredStatement(new RequiredStatement(reqStmtLabel, reqStmt));

imageContent.setWidthHeight(1114, 991).setFormat(IMAGE_JPEG).setServices(service);
page.setAnnotations(annotation.setBody(imageContent));
canvas.setWidthHeight(1114, 991).setPaintingPages(page);
manifest.setCanvases(canvas);

System.out.println(manifest);
```

### Method Three

The third, and last, example uses a `Minter` and creates internationalizations without using `I18n`(s). Instead, internationalizations are passed to their manifest 
properties as arrays. This approach is the most concise method of the three.

_The same note above about `LEVEL_ONE` and `IMAGE_JPEG` also applies to this example._

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json",
  new Label("en", "Whistler's Mother", "fr", "La Mère de Whistler"));

var canvas = new Canvas(MinterFactory.getMinter(manifest));
var imageContent = new ImageContent("https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother/full/max/0/default.jpg");
var service = new ImageService3("https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother",
  LEVEL_ONE);
var creator = new Metadata(new Label("en", "Creator", "fr", "Auteur"),
  new Value("Whistler, James Abbott McNeill"));

var subject = new Metadata(new Label("en", "Subject", "fr", "Sujet"),
  new Value("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)", "fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)"));

var summary = new Summary("en", "Arrangement in Grey and Black No. 1, also called Portrait of the Artist's Mother.",
  "fr", "Arrangement en gris et noir n°1, also called Portrait de la mère de l'artiste.");

var reqStatement = new RequiredStatement(new Label("en", "Held By", "fr", "Détenu par"),
  new Value("Musée d'Orsay, Paris, France"));

manifest.setMetadata(creator, subject);
manifest.setSummary(summary);
manifest.setRequiredStatement(reqStatement);

imageContent.setWidthHeight(1114, 991).setFormat(IMAGE_JPEG).setServices(service);
canvas.setWidthHeight(1114, 991).paintWith(imageContent);
manifest.setCanvases(canvas);

System.out.println(manifest);
```

All three of the above methods work, and any of them is a valid way to create the cookbook recipe's manifests.

{{% sandbox %}}
