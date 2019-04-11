package com.example.ufanews.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ufanews.CropSquareTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(news: News) {
        itemView.tv_title_news.text = news.title
        itemView.tv_desc_news.text = news.description
        Picasso.with(itemView.context)
            .load(news.imageLink)
            .transform(CropSquareTransformation())
            .into(itemView.img_news)
    }

}
