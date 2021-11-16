package com.mindi.movies.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mindi.movies.data.remote.RemoteDataSource
import com.mindi.movies.utils.DataDummyMovies
import com.mindi.movies.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer

class ContentRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteRepository = Mockito.mock(RemoteDataSource::class.java)
    private val movieRepository = DummyContentRepository(remoteRepository)

    private var movies = DataDummyMovies.generateDummyMovies()
    private val movieId = movies[0].id.toString()

    private val tvShows = DataDummyMovies.generateDummyTvShows()
    private val tvShowId = tvShows[0].id.toString()

    private val movieDetail = DataDummyMovies.generateDummyDetailMovie()
    private val tvShowDetail = DataDummyMovies.generateDummyDetailTvShow()

    private var testUtil = LiveDataTestUtil()

    @Test
    fun getMovieList() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.MovieCallback)
                .onResponse(movies)
            null
        }.`when`(remoteRepository).getMovieList(any())

        val movieEntities = testUtil.getValue(movieRepository.getMovieList())
        Mockito.verify(remoteRepository).getMovieList(any())

        assertNotNull(movieEntities)
        assertEquals(movies.size, movieEntities.size)
    }


    @Test
    fun getMovieDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.MovieDetailCallback)
                .onResponse(movieDetail)
            null
        }.`when`(remoteRepository).getMovieDetail(eq(movieId), any())

        val movieDetailEntities = testUtil.getValue(movieRepository.getMovieDetail(movieId))
        Mockito.verify(remoteRepository).getMovieDetail(eq(movieId), any())

        assertNotNull(movieDetailEntities)
        assertEquals(movieDetail.id, movieDetailEntities.id)
        assertEquals(movieDetail.genres, movieDetailEntities.genres)
        assertEquals(movieDetail.original_title, movieDetailEntities.original_title)
        assertEquals(
            movieDetail.vote_average.toString(),
            movieDetailEntities.vote_average.toString()
        )
        assertEquals(movieDetail.release_date, movieDetailEntities.release_date)
        assertEquals(movieDetail.poster_path, movieDetailEntities.poster_path)
        assertEquals(movieDetail.overview, movieDetailEntities.overview)
    }

    @Test
    fun getTvShowList() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.TvShowCallback)
                .onResponse(tvShows)
            null
        }.`when`(remoteRepository).getTvShowList(any())

        val tvEntities = testUtil.getValue(movieRepository.getTvShowList())
        Mockito.verify(remoteRepository).getTvShowList(any())

        assertNotNull(tvEntities)
        assertEquals(tvEntities.size, tvEntities.size)
    }

    @Test
    fun getTvShowDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.TvShowDetailCallback)
                .onResponse(tvShowDetail)
            null
        }.`when`(remoteRepository).getTvShowDetail(eq(tvShowId), any())

        val tvShowDetailEntities = testUtil.getValue(movieRepository.getTvShowDetail(tvShowId))
        Mockito.verify(remoteRepository).getTvShowDetail(eq(tvShowId), any())

        assertNotNull(tvShowDetailEntities)
        assertEquals(tvShowDetail.id, tvShowDetailEntities.id)
        assertEquals(tvShowDetail.first_air_date, tvShowDetailEntities.first_air_date)
        assertEquals(tvShowDetail.poster_path, tvShowDetailEntities.poster_path)
        assertEquals(
            movieDetail.vote_average.toString(),
            tvShowDetailEntities.vote_average.toString()
        )
        assertEquals(tvShowDetail.genres, tvShowDetailEntities.genres)
        assertEquals(tvShowDetail.original_name, tvShowDetailEntities.original_name)
        assertEquals(tvShowDetail.overview, tvShowDetailEntities.overview)

    }
}