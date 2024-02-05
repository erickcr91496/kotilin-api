package com.example.testkotilin

data class Article(
    val type: String,
    val id: String,
    val attributes: ArticleAttributes
)

data class ArticleAttributes(
    val drupal_internal__nid: Int,
    val title: String,
    val body: String
)