package com.mindi.movies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mindi.movies.data.model.movie.Movie
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.remote.ApiResponse
import com.mindi.movies.data.remote.RemoteDataSource
import com.mindi.movies.data.room.LocalDataSource
import com.mindi.movies.data.room.MovieEntity
import com.mindi.movies.data.room.TvEntity
import com.mindi.movies.utils.AppExecutors
import com.mindi.movies.vo.Resource

class ContentRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ContentDataSource {

    companion object {
        @Volatile
        private var instance: ContentRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): ContentRepository =
            instance ?: synchronized(this) {
                instance ?: ContentRepository(remoteData, localDataSource, appExecutors)
            }
    }

    override fun getMovieList(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<Movie>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> =
                remoteDataSource.getMovieList()

            override fun saveCallResult(data: List<Movie>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        id = response.id,
                        genres = "",
                        description = response.overview,
                        imagePath = response.poster_path,
                        releaseDate = response.release_date,
                        title = response.original_title,
                        rating = response.vote_average.toString(),
                        bookmarked = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, Movie>(appExecutors) {
            override fun loadFromDb(): LiveData<MovieEntity> = localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data != null && data.genres == ""

            override fun createCall(): LiveData<ApiResponse<Movie>> =
                remoteDataSource.getMovieDetail(movieId.toString())

            override fun saveCallResult(data: Movie) {
                val genres = StringBuilder().append("")

                for (i in data.genres.indices) {
                    if (i < data.genres.size - 1) {
                        genres.append("${data.genres[i].name}, ")
                    } else {
                        genres.append(data.genres[i].name)
                    }
                }

                val movie = MovieEntity(
                    id = data.id,
                    genres = genres.toString(),
                    description = data.overview,
                    imagePath = data.poster_path,
                    releaseDate = data.release_date,
                    title = data.original_title,
                    rating = data.vote_average.toString(),
                    bookmarked = false
                )
                localDataSource.updateMovie(movie, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavMovies(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movie, state)
        }
    }

    override fun getTvShowList(sort: String): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvShow>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllTvShows(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> =
                remoteDataSource.getTvShowList()

            override fun saveCallResult(data: List<TvShow>) {
                val movieList = ArrayList<TvEntity>()
                for (response in data) {
                    val movie = TvEntity(
                        id = response.id,
                        genres = "",
                        description = response.overview,
                        imagePath = response.poster_path,
                        releaseDate = response.first_air_date,
                        title = response.original_name,
                        rating = response.vote_average.toString(),
                        bookmarked = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertTvShows(movieList)
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvShow>(appExecutors) {
            override fun loadFromDb(): LiveData<TvEntity> =
                localDataSource.getTvShowById(tvShowId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data != null && data.genres == ""

            override fun createCall(): LiveData<ApiResponse<TvShow>> =
                remoteDataSource.getTvShowDetail(tvShowId.toString())

            override fun saveCallResult(data: TvShow) {
                val genres = StringBuilder().append("")

                for (i in data.genres.indices) {
                    if (i < data.genres.size - 1) {
                        genres.append("${data.genres[i].name}, ")
                    } else {
                        genres.append(data.genres[i].name)
                    }
                }

                val tvShow = TvEntity(
                    id = data.id,
                    genres = genres.toString(),
                    description = data.overview,
                    imagePath = data.poster_path,
                    releaseDate = data.first_air_date,
                    title = data.original_name,
                    rating = data.vote_average.toString(),
                    bookmarked = false
                )
                localDataSource.updateTvShow(tvShow, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavTvShows(), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShow(tvShow, state)
        }
    }
}