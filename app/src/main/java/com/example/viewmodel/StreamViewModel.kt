package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MockMediaCatalog
import com.example.data.local.DownloadEntity
import com.example.data.local.NetStreamDatabase
import com.example.data.local.WatchHistoryEntity
import com.example.data.local.WatchlistEntity
import com.example.data.repository.MediaRepository
import com.example.model.Episode
import com.example.model.MediaCategory
import com.example.model.MediaItem
import com.example.model.ShortClip
import com.example.player.VideoPlayerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StreamViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MediaRepository

    val watchlist: StateFlow<List<WatchlistEntity>>
    val watchHistory: StateFlow<List<WatchHistoryEntity>>
    val downloads: StateFlow<List<DownloadEntity>>

    private val _playerState = MutableStateFlow(VideoPlayerUiState(isPlaying = false, isControlsVisible = false))
    val playerState: StateFlow<VideoPlayerUiState> = _playerState.asStateFlow()

    private val _selectedCategory = MutableStateFlow(MediaCategory.ALL)
    val selectedCategory: StateFlow<MediaCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenreFilter = MutableStateFlow("All")
    val selectedGenreFilter: StateFlow<String> = _selectedGenreFilter.asStateFlow()

    private val _shorts = MutableStateFlow(MockMediaCatalog.shortClips)
    val shorts: StateFlow<List<ShortClip>> = _shorts.asStateFlow()

    private val _selectedMediaDetail = MutableStateFlow<MediaItem?>(null)
    val selectedMediaDetail: StateFlow<MediaItem?> = _selectedMediaDetail.asStateFlow()

    init {
        val database = NetStreamDatabase.getDatabase(application)
        repository = MediaRepository(database.mediaDao())

        watchlist = repository.getWatchlist().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        watchHistory = repository.getWatchHistory().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        downloads = repository.getDownloads().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        // Seed sample continue-watching items if history is empty
        viewModelScope.launch {
            repository.updateWatchHistory(
                MockMediaCatalog.featuredItems[0],
                positionMs = 38 * 60 * 1000L,
                durationMs = 142 * 60 * 1000L
            )
            repository.updateWatchHistory(
                MockMediaCatalog.top10MoviesAndShows[3],
                positionMs = 15 * 60 * 1000L,
                durationMs = 95 * 60 * 1000L
            )
            // Seed sample downloaded title
            repository.addDownload(
                MockMediaCatalog.top10MoviesAndShows[1],
                quality = "1080p Ultra HD"
            )
        }
    }

    fun selectCategory(category: MediaCategory) {
        _selectedCategory.value = category
    }

    fun selectGenreFilter(genre: String) {
        _selectedGenreFilter.value = genre
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun showMediaDetail(item: MediaItem) {
        _selectedMediaDetail.value = item
    }

    fun closeMediaDetail() {
        _selectedMediaDetail.value = null
    }

    fun playMedia(item: MediaItem, episode: Episode? = null) {
        val targetUrl = episode?.videoUrl ?: item.videoUrl
        _playerState.value = _playerState.value.copy(
            currentMedia = item,
            currentEpisode = episode,
            isPlaying = true,
            isBuffering = true,
            isControlsVisible = true,
            isMiniPlayer = false,
            showNextEpisodeCountdown = false,
            currentPositionMs = 0L
        )
    }

    fun updatePlayerState(newState: VideoPlayerUiState) {
        _playerState.value = newState
    }

    fun togglePlayPause() {
        _playerState.value = _playerState.value.copy(
            isPlaying = !_playerState.value.isPlaying
        )
    }

    fun minimizePlayer() {
        _playerState.value = _playerState.value.copy(
            isMiniPlayer = true,
            isFullscreen = false,
            isControlsVisible = false
        )
    }

    fun expandMiniPlayer() {
        _playerState.value = _playerState.value.copy(
            isMiniPlayer = false,
            isControlsVisible = true
        )
    }

    fun closePlayer() {
        _playerState.value = _playerState.value.copy(
            currentMedia = null,
            currentEpisode = null,
            isPlaying = false,
            isMiniPlayer = false,
            isControlsVisible = false
        )
    }

    fun playNextEpisode() {
        val currentMedia = _playerState.value.currentMedia ?: return
        val seasons = currentMedia.seasons ?: return
        val currentEp = _playerState.value.currentEpisode
        val allEpisodes = seasons.flatMap { it.episodes }

        if (currentEp == null) {
            allEpisodes.firstOrNull()?.let { playMedia(currentMedia, it) }
        } else {
            val currentIndex = allEpisodes.indexOfFirst { it.id == currentEp.id }
            if (currentIndex in 0 until allEpisodes.size - 1) {
                val nextEp = allEpisodes[currentIndex + 1]
                playMedia(currentMedia, nextEp)
            }
        }
    }

    fun toggleWatchlist(item: MediaItem) {
        viewModelScope.launch {
            val isCurrentIn = watchlist.value.any { it.id == item.id }
            if (isCurrentIn) {
                repository.removeFromWatchlist(item.id)
            } else {
                repository.addToWatchlist(item)
            }
        }
    }

    fun isInWatchlist(id: String): Boolean {
        return watchlist.value.any { it.id == id }
    }

    fun onProgressUpdate(positionMs: Long, durationMs: Long) {
        val media = _playerState.value.currentMedia ?: return
        viewModelScope.launch {
            repository.updateWatchHistory(media, positionMs, durationMs)
        }
    }

    fun removeHistoryItem(id: String) {
        viewModelScope.launch {
            repository.removeFromHistory(id)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    fun downloadMedia(item: MediaItem, quality: String = "1080p Ultra HD") {
        viewModelScope.launch {
            repository.addDownload(item, quality)
        }
    }

    fun removeDownload(id: String) {
        viewModelScope.launch {
            repository.removeDownload(id)
        }
    }

    fun toggleLikeShort(id: String) {
        _shorts.value = _shorts.value.map { clip ->
            if (clip.id == id) {
                val newLiked = !clip.isLiked
                clip.copy(
                    isLiked = newLiked,
                    likesCount = if (newLiked) clip.likesCount + 1 else clip.likesCount - 1
                )
            } else clip
        }
    }

    fun toggleBookmarkShort(id: String) {
        _shorts.value = _shorts.value.map { clip ->
            if (clip.id == id) {
                clip.copy(isBookmarked = !clip.isBookmarked)
            } else clip
        }
    }

    fun addCommentToShort(id: String) {
        _shorts.value = _shorts.value.map { clip ->
            if (clip.id == id) {
                clip.copy(commentsCount = clip.commentsCount + 1)
            } else clip
        }
    }
}
