package com.mindi.movies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mindi.movies.BuildConfig
import com.mindi.movies.R
import com.mindi.movies.data.room.MovieEntity
import com.mindi.movies.data.room.TvEntity
import com.mindi.movies.data.source.ViewModelFactory
import com.mindi.movies.databinding.ActivityDetailBinding
import com.mindi.movies.databinding.ContentMoviesDetailBinding
import com.mindi.movies.ui.detail.DetailViewModel.Companion.MOVIE
import com.mindi.movies.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.mindi.movies.vo.Status

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var detailContentBinding: ContentMoviesDetailBinding
    private lateinit var activityDetailBinding : ActivityDetailBinding
    private var movieCategory : String? = null
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailBinding.detailContent
        setContentView(activityDetailBinding.root)

        val actionbar = supportActionBar
        actionbar?.title = getString(R.string.detail_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getString(EXTRA_MOVIE)
            movieCategory = extras.getString(EXTRA_CATEGORY)

            if (movieId != null && movieCategory != null) {
                viewModel.setMovie(movieId, movieCategory.toString())
                setupState()
                if (movieCategory == TV_SHOW) {
                    viewModel.getTvShowsDetail().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> {
                                activityDetailBinding.progressBar.visibility = View.VISIBLE
                            }
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    populateTvShow(detail.data)
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    activityDetailBinding.content.visibility = View.VISIBLE
                                }
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.error_message),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                } else {
                    viewModel.getMoviesDetail().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> {
                                activityDetailBinding.progressBar.visibility = View.VISIBLE
                            }
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    populateMovie(detail.data)
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    activityDetailBinding.content.visibility = View.VISIBLE
                                }
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.error_message),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        }

        detailContentBinding.fabFavorite.setOnClickListener(this)
    }

    private fun populateMovie(movieEntity: MovieEntity) {
        val posterPath = BuildConfig.IMAGE_PATH + movieEntity.imagePath
        Glide.with(this)
            .load(posterPath)
            .into(detailContentBinding.detailPoster)
        detailContentBinding.detailTitle.text = movieEntity.title
        detailContentBinding.detailRelease.text = movieEntity.releaseDate
        detailContentBinding.detailGenre.text = movieEntity.genres
        detailContentBinding.detailDescription.text = movieEntity.description
        detailContentBinding.detailRating.text = movieEntity.rating
    }

    private fun populateTvShow(tvEntity: TvEntity) {
        val posterPath = BuildConfig.IMAGE_PATH + tvEntity.imagePath
        Glide.with(this)
            .load(posterPath)
            .into(detailContentBinding.detailPoster)
        detailContentBinding.detailTitle.text = tvEntity.title
        detailContentBinding.detailRelease.text = tvEntity.releaseDate
        detailContentBinding.detailGenre.text = tvEntity.genres
        detailContentBinding.detailDescription.text = tvEntity.description
        detailContentBinding.detailRating.text = tvEntity.rating
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupState() {
        if (movieCategory == MOVIE) {
            viewModel.getMoviesDetail().observe(this, { movie ->
                when (movie.status) {
                    Status.LOADING -> {
                        activityDetailBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        if (movie.data != null) {
                            val state = movie.data.bookmarked
                            setFavoriteState(state)
                            activityDetailBinding.progressBar.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(applicationContext, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else if (movieCategory == TV_SHOW) {
            viewModel.getTvShowsDetail().observe(this, { tvShow ->
                when (tvShow.status) {
                    Status.LOADING -> {
                        activityDetailBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        if (tvShow.data != null) {
                            val state = tvShow.data.bookmarked
                            setFavoriteState(state)
                            activityDetailBinding.progressBar.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(applicationContext, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.fab_favorite -> {
                onFabClicked()
            }
        }
    }

    private fun onFabClicked() {
        if (movieCategory == MOVIE) {
            viewModel.setFavoriteMovie()
        } else if (movieCategory == TV_SHOW) {
            viewModel.setFavoriteTvShow()
        }
    }

    private fun setFavoriteState(state: Boolean) {
        val fab = detailContentBinding.fabFavorite
        if (state) {
            fab.setImageResource(R.drawable.ic_baseline_favorite)
        } else {
            fab.setImageResource(R.drawable.ic_baseline_favorite_border)
        }
    }
    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_CATEGORY = "extra_category"
    }
}