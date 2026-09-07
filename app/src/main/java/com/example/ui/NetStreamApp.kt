package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.player.MiniPlayerBar
import com.example.player.VideoPlayerView
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.MediaDetailScreen
import com.example.ui.screens.ShortsScreen
import com.example.ui.theme.NetDarkBackground
import com.example.ui.theme.NetDarkSurface
import com.example.ui.theme.NetRed
import com.example.ui.theme.NetTextPrimary
import com.example.ui.theme.NetTextSecondary
import com.example.viewmodel.StreamViewModel

enum class StreamNavTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val testTag: String) {
    HOME("Home", Icons.Default.Home, "nav_home"),
    SHORTS("Shorts", Icons.Default.FlashOn, "nav_shorts"),
    EXPLORE("Explore", Icons.Default.Search, "nav_explore"),
    LIBRARY("My Space", Icons.Default.VideoLibrary, "nav_library")
}

@Composable
fun NetStreamApp(
    viewModel: StreamViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(StreamNavTab.HOME) }

    val playerState by viewModel.playerState.collectAsState()
    val selectedDetail by viewModel.selectedMediaDetail.collectAsState()

    // When in full screen video playback, hide all navigation and system chrome
    if (playerState.currentMedia != null && playerState.isFullscreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            VideoPlayerView(
                state = playerState,
                onStateChange = { viewModel.updatePlayerState(it) },
                onClose = { viewModel.closePlayer() },
                onNextEpisode = { viewModel.playNextEpisode() },
                onProgressUpdate = { pos, dur -> viewModel.onProgressUpdate(pos, dur) },
                modifier = Modifier.fillMaxSize()
            )
        }
        return
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = NetDarkBackground,
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Mini Player Bar (visible when video is playing in background / minimized)
                AnimatedVisibility(
                    visible = playerState.currentMedia != null && playerState.isMiniPlayer,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    playerState.currentMedia?.let { media ->
                        val progress = if (playerState.durationMs > 0) {
                            playerState.currentPositionMs.toFloat() / playerState.durationMs.toFloat()
                        } else 0f

                        MiniPlayerBar(
                            mediaItem = media,
                            isPlaying = playerState.isPlaying,
                            progress = progress,
                            onExpand = { viewModel.expandMiniPlayer() },
                            onTogglePlay = { viewModel.togglePlayPause() },
                            onClose = { viewModel.closePlayer() }
                        )
                    }
                }

                // Bottom Navigation Bar
                NavigationBar(
                    containerColor = NetDarkSurface,
                    contentColor = NetTextPrimary,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("main_bottom_nav")
                ) {
                    StreamNavTab.values().forEach { tab ->
                        val selected = activeTab == tab
                        NavigationBarItem(
                            selected = selected,
                            onClick = { activeTab = tab },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.label
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NetRed,
                                selectedTextColor = NetRed,
                                unselectedIconColor = NetTextSecondary,
                                unselectedTextColor = NetTextSecondary,
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.testTag(tab.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Tab View
            when (activeTab) {
                StreamNavTab.HOME -> HomeScreen(
                    viewModel = viewModel,
                    onNavigateToSearch = { activeTab = StreamNavTab.EXPLORE },
                    onNavigateToLibrary = { activeTab = StreamNavTab.LIBRARY },
                    onNavigateToShorts = { activeTab = StreamNavTab.SHORTS },
                    modifier = Modifier.fillMaxSize()
                )
                StreamNavTab.SHORTS -> ShortsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
                StreamNavTab.EXPLORE -> ExploreScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
                StreamNavTab.LIBRARY -> LibraryScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Media Detail Overlay (Full page transition)
            selectedDetail?.let { detailMedia ->
                MediaDetailScreen(
                    mediaItem = detailMedia,
                    viewModel = viewModel,
                    onBack = { viewModel.closeMediaDetail() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Expanded Video Player Modal Overlay (when playing and not in mini mode)
            if (playerState.currentMedia != null && !playerState.isMiniPlayer) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("expanded_video_player_modal"),
                    color = NetDarkBackground
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        VideoPlayerView(
                            state = playerState,
                            onStateChange = { viewModel.updatePlayerState(it) },
                            onClose = { viewModel.minimizePlayer() },
                            onNextEpisode = { viewModel.playNextEpisode() },
                            onProgressUpdate = { pos, dur -> viewModel.onProgressUpdate(pos, dur) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // If series, show episodes below player or details
                        playerState.currentMedia?.let { currentMedia ->
                            MediaDetailScreen(
                                mediaItem = currentMedia,
                                viewModel = viewModel,
                                onBack = { viewModel.minimizePlayer() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
