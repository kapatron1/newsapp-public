package com.example.newsapp.repository

import com.example.newsapp.retrofit.NewsRetrofitAPIInterface
import com.example.newsapp.retrofit.NewsRetrofitInstance

class NewsRepository {
    constructor()
    constructor(p0: Any)

    private var newsService: NewsRetrofitAPIInterface = NewsRetrofitInstance.api
    suspend fun getArticlesForSources(sources: String, apiKey: String)= newsService.getArticlesForSources(sources, apiKey)
}