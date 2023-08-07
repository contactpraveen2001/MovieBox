package com.backbase.assignment.ui.util

import com.backbase.assignment.ui.data.Movie
import com.backbase.assignment.ui.data.MoviePoster

interface IMovieDBService {
    var onError: (Exception) -> Unit
    suspend fun getNowPlayingMoviePosters(): List<MoviePoster>
    suspend fun getPopularMovies(pageNo: Int): List<Movie>
    suspend fun getMovieDetails(movieId: String): Movie?
}