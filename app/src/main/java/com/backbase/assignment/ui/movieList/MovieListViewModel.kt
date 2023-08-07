package com.backbase.assignment.ui.movieList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.backbase.assignment.ui.data.Movie
import com.backbase.assignment.ui.data.MoviePoster
import com.backbase.assignment.ui.util.IMovieDBService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    application: Application,
    private val movieDBService: IMovieDBService
) : AndroidViewModel(application) {
    private val _posterList = MutableLiveData<List<MoviePoster>>()
    val posterList: LiveData<List<MoviePoster>> = _posterList
    private val _onMovieClick = MutableLiveData("")
    val onMovieClick: LiveData<String> = _onMovieClick
    private val _serviceError = MutableLiveData<Exception>()
    val serviceError: LiveData<Exception> = _serviceError

    init {
        movieDBService.onError = { exception -> _serviceError.value = exception }
        getNowPlayingMoviePosters()
    }

    fun getPopularMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = { PopularMoviesPagingSource(movieDBService) }
        ).liveData
    }


    private fun getNowPlayingMoviePosters() {
        viewModelScope.launch {
            _posterList.value = movieDBService.getNowPlayingMoviePosters()
        }
    }

    fun onMovieClick(movieId: String) {
        _onMovieClick.value = movieId
        _onMovieClick.value = ""
    }
}