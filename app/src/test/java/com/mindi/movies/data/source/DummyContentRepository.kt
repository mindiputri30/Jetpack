package com.mindi.movies.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindi.movies.data.model.movie.Movie
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.remote.RemoteDataSource

class DummyContentRepository(private val remoteDataSource: RemoteDataSource) : ContentDataSource {
    private val listMovies = MutableLiveData<List<Movie>>()
    private val movies = MutableLiveData<Movie>()
    private val listTvShows = MutableLiveData<List<TvShow>>()
    private val tvShows = MutableLiveData<TvShow>()

    override fun getMovieList(): LiveData<List<Movie>> {
        remoteDataSource.getMovieList(object : RemoteDataSource.MovieCallback {
            override fun onResponse(movieResponse: List<Movie>) {
                listMovies.postValue(movieResponse)
            }
        })
        return listMovies
    }

    override fun getMovieDetail(movieId: String): LiveData<Movie> {
        remoteDataSource.getMovieDetail(movieId, object : RemoteDataSource.MovieDetailCallback {
            override fun onResponse(movieDetailResponse: Movie) {
                movies.postValue(movieDetailResponse)
            }
        })
        return movies
    }

    override fun getTvShowList(): LiveData<List<TvShow>> {
        remoteDataSource.getTvShowList(object : RemoteDataSource.TvShowCallback {
            override fun onResponse(tvShowResponse: List<TvShow>) {
                listTvShows.postValue(tvShowResponse)
            }
        })
        return listTvShows
    }

    override fun getTvShowDetail(tvShowId: String): LiveData<TvShow> {
        remoteDataSource.getTvShowDetail(tvShowId, object : RemoteDataSource.TvShowDetailCallback {
            override fun onResponse(tvShowDetail: TvShow) {
                tvShows.postValue(tvShowDetail)
            }
        })
        return tvShows
    }
}