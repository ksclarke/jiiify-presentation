# jiiify-presentation

Jiiify Presentation is a [IIIF Presentation](http://iiif.io/api/presentation) library for Java. It does not contain a server. It just provides an object model to assist in the creation and manipulation of presentation manifests and collections.

There are two versions of the project: one for version 2 of the IIIF Presentation specification and one for version 3. Each is kept on its own branch (named for the version of the specification it supports: "v2" or "v3"). For information about a particular version, consult the README on branch [v2](https://github.com/ksclarke/jiiify-presentation/tree/v2) or [v3](https://github.com/ksclarke/jiiify-presentation/tree/v3).

### Getting Started

To start working with the library, you can either include it as a dependency in your project's POM or clone the project's GitHub repository and select the branch (i.e. version) that you're interested in using.

To include the pre-compiled jiiify-presentation Jar as a dependency in your project, use one of the following elements in your POM file's `dependencies` section:

```
  <dependency>
    <groupId>info.freelibrary</groupId>
    <artifactId>jiiify-presentation-v2</artifactId>
    <version>0.5.6</version>
  </dependency>
```

or

```
  <dependency>
    <groupId>info.freelibrary</groupId>
    <artifactId>jiiify-presentation-v3</artifactId>
    <version>0.11.0</version>
  </dependency>
```

If you're looking for a v3 uber-jar that you can use with a Ruby or Python script, you can [download that](https://repo1.maven.org/maven2/info/freelibrary/jiiify-presentation-v3/0.11.0/jiiify-presentation-v3-0.11.0-uber.jar) directly from the Central Maven repository.

_Note: You might want to replace the version number above with the most recent from [Maven Central](https://search.maven.org/search?q=%22jiiify-presentation%22). I'll try to keep this README up to date, but it never hurts to go to the source._

### Compiling from Source

To build a particular version from this project's GitHub repository, consult the README on the [v2](https://github.com/ksclarke/jiiify-presentation/tree/v2) or [v3](https://github.com/ksclarke/jiiify-presentation/tree/v3) branch. Each has branch-specific instructions on building and using jiiify-presentation.

### Contact

If you encounter a problem or would like to make a suggestion, feel free to open a ticket in the project's [issues queue](https://github.com/ksclarke/jiiify-presentation/issues "GitHub Issue Queue"). Please specify which branch or version you're using.
