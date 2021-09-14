package com.uzlov.translator.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import coil.load
import coil.request.Disposable
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uzlov.translator.media.R
import com.uzlov.translator.media.di.injectMediaPlayers
import com.uzlov.translator.media.player.ISoundPlayer
import com.uzlov.translator.model.data.WordModel
import kotlinx.android.synthetic.main.detail_word_fragment_layout.*
import org.koin.android.ext.android.get

class DetailWordDialogFragment : BottomSheetDialogFragment() {

    private val player: ISoundPlayer<SimpleExoPlayer> by lazy {
        get<ISoundPlayer<SimpleExoPlayer>>()
    }

    private var disposableLoadImage: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_word_fragment_layout, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectMediaPlayers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            showResult(word = arguments?.getSerializable(WORD_KEY) as WordModel)
        }
    }


    private fun showResult(word: WordModel) {
        tvWord.text = word.text
        if (!word.meanings.isNullOrEmpty()) {
            tvTranslateWord.text = "[${word.meanings?.first()?.transcription}] ${word.meanings?.first()?.translation?.translation}"
            disposableLoadImage = ivPictureWord.load("https:${word.meanings?.first()?.imageUrl}")

            player_view.player = player.getPlayer()
            word.meanings?.first()?.soundUrl?.let { player.play(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableLoadImage?.dispose()
        player.releasePlayer()
    }


    companion object {
        const val WORD_KEY = "word_key"

        fun newInstance(word: WordModel): DetailWordDialogFragment {
            return DetailWordDialogFragment().apply {
                arguments = bundleOf(WORD_KEY to word)
            }
        }
    }
}
