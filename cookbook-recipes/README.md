---
description: Context and Conventions
---

# Cookbook Recipes

The IIIF Cookbook recipes are being created by the IIIF community to gather together examples of IIIF Manifests and Collections, in order to:

* provide many more examples than the specification alone can do, for reference and learning
* encourage publishers to adopt common patterns in modeling classes of complex objects
* enable client software developers to support these patterns, for consistency of user experience \(when desirable\)
* demonstrate the applicability of IIIF to a broad range of use cases

This documentation provides Java, Ruby, and Python examples illustrating how to use Jiiify Presentation \(JP\) to work with these examples. As recipe creation is an ongoing process, the addition of recipes to this documentation will also be an ongoing process.

To make reading the example code easier, there are some language conventions that this document will follow:

#### Java

Full Java files are not reproduced in this document. The missing parts \(e.g. the imports and wrapper code in which the supplied code snippets would be found\) are available, though, in [Jiiify Presentation's tests](https://github.com/ksclarke/jiiify-presentation/blob/v3/src/test/java/info/freelibrary/iiif/presentation/v3/examples/CookbooksTest.java). In addition to this, any Java IDE should add the imports automatically when this document's code snippets are pasted into its editor.

#### Ruby

Full Ruby files are not reproduced in this document. In order to be able to use this document's examples, you'd need to add the necessary requires and includes; for example: 

```text
require 'jruby'
require_relative 'jiiify-presentation-v3-0.9.0-uber.jar'

# You'll also need a list of all used packages, for example:
include_package 'info.freelibrary.iiif.presentation.v3'
include_package 'info.freelibrary.iiif.presentation.v3.ids'
include_package 'info.freelibrary.iiif.presentation.v3.properties'
```

Which packages you include in the script depends on which Jiiify Presentation classes you want to use. If you'd like to see [examples of full Ruby scripts](https://github.com/ksclarke/jiiify-presentation/tree/v3/src/test/ruby), there are many in the project's Ruby tests directory.

#### Python

Full Python files are not reproduced in this document. To use the examples in this document, you would first need to include the necessary imports; for example:

```text
import sys; sys.path.append('jiiify-presentation-v3-0.9.0-uber.jar')

from info.freelibrary.iiif.presentation.v3 import Canvas, ImageContent, Manifest
from info.freelibrary.iiif.presentation.v3.ids import MinterFactory
from info.freelibrary.iiif.presentation.v3.properties import Label
```

Which imports you included would depend on which Jiiify Presentation classes you wanted to use. If you would like to see some examples of full Python scripts using Jiiify Presentation, there are [some expanded examples](https://github.com/ksclarke/jiiify-presentation/tree/v3/src/test/python) available in the project's Python tests directory.

For the Python examples in this documentation, it's also important to remember that, anytime the Java API allows a varargs argument \(e.g., `setCanvases(Canvas...)`\), an array should be passed from the Python side \(e.g., `setCanvases([canvas])`\). This is true even if there is just one resource being passed.

#### JSON Test Fixtures

This document also includes the JSON recipe files that the IIIF Cookbook project makes available. There may be some slight reordering of JSON keys \(until the cookbook project implements a constistent key sort order\), but the contents of the JSON examples should be the same as the original JSON files distributed by the Cookbook project.

