package com.example.testkotilin


data class DrupalData (
    val data: List<Article>
)

data class Article(
    val type: String,
    val id: String,
    val attributes: ArticleAttributes
)

data class ArticleAttributes(
    val title: String,
    //val body: Body
)

data class Body (val processed: String , val summary: String)
