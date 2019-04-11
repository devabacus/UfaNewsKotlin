package com.example.ufanews.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ufanews.R

class NewsAdapter(): RecyclerView.Adapter<NewsViewHolder>(){

    private val listNews = mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount() = listNews.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    fun setList(list: MutableList<News>) {
        this.listNews.clear()
        this.listNews.addAll(list)
        notifyDataSetChanged()
    }


}