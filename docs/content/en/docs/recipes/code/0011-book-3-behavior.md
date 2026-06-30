+++
title = "Book 'behavior' Variations (continuous, individuals)"
linkTitle = "0011-book-3-behavior"
weight = 0
+++

### Use Case

Book objects (and their digital surrogates) come in a variety of forms and layouts that affect how we might display a
resource to users, such as codices with the concept of recto and verso, scrolls or accordion books that have a
continuous layout, or a digitized codex that has been imaged as 2-page spreads.

To tell a presentation client how to display a sequence of resources in relation to one another, IIIF uses the behavior
property. For example, a collection of unordered photographs might use the property "behavior": "unordered"; or, a
standard codex might be best presented in book-view, with two facing pages displaying next to one another. In this case,
the behavior property value used is paged. For an example Manifest using the "behavior": "paged" property, see the Book
(simplest) recipe.

|           |                                                                                    |
|:----------|:----------------------------------------------------------------------------------:|
| Recipe:   |             https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/              |
| RTL JSON: | https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/manifest-continuous.json  |
| TTB JSON: | https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/manifest-individuals.json |

This recipe provides example Manifests demonstrating the use of the behavior property for two additional book-related
use cases:

*Use case 1:* An accordion book that has been imaged by capturing each segment through multiple shots, allowing the book
to be “stitched together” in the viewer for a single continuous view. In this example, the Manifest will use the
behavior property continuous.

*Use case 2:* A book (codex) object where the page images were captured with one image per 2-page spread. In this
example, the Manifest will use the behavior property individuals.

```java

```

{{% sandbox %}}