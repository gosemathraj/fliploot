package com.gosemathraj.fliploot.di

import android.content.Context
import androidx.room.Room
import com.gosemathraj.fliploot.data.local.db.Dao
import com.gosemathraj.fliploot.data.local.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    lateinit var database: Database

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context : Context) : Database {
        database = Room.databaseBuilder(context,
            Database::class.java, "fliploot.db"
        ).build()
        return database
    }

    @Provides
    @Singleton
    fun providesDao(database: Database) : Dao {
        return database.dao()
    }
}