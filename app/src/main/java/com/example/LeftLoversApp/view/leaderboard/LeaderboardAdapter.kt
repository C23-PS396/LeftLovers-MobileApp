package com.example.LeftLoversApp.view.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.model.GamificationResponseItem
import kotlin.math.min


class LeaderboardAdapter(private var leaderboardList: List<GamificationResponseItem>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return LeaderboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val currentItem = leaderboardList[position]
        holder.bind(currentItem, position)
    }

    fun updateData(newList: List<GamificationResponseItem>) {
        leaderboardList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return min(leaderboardList.size, 10)
    }

    inner class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rankingTextView: TextView = itemView.findViewById(R.id.ranking)
        private val usernameTextView: TextView = itemView.findViewById(R.id.username)
        private val totalPointTextView: TextView = itemView.findViewById(R.id.point)

        fun bind(item: GamificationResponseItem, position: Int) {
            rankingTextView.text = (position + 1).toString()
            usernameTextView.text = item.username
            totalPointTextView.text = item.totalPoint?.totalprice.toString()
        }
    }
}





