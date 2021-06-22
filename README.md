# jiiify-presentation <br/>[![IIIF Presentation 3.0](https://img.shields.io/badge/IIIF%20Presentation-3.0-brightgreen)](https://iiif.io/api/presentation/3.0/) [![Maven PR Build](https://github.com/ksclarke/jiiify-presentation/actions/workflows/build.yml/badge.svg)](https://github.com/ksclarke/jiiify-presentation/actions/workflows/build.yml) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/a1fb61b809944441bf65e02132383b6d?branch=v3)](https://www.codacy.com/app/ksclarke/jiiify-presentation?utm_source=github.com&utm_medium=referral&utm_content=ksclarke/jiiify-presentation&utm_campaign=Badge_Coverage) [![Known Vulnerabilities](https://snyk.io/test/github/ksclarke/jiiify-presentation/v3/badge.svg)](https://snyk.io/test/github/ksclarke/jiiify-presentation/v3) [![Maven](https://img.shields.io/maven-central/v/info.freelibrary/jiiify-presentation-v3?colorB=brightgreen)](https://search.maven.org/artifact/info.freelibrary/jiiify-presentation-v3) [![Javadocs](http://javadoc.io/badge2/info.freelibrary/jiiify-presentation-v3/latest/javadoc.svg)](https://javadoc.io/doc/info.freelibrary/jiiify-presentation-v3/latest/index.html)

Jiiify Presentation is a [IIIF Presentation](http://iiif.io/api/presentation) library for Java. It does not contain a server. It's just for working with IIIF presentation manifests.

If you are interested in using the library with Python or Ruby (using Jython or JRuby) you'll want the "[uber jar](https://repo1.maven.org/maven2/info/freelibrary/jiiify-presentation-v3/0.9.1/jiiify-presentation-v3-0.9.1-uber.jar)" that's available from Maven Central. To assist with getting started, there are a few simple Ruby and Python examples in the [tests](https://github.com/ksclarke/jiiify-presentation/tree/v3/src/test) directory. They can also be found in the newly started [Jiiify Presentation GitBook](https://ksclarke.gitbook.io/jiiify-presentation/).

*Warning:* The API for v3 is still in active development. There will definitely be breaking changes before the library reaches version 1.0.0. After it reaches 1.0.0 and becomes stable, the project will use semantic versioning to indicate the types of changes associated with new releases.

### Getting Started

A very simple Java example of using jiiify-presentation follows:

    final Manifestor manifestor = new Manifestor();
    final Manifest manifest = manifestor.readManifest(new File("src/test/resources/json/z1960050.json"));

    manifest.getMetadata().add(new Metadata("Contributor", "Your Name Here"));
    manifestor.write(manifest, File.createTempFile("z1960050", ".json"));

To learn more about how to use the library, consult the project's [Javadocs](https://javadoc.io/doc/info.freelibrary/jiiify-presentation-v3/latest/index.html).

### Building the Project

To check the project out and build it, type:

    git clone https://github.com/ksclarke/jiiify-presentation.git
    cd jiiify-presentation
    git fetch origin
    git checkout -b v3 origin/v3
    mvn install

To build the Javadocs, from the command line, run: `mvn javadoc:javadoc`

### Spec/Cookbook Compliance

Jiiify Presentation (JP) uses the IIIF Presentation [cookbooks](https://iiif.io/api/cookbook/index.html) as an indicator of v3 support. Consult the JP [documentation](docs/cookbook-status.md) to see which cookbooks are fully supported, partially supported, or not yet supported. Not all cookbooks have been completely fleshed out yet so this list will be frequently updated until they are all completed.

### Contact

If you encounter a problem, please feel free to open a ticket in the project's [issues queue](https://github.com/ksclarke/jiiify-presentation/issues "GitHub Issues Queue"). If you have a question, feel free to use the project's [discussion board](https://github.com/ksclarke/jiiify-presentation/discussions). Please specify which branch or version of jiiify-presentation you're using.
