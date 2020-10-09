package com.gosemathraj.fliploot.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gosemathraj.fliploot.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class] , version = 1)
abstract class Database : RoomDatabase() {

    abstract fun dao(): Dao
}