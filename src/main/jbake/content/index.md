title=Welcome to the Jiiify Presentation Project
date=2017-10-10
type=page
status=published
~~~~~~

A [IIIF Presentation](http://iiif.io/api/presentation) library for Java. It does not contain a server. It's just for working with IIIF presentation manifests.


## Using Jiiify Presentation
<p/>

To use the jiiify-presentation library, reference it in your project's `pom.xml` file:

<pre><code>&lt;dependency&gt;
  &lt;groupId&gt;info.freelibrary&lt;/groupId&gt;
  &lt;artifactId&gt;jiiify-presentation&lt;/artifactId&gt;
  &lt;version&gt;${jiiify.pres.version}&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>

<br/>Or, to use it with Gradle/Grails, include the following in your project's `build.gradle` file:

<pre><code>compile &apos;info.freelibrary:jiiify-presentation:${jiiify.pres.version}&apos;</code></pre>
<p/>

## Building Jiiify Presentation
<p/>

To build the project, you need a [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) environment with [Maven](http://maven.apache.org/) installed on your system. Once you have that, download the jiiify-presentation source code:

    git clone https://github.com/ksclarke/jiiify-presentation.git
    cd jiiify-presentation

<br/>To build the project, type:

    mvn install

<br/>To build the project's documentation, type:

    mvn javadoc:javadoc

<br/>To view the documentation online, check the `Documentation` drop down at the top of this page.
