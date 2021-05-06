# Cookbook Tests

## Introduction

The IIIF community has a wonderful effort underway to provide "cookbook" recipes that demonstrate how to use the IIIF Presentation (v3) specification. There is a [website](https://iiif.io/api/cookbook/index.html), where those recipes can be viewed, and a GitHub [repository](https://github.com/IIIF/cookbook-recipes) from which the website is generated. In addition to being a great way to learn about the specification, the IIIF cookbook provides manifests that v3 processing libraries can use as test fixtures.

This page documents jiiify-presentation's use of the cookbook manifests (and collection documents). As the cookbook recipes are still being developed, this page's purpose is to document which cookbooks have been used in jiiify-presentation tests, which ones work, and which ones need future work. This page will be updated as more cookbook manifests are added to the cookbook site.

## Current Statuses

The statuses chart lists the recipes (with links to their GitHub files), the implementation status (i.e., whether jiiify-presentation can handle the manifests and/or collection document), and any notes about the implementation status.

<br/>

| Recipe | Status | Notes (implementation and otherwise) |
:--- | :---: | :---
| [0001-mvm-image](https://iiif.io/api/cookbook/recipe/0001-mvm-image/) | <img src="images/green-check.svg" width="28"> | |
| [0002-mvm-audio](https://iiif.io/api/cookbook/recipe/0002-mvm-audio/) | <img src="images/green-check.svg" width="28"> | |
| [0003-mvm-video](https://iiif.io/api/cookbook/recipe/0003-mvm-video/) | <img src="images/green-check.svg" width="28"> | |
| [0004-canvas-size](https://iiif.io/api/cookbook/recipe/0004-canvas-size/) | <img src="images/green-check.svg" width="28"> | |
| [0005-image-service](https://iiif.io/api/cookbook/recipe/0005-image-service/) | <img src="images/green-check.svg" width="28"> | |
| [0006-text-language](https://iiif.io/api/cookbook/recipe/0006-text-language/) | <img src="images/green-check.svg" width="28"> | |
| [0007-string-formats](https://iiif.io/api/cookbook/recipe/0007-string-formats/) | <img src="images/green-check.svg" width="28"> | |
| [0008-rights](https://iiif.io/api/cookbook/recipe/0008-rights/) | <img src="images/green-check.svg" width="28"> | |
| [0009-book-1](https://iiif.io/api/cookbook/recipe/0009-book-1/) | <img src="images/green-check.svg" width="28"> | |
| [0010-book-2-viewing-direction](https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/) | <img src="images/green-check.svg" width="28"> | |
| [0011-book-3-behavior](https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/) | <img src="images/green-check.svg" width="28"> | |
| [0013-placeholderCanvas](https://iiif.io/api/cookbook/recipe/0013-placeholderCanvas/) | <img src="images/green-check.svg" width="28"> | |
| [0014-accompanyingcanvas](https://iiif.io/api/cookbook/recipe/0014-accompanyingcanvas/) | <img src="images/green-check.svg" width="28"> | |
| [0015-start](https://iiif.io/api/cookbook/recipe/0015-start/) | <img src="images/green-check.svg" width="28"> | |
| [0016-transcription-image](https://iiif.io/api/cookbook/recipe/0016-transcription-image/) | | Recipe not yet completed |
| [0017-transcription-av](https://iiif.io/api/cookbook/recipe/0017-transcription-av/) | | Recipe not yet completed |
| [0018-transcription-xml](https://iiif.io/api/cookbook/recipe/0018-transcription-xml/) | | Recipe not yet completed |
| [0019-comments](https://iiif.io/api/cookbook/recipe/0019-comments/) | | Recipe not yet completed |
| [0020-fragment-selector](https://iiif.io/api/cookbook/recipe/0020-fragment-selector/) | | Recipe not yet completed |
| [0021-tagging](https://iiif.io/api/cookbook/recipe/0021-tagging/) | | Recipe not yet completed |
| [0022-linking](https://iiif.io/api/cookbook/recipe/0022-linking/) | | Recipe not yet completed |
| [0023-annotating-specific-resources](https://iiif.io/api/cookbook/recipe/0023-annotating-specific-resources/) | | Recipe not yet completed |
| [0024-book-4-toc](https://iiif.io/api/cookbook/recipe/0024-book-4-toc/) | <img src="images/green-check.svg" width="28"> | |
| [0026-toc-opera](https://iiif.io/api/cookbook/recipe/0026-toc-opera/) | <img src="images/green-check.svg" width="28"> | |
| [0029-metadata-anywhere](https://iiif.io/api/cookbook/recipe/0029-metadata-anywhere/) | <img src="images/green-check.svg" width="28"> | |
| [0030-multi-volume](https://iiif.io/api/cookbook/recipe/0030-multi-volume/) | <img src="images/green-check.svg" width="28"> | |
| [0046-rendering](https://iiif.io/api/cookbook/recipe/0046-rendering/) | <img src="images/green-check.svg" width="28"> | |
| [0053-seeAlso](https://iiif.io/api/cookbook/recipe/0053-seeAlso/) | <img src="images/green-check.svg" width="28"> | |
| [0064-opera-one-canvas](https://iiif.io/api/cookbook/recipe/0064-opera-one-canvas/) | <img src="images/green-check.svg" width="28"> | |
| [0065-opera-multiple-canvases](https://iiif.io/api/cookbook/recipe/0065-opera-multiple-canvases/) | <img src="images/green-check.svg" width="28"> | |
| [0068-newspaper](https://iiif.io/api/cookbook/recipe/0068-newspaper/) | <img src="images/green-check.svg" width="28"> | |
| [0117-add-image-thumbnail](https://iiif.io/api/cookbook/recipe/0117-add-image-thumbnail/) | <img src="images/green-check.svg" width="28"> | |
| [0118_multivalue](https://iiif.io/api/cookbook/recipe/0118_multivalue/) | <img src="images/green-check.svg" width="28"> | |
| [0139-geolocate-canvas-fragment](https://iiif.io/api/cookbook/recipe/0139-geolocate-canvas-fragment/) | <img src="images/green-check.svg" width="28"> | JP supports supplementing and painting annotations as primary methods; the GeoJSON annotation body is handled by an OtherContent class, which wraps the body's content in a JsonObject. This makes the tagging annotation available to the code without having specific classes to handle it. Widely used standards can be added to JP's classes in the future, with OtherContent still being used as a generic fallback. |
| [0202-start-canvas](https://iiif.io/api/cookbook/recipe/0202-start-canvas/) | <img src="images/green-check.svg" width="28"> | |
| [0219-using-caption-file](https://iiif.io/api/cookbook/recipe/0219-using-caption-file/) | <img src="images/green-check.svg" width="28"> | |
| [0230-navdate](https://iiif.io/api/cookbook/recipe/0230-navdate/) | <img src="images/green-check.svg" width="28"> | |
| | <img width="100px"> | |
