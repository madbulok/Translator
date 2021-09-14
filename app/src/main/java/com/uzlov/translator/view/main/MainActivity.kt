package com.uzlov.translator.view.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.uzlov.translator.R
import androidx.lifecycle.Observer
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.view.base.BaseActivity
import com.uzlov.translator.view.main.adapter.MainAdapter
import com.uzlov.translator.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val model: MainViewModel by viewModel()

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: WordModel) {
                val detailDialogFragment = DetailWordDialogFragment.newInstance(data)
                detailDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_DETAIL_FRAGMENT_DIALOG_TAG)
            }
        }

    private val adapter: MainAdapter = MainAdapter(onListItemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        main_activity_recyclerview.adapter = adapter
    }


    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                val data = appState.data
                if (data == null || data.isEmpty()) {
                    showViewError()
                } else {
                    adapter.setData(data)
                    showViewSuccess()
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showErrorScreen(getString(R.string.error_stub))
            }
        }
    }


    private fun showViewWorking() {
        loading_frame_layout.visibility = GONE
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            model.getData("hi")
        }
    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

        private const val BOTTOM_SHEET_DETAIL_FRAGMENT_DIALOG_TAG =
            "cbf5fgt0-ab6b-46bf-5d62-74a54328-413562"
    }
}
