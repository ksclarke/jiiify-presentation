+++
title = "Cookbook Recipes"
weight = 1

[recipes]
  fileLink = "content/recipes.csv"
  columnTitles = ["Cookbook Recipe", "Code Example", "Implementation Status", "Notes (implementation and otherwise)"]
  title = "Cookbook Recipes"
  table = true
  baseChartOn = 3
+++

## IIIF Cookbook Recipes

The [IIIF community](https://iiif.io/community/) has a wonderful effort underway to provide "recipes" illustrating the many ways in which the [IIIF 
Presentation](https://iiif.io/api/presentation/3.0/) specification can be used. There is a IIIF Cookbook [website](https://iiif.io/api/cookbook/index.html), where recipes can 
be viewed, and a GitHub [repository](https://github.com/IIIF/cookbook-recipes) from which that website is generated.

In addition to being a great way to learn about the specification, the IIIF Cookbook provides manifests and collection documents that software libraries, like Jiiify 
Presentation, can use as test fixtures.

This page documents Jiiify Presentation's use of the Cookbook recipes to express IIIF specification compliance. It also details which recipes are currently used in 
Jiiify Presentation tests: including which ones are supported and which ones are not.

This page will continue to be updated as more recipes are added to the cookbook site.

## Implementation Statuses

This section lists the cookbook recipes, with links to their cookbook pages. It also includes the status of each recipe's implementation (i.e., whether 
jiiify-presentation fully implements the recipe) and any relevant notes about the implementation. In addition, an illustrative code example is provided for each recipe.

{{< tip >}} This recipe list supports filtered viewing (e.g. `image` returns recipes with the word image in their names; `red circle` returns recipes that are not fully 
implemented and `green check` returns those that are). {{< /tip >}}

<br />

{{< recipes-chart "recipes" "table" >}}

