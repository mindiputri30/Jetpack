package com.mindi.movies.network

import com.mindi.movies.data.model.movie.Movie
import com.mindi.movies.data.model.movie.MovieResponse
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.model.tv_show.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiConfig {
    @GET("discover/movie")
    fun getMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("movie/{movieId}")
    fun getMovieDetail(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<Movie>

    @GET("discover/tv")
    fun getTvShows(@Query("api_key") apiKey: String): Call<TvShowResponse>

    @GET("tv/{movieId}")
    fun getTvShowDetail(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<TvShow>
}