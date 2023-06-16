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
import com.example.LeftLoversApp.local.RecommendationItem
import com.example.LeftLoversApp.local.RecommendationResponse
import com.example.LeftLoversApp.localData.Food
import com.example.LeftLoversApp.model.HistoryItem
import com.example.LeftLoversApp.view.detail.DetailActivity
import com.example.LeftLoversApp.view.rating.RatingActivity
import com.example.LeftLoversApp.view.recommendation.RecommendationActivity
import java.text.NumberFormat
import java.util.Locale


class RecommendationMerchAdapter(private var listRecom: List<RecommendationItem>) :
    RecyclerView.Adapter<RecommendationMerchAdapter.RecomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_toko, parent, false)
        return RecomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecomViewHolder, position: Int) {
        val currentItem = listRecom[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("merchantId", listRecom[holder.adapterPosition].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setRecommendationItems(recomItems: List<RecommendationItem>) {
        listRecom = recomItems
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return listRecom.size
    }

    inner class RecomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaTokoView: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvImg: ImageView = itemView.findViewById(R.id.img_story)
        private val regencyTextView: TextView = itemView.findViewById(R.id.tv_item_regency)
        private val fullLockTextView: TextView = itemView.findViewById(R.id.tv_item_fullloc)
//        private val ratingTextView: TextView = itemView.findViewById(R.id.tv_item_rating)

        fun bind(item: RecommendationItem) {
            namaTokoView.text = item.name
            regencyTextView.text = item.location.regency
            fullLockTextView.text = item.location.fullLocation
            Glide.with(itemView.context)
                .load(item.profilePictureUrl) // Ubah 'pictureUrl' sesuai dengan properti yang sesuai pada objek RecommendationItem
                .into(tvImg)
            // ratingTextView.text = item.rating.avg.rating.toString()
        }

    }
}