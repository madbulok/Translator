package com.uzlov.translator.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uzlov.translator.R
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.viewmodels.HistoryViewModel
import kotlinx.android.synthetic.main.detail_word_fragment_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailWordDialogFragment : BottomSheetDialogFragment() {

    private val vmHistoryWord: HistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmHistoryWord.getAllHistoryWords()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_word_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            showResult(word = arguments?.getSerializable(WORD_KEY) as WordModel)
        }
    }


    private fun showResult(word: WordModel) {
        tvWord.text = word.text
        if (!word.meanings.isNullOrEmpty()){
            tvTranslateWord.text = word.meanings.first().translation?.translation
            ivPictureWord.load("https:${word.meanings.first().imageUrl}")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        vmHistoryWord.cancelAllJob()
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
