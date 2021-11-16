package com.mindi.movies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mindi.movies.data.model.movie.Movie
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.room.MovieEntity
import com.mindi.movies.data.room.TvEntity
import com.mindi.movies.vo.Resource

interface ContentDataSource {
    fun getMovieList(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun getTvShowList(sort: String): LiveData<Resource<PagedList<TvEntity>>>

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvEntity>>

    fun setFavoriteTvShow(tvShow: TvEntity, state: Boolean)
}