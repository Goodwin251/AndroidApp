package com.example.pidrozdilua.wikiaip

data class WikipediaResponse(val query: Query)
data class Query(val pages: Map<String, Page>)
data class Page(val title: String, val extract: String)
data class WikipediaArticleResponse(
    val parse: Parse
)

data class Parse(
    val title: String,
    val text: Text
)

data class Text(
    val t: String
)
