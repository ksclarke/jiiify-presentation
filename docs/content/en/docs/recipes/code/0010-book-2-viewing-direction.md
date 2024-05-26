---
title: "Viewing direction and Its Effect on Navigation"
linkTitle: "0010-book-2-viewing-direction"
weight: 0
---

### Use Case

You have a Japanese-language text that in-person would be typically read from right to left (with the binding on the reader’s right), or you have a diary notebook 
written across the long dimension of the paper and therefore read top to bottom (with the binding away from the reader). By using viewingDirection you can communicate 
the reading direction to a conforming client, allowing it to present the content to the reader from right to left and from top to bottom, respectively.

The viewingDirection property tells a presentation client one part of how to display a sequence of resources to a viewer. It is permissible for IIIF Collection, 
Manifest, and Range resources, but is an invalid property on other types of resources. Clients should process the property when it is part of a Collection or Manifest, 
may process it when part of a Range, and should ignore it if used other resource types.

Possible values for viewingDirection are left-to-right (the default if the property is not specified), right-to-left, top-to-bottom, and bottom-to-top. Note particularly 
that this is the visual layout of the views, not the orientation of the views or the layout or orientation of any visual material of the represented objects’ views.

The viewingDirection property can inform a client of the appropriate presentation order and navigational cues for a variety of resource arrangements. Some examples 
include a sequence of pages within a manuscript, all views of a scroll, or a set of multiple books.

Though the example Manifests below use the items property to contain Canvases representing the reading sequence of views that make up an object, the viewingDirection 
property should be given greater weight than the Manifest order of views.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/ |
| RTL JSON: | https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-rtl.json |
| TTB JSON: | https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-ttb.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however, 
create different IDs than are used in the cookbook recipe.

This first examples are sort of a "brute-force" illustrations; they illustrate without any sort of looping. The first example uses RTL (right-to-left) and the second 
uses TTB (top-to-bottom) as their viewing directions.

_Note that `RIGHT_TO_LEFT` and `TOP_TO_BOTTOM`, `LEVEL_ONE`, and `IMAGE_JPEG` are static imports whose classes (`ViewingDirection`, `ImageService3.Profile`, and 
`MediaType`) are predefined in this site’s code sandbox._

#### RTL Exampe

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-rtl.json",
  new Label("en", "Book with Right-to-Left Viewing Direction"));
var minter = MinterFactory.getMinter(manifest);

var canvas1 = new Canvas(minter, new Label("en", "front cover"));
var imageContent1 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001/full/max/0/default.jpg");
var service1 = new ImageService3(LEVEL_ONE,"https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001");

var canvas2 = new Canvas(minter, new Label("en", "pages 1–2"));
var imageContent2 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002/full/max/0/default.jpg");
var service2 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002");

var canvas3 = new Canvas(minter, new Label("en", "pages 3–4"));
var imageContent3 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003/full/max/0/default.jpg");
var service3 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003");

var canvas4 = new Canvas(minter, new Label("en", "pages 5–6"));
var imageContent4 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004/full/max/0/default.jpg");
var service4 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004");

var canvas5 = new Canvas(minter, new Label("en", "back cover"));
var imageContent5 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005/full/max/0/default.jpg");
var service5 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005");

manifest.setSummary(new Summary("en", "Playbill for \"Akiba gongen kaisen-banashi,\" \"Futatsu chōchō kuruwa nikki\" and \"Godairiki koi no fūjime\" performed at the Chikugo Theater in Osaka from the fifth month of Kaei 2 (May, 1849); main actors: Gadō Kataoka II, Ebizō Ichikawa VI, Kitō Sawamura II, Daigorō Mimasu IV and Karoku Nakamura I; on front cover: producer Mominosuke Ichikawa's crest."));
manifest.setViewingDirection(RIGHT_TO_LEFT);

imageContent1.setWidthHeight(3497, 4823).setFormat(IMAGE_JPEG).setServices(service1);
canvas1.setWidthHeight(3497, 4823).paintWith(imageContent1);

imageContent2.setWidthHeight(6062, 4804).setFormat(IMAGE_JPEG).setServices(service2);
canvas2.setWidthHeight(6062, 4804).paintWith(imageContent2);

