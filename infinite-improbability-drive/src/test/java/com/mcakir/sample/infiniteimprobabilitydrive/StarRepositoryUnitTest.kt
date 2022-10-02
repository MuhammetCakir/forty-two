package com.mcakir.sample.infiniteimprobabilitydrive

import androidx.lifecycle.*
import com.mcakir.sample.infiniteimprobabilitydrive.data.db.StarDao
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Brightness
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Color
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Size
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Star
import com.mcakir.sample.infiniteimprobabilitydrive.data.repository.StarRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StarRepositoryUnitTest {

    private lateinit var mockStarDao: StarDao
    private lateinit var mockLifecycleOwner: LifecycleOwner
    private lateinit var mockLiveStars: LiveData<List<Star>>

    /*val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()*/

    @Before
    fun init() {
        mockStarDao = mockk()
        mockLifecycleOwner = mockk()
        mockLiveStars = mockk()

        val lifecycle = LifecycleRegistry(mockLifecycleOwner)
        lifecycle.markState(Lifecycle.State.RESUMED)
        every { mockLifecycleOwner.lifecycle } returns lifecycle
    }

    @Test
    fun `given valid size when less then ten star then input should be inserted to DB`() {
        // GIVEN
        val id = 0L
        val size = Size.BIG
        val brightness = Brightness.randomBrightness()
        val color = Color.randomColor()

        val star = Star(id, size, color, brightness)

        // WHEN
        every { mockStarDao.gelAllStars() } returns MutableLiveData<List<Star>>()

        // THEN
        val starRepository = StarRepository(mockStarDao, mockLifecycleOwner)

        starRepository.addStar(size)

        coVerify { mockStarDao.insertStar(star) }
    }
}