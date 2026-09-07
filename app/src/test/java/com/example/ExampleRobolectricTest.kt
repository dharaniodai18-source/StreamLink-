package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.MockMediaCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("NetStream", appName)
    }

    @Test
    fun `verify mock media catalog contains streaming content`() {
        assertTrue(MockMediaCatalog.featuredItems.isNotEmpty())
        assertTrue(MockMediaCatalog.top10MoviesAndShows.isNotEmpty())
        assertTrue(MockMediaCatalog.liveChannels.isNotEmpty())
        assertTrue(MockMediaCatalog.seriesWithEpisodes.isNotEmpty())
        assertTrue(MockMediaCatalog.shortClips.isNotEmpty())

        val firstItem = MockMediaCatalog.featuredItems.first()
        assertNotNull(firstItem.videoUrl)
        assertTrue(firstItem.videoUrl.isNotBlank())
    }
}
