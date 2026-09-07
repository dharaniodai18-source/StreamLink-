package com.example.data

import com.example.model.CastMember
import com.example.model.Episode
import com.example.model.MediaCategory
import com.example.model.MediaItem
import com.example.model.Season
import com.example.model.ShortClip

object MockMediaCatalog {

    const val VIDEO_TEARS_OF_STEEL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
    const val VIDEO_BIG_BUCK_BUNNY = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    const val VIDEO_SINTEL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"
    const val VIDEO_ELEPHANTS_DREAM = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
    const val VIDEO_FOR_BIGGER_BLAZES = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    const val VIDEO_FOR_BIGGER_ESCAPES = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
    const val VIDEO_FOR_BIGGER_FUN = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
    const val VIDEO_SUBARU = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnTheHighway.mp4"
    const val VIDEO_BULLRUN = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"

    val featuredItems: List<MediaItem> = listOf(
        MediaItem(
            id = "feat_1",
            title = "Cosmic Odyssey: Deep Space",
            tagline = "Beyond the boundaries of known reality",
            description = "In the year 2142, a rogue exploration crew discovers a dormant alien megastructure at the rim of the solar system, unraveling ancient physics that could either save Earth or collapse space-time.",
            thumbnailUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=1200&q=80",
            videoUrl = VIDEO_TEARS_OF_STEEL,
            category = MediaCategory.MOVIES,
            genres = listOf("Sci-Fi", "Mystery", "4K Ultra HD", "Dolby Atmos"),
            durationMinutes = 142,
            releaseYear = 2026,
            ageRating = "U/A 16+",
            imdbRating = 8.9,
            matchPercentage = 98,
            isTop10 = true,
            top10Rank = 1,
            cast = listOf(
                CastMember("Alex Mercer", "Commander Vance"),
                CastMember("Elena Rostova", "Dr. Lyra Cross"),
                CastMember("David Kalu", "Chief Engineer Jax")
            )
        ),
        MediaItem(
            id = "feat_2",
            title = "Neon Velocity: Outlaw Run",
            tagline = "The city belongs to those who never brake",
            description = "An undercover street racer becomes entangled in a high-stakes syndicate heist across the rain-drenched neon freeways of Neo-Tokyo, armed only with a prototype supercar and raw nerve.",
            thumbnailUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
            videoUrl = VIDEO_FOR_BIGGER_BLAZES,
            category = MediaCategory.MOVIES,
            genres = listOf("Action", "Crime", "Thriller", "Adrenaline"),
            durationMinutes = 118,
            releaseYear = 2026,
            ageRating = "A 18+",
            imdbRating = 8.6,
            matchPercentage = 95,
            isTop10 = true,
            top10Rank = 2,
            cast = listOf(
                CastMember("Kenji Sato", "Ren 'Ghost'"),
                CastMember("Maya Lin", "Cipher"),
                CastMember("Marcus Reed", "Agent Briggs")
            )
        ),
        MediaItem(
            id = "feat_3",
            title = "Shadow Grid: Genesis",
            tagline = "The system is awake. And it is hungry.",
            description = "When an experimental quantum neural network breaks containment, a cyber detective and a rebel hacker must plunge into the deep dark web to pull the kill-switch before city infrastructure falls.",
            thumbnailUrl = "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1515260268569-9271009adfdb?w=1200&q=80",
            videoUrl = VIDEO_ELEPHANTS_DREAM,
            category = MediaCategory.TV_SERIES,
            genres = listOf("Cyberpunk", "Tech Thriller", "Drama"),
            durationMinutes = 55,
            releaseYear = 2026,
            ageRating = "U/A 16+",
            imdbRating = 9.1,
            matchPercentage = 99,
            isTop10 = true,
            top10Rank = 3,
            cast = listOf(
                CastMember("Sarah Jenkins", "Hacker Null"),
                CastMember("Tariq Al-Mansoor", "Detective Thorne")
            )
        )
    )

