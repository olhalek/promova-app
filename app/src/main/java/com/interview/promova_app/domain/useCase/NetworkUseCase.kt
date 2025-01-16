package com.interview.promova_app.domain.useCase

import com.interview.promova_app.data.network.NetworkStatus
import com.interview.promova_app.domain.repository.NetworkConnectivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NetworkUseCase @Inject constructor(
    private val networkConnectivityRepository: NetworkConnectivityRepository
) {

    suspend fun getNetworkStatus(coroutineScope: CoroutineScope):StateFlow<NetworkStatus> {
         return networkConnectivityRepository.getNetworkStatus().stateIn(coroutineScope)
    }
}