# jiiify-presentation
[![IIIF Presentation 2.1](https://img.shields.io/badge/IIIF%20Presentation-2.1-brightgreen)](https://iiif.io/api/presentation/2.1/) [![Build Status](https://travis-ci.org/ksclarke/jiiify-presentation.svg?branch=v2)](https://travis-ci.org/ksclarke/jiiify-presentation) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/a1fb61b809944441bf65e02132383b6d?branch=v2)](https://www.codacy.com/app/ksclarke/jiiify-presentation?utm_source=github.com&utm_medium=referral&utm_content=ksclarke/jiiify-presentation&utm_campaign=Badge_Coverage) [![Known Vulnerabilities](https://snyk.io/test/github/ksclarke/jiiify-presentation/v2/badge.svg)](https://snyk.io/test/github/ksclarke/jiiify-presentation) [![Maven](https://img.shields.io/maven-central/v/info.freelibrary/jiiify-presentation/v2?colorB=brightgreen)](http://mvnrepository.com/artifact/info.freelibrary/jiiify-presentation) [![Javadocs](http://javadoc.io/badge2/info.freelibrary/jiiify-presentation/v2-0.3.1/javadoc.svg)](http://projects.freelibrary.info/jiiify-presentation/javadocs.html)

<br/>

Jiiify Presentation is a [IIIF Presentation](http://iiif.io/api/presentation) library for Java. It does not contain a server. It's just for working with IIIF presentation manifests and collections.

### Getting Started

A very simple example of using jiiify-presentation follows:

    final Manifestor manifestor = new Manifestor();
    final Manifest manifest = manifestor.readManifest(new File("src/test/resources/json/z1960050.json"));

    manifest.getMetadata().add("Contributor", "Your Name Here");
    manifestor.write(manifest, File.createTempFile("z1960050", ".json"));

To learn more about how to use the library, consult the project's [Javadocs](http://projects.freelibrary.info/jiiify-presentation/javadocs.html).

### Building the Project

To check the project out and build it, type:

    git clone https://github.com/ksclarke/jiiify-presentation.git
    cd jiiify-presentation
    git fetch origin
    git checkout -b v2 origin/v2
    mvn install

To build the Javadocs, from the command line, run: `mvn javadoc:javadoc`

### Contact

If you encounter a problem or would like to make a suggestion, please feel free to open a ticket in the project's [issues queue](https://github.com/ksclarke/jiiify-presentation/issues "GitHub Issue Queue"). Please specify which branch or version you're using.