imageContent3.setWidthHeight(6127, 4776).setFormat(IMAGE_JPEG).setServices(service3);
canvas3.setWidthHeight(6127, 4776).paintWith(imageContent3);

imageContent4.setWidthHeight(6124, 4751).setFormat(IMAGE_JPEG).setServices(service4);
canvas4.setWidthHeight(6124, 4751).paintWith(imageContent4);

imageContent5.setWidthHeight(3510, 4808).setFormat(IMAGE_JPEG).setServices(service5);
canvas5.setWidthHeight(3510, 4808).paintWith(imageContent5);

manifest.addCanvases(canvas1, canvas2, canvas3, canvas4, canvas5);

System.out.println(manifest);
```

#### TTB Example

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-ttb.json",
  new Label("en", "Diary with Top-to-Bottom Viewing Direction"));
var minter = MinterFactory.getMinter(manifest);

var canvas1 = new Canvas(minter, new Label("en", "image 1"));
var imageContent1 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02/full/max/0/default.jpg");
var service1 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02");

var canvas2 = new Canvas(minter, new Label("en", "image 2"));
var imageContent2 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03/full/max/0/default.jpg");
var service2 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03");

var canvas3 = new Canvas(minter, new Label("en", "image 3"));
var imageContent3 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04/full/max/0/default.jpg");
var service3 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04");

var canvas4 = new Canvas(minter, new Label("en", "image 4"));
var imageContent4 = new ImageContent("https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05/full/max/0/default.jpg");
var service4 = new ImageService3(LEVEL_ONE, "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05");

manifest.setSummary(new Summary("en", "William Lewis Sachtleben was an American long-distance cyclist who rode across Asia from Istanbul to Peking in 1891 to 1892 with Thomas Gaskell Allen Jr., his classmate from Washington University. This was part of a longer journey that began the day after they had graduated from college, when they travelled to New York and on to Liverpool; in all they travelled 15,044 miles by bicycle, 'the longest continuous land journey ever made around the world' as reported in their book <cite>Across Asia on a bicycle</cite> (1895). Sachtleben documented his travels with photographs and diaries, the latter of which he numbered sequentially. The diary of notebook 'No. 10' covers a portion of their journey through the Armenian area of Turkey from April 12 to May 9 (there is a 2-page reading list at the end). During this time they rode from Ankara (Angora in the diary) to Sivas, where they stayed for ten days while Allen had a bout of typhoid fever, and the first half of a ten-day excursion to Merzifon (Mersovan in the diary), taken by Sachtleben to give Allen additional time to recover."));
manifest.setViewingDirection(TOP_TO_BOTTOM);

imageContent1.setWidthHeight(2251, 3152).setFormat(IMAGE_JPEG).setServices(service1);
canvas1.setWidthHeight(2251, 3152).paintWith(imageContent1);

imageContent2.setWidthHeight(2268, 3135).setFormat(IMAGE_JPEG).setServices(service2);
canvas2.setWidthHeight(2268, 3135).paintWith(imageContent2);

imageContent3.setWidthHeight(2274, 3135).setFormat(IMAGE_JPEG).setServices(service3);
canvas3.setWidthHeight(2274, 3135).paintWith(imageContent3);

imageContent4.setWidthHeight(2268, 3135).setFormat(IMAGE_JPEG).setServices(service4);
canvas4.setWidthHeight(2268, 3135).paintWith(imageContent4);

manifest.addCanvases(canvas1, canvas2, canvas3, canvas4);

System.out.println(manifest);
```

### Method Two

The second method will also create a manifest, but assumes the manifest data is coming from a spreadsheet like data structure, instead of being hard-coded. As a result, 
it uses looping to construct the manifest's resources. It still uses a minter to generate some of the manifest's IDs.

The first example uses RTL (right-to-left) and the second uses TTB (top-to-bottom) as their viewing directions.

_Note that `RIGHT_TO_LEFT` and `TOP_TO_BOTTOM`, `LEVEL_ONE`, and `IMAGE_JPEG` are static imports whose classes (`ViewingDirection`, `ImageService3.Profile`, and 
`MediaType`) are predefined in this site’s code sandbox._

#### RTL Example

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-rtl.json",
  new Label("en", "Book with Right-to-Left Viewing Direction"));
var minter = MinterFactory.getMinter(manifest);
var canvases = new ArrayList<Canvas>();

