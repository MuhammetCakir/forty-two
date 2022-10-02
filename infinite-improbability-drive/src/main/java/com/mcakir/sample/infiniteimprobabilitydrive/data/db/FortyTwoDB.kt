package com.mcakir.sample.infiniteimprobabilitydrive.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mcakir.sample.infiniteimprobabilitydrive.data.entity.Star

@Database(
    entities = [
        Star::class
    ],
    version = 1
)
internal abstract class FortyTwoDB : RoomDatabase() {

    abstract fun starDao(): StarDao

    companion object {
        private const val DB_NAME: String = "forty-two-db"

        fun getInstance(context: Context) : FortyTwoDB =
            Room.databaseBuilder(context.applicationContext, FortyTwoDB::class.java, DB_NAME).build()
    }
}