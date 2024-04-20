package com.example.newsapp.retrofit

import com.example.newsapp.model.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRetrofitAPIInterface {

    @GET("top-headlines")
    suspend fun getArticlesForSources(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Response<ArticlesResponse>

}