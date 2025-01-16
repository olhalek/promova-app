package com.interview.promova_app.di

import android.content.Context
import android.net.ConnectivityManager
import com.interview.promova_app.data.network.NetworkConnectivityRepositoryImpl
import com.interview.promova_app.domain.repository.NetworkConnectivityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class NetworkConnectivityModule {

    @Binds
    abstract fun provideNetworkConnectivityRepository(
        repositoryImpl: NetworkConnectivityRepositoryImpl
    ): NetworkConnectivityRepository
}


@Module
@InstallIn(SingletonComponent::class)
object ConnectivityManagerModule {

    @Provides
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ) = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}