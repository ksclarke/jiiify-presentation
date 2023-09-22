+++
title = "Using Official Release"
weight = 2
description = """
This page tells you how to use the JPv3 jar artifact in your projects.
"""
+++

The most common way to use Jiiify Presentation (v3) is to include it as a dependency in your own Java project. Assuming you're using Maven as a project management tool, 
you can copy and paste the below into your [POM](https://maven.apache.org/pom.html) file, replacing `${jpv3.version}` with the version of Jiiify Presentation you want to 
use.


```xml {linenos=false}
    <dependency>
      <groupId>info.freelibrary</groupId>
      <artifactId>jiiify-presentation-v3</artifactId>
      <version>${jpv3.version}</version>
    </dependency>
```

Dependency inclusion formats for other project management tools (e.g., Gradle, Sbt, IVY, etc.) can be found on the project's [Maven 
page](https://central.sonatype.com/artifact/info.freelibrary/jiiify-presentation-v3).

