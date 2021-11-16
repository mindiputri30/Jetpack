package com.mindi.movies.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtil {
    const val VOTE_BEST = "Best"
    const val VOTE_WORST = "Worst"
    const val RANDOM = "Random"
    const val MOVIE_ENTITIES = "movieEntities"
    const val TV_SHOW_ENTITIES = "tvEntities"

    fun getSortedQuery(filter: String, table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
        when (filter) {
            VOTE_BEST -> simpleQuery.append("ORDER BY rating DESC")
            VOTE_WORST -> simpleQuery.append("ORDER BY rating ASC")
            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}