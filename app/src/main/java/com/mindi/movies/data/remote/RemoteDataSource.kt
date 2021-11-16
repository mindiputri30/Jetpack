package com.mindi.movies.data.remote


import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindi.movies.BuildConfig
import com.mindi.movies.data.model.movie.Movie
import com.mindi.movies.data.model.movie.MovieResponse
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.model.tv_show.TvShowResponse
import com.mindi.movies.network.RetrofitClient
import com.mindi.movies.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor() {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val apiKey = BuildConfig.API_KEY
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getMovieList(): LiveData<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<Movie>>>()
        val retrofitClient = RetrofitClient()

        handler.postDelayed({
            retrofitClient.getApiService().getMovies(apiKey)
                .enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        resultMovies.value = ApiResponse.success(response.body()?.results as List<Movie>)
                        EspressoIdlingResource.decrement()
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.e("RemoteDataSource", "getMovieList onFailure : ${t.message}")
                        EspressoIdlingResource.decrement()
                    }
                })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovies
    }

    fun getMovieDetail(movieId: String): LiveData<ApiResponse<Movie>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<Movie>>()
        val retrofitClient = RetrofitClient()
        handler.postDelayed({
            retrofitClient.getApiService().getMovieDetail(movieId, apiKey)
                .enqueue(object : Callback<Movie> {
                    override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                        resultDetailMovie.value = ApiResponse.success(response.body() as Movie)
                        EspressoIdlingResource.decrement()
                    }

                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                        Log.e("RemoteDataSource", "getMovieDetail onFailure : ${t.message}")
                        EspressoIdlingResource.decrement()
                    }
                })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultDetailMovie
    }

    fun getTvShowList(): LiveData<ApiResponse<List<TvShow>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShow>>>()
        val retrofitClient = RetrofitClient()
        handler.postDelayed({
            retrofitClient.getApiService().getTvShows(apiKey)
                .enqueue(object : Callback<TvShowResponse> {
                    override fun onResponse(
                        call: Call<TvShowResponse>,
                        response: Response<TvShowResponse>
                    ) {
                        resultTvShow.value = ApiResponse.success(response.body()?.results as List<TvShow>)
                        EspressoIdlingResource.decrement()
                    }

                    override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                        Log.e("RemoteDataSource", "getTvShowList onFailure : ${t.message}")
                        EspressoIdlingResource.decrement()
                    }

                })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultTvShow
    }

    fun getTvShowDetail(movieId: String): LiveData<ApiResponse<TvShow>> {
        EspressoIdlingResource.increment()
        val resultDetailTvShow = MutableLiveData<ApiResponse<TvShow>>()
        val retrofitClient = RetrofitClient()
        handler.postDelayed({
            retrofitClient.getApiService().getTvShowDetail(movieId, apiKey)
                .enqueue(object : Callback<TvShow> {
                    override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                        resultDetailTvShow.value = ApiResponse.success(response.body() as TvShow)
                        EspressoIdlingResource.decrement()
                    }

                    override fun onFailure(call: Call<TvShow>, t: Throwable) {
                        Log.e("RemoteDataSource", "getTvDetail onFailure : ${t.message}")
                        EspressoIdlingResource.decrement()
                    }
                })
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultDetailTvShow
    }
}