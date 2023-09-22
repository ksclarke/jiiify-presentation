+++
title = "Getting Started"
weight = 3
description = """
Introduces very basic instructions on how to use JPV3
"""
+++

Yada

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

