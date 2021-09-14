package com.uzlov.translator.media.di

import com.google.android.exoplayer2.SimpleExoPlayer
import com.uzlov.translator.media.player.ISoundPlayer
import com.uzlov.translator.media.player.SoundPlayerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectMediaPlayers() = loadFeatures

private val loadFeatures by lazy {
    loadKoinModules(listOf(mediaPlayer))
}

val mediaPlayer = module {
    factory <SimpleExoPlayer> { SimpleExoPlayer.Builder(androidContext()).build()}
    factory <ISoundPlayer<SimpleExoPlayer>> { SoundPlayerImpl(player = get()) }
}