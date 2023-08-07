package com.backbase.assignment.ui.util

import android.annotation.SuppressLint
import android.net.Uri
import com.backbase.assignment.ui.data.Movie
import com.backbase.assignment.ui.data.MoviePoster
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Inject

class MovieDBService @Inject constructor(private val networkCall: INetworkCall) : IMovieDBService {
    private val baseUrl = "https://api.themoviedb.org/3/movie"
    private val apiKey = "55957fcf3ba81b137f8fc01ac5a31fb5"
    override var onError = { _: Exception -> }

    init {
        networkCall.onError = onError
    }

    override suspend fun getNowPlayingMoviePosters(): List<MoviePoster> {
        return try {
            val jsonString =
                networkCall.getJsonString("$baseUrl/now_playing?language=en-US&page=undefined&api_key=$apiKey")
            val jsonObject = JsonParser.parseString(jsonString).asJsonObject
            val results = jsonObject["results"] as JsonArray
            results.map {
                it as JsonObject
                getMovePoster(it["poster_path"].asString)
            }
        } catch (ex: Exception) {
            onError(ex)
            emptyList()
        }
    }

    override suspend fun getPopularMovies(pageNo: Int): List<Movie> {
        return try {
            val jsonString =
                networkCall.getJsonString("$baseUrl/popular?language=en-US&page=$pageNo&api_key=$apiKey")
            val jsonObject = JsonParser.parseString(jsonString).asJsonObject
            val results = jsonObject["results"] as JsonArray
            results.map {
                it as JsonObject
                getMovie(it)
            }
        } catch (ex: Exception) {
            onError(ex)
            emptyList()
        }
    }

    override suspend fun getMovieDetails(movieId: String): Movie? {
        return try {
            val jsonString =
                networkCall.getJsonString("$baseUrl/$movieId?api_key=$apiKey&language=en-US")
            val movieJsonObject = JsonParser.parseString(jsonString).asJsonObject
            getMovie(movieJsonObject)
        } catch (ex: Exception) {
            onError(ex)
            null
        }
    }


    private fun getMovePoster(posterPath: String): MoviePoster {
        val posterId = posterPath.replace("/", "").replace(".jpg", "")
        val posterUrl = Uri.parse("https://image.tmdb.org/t/p/w500$posterPath")
        return MoviePoster(posterId, posterUrl)
    }

    private fun getRunTime(runTime: Int) = "${runTime / 60}h ${runTime % 60}m"

    @SuppressLint("SimpleDateFormat")
    private fun getReleaseDate(releaseDateString: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val releaseDate = parser.parse(releaseDateString) ?: return ""
        return DateFormat.getDateInstance().format(releaseDate)
    }

    private fun getGenreList(genres : JsonArray) : List<String>{
        return genres.map { it as JsonObject
            it["name"].asString
        }
    }

    private fun getMovie(movieJsonObject : JsonObject) : Movie{
        val movieId = movieJsonObject["id"].asString
        val title = movieJsonObject["title"].asString
        val overview = if (movieJsonObject.has("overview")) movieJsonObject["overview"].asString
        else ""
        val rating =
            if (movieJsonObject.has("vote_average")) (movieJsonObject["vote_average"].asFloat) * 10
            else 0
        val duration =
            if (movieJsonObject.has("runtime")) getRunTime(movieJsonObject["runtime"].asInt)
            else ""
        val releaseDate = if(movieJsonObject.has("release_date")) getReleaseDate(movieJsonObject["release_date"].asString)
        else ""
        val genres = if(movieJsonObject.has("genres")) getGenreList(movieJsonObject["genres"].asJsonArray)
        else listOf()
        val poster  = getMovePoster(movieJsonObject["poster_path"].asString)
        return Movie(
            movieId,
            title,
            overview,
            rating.toInt(),
            duration,
            releaseDate,
            genres,
            poster
        )
    }
}