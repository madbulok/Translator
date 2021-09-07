package com.uzlov.translator.view.main

import android.content.Context
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.datasource.DataSourceLocal
import com.uzlov.translator.model.datasource.DataSourceRemote
import com.uzlov.translator.model.net.AndroidNetworkStatus
import com.uzlov.translator.model.repository.RepositoryImplementation
import com.uzlov.translator.presenter.Presenter
import com.uzlov.translator.rx.SchedulerProvider
import com.uzlov.translator.view.base.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainPresenterImpl<T : AppState, V : View>(
    private var context: Context?,
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal()),
    AndroidNetworkStatus(context)
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView() {
        compositeDisposable.clear()
        currentView = null
        context = null
    }

    override fun getData(word: String) {
        compositeDisposable.add(
            interactor.getData(word)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}
