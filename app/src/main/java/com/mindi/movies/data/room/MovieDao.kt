package com.mindi.movies.data.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getAllMovies(query: SimpleSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieEntities WHERE id = :id")
    fun getMovieById(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM movieEntities WHERE bookmarked = 1")
    fun getFavMovies(): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [TvEntity::class])
    fun getAllTvShows(query: SimpleSQLiteQuery): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tvEntities WHERE id = :id")
    fun getTvShowById(id: Int): LiveData<TvEntity>

    @Query("SELECT * FROM tvEntities WHERE bookmarked = 1")
    fun getFavTvShows(): DataSource.Factory<Int, TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvEntity>)

    @Update
    fun updateTvShow(tvShow: TvEntity)
}