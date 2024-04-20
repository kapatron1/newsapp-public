package com.example.newsapp

import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.retrofit.NewsRetrofitAPIInterface
import com.example.newsapp.utils.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class NewsRepositoryTest {

    @Test
    fun testGetArticlesForSourcesSuccess() = runBlocking {
        // Create a mock NewsRetrofitAPIInterface
        val mockNewsService = Mockito.mock(NewsRetrofitAPIInterface::class.java)

        // Create a mock Response
        var mockResponse: Response<ArticlesResponse> = Response.success(200,ArticlesResponse(mutableListOf(), "ok"  ,1))

        // When getArticlesForSources is called on the mock, return the mock response
        Mockito.`when`(mockNewsService.getArticlesForSources("bbc-news", Constants.API_KEY)).thenReturn(mockResponse)

        // Initialize the Repository with the mock service
        val repository = NewsRepository(mockNewsService)

        // Call the method on the Repository
        val actualResponse = repository.getArticlesForSources("bbc-news", Constants.API_KEY)

        // Assert that the actual response matches the mock response
        assertEquals(mockResponse.body()?.status ?: "nok", actualResponse.body()?.status ?: "nok")
        assertEquals(mockResponse.code(), actualResponse.code())
    }


    //TODO do tests for Empty Response
    @Test
    fun testGetArticlesForSourcesErrorResponseWrongKey() = runBlocking {
        // Create a mock NewsRetrofitAPIInterface
        val mockNewsService = Mockito.mock(NewsRetrofitAPIInterface::class.java)

        // Create an error Response
        var errorResponse: Response<ArticlesResponse> = Response.error(401,
            okhttp3.ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                "{\"status\":\"error\",\"code\":\"apiKeyInvalid\",\"message\":\"Your API key is invalid or incorrect.\"}"
            )
        )

        // When getArticlesForSources is called on the mock, return the error response
        Mockito.`when`(mockNewsService.getArticlesForSources("", "wrong-key")).thenReturn(errorResponse)

        // Initialize the Repository with the mock service
        val repository = NewsRepository(mockNewsService)

        // Call the method on the Repository
        val actualResponse = repository.getArticlesForSources("", "wrong-key")

        // Assert that the actual response matches the error response
        assertEquals(errorResponse.code(), actualResponse.code())
    }

    @Test
    fun testGetArticlesForSourcesErrorResponseParametersMissing() = runBlocking {
        // Create a mock NewsRetrofitAPIInterface
        val mockNewsService = Mockito.mock(NewsRetrofitAPIInterface::class.java)

        // Create an error Response
        var errorResponse: Response<ArticlesResponse> = Response.error(400,
            okhttp3.ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                "{\"status\":\"error\",\"code\":\"parametersMissing\",\"message\":\"Required parameters are missing. Please set any of the following parameters and try again: sources, q, language, country, category.\"}"
            )
        )

        // When getArticlesForSources is called on the mock, return the error response
        Mockito.`when`(mockNewsService.getArticlesForSources("", Constants.API_KEY)).thenReturn(errorResponse)

        // Initialize the Repository with the mock service
        val repository = NewsRepository(mockNewsService)

        // Call the method on the Repository
        val actualResponse = repository.getArticlesForSources("", Constants.API_KEY)

        // Assert that the actual response matches the error response
        assertEquals(errorResponse.code(), actualResponse.code())
    }

}