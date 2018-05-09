# jiiify-presentation &nbsp;[![Build Status](https://travis-ci.org/ksclarke/jiiify-presentation.svg)](https://travis-ci.org/ksclarke/jiiify-presentation) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/a1fb61b809944441bf65e02132383b6d)](https://www.codacy.com/app/ksclarke/jiiify-presentation?utm_source=github.com&utm_medium=referral&utm_content=ksclarke/jiiify-presentation&utm_campaign=Badge_Coverage) [![Known Vulnerabilities](https://snyk.io/test/github/ksclarke/jiiify-presentation/badge.svg)](https://snyk.io/test/github/ksclarke/jiiify-presentation) [![Maven](https://img.shields.io/maven-central/v/info.freelibrary/jiiify-presentation.svg)](http://mvnrepository.com/artifact/info.freelibrary/jiiify-presentation) [![Javadocs](http://javadoc.io/badge/info.freelibrary/jiiify-presentation.svg)](http://projects.freelibrary.info/jiiify-presentation/javadocs.html)

A [IIIF Presentation](http://iiif.io/api/presentation) library for Java. It does not contain a server. It's meant to be used in a server or it can be used from a command line / GUI application that generates 
presentation manifests from files on disk, etc.

This is a work in progress and should not be considered stable.

Deserializing to JSON requires Jackson's `ObjectMapper`. A very simple example:

    final Manifest manifest = new Manifest("https://example.org/iiif/001/manifest", "My document");
    final ObjectMapper mapper = new ObjectMapper();
    
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.findAndRegisterModules(); // Needed for Jdk8Module
    
    System.out.println(mapper.writeValueAsString(manifest));

### Getting Started

To check out and build the project, type on the command line:

    git clone https://github.com/ksclarke/jiiify-presentation.git
    cd jiiify-presentation
    mvn install

This will put the jar in your local Maven repository and you can reference it from your project.

To build the Javadocs, from the command line, run: `mvn javadoc:javadoc`

### Contact

If you have questions about jiiify-presentation <a href="mailto:ksclarke@ksclarke.io">feel free to ask</a> or, if you encounter a problem, please feel free to [open an issue](https://github.com/ksclarke/jiiify-presentation/issues "GitHub Issue Queue") in the project's issue queue.
