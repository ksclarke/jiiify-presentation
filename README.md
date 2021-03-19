# jiiify-presentation <br/>[![IIIF Presentation 2.1](https://img.shields.io/badge/IIIF%20Presentation-2.1-brightgreen)](https://iiif.io/api/presentation/2.1/) [![Maven PR Build](https://github.com/ksclarke/jiiify-presentation/actions/workflows/build.yml/badge.svg)](https://github.com/ksclarke/jiiify-presentation/actions/workflows/build.yml) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/a1fb61b809944441bf65e02132383b6d?branch=v2)](https://www.codacy.com/app/ksclarke/jiiify-presentation?utm_source=github.com&utm_medium=referral&utm_content=ksclarke/jiiify-presentation&utm_campaign=Badge_Coverage) [![Known Vulnerabilities](https://snyk.io/test/github/ksclarke/jiiify-presentation/v2/badge.svg)](https://snyk.io/test/github/ksclarke/jiiify-presentation/v2) [![Maven](https://img.shields.io/maven-central/v/info.freelibrary/jiiify-presentation-v2?colorB=brightgreen)](https://search.maven.org/artifact/info.freelibrary/jiiify-presentation-v2) [![Javadocs](http://javadoc.io/badge2/info.freelibrary/jiiify-presentation-v2/latest/javadoc.svg)](https://javadoc.io/doc/info.freelibrary/jiiify-presentation-v2/latest/index.html)

<br/>

Jiiify Presentation is a [IIIF Presentation](http://iiif.io/api/presentation) library for Java. It does not contain a server. It's just for working with IIIF presentation manifests and collections.

### Getting Started

A very simple example of using jiiify-presentation follows:

    final Manifestor manifestor = new Manifestor();
    final Manifest manifest = manifestor.readManifest(new File("src/test/resources/json/z1960050.json"));

    manifest.getMetadata().add("Contributor", "Your Name Here");
    manifestor.write(manifest, File.createTempFile("z1960050", ".json"));

To learn more about how to use the library, consult the project's [Javadocs](https://javadoc.io/doc/info.freelibrary/jiiify-presentation-v2/latest/index.html).

### Building the Project

To check the project out and build it, type:

    git clone https://github.com/ksclarke/jiiify-presentation.git
    cd jiiify-presentation
    git fetch origin
    git checkout -b v2 origin/v2
    mvn install

To build the Javadocs, from the command line, run: `mvn javadoc:javadoc`

### Contact

If you encounter a problem, please feel free to open a ticket in the project's [issues queue](https://github.com/ksclarke/jiiify-presentation/issues "GitHub Issue Queue"). If you have a question, feel free to use the project's [discussion board](https://github.com/ksclarke/jiiify-presentation/discussions). Please specify which branch or version or jiiify-presentation you're using.
