package com.uzlov.translator.view.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.uzlov.translator.R
import com.uzlov.translator.di.app
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.view.main.adapter.HistoryWordAdapter
import com.uzlov.translator.view.main.adapter.MainAdapter
import com.uzlov.translator.viewmodels.HistoryViewModel
import kotlinx.android.synthetic.main.search_dialog_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchDialogFragment : BottomSheetDialogFragment() {

    private lateinit var searchEditText: TextInputEditText
    private lateinit var searchButton: TextView
    private lateinit var rvHistory: RecyclerView
    private var onSearchClickListener: OnSearchClickListener? = null
    private val vmHistoryWord: HistoryViewModel by viewModel()

    private val onListItemClickListener: HistoryWordAdapter.OnListItemClickListener =
        object : HistoryWordAdapter.OnListItemClickListener {
            override fun onItemClick(data: WordModel) {
                searchEditText.setText(data.text)
                onSearchClickListener?.onClick(data.text.toString())
                dismiss()
            }
        }
    private val adapter: HistoryWordAdapter = HistoryWordAdapter(onListItemClickListener)

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            searchButton.isEnabled = searchEditText.text != null && searchEditText.text.toString().isNotEmpty()
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchClickListener?.onClick(searchEditText.text.toString())
            dismiss()
        }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmHistoryWord.getAllHistoryWords()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEditText = search_edit_text
        searchButton = search_button_textview
        rvHistory = rwHistoryWord

        searchButton.setOnClickListener(onSearchButtonClickListener)
        searchEditText.addTextChangedListener(textWatcher)
        rvHistory.adapter = adapter

        vmHistoryWord.subscribe().observe(viewLifecycleOwner, {
            showResult(it)
        })
    }


    private fun showResult(appState: AppState){
        when(appState){
            is AppState.Error -> {
                Toast.makeText(requireContext(), appState.error.message, Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                // тех долг =(
            }
            is AppState.Success -> {
                appState.data?.let { fillRecyclerView(it) }
            }
        }
    }

    private fun fillRecyclerView(data: List<WordModel>) {
        adapter.setData(data)
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
    }

    interface OnSearchClickListener {
        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }
}
