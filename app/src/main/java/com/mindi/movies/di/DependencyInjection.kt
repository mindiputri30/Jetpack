package com.mindi.movies.di

import android.content.Context
import com.mindi.movies.data.remote.RemoteDataSource
import com.mindi.movies.data.room.LocalDataSource
import com.mindi.movies.data.room.MovieRoomDatabase
import com.mindi.movies.data.source.ContentRepository
import com.mindi.movies.utils.AppExecutors

object DependencyInjection {
    fun provideRepository(context: Context): ContentRepository {
        val database = MovieRoomDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()
        return ContentRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}