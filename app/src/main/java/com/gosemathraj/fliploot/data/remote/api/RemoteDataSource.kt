package com.gosemathraj.fliploot.data.remote.api

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: FlipLootApiService){

    suspend fun getAllProductsList() = apiService.getAllProductsList()

    suspend fun getProductDetails() = apiService.getProductDetails()
}