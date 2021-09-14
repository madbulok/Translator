package com.uzlov.translator.media.player

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class SoundPlayerImpl(private val player: SimpleExoPlayer) : ISoundPlayer<SimpleExoPlayer> {

    //source - URL
    override fun play(source: String) {
        val mediaItem = MediaItem.fromUri("https:${source}")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun releasePlayer() {
        player.release()
    }

    override fun getPlayer(): SimpleExoPlayer  = player
}