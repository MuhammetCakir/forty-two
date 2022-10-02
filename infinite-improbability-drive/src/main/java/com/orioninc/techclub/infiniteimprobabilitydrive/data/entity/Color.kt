package com.orioninc.techclub.infiniteimprobabilitydrive.data.entity

import java.util.*

internal enum class Color {
    RED,
    BLUE,
    GREEN;

    companion object {
        private val size = Color.values().size

        fun randomColor(): Color {
            val randomIndex = Random().nextInt(size)

            return Color.values()[randomIndex]
        }
    }
}