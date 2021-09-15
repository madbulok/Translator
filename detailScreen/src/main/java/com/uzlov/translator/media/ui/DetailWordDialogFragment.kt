package com.uzlov.translator.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import coil.load
import coil.request.Disposable
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uzlov.translator.media.R
import com.uzlov.translator.media.di.injectMediaPlayers
import com.uzlov.translator.media.player.ISoundPlayer
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.utils.viewById
import org.koin.android.scope.currentScope

class DetailWordDialogFragment : BottomSheetDialogFragment()  {

    init {
        injectMediaPlayers()
    }
    private val player: ISoundPlayer<SimpleExoPlayer> =  currentScope.get<ISoundPlayer<SimpleExoPlayer>>()

    private var disposableLoadImage: Disposable? = null
    private val tvWord by viewById<TextView>(R.id.tvWord)
    private val tvTranslateWord by viewById<TextView>(R.id.tvTranslateWord)
    private val ivPictureWord by viewById<ImageView>(R.id.ivPictureWord)
    private val playerView by viewById<StyledPlayerView>(R.id.player_view)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_word_fragment_layout, container, false)
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

            playerView.player = player.getPlayer()
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
