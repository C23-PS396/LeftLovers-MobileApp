package com.example.LeftLoversApp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.local.Story
import com.example.LeftLoversApp.view.detail.DetailActivity


class StoryAdapter(private val listStory: List<Story>) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_story, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (id, nama, desc, photo, createdAt, lat, lon) = listStory[position]
        Glide.with(viewHolder.itemView.context).load(photo).into(viewHolder.tvImg)
        viewHolder.tvTitle.text = nama
        viewHolder.tvDesc.text = desc

        viewHolder.itemView.setOnClickListener() {
            val intentUserDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentUserDetail.putExtra("name", listStory[viewHolder.adapterPosition].name)
            intentUserDetail.putExtra("desc", listStory[viewHolder.adapterPosition].description)
            intentUserDetail.putExtra("img", listStory[viewHolder.adapterPosition].photoUrl)
            viewHolder.itemView.context.startActivity(intentUserDetail)
        }

    }
    override fun getItemCount() = listStory.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvImg: ImageView = itemView.findViewById(R.id.img_story)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_item_desc)
    }
}