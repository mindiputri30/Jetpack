package com.mindi.movies.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindi.movies.BuildConfig
import com.mindi.movies.data.room.MovieEntity
import com.mindi.movies.databinding.ItemMoviesTvBinding
import com.mindi.movies.ui.detail.DetailActivity
import com.mindi.movies.ui.detail.DetailViewModel.Companion.MOVIE

class MoviesAdapter : PagedListAdapter< MovieEntity , MoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsBinding =
            ItemMoviesTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind(movie)
        }
    }

    class MovieViewHolder(private val binding: ItemMoviesTvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            val posterPath = BuildConfig.IMAGE_PATH + movie.imagePath

            with(binding) {
                tvTitle.text = movie.title
                tvVote.text = movie.rating
                tvRelease.text = movie.releaseDate
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.id.toString())
                    intent.putExtra(DetailActivity.EXTRA_CATEGORY, MOVIE)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(posterPath)
                    .into(poster)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}