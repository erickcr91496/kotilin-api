package com.example.testkotilin

import java.time.format.DateTimeFormatter

data class ApiResponse(
    val data: List<Article>,

)


data class Article(
    val type: String,
    val id: String,
    val attributes: ArticleAttributes,
)

data class ArticleAttributes(
    val drupal_internal__nid: Int,
    val title: String,
    val body : String
    // ... otros atributos
)



data class Self(
    val href: String
)
