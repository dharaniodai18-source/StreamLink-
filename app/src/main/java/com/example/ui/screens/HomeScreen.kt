package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.data.MockMediaCatalog
import com.example.model.MediaCategory
import com.example.model.MediaItem
import com.example.ui.components.ContinueWatchingCard
import com.example.ui.components.HeroBanner
import com.example.ui.components.LiveTvCard
import com.example.ui.components.MediaCard
import com.example.ui.components.NetStreamHeader
import com.example.ui.components.Top10Card
import com.example.ui.theme.NetDarkBackground
import com.example.ui.theme.NetRed
import com.example.ui.theme.NetTextPrimary
import com.example.ui.theme.NetTextSecondary
import com.example.viewmodel.StreamViewModel

@Composable
fun HomeScreen(
    viewModel: StreamViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToShorts: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val watchHistory by viewModel.watchHistory.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()

    val categories = listOf(
        MediaCategory.ALL to "All",
        MediaCategory.MOVIES to "Movies",
        MediaCategory.TV_SERIES to "TV Shows",
        MediaCategory.LIVE_TV to "Live TV",
        MediaCategory.HOTSTAR_SPECIALS to "Hotstar Specials",
        MediaCategory.SPORTS to "Sports"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NetDarkBackground)
    ) {
        // App Header
        NetStreamHeader(
            onSearchClick = onNavigateToSearch,
            onProfileClick = onNavigateToLibrary
        )

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { (cat, label) ->
                val isSelected = selectedCategory == cat
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = if (isSelected) NetRed else Color.White.copy(alpha = 0.12f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { viewModel.selectCategory(cat) }
                        .testTag("category_chip_${cat.name}")
                ) {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Main Scrollable Feed
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_feed_list"),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            // 1. Featured Hero Banner
            item {
                HeroBanner(
                    featuredList = MockMediaCatalog.featuredItems,
                    isInWatchlist = { id -> viewModel.isInWatchlist(id) },
                    onPlayClick = { item -> viewModel.playMedia(item) },
                    onToggleWatchlist = { item -> viewModel.toggleWatchlist(item) },
                    onDetailsClick = { item -> viewModel.showMediaDetail(item) }
                )
            }

            // 2. Continue Watching (if items exist)
            if (watchHistory.isNotEmpty()) {
                item {
                    SectionHeader(title = "Continue Watching for You")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(watchHistory, key = { it.id }) { hist ->
                            ContinueWatchingCard(
                                historyItem = hist,
                                onClick = {
                                    val media = MockMediaCatalog.findById(hist.id)
                                    if (media != null) viewModel.playMedia(media)
                                },
                                onRemove = { viewModel.removeHistoryItem(hist.id) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }

            // 3. Top 10 Movies & Shows in India & Global
            if (selectedCategory == MediaCategory.ALL || selectedCategory == MediaCategory.MOVIES || selectedCategory == MediaCategory.TV_SERIES) {
                item {
                    SectionHeader(title = "Top 10 Today")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val top10 = MockMediaCatalog.top10MoviesAndShows.take(10)
                        items(top10.size) { index ->
                            val item = top10[index]
                            Top10Card(
                                rank = index + 1,
                                item = item,
                                onClick = { viewModel.showMediaDetail(item) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // 4. Live TV & Sports (Hotstar / YouTube Live Channels)
            if (selectedCategory == MediaCategory.ALL || selectedCategory == MediaCategory.LIVE_TV || selectedCategory == MediaCategory.SPORTS) {
                item {
                    SectionHeader(title = "🔴 Live TV & Sports Matches")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(MockMediaCatalog.liveChannels, key = { it.id }) { liveItem ->
                            LiveTvCard(
                                item = liveItem,
                                onClick = { viewModel.playMedia(liveItem) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // 5. Trending Web Series (Binge-Worthy)
            if (selectedCategory == MediaCategory.ALL || selectedCategory == MediaCategory.TV_SERIES || selectedCategory == MediaCategory.HOTSTAR_SPECIALS) {
                item {
                    SectionHeader(title = "Trending Web Series & Specials")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(MockMediaCatalog.seriesWithEpisodes, key = { it.id }) { series ->
                            MediaCard(
                                item = series,
                                onClick = { viewModel.showMediaDetail(series) },
                                cardWidth = 140
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // 6. Blockbuster Movies
            if (selectedCategory == MediaCategory.ALL || selectedCategory == MediaCategory.MOVIES) {
                item {
                    SectionHeader(title = "Blockbuster Movies")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val movies = MockMediaCatalog.allMedia.filter { it.category == MediaCategory.MOVIES }
                        items(movies, key = { it.id }) { movie ->
                            MediaCard(
                                item = movie,
                                onClick = { viewModel.showMediaDetail(movie) },
                                cardWidth = 135
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // 7. YouTube Shorts & Viral Highlights Preview
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "⚡ Shorts & Viral Clips",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "See All",
                        color = NetRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onNavigateToShorts() }
                    )
                }
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(MockMediaCatalog.shortClips, key = { it.id }) { clip ->
                        Surface(
                            modifier = Modifier
                                .width(120.dp)
                                .height(190.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onNavigateToShorts() },
                            color = Color(0xFF1E1A2B)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AsyncImage(
                                    model = clip.creatorAvatarUrl,
                                    contentDescription = clip.title,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.45f))
                                )
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = clip.title,
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 3
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "❤️ ${(clip.likesCount / 1000)}k",
                                        color = NetRed,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = NetTextPrimary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
