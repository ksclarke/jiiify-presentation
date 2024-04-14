---
title: "Embedding HTML in descriptive properties"
linkTitle: "0007-string-formats"
weight: 0
---

You want to have more control on how your metadata is displayed by adding links or simple formatting instructions to selected text blocks. For example, scientific names 
might need special formatting and links out to other sites benefit from being activatable. Legacy systems may also include rudimentary formatting in their output.

| | |
| :--- | :---: |
| Recipe: | https://iiif.io/api/cookbook/recipe/0007-string-formats/ |
| Manifest: | https://iiif.io/api/cookbook/recipe/0007-string-formats/manifest.json |

### Method One

For the first method, we use a Minter to create IDs for the components of the manifest. This allows us to use less code to create the same structures. It will, however,
create different IDs than are used in the cookbook recipe.

```java

```

### Method Two

The second method will create a manifest with the same IDs that the cookbook recipe uses. It involves setting all the IDs manually, which involves a little more code.

```java

```

{{% sandbox %}}
