package com.gosemathraj.fliploot.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gosemathraj.fliploot.data.models.productdetails.ProductDetails
import com.gosemathraj.fliploot.data.models.productlist.Products

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductList(productList : List<Products>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun getProductDetails(productDetails: ProductDetails)


    suspend fun getAllProductsList() : List<Products>

    suspend fun getProductDetails() : ProductDetails
}