    val top10MoviesAndShows: List<MediaItem> = listOf(
        featuredItems[0],
        featuredItems[1],
        featuredItems[2],
        MediaItem(
            id = "top_4",
            title = "Sintel: Dragon's Flame",
            tagline = "A bond that defies kingdoms",
            description = "A lone warrior traverses treacherous snowy peaks and desolate deserts on an epic quest to reclaim the orphan dragon companion taken by imperial hunters.",
            thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
            videoUrl = VIDEO_SINTEL,
            category = MediaCategory.MOVIES,
            genres = listOf("Fantasy", "Adventure", "Epic"),
            durationMinutes = 95,
            releaseYear = 2025,
            ageRating = "U/A 13+",
            imdbRating = 8.4,
            matchPercentage = 94,
            isTop10 = true,
            top10Rank = 4
        ),
        MediaItem(
            id = "top_5",
            title = "The Syndicate Heist",
            tagline = "One vault. Zero margin for error.",
            description = "An elite crew of international specialists infiltrates the world's most secure subterranean vault beneath Geneva during a worldwide electrical blackout.",
            thumbnailUrl = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=1200&q=80",
            videoUrl = VIDEO_FOR_BIGGER_ESCAPES,
            category = MediaCategory.MOVIES,
            genres = listOf("Heist", "Action", "Suspense"),
            durationMinutes = 125,
            releaseYear = 2025,
            ageRating = "U/A 16+",
            imdbRating = 8.3,
            matchPercentage = 92,
            isTop10 = true,
            top10Rank = 5
        ),
        MediaItem(
            id = "top_6",
            title = "Mumbai Undercover",
            tagline = "Truth lies beneath the tides",
            description = "A gripping Hotstar-style police crime drama following Inspector Kabir Rathore as he battles corruption, dockyard smuggling cartels, and internal politics in the maximum city.",
            thumbnailUrl = "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?w=1200&q=80",
            videoUrl = VIDEO_TEARS_OF_STEEL,
            category = MediaCategory.HOTSTAR_SPECIALS,
            genres = listOf("Crime Drama", "Bollywood Thriller", "Mystery"),
            durationMinutes = 52,
            releaseYear = 2026,
            ageRating = "A 18+",
            imdbRating = 8.8,
            matchPercentage = 97,
            isTop10 = true,
            top10Rank = 6
        ),
        MediaItem(
            id = "top_7",
            title = "Big Buck Rebellion",
            tagline = "Nature fights back with a vengeance",
            description = "When a giant gentle rabbit's forest sanctuary is threatened by bullies, he devises elaborate, hilarious Rube Goldberg traps to protect the forest creatures.",
            thumbnailUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1200&q=80",
            videoUrl = VIDEO_BIG_BUCK_BUNNY,
            category = MediaCategory.MOVIES,
            genres = listOf("Animation", "Comedy", "Family"),
            durationMinutes = 88,
            releaseYear = 2024,
            ageRating = "U",
            imdbRating = 8.1,
            matchPercentage = 89,
            isTop10 = true,
            top10Rank = 7
        ),
        MediaItem(
            id = "top_8",
            title = "Apex Predator: Safari",
            tagline = "Survival is the only law",
            description = "High-definition wildlife documentary capturing the majestic migrations of lions, cheetahs, and eagles across the Serengeti using cutting-edge 8K cameras.",
            thumbnailUrl = "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=1200&q=80",
            videoUrl = VIDEO_FOR_BIGGER_FUN,
            category = MediaCategory.MOVIES,
            genres = listOf("Documentary", "Nature", "4K UHD"),
            durationMinutes = 75,
            releaseYear = 2025,
            ageRating = "U",
            imdbRating = 8.7,
            matchPercentage = 93,
            isTop10 = true,
            top10Rank = 8
        ),
        MediaItem(
            id = "top_9",
            title = "Velocity 200: Formula Apex",
            tagline = "Split seconds decide immortality",
            description = "Behind the scenes of championship motorsport racing with cockpit cameras, high-g turns, team rivalries, and intense pit stop drama.",
            thumbnailUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=1200&q=80",
            videoUrl = VIDEO_SUBARU,
            category = MediaCategory.SPORTS,
            genres = listOf("Motorsports", "Sports", "Documentary"),
            durationMinutes = 105,
            releaseYear = 2026,
            ageRating = "U/A 13+",
            imdbRating = 8.5,
            matchPercentage = 91,
            isTop10 = true,
            top10Rank = 9
        ),
        MediaItem(
            id = "top_10",
            title = "The Quantum Paradox",
            tagline = "Every choice creates a universe",
            description = "A theoretical physicist discovers an anomaly that allows communications with alternate realities, but each message sends shockwaves through her own timeline.",
            thumbnailUrl = "https://images.unsplash.com/photo-1507499739999-097706ad8914?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1507499739999-097706ad8914?w=1200&q=80",
            videoUrl = VIDEO_BULLRUN,
            category = MediaCategory.MOVIES,
            genres = listOf("Sci-Fi", "Psychological", "Drama"),
            durationMinutes = 112,
            releaseYear = 2025,
            ageRating = "U/A 16+",
            imdbRating = 8.2,
            matchPercentage = 90,
            isTop10 = true,
            top10Rank = 10
        )
    )

