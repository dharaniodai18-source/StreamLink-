package com.example.player

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.model.MediaItem
import com.example.ui.theme.NetDarkBackground
import com.example.ui.theme.NetRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VideoPlayerView(
    state: VideoPlayerUiState,
    onStateChange: (VideoPlayerUiState) -> Unit,
    onClose: () -> Unit,
    onNextEpisode: () -> Unit,
    onProgressUpdate: (positionMs: Long, durationMs: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var videoViewRef by remember { mutableStateOf<VideoView?>(null) }
    var mediaPlayerRef by remember { mutableStateOf<MediaPlayer?>(null) }
    var doubleTapFeedback by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Handle back press: if fullscreen, exit fullscreen first; otherwise close or minimize
    BackHandler {
        if (state.isFullscreen) {
            onStateChange(state.copy(isFullscreen = false))
        } else {
            onClose()
        }
    }

    // Auto-hide controls after 4 seconds of inactivity if playing
    LaunchedEffect(state.isControlsVisible, state.isPlaying) {
        if (state.isControlsVisible && state.isPlaying && !state.isLocked) {
            delay(4000)
            onStateChange(state.copy(isControlsVisible = false))
        }
    }

    // Polling loop for current position
    LaunchedEffect(state.isPlaying, videoViewRef) {
        while (true) {
            videoViewRef?.let { vv ->
                if (vv.isPlaying) {
                    val pos = vv.currentPosition.toLong()
                    val dur = vv.duration.toLong()
                    if (dur > 0) {
                        onStateChange(state.copy(currentPositionMs = pos, durationMs = dur, isBuffering = false))
                        onProgressUpdate(pos, dur)

                        // Check if video is ending (last 8 seconds) to show Next Episode countdown
                        if (dur - pos in 1000..8000 && state.currentMedia?.seasons != null && !state.showNextEpisodeCountdown) {
                            onStateChange(state.copy(showNextEpisodeCountdown = true, nextCountdownSeconds = 5))
                        }
                    }
                }
            }
            delay(500)
        }
    }

    // Countdown timer for next episode banner
    LaunchedEffect(state.showNextEpisodeCountdown) {
        if (state.showNextEpisodeCountdown) {
            for (i in 5 downTo 1) {
                onStateChange(state.copy(nextCountdownSeconds = i))
                delay(1000)
            }
            onNextEpisode()
        }
    }

    // Playback speed application
    LaunchedEffect(state.playbackSpeed, mediaPlayerRef) {
        mediaPlayerRef?.let { mp ->
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    val params = mp.playbackParams
                    params.speed = state.playbackSpeed
                    mp.playbackParams = params
                }
            } catch (_: Exception) {}
        }
    }

    val boxModifier = if (state.isFullscreen) {
        Modifier.fillMaxSize()
    } else {
        Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    }

    Box(
        modifier = modifier
            .then(boxModifier)
            .background(NetDarkBackground)
            .pointerInput(state.isLocked) {
                detectTapGestures(
                    onTap = {
                        if (!state.isLocked) {
                            onStateChange(state.copy(isControlsVisible = !state.isControlsVisible))
                        }
                    },
                    onDoubleTap = { offset ->
                        if (!state.isLocked) {
                            val isRight = offset.x > size.width / 2
                            val seekDelta = if (isRight) 10000L else -10000L
                            videoViewRef?.let { vv ->
                                val target = (vv.currentPosition + seekDelta).coerceIn(0, vv.duration.toLong()).toInt()
                                vv.seekTo(target)
                                onStateChange(state.copy(currentPositionMs = target.toLong()))
                            }
                            doubleTapFeedback = if (isRight) "+10s" else "-10s"
                            coroutineScope.launch {
                                delay(800)
                                doubleTapFeedback = null
                            }
                        }
                    }
                )
            }
    ) {
        // Native VideoView embedded in Compose AndroidView
        AndroidView(
            factory = { ctx ->
                VideoView(ctx).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    state.currentMedia?.videoUrl?.let { url ->
                        setVideoURI(Uri.parse(url))
                    }
                    setOnPreparedListener { mp ->
                        mediaPlayerRef = mp
                        mp.isLooping = false
                        mp.setOnBufferingUpdateListener { _, percent ->
                            // buffering update
                        }
                        try {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                val params = mp.playbackParams
                                params.speed = state.playbackSpeed
                                mp.playbackParams = params
                            }
                        } catch (_: Exception) {}
                        val dur = mp.duration.toLong()
                        onStateChange(state.copy(durationMs = dur, isBuffering = false))
                        if (state.currentPositionMs > 0) {
                            seekTo(state.currentPositionMs.toInt())
                        }
                        if (state.isPlaying) {
                            start()
                        }
                    }
                    setOnCompletionListener {
                        onStateChange(state.copy(isPlaying = false))
                        if (state.currentMedia?.seasons != null) {
                            onNextEpisode()
                        }
                    }
                    setOnErrorListener { _, _, _ ->
                        onStateChange(state.copy(isBuffering = false))
                        true
                    }
                    videoViewRef = this
                }
            },
            update = { vv ->
                videoViewRef = vv
                state.currentMedia?.videoUrl?.let { currentUrl ->
                    // Change video source if changed
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Double-tap visual indicator feedback (+10s / -10s)
        doubleTapFeedback?.let { feedback ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f)),
                contentAlignment = if (feedback.contains("+")) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier.padding(36.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (feedback.contains("+")) Icons.Default.FastForward else Icons.Default.FastRewind,
                            contentDescription = feedback,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = feedback,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Overlay Controls
        PlayerOverlayControls(
            state = state,
            onTogglePlay = {
                videoViewRef?.let { vv ->
                    if (vv.isPlaying) {
                        vv.pause()
                        onStateChange(state.copy(isPlaying = false))
                    } else {
                        vv.start()
                        onStateChange(state.copy(isPlaying = true))
                    }
                }
            },
            onSeek = { targetMs ->
                videoViewRef?.seekTo(targetMs.toInt())
                onStateChange(state.copy(currentPositionMs = targetMs))
            },
            onSeekRelative = { deltaMs ->
                videoViewRef?.let { vv ->
                    val target = (vv.currentPosition + deltaMs).coerceIn(0, vv.duration.toLong()).toInt()
                    vv.seekTo(target)
                    onStateChange(state.copy(currentPositionMs = target.toLong()))
                }
            },
            onToggleLock = {
                onStateChange(state.copy(isLocked = !state.isLocked, isControlsVisible = !state.isLocked))
            },
            onToggleFullscreen = {
                onStateChange(state.copy(isFullscreen = !state.isFullscreen))
            },
            onToggleAspectRatio = {
                val nextMode = when (state.aspectRatioMode) {
                    AspectRatioMode.FIT -> AspectRatioMode.FILL
                    AspectRatioMode.FILL -> AspectRatioMode.SIXTEEN_NINE
                    AspectRatioMode.SIXTEEN_NINE -> AspectRatioMode.FIT
                }
                onStateChange(state.copy(aspectRatioMode = nextMode))
            },
            onSelectQuality = { quality ->
                onStateChange(state.copy(selectedQuality = quality))
            },
            onSelectSpeed = { speed ->
                onStateChange(state.copy(playbackSpeed = speed))
            },
            onSelectAudio = { audio ->
                onStateChange(state.copy(selectedAudio = audio))
            },
            onSelectSubtitle = { subtitle ->
                onStateChange(state.copy(selectedSubtitle = subtitle))
            },
            onNextEpisode = onNextEpisode,
            onCloseOrMinimize = onClose
        )

        // Auto Next Episode Countdown Overlay (Hotstar / Netflix style)
        AnimatedVisibility(
            visible = state.showNextEpisodeCountdown,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 50.dp, end = 16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.Black.copy(alpha = 0.85f),
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Next Episode in ${state.nextCountdownSeconds}s",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = onNextEpisode,
                        colors = ButtonDefaults.buttonColors(containerColor = NetRed),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Play Now", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
