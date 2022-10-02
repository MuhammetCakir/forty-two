package com.mcakir.sample.infiniteimprobabilitydrive.data.entity

import java.util.*


internal enum class Brightness {
    BRIGHT,
    NOT_SO_MUCH;

    companion object {
        private val size = Brightness.values().size

        fun randomBrightness(): Brightness {
            val randomIndex = Random().nextInt(size)

            return Brightness.values()[randomIndex]
        }
    }
}