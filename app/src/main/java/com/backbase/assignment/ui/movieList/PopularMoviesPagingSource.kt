package com.backbase.assignment.ui.movieList

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.backbase.assignment.ui.data.Movie
import com.backbase.assignment.ui.data.MoviePoster
import com.backbase.assignment.ui.util.IMovieDBService

class PopularMoviesPagingSource(private val movieDBService: IMovieDBService) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = if (page == 1) {
                val data = movieDBService.getPopularMovies(page)
                val newList = ArrayList<Movie>()
                val emptyList =
                    List(3) { Movie("", "", "", 0, "", "", listOf(), MoviePoster("", Uri.EMPTY)) }
                newList.addAll(emptyList)
                newList.addAll(data)
                newList
            } else movieDBService.getPopularMovies(page)
            LoadResult.Page(
                response, prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}