package com.mindi.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mindi.movies.data.room.MovieEntity
import com.mindi.movies.data.room.TvEntity
import com.mindi.movies.data.source.ContentRepository
import com.mindi.movies.vo.Resource


class DetailViewModel(private val contentRepository: ContentRepository) : ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieEntity>>
    private lateinit var detailTvShow: LiveData<Resource<TvEntity>>
    fun setMovie(id: String, category: String) {
        when (category) {
            TV_SHOW -> {
                detailTvShow = contentRepository.getTvShowDetail(id.toInt())
            }

            MOVIE -> {
                detailMovie = contentRepository.getMovieDetail(id.toInt())
            }
        }
    }

    fun getMoviesDetail() = detailMovie

    fun getTvShowsDetail() = detailTvShow

    fun setFavoriteMovie() {
        val resource = detailMovie.value
        if (resource?.data != null) {
            val newState = !resource.data.bookmarked
            contentRepository.setFavoriteMovie(resource.data, newState)
        }
    }

    fun setFavoriteTvShow() {
        val resource = detailTvShow.value
        if (resource?.data != null) {
            val newState = !resource.data.bookmarked
            contentRepository.setFavoriteTvShow(resource.data, newState)
        }
    }

    companion object {
        const val TV_SHOW = "extra_tv_show"
        const val MOVIE = "extra_movie"
    }
}