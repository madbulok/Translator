package com.uzlov.translator.media.player

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class SoundPlayerImpl(val player: SimpleExoPlayer) : ISoundPlayer<String> {

    //source - URL
    override fun play(source: String) {
        val mediaItem = MediaItem.fromUri(source)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun releasePlayer() {
        player.release()
    }
}