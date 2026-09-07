package com.example.model

enum class MediaCategory {
    ALL,
    MOVIES,
    TV_SERIES,
    LIVE_TV,
    SHORTS,
    HOTSTAR_SPECIALS,
    SPORTS
}

data class CastMember(
    val name: String,
    val role: String,
    val avatarUrl: String = ""
)

data class Episode(
    val id: String,
    val episodeNumber: Int,
    val seasonNumber: Int = 1,
    val title: String,
    val overview: String,
    val duration: String,
    val thumbnailUrl: String,
    val videoUrl: String
)

data class Season(
    val seasonNumber: Int,
    val name: String,
    val episodes: List<Episode>
)

data class MediaItem(
    val id: String,
    val title: String,
    val tagline: String,
    val description: String,
    val thumbnailUrl: String,
    val bannerUrl: String,
    val videoUrl: String,
    val category: MediaCategory,
    val genres: List<String>,
    val durationMinutes: Int,
    val releaseYear: Int,
    val ageRating: String = "U/A 16+",
    val imdbRating: Double = 8.5,
    val matchPercentage: Int = 95,
    val isTop10: Boolean = false,
    val top10Rank: Int? = null,
    val isLive: Boolean = false,
    val liveViewerCount: String? = null,
    val channelName: String? = null,
    val cast: List<CastMember> = emptyList(),
    val seasons: List<Season>? = null
)

data class ShortClip(
    val id: String,
    val title: String,
    val creatorName: String,
    val creatorHandle: String,
    val creatorAvatarUrl: String = "",
    val videoUrl: String,
    var likesCount: Int,
    var commentsCount: Int,
    var isLiked: Boolean = false,
    var isBookmarked: Boolean = false,
    val musicTrack: String,
    val tags: List<String>
)
