package com.uzlov.translator.di


import androidx.room.Room
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.repository.datasource.RetrofitImplementation
import com.uzlov.translator.repository.datasource.RoomIDataBaseImplementation
import com.uzlov.translator.model.net.AndroidNetworkStatus
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.presenter.IHistoryInteractor
import com.uzlov.translator.repository.room.HistoryDataBase
import com.uzlov.translator.repository.room.HistoryEntity
import com.uzlov.translator.view.main.HistoryInteractorImpl
import com.uzlov.translator.view.main.MainInteractor
import com.uzlov.translator.viewmodels.HistoryViewModel
import com.uzlov.translator.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val app = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }

    single<com.uzlov.translator.repository.IRepository<List<WordModel>>>(named(NAME_REMOTE)) {
        com.uzlov.translator.repository.RemoteRepositoryImplementation(dataSource = RetrofitImplementation())
    }

    single<com.uzlov.translator.repository.ILocalRepository<List<HistoryEntity>>>(named(NAME_LOCAL)) {
        com.uzlov.translator.repository.LocalRepositoryImpl(
            dataSource = RoomIDataBaseImplementation(
                historyDao = get()
            )
        )
    }
}

val network = module {
    single<INetworkStatus> {
        AndroidNetworkStatus(context = androidContext())
    }
}

val mainScreen = module {
    factory {
        MainInteractor(
            remoteRepository = get(qualifier = named(NAME_REMOTE)),
            localRepository = get(qualifier = named(NAME_LOCAL)),
            networkStatus = get()
        )
    }
    scope(named<MainActivity>()) {
        viewModel {
            MainViewModel(interactor = get())
        }
    }
}

val searchScreen = module {

    scope(named<SearchDialogFragment>()) {
        factory<IHistoryInteractor<List<HistoryEntity>>> {
            HistoryInteractorImpl(
                localRepository = get(named(NAME_LOCAL))
            )
        }

        viewModel {
            HistoryViewModel(historyInteractor = get())

        }
    }
}