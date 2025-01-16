package com.interview.promova_app.domain.repository

import com.interview.promova_app.data.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityRepository {

    fun getNetworkStatus(): Flow<NetworkStatus>
}