package com.mcakir.sample.infiniteimprobabilitydrive.data.repository

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mcakir.sample.infiniteimprobabilitydrive.data.db.StarDao
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Brightness
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Color
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Size
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Star
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

internal class StarRepository(private val starDao: StarDao, private val lifecycleOwner: LifecycleOwner) {

    private val STAR_REPOSITORY_THREAD_NAME = "star-repository-thread"

    private val scope = lifecycleOwner.lifecycleScope

    private val stars = starDao.gelAllStars().also {
        it.observe(lifecycleOwner) { stars ->
            logStars(stars)
            logBrightStarCount(stars)
        }
    }

    @ObsoleteCoroutinesApi
    val serializedDispatcher = newSingleThreadContext(STAR_REPOSITORY_THREAD_NAME)

    fun addStar(size: Size): Boolean {
        if (stars.value?.size == 10) return false

        val star = Star(0, size, Color.randomColor(), Brightness.randomBrightness())

        scope.launch(serializedDispatcher) {
            starDao.insertStar(star)
        }

        return true
    }

    fun reset() {
        scope.launch(serializedDispatcher) {
            starDao.deleteAllStars()
        }
    }

    private fun logStars(stars: List<Star>) {
        Log.d("StarRepository", "All stars: ${stars.toString()}")
    }

    private fun logBrightStarCount(stars: List<Star>) {
        var brightStarCount = 0

        for (star in stars) {
            if (star.brightness == Brightness.BRIGHT) brightStarCount++
        }

        Log.d("StarRepository", "Bright star count: $brightStarCount")
    }
}