package com.example.newsapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsRepositoryInstrumentedTest {
    @Test
    fun testGetArticlesForSources() = runBlocking {
        // Initialize the Repository with the real service
        val repository = NewsRepository()

        // Call the method on the Repository
        val actualResponse = repository.getArticlesForSources("bbc-news", "74db913c361e4749a6faecbe85c0dfd4")

        // Assert that the actual response is not null and the status is "ok"
        assertNotNull(actualResponse)
        assertEquals("ok", actualResponse.body()?.status)
    }
}