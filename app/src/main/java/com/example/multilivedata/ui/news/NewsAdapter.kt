package com.example.multilivedata.ui.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.multilivedata.databinding.ItemViewNewsBinding
import com.example.multilivedata.network.common.GlideApp

data class NewsItem(val title: String?, val imageUrl: String?, val url: String?)

class NewsViewHolder(binding: ItemViewNewsBinding)
    : RecyclerView.ViewHolder(binding.root) {
    val view: View = itemView
    val title = binding.newsTitle
    val image = binding.newsImage
}

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    var items = emptyList<NewsItem>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    var itemClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder
        = NewsViewHolder(ItemViewNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]

        val context = holder.view.context

        GlideApp.with(context)
            .load(item.imageUrl)
            .fitCenter()
            .into(holder.image)

        holder.title.text = item.title
        holder.view.setOnClickListener { itemClickListener?.invoke() }
    }
}