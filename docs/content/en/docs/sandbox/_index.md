---
title: Code Sandbox
description: A simple sandbox for testing out JPv3 code.
weight: 8
---

This page provides a very simple sandbox where JPv3 code can be run. It's provided to test the code examples from the cookbook recipes or any other simple JPv3 code 
snippets.

{{% pageinfo %}}You do not need to include Java imports in your code because this instance of JShell is pre-configured to have all the imports one would need to run 
examples from this site.{{% /pageinfo %}}

Warning: This sandbox is not very robust; it's just a quick and dirty solution to test small snippets of JPv3 code. If you notice the sandbox is broken, please [open a 
ticket](https://github.com/ksclarke/jiiify-presentation/issues) on the project's GitHub page.

{{< rawhtml >}}
<pre id="editor" style="height: 20em; margin-top: 40px; border: 2px solid #aaa;">
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
  new Label("en", "Single Image Example"));
var canvas = new Canvas(MinterFactory.getMinter(manifest)).setWidthHeight(1200, 1800);
var imageContent = new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png");

canvas.paintWith(imageContent.setWidthHeight(1200, 1800));
manifest.setCanvases(canvas);

System.out.println(manifest);
</pre>
{{< /rawhtml >}}

<script src="https://cdn.jsdelivr.net/npm/ace-builds@1.32.9/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
<script>
    var editor = ace.edit("editor");

    editor.setTheme("ace/theme/tomorrow");
    editor.getSession().setMode("ace/mode/java");
    editor.setFontSize("1em");
    editor.setOption("showLineNumbers", false);

    function clearResults() {
        var replResults = document.getElementById("replResults");
        replResults.innerHTML = "";
    }

    function runCode() {
        var code = editor.getValue();
        var xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                var replResults = document.getElementById("replResults");

                if (xhr.status === 200) {
                    replResults.innerHTML = xhr.responseText.replace(/</g, "&lt;").replace(/>/g, "&gt;");
                } else {
                    replResults.innerHTML = xhr.status;
                }
            }
        };

        xhr.open('POST', 'http://localhost:8080/cgi-bin/jpv3.cgi', true);
//        xhr.open('POST', 'https://jsh4jpv3.lisforge.net/cgi-bin/jpv3.cgi', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send("code=" + encodeURIComponent(code));
    }
</script>

<button onclick="runCode()">Run Code</button> <button onclick="clearResults()">Clear Results</button>

<div style="margin-bottom: 30px; margin-top:30px;"><div style="font-weight: bold; padding-bottom: 20px;">Results should appear below (approximately 15 seconds or so after 
you click the button above)</div>If, on submission, you see <code style="font-weight: bold">jshell&gt;</code> prompt in the box below, JShell failed to parse the 
submitted code, likely because of a syntax issue (e.g., a missing parenthesis).</div>

<pre id="replResults" style="border: 2px solid #aaa"></pre>
