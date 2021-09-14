package com.uzlov.translator.media.player

import com.google.android.exoplayer2.SimpleExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun inject() = loadFeatures

private val loadFeatures by lazy {
    loadKoinModules(listOf(mediaPlayer))
}

val mediaPlayer = module {
    single<SimpleExoPlayer> { SimpleExoPlayer.Builder(androidContext()).build()}
    single<ISoundPlayer<String>> { SoundPlayerImpl(player = get()) }
}