Docr
====

Docr is a quick little app that uses [Qdox](http://qdox.codehaus.org/) to generate textual or HTML documentation for Spring controllers. It uses
Spring's MVC annotations to identify controllers within a source directory and has some additional annotations that can be used to improve the
documentation.

Usage
=====

<pre>
mvn exec:java -Dexec.mainClass="docr.Main" -Ddir="<java source files>"
</pre> 