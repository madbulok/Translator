package com.uzlov.translator.di


import androidx.room.Room
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.datasource.RetrofitImplementation
import com.uzlov.translator.model.datasource.RoomIDataBaseImplementation
import com.uzlov.translator.model.net.AndroidNetworkStatus
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.model.repository.ILocalRepository
import com.uzlov.translator.model.repository.IRepository
import com.uzlov.translator.model.repository.LocalRepositoryImpl
import com.uzlov.translator.model.repository.RemoteRepositoryImplementation
import com.uzlov.translator.presenter.IHistoryInteractor
import com.uzlov.translator.room.HistoryDataBase
import com.uzlov.translator.room.HistoryEntity
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

    single<IRepository<List<WordModel>>>(named(NAME_REMOTE)) {
        RemoteRepositoryImplementation(dataSource = RetrofitImplementation())
    }

    single<ILocalRepository<List<HistoryEntity>>>(named(NAME_LOCAL)) {
        LocalRepositoryImpl(dataSource = RoomIDataBaseImplementation(historyDao = get()))
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

    viewModel {
         MainViewModel(interactor = get())
    }
}

val searchScreen = module {

    factory<IHistoryInteractor<List<HistoryEntity>>> {
        HistoryInteractorImpl(
            localRepository = get(named(NAME_LOCAL)))

    }

    viewModel {
        HistoryViewModel(historyInteractor = get())
    }
}