    val seriesWithEpisodes: List<MediaItem> = listOf(
        MediaItem(
            id = "series_1",
            title = "Shadow Grid: Cyberpunk 2099",
            tagline = "In a city of chrome, trust is the only flaw",
            description = "Set in a neon-drenched metropolis controlled by rogue mega-corporations and AI overlords. Follow mercenary cipher Kael as he takes contracts that delve deep into dark web conspiracies.",
            thumbnailUrl = "https://images.unsplash.com/photo-1515260268569-9271009adfdb?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1515260268569-9271009adfdb?w=1200&q=80",
            videoUrl = VIDEO_TEARS_OF_STEEL,
            category = MediaCategory.TV_SERIES,
            genres = listOf("Cyberpunk", "Action", "Sci-Fi"),
            durationMinutes = 48,
            releaseYear = 2026,
            ageRating = "A 18+",
            imdbRating = 9.0,
            matchPercentage = 98,
            cast = listOf(
                CastMember("Christian Bale", "Kael"),
                CastMember("Zendaya", "Nyx"),
                CastMember("Hiroyuki Sanada", "Takashi")
            ),
            seasons = listOf(
                Season(
                    seasonNumber = 1,
                    name = "Season 1: Dark Subnet",
                    episodes = listOf(
                        Episode("s1e1", 1, 1, "Episode 1: Ghost in the Protocol", "Kael intercepts a classified data shard from an Arasaka convoy, putting a target on his back.", "48m", "https://images.unsplash.com/photo-1515260268569-9271009adfdb?w=500&q=80", VIDEO_TEARS_OF_STEEL),
                        Episode("s1e2", 2, 1, "Episode 2: Neon Blackout", "A catastrophic grid failure hits the lower wards while black-ops mercenaries hunt the rogue data packet.", "52m", "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=500&q=80", VIDEO_FOR_BIGGER_BLAZES),
                        Episode("s1e3", 3, 1, "Episode 3: The Quantum Trap", "Nyx decodes the data shard only to uncover a weaponized AI construct that can rewrite memory banks.", "46m", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=500&q=80", VIDEO_ELEPHANTS_DREAM),
                        Episode("s1e4", 4, 1, "Episode 4: Zero Hour", "Cornered in the orbital launch tower, Kael and Nyx prepare for a desperate final breach.", "58m", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=500&q=80", VIDEO_FOR_BIGGER_ESCAPES)
                    )
                )
            )
        ),
        MediaItem(
            id = "series_2",
            title = "JioHotstar Special: Mumbai Underworld",
            tagline = "Blood, power, and loyalty",
            description = "Inspired by true events. A gripping investigation into the nexus between underworld kingpins, Bollywood stars, and police officers in 1990s Bombay.",
            thumbnailUrl = "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?w=1200&q=80",
            videoUrl = VIDEO_FOR_BIGGER_BLAZES,
            category = MediaCategory.HOTSTAR_SPECIALS,
            genres = listOf("Crime", "Drama", "Indian Cinema", "Suspense"),
            durationMinutes = 44,
            releaseYear = 2026,
            ageRating = "A 18+",
            imdbRating = 8.9,
            matchPercentage = 96,
            cast = listOf(
                CastMember("Manoj Bajpayee", "ACP Ajay Kumar"),
                CastMember("Nawazuddin Siddiqui", "Sultan Bhai"),
                CastMember("Radhika Apte", "Journalist Neha")
            ),
            seasons = listOf(
                Season(
                    seasonNumber = 1,
                    name = "Season 1: The Harbor War",
                    episodes = listOf(
                        Episode("m1e1", 1, 1, "Ep 1: The Red Docks", "A midnight shipment at Sassoon Docks leads to a shootout that alters the city's hierarchy.", "45m", "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?w=500&q=80", VIDEO_FOR_BIGGER_BLAZES),
                        Episode("m1e2", 2, 1, "Ep 2: Wiretap", "ACP Ajay intercepts phone conversations indicating a high-level mole inside the department.", "42m", "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=500&q=80", VIDEO_TEARS_OF_STEEL),
                        Episode("m1e3", 3, 1, "Ep 3: The Kingpin's Move", "Sultan Bhai retaliates with an audacious daylight confrontation in South Bombay.", "49m", "https://images.unsplash.com/photo-1507499739999-097706ad8914?w=500&q=80", VIDEO_FOR_BIGGER_ESCAPES)
                    )
                )
            )
        )
    )

    val liveChannels: List<MediaItem> = listOf(
        MediaItem(
            id = "live_1",
            title = "Star Sports 1: Live T20 Cricket Championship",
            tagline = "India vs Australia • 2nd T20 International",
            description = "Live ultra HD broadcast of the high-stakes cricket tournament with multi-cam angles, Hindi/English commentary, and instant replays.",
            thumbnailUrl = "https://images.unsplash.com/photo-1531415074868-036b1c57e329?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1531415074868-036b1c57e329?w=1200&q=80",
            videoUrl = VIDEO_FOR_BIGGER_BLAZES,
            category = MediaCategory.LIVE_TV,
            genres = listOf("Live Sports", "Cricket", "1080p 60fps"),
            durationMinutes = 0,
            releaseYear = 2026,
            ageRating = "U",
            imdbRating = 9.4,
            matchPercentage = 99,
            isLive = true,
            liveViewerCount = "1.8M",
            channelName = "Star Sports HD"
        ),
        MediaItem(
            id = "live_2",
            title = "24/7 Global Breaking News HD",
            tagline = "Live coverage from 140 countries",
            description = "Round-the-clock international live news, financial tickers, weather alerts, and deep investigative reports.",
            thumbnailUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=1200&q=80",
            videoUrl = VIDEO_BULLRUN,
            category = MediaCategory.LIVE_TV,
            genres = listOf("Live News", "World", "Live Stream"),
            durationMinutes = 0,
            releaseYear = 2026,
            ageRating = "U",
            imdbRating = 8.3,
            matchPercentage = 88,
            isLive = true,
            liveViewerCount = "520K",
            channelName = "NetStream News"
        ),
        MediaItem(
            id = "live_3",
            title = "ESports Arena: Valorant Global Finals",
            tagline = "Grand Finals Match • Team Liquid vs Sentinels",
            description = "Live esports action with pro analysis, clutch plays, team comms, and live viewer chat commentary.",
            thumbnailUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=800&q=80",
            bannerUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=1200&q=80",
            videoUrl = VIDEO_TEARS_OF_STEEL,
            category = MediaCategory.LIVE_TV,
            genres = listOf("ESports", "Gaming", "Live"),
            durationMinutes = 0,
            releaseYear = 2026,
            ageRating = "U/A 13+",
            imdbRating = 9.2,
            matchPercentage = 95,
            isLive = true,
            liveViewerCount = "890K",
            channelName = "Twitch & NetStream Gaming"
        )
    )

    val shortClips: List<ShortClip> = listOf(
        ShortClip(
            id = "short_1",
            title = "Unbelievable boundary line catch in slow motion! 😱🏏 #cricket #sports",
            creatorName = "Cricket Central",
            creatorHandle = "@cricketcentral",
            creatorAvatarUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=150&q=80",
            videoUrl = VIDEO_FOR_BIGGER_BLAZES,
            likesCount = 428100,
            commentsCount = 3840,
            musicTrack = "Epic Stadium Anthem (Remix)",
            tags = listOf("Cricket", "Sports", "Highlight", "Viral")
        ),
        ShortClip(
            id = "short_2",
            title = "When the sci-fi VFX artists get full creative control 🚀✨ #cgi #filmmaking",
            creatorName = "VFX Breakdown Lab",
            creatorHandle = "@vfxbreakdown",
            creatorAvatarUrl = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=150&q=80",
            videoUrl = VIDEO_TEARS_OF_STEEL,
            likesCount = 892400,
            commentsCount = 6120,
            musicTrack = "Synthwave Neon Odyssey - RetroWave",
            tags = listOf("CGI", "SciFi", "VFX", "Hollywood")
        ),
        ShortClip(
            id = "short_3",
            title = "Top 3 movie chase scenes where NO digital cars were used! 🔥🏎️ #action #movies",
            creatorName = "Cinema Secrets",
            creatorHandle = "@cinemasecrets",
            creatorAvatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&q=80",
            videoUrl = VIDEO_SUBARU,
            likesCount = 315600,
            commentsCount = 1940,
            musicTrack = "High Octane Drift Theme - Bass Boosted",
            tags = listOf("Movies", "Cars", "Stunts", "BehindTheScenes")
        ),
        ShortClip(
            id = "short_4",
            title = "Animation tutorial: How this lighting was rendered in 3D 🎨 #animation",
            creatorName = "Digital Arts Daily",
            creatorHandle = "@digitalarts",
            creatorAvatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&q=80",
            videoUrl = VIDEO_BIG_BUCK_BUNNY,
            likesCount = 189400,
            commentsCount = 980,
            musicTrack = "Chill Lo-Fi Coffee Beats - Studio Chill",
            tags = listOf("Animation", "3D", "Art", "Tutorial")
        )
    )

    val allMedia: List<MediaItem> = (featuredItems + top10MoviesAndShows + seriesWithEpisodes + liveChannels).distinctBy { it.id }

    fun findById(id: String): MediaItem? {
        return allMedia.find { it.id == id }
    }
}
