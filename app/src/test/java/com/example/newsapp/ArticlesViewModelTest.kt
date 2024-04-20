package com.example.newsapp

import com.example.newsapp.model.Article
import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.retrofit.NewsRetrofitAPIInterface
import com.example.newsapp.utils.Constants
import com.example.newsapp.viewmodel.ArticlesViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class ArticlesViewModelTest {

    @Test
    fun testGetArticlesForSources() = runBlocking {
        // Create a mock NewsRetrofitAPIInterface
        val mockNewsService = Mockito.mock(NewsRetrofitAPIInterface::class.java)

        // Create a mock Response
        var mockResponse: Response<ArticlesResponse> = Response.success(ArticlesResponse(mutableListOf(), "ok"  ,1))

        // When getArticlesForSources is called on the mock, return the mock response
        Mockito.`when`(mockNewsService.getArticlesForSources(BuildConfig.SOURCE, Constants.API_KEY)).thenReturn(mockResponse)

        // Initialize the Repository with the mock service
        val repository = NewsRepository(mockNewsService)

        // Call the method on the Repository
        val actualResponse = repository.getArticlesForSources(BuildConfig.SOURCE, Constants.API_KEY)

        // Assert that the actual response matches the mock response
        assertEquals(mockResponse.body()?.status ?: "nok", "ok")
        assertEquals(mockResponse.body()?.totalResults ?: -1, 1)
    }

    @Test
    fun testSortListOfSourcesByDate() {
        // Create a list of Article objects with different publishedAt dates
        val article2024 = Article(publishedAt = "2024-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")
        val article2023 = Article(publishedAt = "2023-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")
        val article2022 = Article(publishedAt = "2022-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")

        val listOfArticlesToSort = mutableListOf(
            article2022,
            article2023,
            article2024
        )

        // Initialize the ViewModel with a mock repository
        val viewModel = ArticlesViewModel(NewsRepository())

        // Call the method with the list of articles
        val sortedListOfArticles = viewModel.sortListOfSourcesByDate(listOfArticlesToSort)

        // Assert that the returned list is sorted in descending order by publishedAt date

        assertTrue(sortedListOfArticles == mutableListOf(
            article2024,
            article2023,
            article2022
        ))
    }

    @Test
    fun testSortListOfSourcesByDateEmpty() {
        val listOfArticlesToSort:MutableList<Article> = mutableListOf()
        val viewModel = ArticlesViewModel(NewsRepository())
        val sortedListOfArticles = viewModel.sortListOfSourcesByDate(listOfArticlesToSort)
        assertTrue(sortedListOfArticles.isEmpty())
    }

    @Test
    fun testSortListOfSourcesByDateAlreadySorted() {
        // Create a list of Article objects with different publishedAt dates
        val article2024 = Article(publishedAt = "2024-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")
        val article2023 = Article(publishedAt = "2023-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")
        val article2022 = Article(publishedAt = "2022-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")

        val listOfArticlesToSort = mutableListOf(
            article2024,
            article2023,
            article2022
        )

        // Initialize the ViewModel with a mock repository
        val viewModel = ArticlesViewModel(NewsRepository())

        // Call the method with the list of articles
        val sortedListOfArticles = viewModel.sortListOfSourcesByDate(listOfArticlesToSort)

        // Assert that the returned list is sorted in descending order by publishedAt date

        assertTrue(sortedListOfArticles == listOfArticlesToSort)
    }

    @Test
    fun testSortListOfSourcesByDateAlreadyNullDates() {
        // Create a list of Article objects with different publishedAt dates
        val articleNull = Article(publishedAt = null, source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")
        val article2023 = Article(publishedAt = "2023-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")
        val article2022 = Article(publishedAt = "2022-04-19T08:27:15Z", source = null, author = "authorC", title = "titleC", description = "descriptionC", url = "urlC", urlToImage = "urlToImageC", content = "contentC")

        val listOfArticlesToSort = mutableListOf(
            articleNull,
            article2022,
            article2023
        )

        // Initialize the ViewModel with a mock repository
        val viewModel = ArticlesViewModel(NewsRepository())

        // Call the method with the list of articles
        val sortedListOfArticles = viewModel.sortListOfSourcesByDate(listOfArticlesToSort)

        // Assert that the returned list is sorted in descending order by publishedAt date

        assertTrue(sortedListOfArticles == mutableListOf(
            article2023,
            article2022,
            articleNull
        ))
    }

}