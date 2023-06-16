package com.example.LeftLoversApp.view.history


import android.content.Context
import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.model.HistoryItem

import com.example.LeftLoversApp.view.detail.DetailActivity
import com.example.LeftLoversApp.view.rating.RatingActivity
import com.example.LeftLoversApp.view.review.ReviewFragment

import java.text.NumberFormat
import java.util.*


class HistoryAdapter(private val context: Context,
                     private var listHistory: List<HistoryItem>) :
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


        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID")) // Membuat instance NumberFormat dengan locale Indonesia
        currencyFormat.maximumFractionDigits = 0// Mengatur jumlah digit desimal menjadi 0
        val formattedPrice = currencyFormat.format(currentItem.totalprice) // Memformat nilai price menjadi format mata uang Rupiah


    }

    fun setHistoryItems(historyItems: List<HistoryItem>) {
        listHistory = historyItems
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return listHistory.size
    }

    fun convertStatusToString(status: Int): String {
        return when (status) {
            1 -> "Pending"
            2 -> "Checking Payment"
            3 -> "Payment Accrpy"
            4 -> "Accept"
            5 -> "Done"
            else -> "Fail"
        }
    }

    fun getStatusColor(status: Int): Int {
        return when (status) {
            1 -> ContextCompat.getColor(context, R.color.pending)
            2 -> ContextCompat.getColor(context, R.color.pending)
            3 -> ContextCompat.getColor(context, R.color.pending)
            4 -> ContextCompat.getColor(context, R.color.pending)
            5 -> ContextCompat.getColor(context, R.color.done)
            else -> ContextCompat.getColor(context, R.color.failed)
        }
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaTokoTextView: TextView = itemView.findViewById(R.id.namaToko)
        private val totalBiayaTextView: TextView = itemView.findViewById(R.id.totalBiaya)
        private val statusTextView: TextView = itemView.findViewById(R.id.status)

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID")) // Membuat instance NumberFormat dengan locale Indonesia


        fun bind(item: HistoryItem) {
            currencyFormat.maximumFractionDigits = 0
            namaTokoTextView.text = item.merchant?.name.toString()
            totalBiayaTextView.text = currencyFormat.format(item.totalprice)
            statusTextView.text = "Status: ${convertStatusToString(item.status!!)}"
            statusTextView.setTextColor(getStatusColor(item.status!!))
        }
    }
}