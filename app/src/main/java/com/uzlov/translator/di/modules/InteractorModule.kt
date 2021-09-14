package com.uzlov.translator.di.modules

import com.uzlov.translator.di.NAME_LOCAL
import com.uzlov.translator.di.NAME_REMOTE
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.model.repository.Repository
import com.uzlov.translator.view.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {
    @Provides
    internal fun getInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<WordModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<WordModel>>,
        networkStatus: INetworkStatus
    ) = MainInteractor(repositoryRemote, repositoryLocal, networkStatus)
}