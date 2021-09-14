package com.uzlov.translator.media.player

interface ISoundPlayer<T> {
    fun getPlayer() : T
    fun play(source: String)
    fun releasePlayer()
}