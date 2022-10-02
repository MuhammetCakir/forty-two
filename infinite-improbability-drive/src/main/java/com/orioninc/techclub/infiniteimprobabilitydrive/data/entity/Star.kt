package com.orioninc.techclub.infiniteimprobabilitydrive.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class Star(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val size: Size,
    val color: Color,
    val brightness: Brightness
)
