## webrepl

A quick and dirty REPL for JPv3's IIIF Cookbook recipes.

### How to Build

The project requires several pre-requisites:

* [JDK](https://adoptium.net/temurin/releases/?package=jdk&version=21) (version 21)
* [Maven](https://maven.apache.org/)
* [Docker](https://docs.docker.com/engine/install/)

Once they are installed correctly, the Web REPL's Docker container can be built with the following:

`mvn verify` 

To push the image to DockerHub:

```
mvn verify -Ddocker.registry.username="YOURS_HERE" -Ddocker.registry.password="YOURS_HERE" \
  -Ddocker.registry.account="YOURS_HERE/"
```

### Contact

If you have any suggestions for improvement (or find any issues with the build), please feel free to 
[open a ticket](https://github.com/ksclarke/jiiify-presentation/issues).
