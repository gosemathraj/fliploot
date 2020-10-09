package com.gosemathraj.fliploot.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId") var productId : Int? = null
}