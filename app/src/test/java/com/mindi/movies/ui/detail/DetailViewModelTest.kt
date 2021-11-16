package com.mindi.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mindi.movies.data.model.movie.Movie
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.source.ContentRepository
import com.mindi.movies.ui.detail.DetailViewModel.Companion.MOVIE
import com.mindi.movies.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.mindi.movies.utils.DataDummyMovies
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel

    private val dummyMovie = DataDummyMovies.generateDummyMovies()[0]
    private val dummyMovieId = dummyMovie.id.toString()

    private val dummyTvShow = DataDummyMovies.generateDummyTvShows()[0]
    private val dummyTvShowId = dummyTvShow.id.toString()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: ContentRepository

    @Mock
    private lateinit var movieObserver: Observer<Movie>

    @Mock
    private lateinit var tvShowObserver:Observer<TvShow>

    @Before
    fun setUpMovie() {
        viewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun getMovieDetail() {
        val movieDetail = MutableLiveData<Movie>()
        movieDetail.value = dummyMovie

        `when`(movieRepository.getMovieDetail(dummyMovieId)).thenReturn(movieDetail)
        viewModel.setMovie(dummyMovieId, MOVIE)

        val detailEntity = viewModel.getMoviesDetail().value as Movie
        verify(movieRepository).getMovieDetail(dummyMovieId)


        assertNotNull(detailEntity)
        assertEquals(dummyMovie.id, detailEntity.id)
        assertEquals(dummyMovie.original_title, detailEntity.original_title)
        assertEquals(dummyMovie.genres, detailEntity.genres)
        assertEquals(dummyMovie.poster_path, detailEntity.poster_path)
        assertEquals(dummyMovie.release_date, detailEntity.release_date)
        assertEquals(dummyMovie.vote_average.toString(), detailEntity.vote_average.toString())
        assertEquals(dummyMovie.overview, detailEntity.overview)

        viewModel.getMoviesDetail().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Before
    fun setupTvShow() {
        viewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun getTvShowDetail() {
        val tvShow = MutableLiveData<TvShow>()
        tvShow.value = dummyTvShow

        `when`(movieRepository.getTvShowDetail(dummyTvShowId)).thenReturn(tvShow)
        viewModel.setMovie(dummyTvShowId, TV_SHOW)

        val tvDetail = viewModel.getTvShowsDetail().value as TvShow
        verify(movieRepository).getTvShowDetail(dummyTvShowId)

        assertNotNull(tvDetail)
        assertEquals(dummyTvShow.id, tvDetail.id)
        assertEquals(dummyTvShow.genres, tvDetail.genres)
        assertEquals(dummyTvShow.first_air_date, tvDetail.first_air_date)
        assertEquals(dummyTvShow.poster_path, tvDetail.poster_path)
        assertEquals(dummyTvShow.original_name, tvDetail.original_name)
        assertEquals(dummyTvShow.vote_average.toString(), tvDetail.vote_average.toString())
        assertEquals(dummyTvShow.overview, tvDetail.overview)

        viewModel.getTvShowsDetail().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }
}