List<List<String>> canvasList = Arrays.asList( //
  List.of("front cover", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001", "3497", "4823"),
  List.of("pages 1–2", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002", "6062", "4804"),
  List.of("pages 3–4", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003", "6127", "4776"),
  List.of("pages 5–6", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004", "6124", "4751"),
  List.of("back cover", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005", "3510", "4808")
);

canvasList.forEach(canvasData -> {
  var canvas = new Canvas(minter, new Label("en", canvasData.get(0)));
  var imageContent = new ImageContent(canvasData.get(1));
  var service = new ImageService3(LEVEL_ONE, canvasData.get(2));
  var width = Integer.valueOf(canvasData.get(3));
  var height = Integer.valueOf(canvasData.get(4));

  imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
  canvases.add(canvas.setWidthHeight(width, height).paintWith(imageContent));
});

manifest.setSummary(new Summary("en", "Playbill for \"Akiba gongen kaisen-banashi,\" \"Futatsu chōchō kuruwa nikki\" and \"Godairiki koi no fūjime\" performed at the Chikugo Theater in Osaka from the fifth month of Kaei 2 (May, 1849); main actors: Gadō Kataoka II, Ebizō Ichikawa VI, Kitō Sawamura II, Daigorō Mimasu IV and Karoku Nakamura I; on front cover: producer Mominosuke Ichikawa's crest."));
manifest.setViewingDirection(RIGHT_TO_LEFT);
manifest.addCanvases(canvases);

System.out.println(manifest);
```

#### TTB Example

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-ttb.json",
  new Label("en", "Diary with Top-to-Bottom Viewing Direction"));
var minter = MinterFactory.getMinter(manifest);
var canvases = new ArrayList<Canvas>();

List<List<String>> canvasList = Arrays.asList(
  List.of("image 1", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02", "2251", "3152"),
  List.of("image 2", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03", "2268", "3135"),
  List.of("image 3", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04", "2274", "3135"),
  List.of("image 4", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05", "2268", "3135")
);

canvasList.forEach(canvasData -> {
  var canvas = new Canvas(minter, new Label("en", canvasData.get(0)));
  var imageContent = new ImageContent(canvasData.get(1));
  var service = new ImageService3(LEVEL_ONE, canvasData.get(2));
  var width = Integer.valueOf(canvasData.get(3));
  var height = Integer.valueOf(canvasData.get(4));

  imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
  canvases.add(canvas.setWidthHeight(width, height).paintWith(imageContent));
});

manifest.setSummary(new Summary("en", "William Lewis Sachtleben was an American long-distance cyclist who rode across Asia from Istanbul to Peking in 1891 to 1892 with Thomas Gaskell Allen Jr., his classmate from Washington University. This was part of a longer journey that began the day after they had graduated from college, when they travelled to New York and on to Liverpool; in all they travelled 15,044 miles by bicycle, 'the longest continuous land journey ever made around the world' as reported in their book <cite>Across Asia on a bicycle</cite> (1895). Sachtleben documented his travels with photographs and diaries, the latter of which he numbered sequentially. The diary of notebook 'No. 10' covers a portion of their journey through the Armenian area of Turkey from April 12 to May 9 (there is a 2-page reading list at the end). During this time they rode from Ankara (Angora in the diary) to Sivas, where they stayed for ten days while Allen had a bout of typhoid fever, and the first half of a ten-day excursion to Merzifon (Mersovan in the diary), taken by Sachtleben to give Allen additional time to recover."));
manifest.setViewingDirection(TOP_TO_BOTTOM);
manifest.addCanvases(canvases);

System.out.println(manifest);
```

### Method Three

The last method, like the previous, assumes the manifest data is coming from a spreadsheet like data structure. It also uses a loop to construct the manifest's 
resources.

However, unlike the other examples, it does not use a minter. Instead, it uses the same IDs as are found in the cookbook recipe. The first example uses RTL 
(right-to-left) and the second uses TTB (top-to-bottom) as their viewing directions.

_Note that `RIGHT_TO_LEFT` and `TOP_TO_BOTTOM`, `LEVEL_ONE`, and `IMAGE_JPEG` are static imports whose classes (`ViewingDirection`, `ImageService3.Profile`, and 
`MediaType`) are predefined in this site’s code sandbox._

#### RTL Example

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-rtl.json",
  new Label("en", "Book with Right-to-Left Viewing Direction"));
var canvases = new ArrayList<Canvas>();

List<List<String>> canvasList = Arrays.asList(
  List.of("front cover", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001", "3497", "4823"),
  List.of("pages 1–2", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002", "6062", "4804"),
  List.of("pages 3–4", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003", "6127", "4776"),
  List.of("pages 5–6", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004", "6124", "4751"),
  List.of("back cover", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005", "3510", "4808")
);

for (int index = 0; index < canvasList.size(); index++) {
  var pageDataList = canvasList.get(index);
  var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/canvas/p" + index,
    new Label("en", pageDataList.get(0)));
  var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/page/p" + index + "/1");
  var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/annotation/p000" + index + "-image", canvas);
  var imageContent = new ImageContent(pageDataList.get(1));
  var service = new ImageService3(LEVEL_ONE, pageDataList.get(2));
  var width = Integer.valueOf(pageDataList.get(3));
  var height = Integer.valueOf(pageDataList.get(4));

  imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
  page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
  canvases.add(canvas.setWidthHeight(width, height).setPaintingPages(page));
}

manifest.setSummary(new Summary("en", "Playbill for \"Akiba gongen kaisen-banashi,\" \"Futatsu chōchō kuruwa nikki\" and \"Godairiki koi no fūjime\" performed at the Chikugo Theater in Osaka from the fifth month of Kaei 2 (May, 1849); main actors: Gadō Kataoka II, Ebizō Ichikawa VI, Kitō Sawamura II, Daigorō Mimasu IV and Karoku Nakamura I; on front cover: producer Mominosuke Ichikawa's crest."));
manifest.setViewingDirection(RIGHT_TO_LEFT);
manifest.addCanvases(canvases);

System.out.println(manifest);
```

#### TTB Example

```java
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-ttb.json",
  new Label("en", "Diary with Top-to-Bottom Viewing Direction"));
var canvases = new ArrayList<Canvas>();

List<List<String>> canvasList = Arrays.asList(
  List.of("image 1", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02", "2251", "3152"),
  List.of("image 2", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03", "2268", "3135"),
  List.of("image 3", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04", "2274", "3135"),
  List.of("image 4", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05/full/max/0/default.jpg", "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05", "2268", "3135")
);

for (int index = 0; index < canvasList.size(); index++) {
  var pageDataList = canvasList.get(index);
  var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/canvas/p" + index,
    new Label("en", pageDataList.get(0)));
  var page = new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/page/p" + index + "/1");
  var annotation = new PaintingAnnotation("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/annotation/p000" + index + "-image", canvas);
  var imageContent = new ImageContent(pageDataList.get(1));
  var service = new ImageService3(LEVEL_ONE, pageDataList.get(2));
  var width = Integer.valueOf(pageDataList.get(3));
  var height = Integer.valueOf(pageDataList.get(4));

  imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
  page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
  canvases.add(canvas.setWidthHeight(width, height).setPaintingPages(page));
}

manifest.setSummary(new Summary("en", "William Lewis Sachtleben was an American long-distance cyclist who rode across Asia from Istanbul to Peking in 1891 to 1892 with Thomas Gaskell Allen Jr., his classmate from Washington University. This was part of a longer journey that began the day after they had graduated from college, when they travelled to New York and on to Liverpool; in all they travelled 15,044 miles by bicycle, 'the longest continuous land journey ever made around the world' as reported in their book <cite>Across Asia on a bicycle</cite> (1895). Sachtleben documented his travels with photographs and diaries, the latter of which he numbered sequentially. The diary of notebook 'No. 10' covers a portion of their journey through the Armenian area of Turkey from April 12 to May 9 (there is a 2-page reading list at the end). During this time they rode from Ankara (Angora in the diary) to Sivas, where they stayed for ten days while Allen had a bout of typhoid fever, and the first half of a ten-day excursion to Merzifon (Mersovan in the diary), taken by Sachtleben to give Allen additional time to recover."));
manifest.setViewingDirection(TOP_TO_BOTTOM);
manifest.addCanvases(canvases);

System.out.println(manifest);
```

{{% sandbox %}}
