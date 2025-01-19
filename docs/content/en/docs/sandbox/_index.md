---
title: Code Sandbox
description: A simple sandbox for testing out JPv3 code.
weight: 8
---

This page provides a very simple sandbox where JPv3 code can be run. It's provided to test the code examples from the 
cookbook recipes, but can also be used to test other simple JPv3 code snippets.

{{% pageinfo %}}You do not need to include imports in your code because the JPv3 imports are 
pre-configured.{{% /pageinfo %}}

{{< rawhtml >}}
<pre id="editor"
  style="overflow-y: hidden; height: 11em; margin-top: 40px; margin-bottom: 20px; border: 1px solid #aaa">
var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
  new Label("en", "Single Image Example"));
var canvas = new Canvas(MinterFactory.getMinter(manifest)).setWidthHeight(1200, 1800);
var imageContent = new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png");

canvas.paintWith(imageContent.setWidthHeight(1200, 1800));
manifest.setCanvases(canvas);

System.out.println(manifest);</pre>
{{< /rawhtml >}}


<script src="https://cdn.jsdelivr.net/npm/ace-builds@1.32.9/src-noconflict/ace.js"
        type="text/javascript"
        charset="utf-8"></script>
<script>
    let editor = ace.edit("editor");

    editor.setTheme("ace/theme/tomorrow");
    editor.getSession().setMode("ace/mode/java");
    editor.setFontSize(".95em");
    editor.setOption("showLineNumbers", true);

    function clearResults() {
        document.getElementById("replResults").innerHTML = "";
    }

    function runCode() {
        let code = editor.getValue();
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                let replResults = document.getElementById("replResults");

                if (xhr.status === 200 || xhr.status === 201) {
                    replResults.innerHTML = xhr.responseText.replace(/</g, "&lt;").replace(/>/g, "&gt;");
                } else {
                    replResults.innerHTML = xhr.status.toString();
                }
            }
        };

        xhr.open('POST', 'http://localhost:8888/submit', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send("code=" + encodeURIComponent(code));
    }

    editor.getSession().on('change', function() {
        editor.container.style.height = editor.session.getLength() + "em";
        editor.resize();
    });
</script>

<button onclick="runCode()">Run Code</button> <button onclick="clearResults()">Clear Results</button>

<pre id="replResults" style="border: 2px solid #aaa"></pre>

<div style="margin-bottom: 30px; margin-top:30px"><span style="font-weight: bold">Problems?</span> If you notice the sandbox isn't working, please <a 
href="https://github.com/ksclarke/jiiify-presentation/issues">open a ticket</a> with the details about what's wrong. If 
the problem is with the code itself, you should see an error message in the results box. You should be able to fix that
yourself.</div>
