package com.mindi.movies.data.model.tv_show

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    val id: Int,
    val first_air_date: String,
    val genres: List<TvShowGenre>,
    val original_name: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double
) : Parcelable