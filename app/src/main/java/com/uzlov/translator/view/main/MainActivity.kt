package com.uzlov.translator.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.uzlov.translator.R
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.utils.viewById
import com.uzlov.translator.view.main.adapter.MainAdapter
import com.uzlov.translator.viewmodels.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope

private const val MODULES_KOIN_PATH = "com.uzlov.translator.media.ui.DetailWordDialogFragment"
private const val MEDIA_FEATURE_NAME = "detailScreen"

class MainActivity : com.uzlov.translator.core.BaseActivity() {

    private var splitInstallManager: SplitInstallManager? = null

    private val model: MainViewModel by currentScope.inject()
    private val networkStatus: INetworkStatus by inject<INetworkStatus>()

    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)
    private val errorContainer by viewById<LinearLayout>(R.id.error_linear_layout)
    private val successContainer by viewById<FrameLayout>(R.id.success_linear_layout)
    private val loadingContainer by viewById<FrameLayout>(R.id.loading_frame_layout)
    private val reloadBtn by viewById<Button>(R.id.reload_button)
    private val tvError by viewById<TextView>(R.id.error_textview)
    private val pbLoadingStatusHorizontal by viewById<ProgressBar>(R.id.progress_bar_horizontal)
    private val pbLoadingStatusRound by viewById<ProgressBar>(R.id.progress_bar_round)

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: WordModel) {
                startFeature(data)
            }
        }

    private fun startFeature(data: WordModel) {
        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
        val request =
            SplitInstallRequest
                .newBuilder()
                .addModule(MEDIA_FEATURE_NAME)
                .build()

        splitInstallManager?.let { mng ->
            mng.startInstall(request)
                .addOnSuccessListener {
                    val detailDialogFragment = instantiateFragment(MODULES_KOIN_PATH, data)
                    detailDialogFragment?.show(
                        supportFragmentManager,
                        BOTTOM_SHEET_DETAIL_FRAGMENT_DIALOG_TAG
                    )
                }
                .addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Couldn't download feature: " + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun instantiateFragment(
        className: String,
        data: WordModel
    ): BottomSheetDialogFragment? = try {
        (Class.forName(className).newInstance() as BottomSheetDialogFragment).apply {
            arguments = bundleOf("word_key" to data)
        }
    } catch (e: Exception) {
        null
    }

    private val adapter: MainAdapter = MainAdapter(onListItemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })

        searchFAB.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        mainActivityRecyclerView.adapter = adapter

        networkStatus.observeSateNetwork().observe(this, { isOnline ->
            if (!isOnline) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    openSettingsPanel(
                        Settings.Panel.ACTION_INTERNET_CONNECTIVITY
                    )
                } else {
                    openLegacySettings(Intent.ACTION_MAIN)
                }

            }
        })

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
                    pbLoadingStatusHorizontal.visibility = VISIBLE
                    pbLoadingStatusRound.visibility = GONE
                    pbLoadingStatusHorizontal.progress = appState.progress ?: 0
                } else {
                    pbLoadingStatusHorizontal.visibility = GONE
                    pbLoadingStatusRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showErrorScreen(getString(R.string.error_stub))
            }
        }
    }


    private fun showViewWorking() {
        loadingContainer.visibility = GONE
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        tvError.text = error ?: getString(R.string.undefined_error)
        reloadBtn.setOnClickListener {
            model.getData("hi")
        }
    }

    private fun showViewSuccess() {
        successContainer.visibility = VISIBLE
        loadingContainer.visibility = GONE
        errorContainer.visibility = GONE
    }

    private fun showViewLoading() {
        successContainer.visibility = GONE
        loadingContainer.visibility = VISIBLE
        errorContainer.visibility = GONE
    }

    private fun showViewError() {
        successContainer.visibility = GONE
        loadingContainer.visibility = GONE
        errorContainer.visibility = VISIBLE
    }

    private fun openSettingsPanel(panel: String) {
        val panelIntent = Intent(panel)
        startActivity(panelIntent)
    }

    private fun openLegacySettings(actionMain: String) {
        val intent = Intent(actionMain).apply {
            setClassName("com.android.phone", "com.android.phone.NetworkSetting")
        }
        startActivity(intent)
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

        private const val BOTTOM_SHEET_DETAIL_FRAGMENT_DIALOG_TAG =
            "cbf5fgt0-ab6b-46bf-5d62-74a54328-413562"
    }
}
