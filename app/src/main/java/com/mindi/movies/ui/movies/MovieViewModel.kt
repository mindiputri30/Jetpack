package com.mindi.movies.ui.movies

import androidx.lifecycle.ViewModel
import com.mindi.movies.data.source.ContentRepository

class MovieViewModel(private val contentRepository: ContentRepository) : ViewModel() {
    fun getMovies(sort: String) = contentRepository.getMovieList(sort)
}