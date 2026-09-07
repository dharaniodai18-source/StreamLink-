package com.example.player

import com.example.model.Episode
import com.example.model.MediaItem

enum class AspectRatioMode(val label: String) {
    FIT("Original Fit"),
    FILL("Crop to Fill"),
    SIXTEEN_NINE("16:9 Standard")
}

data class VideoPlayerUiState(
    val currentMedia: MediaItem? = null,
    val currentEpisode: Episode? = null,
    val isPlaying: Boolean = true,
    val currentPositionMs: Long = 0L,
    val durationMs: Long = 0L,
    val isBuffering: Boolean = true,
    val isControlsVisible: Boolean = true,
    val isLocked: Boolean = false,
    val isFullscreen: Boolean = false,
    val isMiniPlayer: Boolean = false,
    val selectedQuality: String = "1080p Ultra HD",
    val playbackSpeed: Float = 1.0f,
    val selectedAudio: String = "English [Original Dolby 5.1]",
    val selectedSubtitle: String = "English [CC]",
    val aspectRatioMode: AspectRatioMode = AspectRatioMode.FIT,
    val showNextEpisodeCountdown: Boolean = false,
    val nextCountdownSeconds: Int = 5
)
