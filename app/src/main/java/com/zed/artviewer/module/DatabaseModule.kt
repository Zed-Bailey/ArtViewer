package com.zed.artviewer.module

import android.content.Context
import androidx.room.Room
import com.zed.artviewer.data.database.AppDatabase
import com.zed.artviewer.data.database.FavouriteArtworkDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideFavouriteArtworkDao(appDatabase: AppDatabase): FavouriteArtworkDAO {
        return appDatabase.artworkDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        ).build()
    }
}