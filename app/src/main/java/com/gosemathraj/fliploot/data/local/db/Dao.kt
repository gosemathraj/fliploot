package com.gosemathraj.fliploot.data.local.db

import androidx.room.*
import androidx.room.Dao
import com.gosemathraj.fliploot.data.local.entity.ProductEntity
import com.gosemathraj.fliploot.data.models.productdetails.Product
import com.gosemathraj.fliploot.data.models.productdetails.ProductDetails
import com.gosemathraj.fliploot.data.models.productlist.Products

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductList(productEntityList : List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM productentity")
    suspend fun getAllProductsList() : List<ProductEntity>

    @Delete
    suspend fun removeProduct(productEntity: ProductEntity)
}