package com.example.LeftLoversApp.view.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.model.HistoryItem
import com.example.LeftLoversApp.view.detail.DetailActivity
import com.example.LeftLoversApp.view.rating.RatingActivity
import com.example.LeftLoversApp.view.review.ReviewFragment

class HistoryAdapter(private var listHistory: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = listHistory[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, RatingActivity::class.java)
            intent.putExtra("id", listHistory[holder.adapterPosition].id)
            intent.putExtra("status", listHistory[holder.adapterPosition].status)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setHistoryItems(historyItems: List<HistoryItem>) {
        listHistory = historyItems
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return listHistory.size
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaTokoTextView: TextView = itemView.findViewById(R.id.namaToko)
        private val totalBiayaTextView: TextView = itemView.findViewById(R.id.totalBiaya)
        private val statusTextView: TextView = itemView.findViewById(R.id.status)

        fun bind(item: HistoryItem) {
            namaTokoTextView.text = item.merchantId
            totalBiayaTextView.text = item.totalprice.toString()
            statusTextView.text = "Status: ${item.status}"
        }
    }
}