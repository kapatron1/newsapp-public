package com.example.newsapp.model

data class ArticlesResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)