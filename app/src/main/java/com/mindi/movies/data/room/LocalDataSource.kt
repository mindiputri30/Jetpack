package com.mindi.movies.data.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.mindi.movies.utils.SortUtil
import com.mindi.movies.utils.SortUtil.MOVIE_ENTITIES
import com.mindi.movies.utils.SortUtil.TV_SHOW_ENTITIES

class LocalDataSource (private val movieDao: MovieDao) {

    fun getAllMovies(sort: String): DataSource.Factory<Int, MovieEntity> = movieDao.getAllMovies(SortUtil.getSortedQuery(sort, MOVIE_ENTITIES))

    fun getMovieById(id: Int): LiveData<MovieEntity> = movieDao.getMovieById(id)

    fun getFavMovies(): DataSource.Factory<Int, MovieEntity> = movieDao.getFavMovies()

    fun getAllTvShows(sort: String): DataSource.Factory<Int, TvEntity> = movieDao.getAllTvShows(SortUtil.getSortedQuery(sort, TV_SHOW_ENTITIES))

    fun getTvShowById(id: Int): LiveData<TvEntity> = movieDao.getTvShowById(id)

    fun getFavTvShows(): DataSource.Factory<Int, TvEntity> = movieDao.getFavTvShows()

    fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.bookmarked = newState
        movieDao.updateMovie(movie)
    }

    fun updateMovie(movie: MovieEntity, newState: Boolean) {
        movie.bookmarked = newState
        movieDao.updateMovie(movie)
    }

    fun insertTvShows(tvShows: List<TvEntity>) = movieDao.insertTvShows(tvShows)

    fun setFavoriteTvShow(tvShow: TvEntity, newState: Boolean) {
        tvShow.bookmarked = newState
        movieDao.updateTvShow(tvShow)
    }

    fun updateTvShow(tvShow: TvEntity, newState: Boolean) {
        tvShow.bookmarked = newState
        movieDao.updateTvShow(tvShow)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }
}