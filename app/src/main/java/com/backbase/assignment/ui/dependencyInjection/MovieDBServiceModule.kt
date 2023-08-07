package com.backbase.assignment.ui.dependencyInjection

import com.backbase.assignment.ui.util.IMovieDBService
import com.backbase.assignment.ui.util.INetworkCall
import com.backbase.assignment.ui.util.MovieDBService
import com.backbase.assignment.ui.util.NetworkCall
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class MovieDBServiceNetworkModule {
    @ViewModelScoped
    @Binds
    abstract fun bindMovieDBService(movieDBService: MovieDBService): IMovieDBService
}

@InstallIn(ViewModelComponent::class)
@Module
abstract class NetworkCallModule {
    @ViewModelScoped
    @Binds
    abstract fun bindNetworkCall(networkCall: NetworkCall): INetworkCall
}