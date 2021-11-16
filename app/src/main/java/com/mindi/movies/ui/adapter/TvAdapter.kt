package com.mindi.movies.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindi.movies.BuildConfig
import com.mindi.movies.data.room.TvEntity
import com.mindi.movies.databinding.ItemMoviesTvBinding
import com.mindi.movies.ui.detail.DetailActivity
import com.mindi.movies.ui.detail.DetailViewModel.Companion.TV_SHOW

class TvAdapter: PagedListAdapter<TvEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsBinding =
            ItemMoviesTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null){
            holder.bind(tv)
        }
    }


    class TvViewHolder(private val binding: ItemMoviesTvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            val posterPath = BuildConfig.IMAGE_PATH + tv.imagePath
            with(binding) {
                tvTitle.text = tv.title
                tvVote.text = tv.rating
                tvRelease.text = tv.releaseDate
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, tv.id.toString())
                    intent.putExtra(DetailActivity.EXTRA_CATEGORY, TV_SHOW)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(posterPath)
                    .into(poster)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}