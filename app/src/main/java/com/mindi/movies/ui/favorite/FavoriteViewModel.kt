package com.mindi.movies.ui.favorite

import androidx.lifecycle.ViewModel
import com.mindi.movies.data.room.MovieEntity
import com.mindi.movies.data.room.TvEntity
import com.mindi.movies.data.source.ContentRepository

class FavoriteViewModel(private val repository : ContentRepository): ViewModel() {
    fun getFavMovies() = repository.getFavoriteMovies()

    fun setFavMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.bookmarked
        repository.setFavoriteMovie(movieEntity, newState)
    }

    fun getFavTvShows() = repository.getFavoriteTvShows()

    fun setFavTvShow(tvShowEntity: TvEntity) {
        val newState = !tvShowEntity.bookmarked
        repository.setFavoriteTvShow(tvShowEntity, newState)
    }
}