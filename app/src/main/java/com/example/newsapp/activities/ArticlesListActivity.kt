package com.example.newsapp.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.BuildConfig
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Utils
import com.example.newsapp.viewmodel.ArticlesViewModel
import java.util.Locale

class ArticlesListActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticlesViewModel
    private lateinit var lvArticles :ListView
    private lateinit var tvNoArticles :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_list)
        title =  BuildConfig.SOURCE.uppercase(Locale.getDefault())
        lvArticles = findViewById(R.id.lvArticles)
        tvNoArticles = findViewById(R.id.tvNoArticles)

        updateListView()

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            updateListView()
            Handler().postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }
    }

    private fun updateListView(){
        if(Utils.isOnline(this)) {
            initViewModel()
            getArticles()
            initObservers()
            lvArticles.visibility = View.VISIBLE
            tvNoArticles.visibility = View.GONE
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            lvArticles.visibility = View.GONE
            tvNoArticles.visibility = View.VISIBLE
        }
    }

    private fun initViewModel() {
        viewModel = ArticlesViewModel(
            NewsRepository()
        )
    }

    private fun getArticles(){
        viewModel.getArticles()
    }

    private fun initObservers() {
        viewModel.articlesList.observe(this) {
            val adapter = ArticleAdapter(this,it)
            findViewById<ListView>(R.id.lvArticles).adapter = adapter
        }
    }
}