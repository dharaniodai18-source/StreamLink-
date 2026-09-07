package com.example.data.repository

import com.example.data.local.DownloadEntity
import com.example.data.local.MediaDao
import com.example.data.local.WatchHistoryEntity
import com.example.data.local.WatchlistEntity
import com.example.model.MediaItem
import kotlinx.coroutines.flow.Flow

class MediaRepository(private val mediaDao: MediaDao) {

    fun getWatchlist(): Flow<List<WatchlistEntity>> = mediaDao.getWatchlist()

    fun isInWatchlist(id: String): Flow<Boolean> = mediaDao.isInWatchlist(id)

    suspend fun addToWatchlist(item: MediaItem) {
        mediaDao.addToWatchlist(
            WatchlistEntity(
                id = item.id,
                title = item.title,
                thumbnailUrl = item.thumbnailUrl,
                bannerUrl = item.bannerUrl,
                videoUrl = item.videoUrl,
                category = item.category.name,
                genres = item.genres.joinToString(", "),
                rating = item.imdbRating,
                releaseYear = item.releaseYear
            )
        )
    }

    suspend fun removeFromWatchlist(id: String) {
        mediaDao.removeFromWatchlist(id)
    }

    fun getWatchHistory(): Flow<List<WatchHistoryEntity>> = mediaDao.getWatchHistory()

    suspend fun updateWatchHistory(item: MediaItem, positionMs: Long, durationMs: Long) {
        if (positionMs <= 0 || durationMs <= 0) return
        mediaDao.upsertWatchHistory(
            WatchHistoryEntity(
                id = item.id,
                title = item.title,
                thumbnailUrl = item.thumbnailUrl,
                videoUrl = item.videoUrl,
                currentPositionMs = positionMs,
                durationMs = durationMs,
                lastWatchedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun removeFromHistory(id: String) {
        mediaDao.removeFromHistory(id)
    }

    suspend fun clearHistory() {
        mediaDao.clearHistory()
    }

    fun getDownloads(): Flow<List<DownloadEntity>> = mediaDao.getDownloads()

    suspend fun addDownload(item: MediaItem, quality: String = "1080p Ultra HD") {
        mediaDao.upsertDownload(
            DownloadEntity(
                id = item.id,
                title = item.title,
                thumbnailUrl = item.thumbnailUrl,
                videoUrl = item.videoUrl,
                quality = quality,
                sizeMb = if (quality.contains("1080")) 680 else 320,
                progressPercent = 100,
                isCompleted = true,
                downloadedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun removeDownload(id: String) {
        mediaDao.removeDownload(id)
    }
}
