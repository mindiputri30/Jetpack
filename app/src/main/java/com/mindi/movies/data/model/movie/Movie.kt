package com.mindi.movies.data.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val id: Int,
    val genres: List<MovieGenre>,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double
) : Parcelable