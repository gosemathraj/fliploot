package com.gosemathraj.fliploot.data.local

import com.gosemathraj.fliploot.data.local.db.Database
import com.gosemathraj.fliploot.data.local.entity.ProductEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val database: Database) {

    suspend fun getAll(): List<ProductEntity> = database.dao().getAllProductsList()

    suspend fun insertAll(productEntityList : List<ProductEntity>) = database.dao().insertProductList(productEntityList)

    suspend fun insert(productEntity: ProductEntity) = database.dao().insertProduct(productEntity)

    suspend fun deleteQuote(productEntity: ProductEntity) = database.dao().removeProduct(productEntity)
}