# Introduction

Jiiify Presentation is a Java library for working with IIIF Presentation manifests and collections. There are two different versions of Jiiify Presentation: [version 2](https://github.com/ksclarke/jiiify-presentation/tree/v2) and [version 3](https://github.com/ksclarke/jiiify-presentation/tree/v3). These versions correspond to versions of the IIIF Presentation specification. Though both versions of Jiiify Presentation are being used in production, version 3 is much more complete than version 2. Version 3 is also more actively developed at the moment.

The Jiiify Presentation library just contains an object model. It does not contain a server or IIIF client. It's intended to be used in servers, clients, or one-off scripts that need to work with presentation manifests and collections. Because Jiiify Presentation is written in Java and runs on the JVM, it can also be used in Ruby scripts \(with the help of JRuby\) and Python scripts \(with the help of Jython\).

This guide attempts to show how one might use Jiiify Presentation by working through the [example recipes](https://iiif.io/api/cookbook/) that the IIIF Cookbook Committee is developing. These cookbook recipes illustrate how to use the IIIF Presentation specification and this guide, in turn, uses them to illustrate how to use Jiiify Presentation. The goal is to include examples for each cookbook recipe in Java, Ruby, and Python.

Suggestions on how to improve this documentation are welcome. If you have a question or suggestion, feel free [create an issue](https://github.com/ksclarke/jiiify-presentation/issues) or [start a discussion](https://github.com/ksclarke/jiiify-presentation/discussions) on the GitHub project page.

This documentation is released under the [Creative Commons Attribution-ShareAlike 4.0 International Public License](https://creativecommons.org/licenses/by-sa/4.0/legalcode). The documentation's code examples are licensed under the [BSD 3-Clause License](https://opensource.org/licenses/BSD-3-Clause). The JSON documents in the documentation \(borrowed from the IIIF Cookbook\) are gifted to the public domain using the [CC0 Public Domain Dedication](https://creativecommons.org/publicdomain/zero/1.0/).

