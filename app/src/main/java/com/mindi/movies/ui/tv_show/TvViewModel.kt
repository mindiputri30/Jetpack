package com.mindi.movies.ui.tv_show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.source.ContentRepository

class TvViewModel(private val contentRepository: ContentRepository) : ViewModel() {
    fun getTvShow(sort: String) = contentRepository.getTvShowList(sort)
}