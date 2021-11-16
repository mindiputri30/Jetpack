package com.mindi.movies.data.room

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mindi.movies.data.model.movie.MovieGenre
import kotlinx.parcelize.Parcelize


@Entity(tableName = "movieEntities")
@Parcelize
data class MovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "imagePath")
    var imagePath: String = "",

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String = "",

    @ColumnInfo(name = "rating")
    var rating: String = "",

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false

) : Parcelable
