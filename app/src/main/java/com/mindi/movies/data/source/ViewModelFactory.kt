package com.mindi.movies.data.source

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mindi.movies.di.DependencyInjection
import com.mindi.movies.ui.detail.DetailViewModel
import com.mindi.movies.ui.favorite.FavoriteViewModel
import com.mindi.movies.ui.movies.MovieViewModel
import com.mindi.movies.ui.tv_show.TvViewModel

class ViewModelFactory(private val contentRepository: ContentRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(
                contentRepository
            ) as T
            modelClass.isAssignableFrom(TvViewModel::class.java) -> TvViewModel(contentRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(
                contentRepository
            ) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(contentRepository) as T
            }
//            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
//                FavoriteTvShowViewModel(movieCatalogueRepository) as T
//            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(DependencyInjection.provideRepository(context))
            }
    }
}