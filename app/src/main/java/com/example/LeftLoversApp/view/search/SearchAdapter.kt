package com.example.LeftLoversApp.view.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.localData.Food
import com.example.LeftLoversApp.model.SearchItem
import com.example.LeftLoversApp.view.detail.DetailActivity
//import com.example.LeftLoversApp.view.detail.DetailActivity
import java.text.NumberFormat
import java.util.Locale


class SearchAdapter(private val listSearch : List<SearchItem>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_story, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val (id, createdAt, updatedAt, name, pictureUrl, price, merchantId, category, activeFood) = listStory[position]
        val currentItem = listSearch[position]
        Glide.with(viewHolder.itemView.context).load(currentItem.profilePictureUrl).into(viewHolder.tvImg)
        viewHolder.tvTitle.text = currentItem.name
//        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID")) // Membuat instance NumberFormat dengan locale Indonesia
//        currencyFormat.maximumFractionDigits = 0 // Mengatur jumlah digit desimal menjadi 0
//        val formattedPrice = currencyFormat.format(price) // Memformat nilai price menjadi format mata uang Rupiah

//        viewHolder.tvDesc.text = "Harga:  $formattedPrice"

        viewHolder.itemView.setOnClickListener() {
            val intentUserDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentUserDetail.putExtra("name", listSearch[viewHolder.adapterPosition].name)
            intentUserDetail.putExtra("merchantId", listSearch[viewHolder.adapterPosition].id)
            intentUserDetail.putExtra("pictureUrl", listSearch[viewHolder.adapterPosition].profilePictureUrl)
            viewHolder.itemView.context.startActivity(intentUserDetail)
        }

    }
    override fun getItemCount() = listSearch.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvImg: ImageView = itemView.findViewById(R.id.img_story)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_item_desc)
    }
}

