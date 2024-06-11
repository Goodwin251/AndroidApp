package com.example.pidrozdilua.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pidrozdilua.ArticleActivity
import com.example.pidrozdilua.R
import com.example.pidrozdilua.models.Link

class ArticleAdapter(private val articles: List<Link>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleTitle: TextView = itemView.findViewById(R.id.article_title)
        val articleThumbnail: ImageView = itemView.findViewById(R.id.article_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.articleTitle.text = article.title
        Glide.with(holder.itemView.context).load(article.imageUrl).into(holder.articleThumbnail)

        // Set the click listener to navigate to ArticleActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra("article_title", article.title)
                putExtra("article_url", article.url)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = articles.size
}
