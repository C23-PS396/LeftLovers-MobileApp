package com.example.LeftLoversApp.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.model.HistoryItem

class HistoryAdapter(private var listHistory: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = listHistory[position]
        holder.bind(currentItem)
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