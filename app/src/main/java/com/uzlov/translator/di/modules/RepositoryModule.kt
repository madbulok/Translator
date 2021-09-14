package com.uzlov.translator.di.modules

import com.uzlov.translator.di.*
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.datasource.DataSource
import com.uzlov.translator.model.datasource.RetrofitImplementation
import com.uzlov.translator.model.datasource.RoomDataBaseImplementation
import com.uzlov.translator.model.repository.Repository
import com.uzlov.translator.model.repository.RepositoryImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<WordModel>>): Repository<List<WordModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<WordModel>>): Repository<List<WordModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<WordModel>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<WordModel>> = RoomDataBaseImplementation()
}