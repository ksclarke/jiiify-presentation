# jiiify-presentation

Jiiify Presentation is a [IIIF Presentation](http://iiif.io/api/presentation) library for Java. It does not contain a server. It's just a library for working with IIIF presentation manifests and collections.

There are two versions of the project: one for v.2 of the IIIF Presentation specification and one for v.3. Each of these versions is kept on its own branch (named for the supported version: "v2" or "v3"). The "main" branch of the project is the default branch and just contains this README.

### Getting Started

To start working with the library, you can either include it as a dependency in your project's POM or clone the project's GitHub repository and select the branch / version you're interested in using.

To include jiiify-presentation as a dependency in your POM, use one of the following dependency elements:

```
  <dependency>
    <groupId>info.freelibrary</groupId>
    <artifactId>jiiify-presentation</artifactId>
    <version>v2-1.0.0</version>
  </dependency>
```

or

```
  <dependency>
    <groupId>info.freelibrary</groupId>
    <artifactId>jiiify-presentation</artifactId>
    <version>v3-1.0.0</version>
  </dependency>
```

You'll have to change the version number above to match the most recent version from [Maven Central](https://search.maven.org/search?q=a:jiiify-presentation).

### Compiling from Source

To build a particular version from the project's GitHub repository, consult the README on the [v2](https://github.com/ksclarke/jiiify-presentation/tree/v2) or [v3](https://github.com/ksclarke/jiiify-presentation/tree/v3) branches. They have specific instructions on building and using each version.

### Contact

If you encounter a problem or would like to make a suggestion, please feel free to open a ticket in the project's [issues queue](https://github.com/ksclarke/jiiify-presentation/issues "GitHub Issue Queue"). Please specify which branch or version you're using.
