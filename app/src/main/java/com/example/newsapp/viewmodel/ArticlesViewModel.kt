package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val repository: NewsRepository
): ViewModel() {

    private val _articlesList: MutableLiveData< List<Article>> = MutableLiveData()
    val articlesList: LiveData< List<Article>> = _articlesList

    fun getArticles() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = repository.getArticlesForSources(Constants.source, Constants.API_KEY)
            val listOfArticles = response.body()!!.articles
            val sortedListOfArticles = sortListOfSourcesByDate(listOfArticles)
            _articlesList.value = sortedListOfArticles
        }
    }

    fun sortListOfSourcesByDate(listOfSources: MutableList<Article>): List<Article> {
        val array = listOfSources.toTypedArray()
        array.sortByDescending { it.publishedAt }

        return array.asList()
    }
}