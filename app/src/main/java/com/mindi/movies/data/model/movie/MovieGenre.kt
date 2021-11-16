package com.mindi.movies.data.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MovieGenre(
    val name: String
) : Parcelable