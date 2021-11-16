package com.mindi.movies.ui.tv_show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.source.ContentRepository
import com.mindi.movies.utils.DataDummyMovies
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {
    private lateinit var viewModel: TvViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: ContentRepository

    @Mock
    private lateinit var observer: Observer<List<TvShow>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(movieRepository)
    }
    @Test
    fun getTvShow(){
        val dummyTvShows = DataDummyMovies.generateDummyTvShows()
        val tvShows = MutableLiveData<List<TvShow>>()
        tvShows.value = dummyTvShows

        `when`(movieRepository.getTvShowList()).thenReturn(tvShows)
        val tvShow = viewModel.getTvShow().value
        verify(movieRepository).getTvShowList()
        assertNotNull(tvShow)
        assertEquals(10, tvShow?.size)

        movieRepository.getTvShowList().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}