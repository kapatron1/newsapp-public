package com.example.newsapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article

class ArticleAdapter (private val context: Context, private val dataSet: List<Article>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): Any {
        return dataSet.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row: View
        val holder: ArticleViewHolder

        if(convertView == null) {
            row = inflater.inflate(R.layout.article_headline_row, parent, false)
            holder = ArticleViewHolder()
            holder.headlineTitle = row.findViewById(R.id.article_headline)
            holder.headLineImage = row.findViewById(R.id.article_image)
            row.tag = holder
        } else {
            row = convertView
            holder = convertView.tag as ArticleViewHolder
        }
        holder.headlineTitle.text = dataSet[position].title

        //TODO create a default image for the articles that do not have an image
        Glide.with(context).load(dataSet[position].urlToImage)
            .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.headLineImage);


        return row
    }

    private class ArticleViewHolder {
        lateinit var headLineImage: ImageView
        lateinit var headlineTitle: TextView
    }
}


