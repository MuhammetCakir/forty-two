package com.orioninc.techclub.infiniteimprobabilitydrive.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.orioninc.techclub.infiniteimprobabilitydrive.data.entity.Star

@Dao
internal interface StarDao {

    @Insert
    suspend fun insertStar(star: Star): Long

    @Query("SELECT * FROM star")
    fun gelAllStars(): LiveData<List<Star>>

    @Query("DELETE FROM star")
    suspend fun deleteAllStars()
}