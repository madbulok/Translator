package com.uzlov.translator.di


import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.datasource.RetrofitImplementation
import com.uzlov.translator.model.datasource.RoomDataBaseImplementation
import com.uzlov.translator.model.net.AndroidNetworkStatus
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.model.repository.Repository
import com.uzlov.translator.model.repository.RepositoryImplementation
import com.uzlov.translator.view.main.MainInteractor
import com.uzlov.translator.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val app = module {
    single<Repository<List<WordModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(dataSource = RetrofitImplementation())
    }

    single<Repository<List<WordModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(dataSource = RoomDataBaseImplementation())
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