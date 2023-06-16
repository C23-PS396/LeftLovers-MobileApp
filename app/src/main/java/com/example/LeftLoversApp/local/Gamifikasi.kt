package com.example.LeftLoversApp.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leaderboard_table")
data class Gamifikasi(
    @field:ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "username")
    val username: String,

    @field:ColumnInfo(name = "totalprice")
    val totalprice: Double

)