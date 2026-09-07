package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnailUrl: String,
    val bannerUrl: String,
    val videoUrl: String,
    val category: String,
    val genres: String,
    val rating: Double,
    val releaseYear: Int,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val currentPositionMs: Long,
    val durationMs: Long,
    val lastWatchedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val quality: String = "1080p Full HD",
    val sizeMb: Int = 450,
    val progressPercent: Int = 100,
    val isCompleted: Boolean = true,
    val downloadedAt: Long = System.currentTimeMillis()
)
