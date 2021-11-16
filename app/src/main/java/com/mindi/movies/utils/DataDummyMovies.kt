package com.mindi.movies.utils

import com.mindi.movies.data.model.movie.Movie
import com.mindi.movies.data.model.movie.MovieGenre
import com.mindi.movies.data.model.tv_show.TvShow
import com.mindi.movies.data.model.tv_show.TvShowGenre

object DataDummyMovies {

    fun generateDummyMovies(): List<Movie> {
        val movies = ArrayList<Movie>()

        movies.add(Movie(464052, listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(587996, listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(
            Movie(
                602269,
                listOf(MovieGenre("Crime"), MovieGenre("Action")),
                "The Little Things",
                "Deputy Sheriff Joe \"Deke\" Deacon joins forces with Sgt. Jim Baxter to search for a serial killer who's terrorizing Los Angeles. As they track the culprit, Baxter is unaware that the investigation is dredging up echoes of Deke's past, uncovering disturbing secrets that could threaten more than his case.",
                "/c7VlGCCgM9GZivKSzBgzuOVxQn7.jpg",
                "2021-01-27",
                7.5
            ))

        movies.add(Movie(775996, listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(604822,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(651571,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(508442,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(560144,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(522444,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(644092,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(539885,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(581387,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(520946,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(495764,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        movies.add(Movie(553604,  listOf(MovieGenre("genre")), "", "", "", "", 0.0))

        return movies
    }

    fun generateDummyTvShows(): List<TvShow> {
        val tvShow = ArrayList<TvShow>()

        tvShow.add(TvShow(85271, "",  listOf(TvShowGenre("")), "", "", "", 0.0))

        tvShow.add(TvShow(69050,  "2017-01-26", listOf(TvShowGenre("Mystery"), TvShowGenre("Drama"), TvShowGenre("Crime")), "Riverdale", "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.", "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg", 0.0))

        tvShow.add(TvShow(79460,  "2018-10-25", listOf(TvShowGenre("Sci-Fi & Fantasy"), TvShowGenre("Drama")), "Legacies", "In a place where young witches, vampires, and werewolves are nurtured to be their best selves in spite of their worst impulses, Klaus Mikaelson’s daughter, 17-year-old Hope Mikaelson, Alaric Saltzman’s twins, Lizzie and Josie Saltzman, among others, come of age into heroes and villains at The Salvatore School for the Young and Gifted.", "/qTZIgXrBKURBK1KrsT7fe3qwtl9.jpg", 0.0))

        tvShow.add(TvShow(114695,  "2021-01-08", listOf(TvShowGenre("Documentary")), "Marvel Studios: Legends", "Revisit the epic heroes, villains and moments from across the MCU in preparation for the stories still to come. Each dynamic segment feeds directly into the upcoming series — setting the stage for future events. This series weaves together the many threads that constitute the unparalleled Marvel Cinematic Universe.", "/EpDuYIK81YtCUT3gH2JDpyj8Qk.jpg", 0.0))

        tvShow.add(TvShow(71712, "2017-09-25", listOf(TvShowGenre("Drama")), "The Good Doctor", "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives", "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg", 0.0))

        tvShow.add(TvShow(77169, "",  listOf(TvShowGenre("")), "", "", "", 0.0))

        tvShow.add(TvShow(1416, "",  listOf(TvShowGenre("")), "", "", "", 0.0))

        tvShow.add(TvShow(82856, "",  listOf(TvShowGenre("")), "", "", "", 0.0))

        tvShow.add(TvShow(93297, "",  listOf(TvShowGenre("")), "", "", "", 0.0))

        tvShow.add(TvShow(44217, "",  listOf(TvShowGenre("")), "", "", "", 0.0))

        return tvShow
    }

    fun generateDummyDetailMovie(): Movie {
        return Movie(
            602269,
            listOf(MovieGenre("Crime"), MovieGenre("Action")),
            "The Little Things",
            "Deputy Sheriff Joe \"Deke\" Deacon joins forces with Sgt. Jim Baxter to search for a serial killer who's terrorizing Los Angeles. As they track the culprit, Baxter is unaware that the investigation is dredging up echoes of Deke's past, uncovering disturbing secrets that could threaten more than his case.",
            "/c7VlGCCgM9GZivKSzBgzuOVxQn7.jpg",
            "2021-01-27",
            0.0
        )
    }

    fun generateDummyDetailTvShow(): TvShow {
        return TvShow(
            69050,
            "2017-01-26",
            listOf(TvShowGenre("Mystery"), TvShowGenre("Drama"), TvShowGenre("Crime")),
            "Riverdale",
            "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
            "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
            0.0
        )
    }

}