package com.gosemathraj.fliploot.data.remote.api

import com.gosemathraj.fliploot.data.models.productdetails.ProductDetails
import com.gosemathraj.fliploot.data.models.productlist.ProductList
import com.gosemathraj.fliploot.data.remote.api.config.Urls
import retrofit2.http.GET

interface FlipLootApiService {

    @GET(Urls.getProductList)
    suspend fun getAllProductsList() : ProductList

    @GET(Urls.getProductDetails)
    suspend fun getProductDetails() : ProductDetails
}