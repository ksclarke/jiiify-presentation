+++
description = "Instructions on how to build JPv3"
title = "Building from Source"
weight = 1
+++

### Getting Started

To build Jiiify Presentation (v3), three things are required: a [Java Development Kit](https://adoptium.net/marketplace/) (JDK), [Git](https://git-scm.com/), and 
[Maven](https://maven.apache.org/).

Everything else that's needed will be installed during the Maven build. These prequisites can be installed using your system's package distribution method or, manually, 
through the links above.

{{< tip >}}
The official version of Jiiify Presentation (v3) is built using JDK 17, Maven 3.8.6, and Git 2.34.

Earlier versions of these prerequisites have not been tested and are not guaranteed to work. Later versions should be fine.
{{< /tip >}}

The following steps can be copied into a terminal window, en masse, by hovering over the snippet below and selecting the copy icon. They document how to download and 
build the project.

```shell {linenos=false}
git clone https://github.com/ksclarke/jiiify-presentation.git
cd jiiify-presentation
git fetch origin
git checkout -b v3 origin/v3
mvn install
```

Once these steps are completed, a Jar file containing Jiiify Presentation (v3) will exist in your project's build (i.e., `target`) directory (and also in your system's 
local Maven repository).

### Running the Tests

You might notice that while running the commands above a series of tests are run. These tests can also be run independent of a full build by running the test suite 
directly from the command line:

```shell {linenos=false}
mvn test
```

It's also possible to run an individual test on the command line. To do that, supply a particular test's class and method names as a build argument:

```shell {linenos=false}
mvn test -Dtest=CookbooksTest#test0001WithMinter
```

### Code Conventions

The project's code conventions are encoded in two files: a [Checkstyle](https://checkstyle.sourceforge.io/) configuration file and a [PMD](https://pmd.github.io/) 
configuration file. Both of these can be found in the project's [tools directory](https://github.com/ksclarke/jiiify-presentation/tree/v3/src/main/tools).

There is not currently any better, more human-friendly version of this documentation. When there is, it will be linked here.

