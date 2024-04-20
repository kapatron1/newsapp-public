package com.example.newsapp.activities

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Constants
import com.example.newsapp.viewmodel.ArticlesViewModel
import java.util.Locale

class ArticlesListActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_list)

        initViewModel()
        getArticles()
        initObservers()

        title =  Constants.source.uppercase(Locale.getDefault())
    }

    private fun initViewModel() {
        viewModel = ArticlesViewModel(
            NewsRepository()
        )
    }

    private fun getArticles() {
        viewModel.getArticles()
    }

    private fun initObservers() {
        viewModel.articlesList.observe(this) {
            val adapter = ArticleAdapter(this,it)
            findViewById<ListView>(R.id.lvArticles).adapter = adapter
        }
    }
}