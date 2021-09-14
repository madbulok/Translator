package com.uzlov.translator.media.player

interface ISoundPlayer<T> {
    fun play(source: T)
    fun releasePlayer()
}