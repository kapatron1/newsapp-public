package com.example.newsapp.activities

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.utils.Constants

class ArticleDetailsActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        title = getString(R.string.article_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.EXTRA_ARTICLE, Article::class.java)
        } else {
            intent.getParcelableExtra(Constants.EXTRA_ARTICLE)
        }

        if (article != null) {
            Glide.with(this).load(article.urlToImage)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into( findViewById(R.id.article_image))
        }

        if(article != null) {
            findViewById<TextView>(R.id.article_title).text = article.title
            findViewById<TextView>(R.id.article_description).text = article.description
            //TODO implement a request to get the FULL content of the article
            findViewById<TextView>(R.id.article_content).text = article.content
        }
    }
}