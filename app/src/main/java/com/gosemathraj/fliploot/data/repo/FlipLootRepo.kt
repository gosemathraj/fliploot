package com.gosemathraj.fliploot.data.repo

import com.gosemathraj.fliploot.data.remote.api.RemoteDataSource
import javax.inject.Inject

class FlipLootRepo @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getAllProductsList() = remoteDataSource.getAllProductsList()

    suspend fun getProductsDetails() = remoteDataSource.getProductDetails()
}