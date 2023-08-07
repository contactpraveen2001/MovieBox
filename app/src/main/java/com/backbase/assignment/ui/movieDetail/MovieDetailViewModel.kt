package com.backbase.assignment.ui.movieDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.backbase.assignment.ui.data.Movie
import com.backbase.assignment.ui.util.IMovieDBService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    application: Application,
    private val movieDBService: IMovieDBService
) : AndroidViewModel(application) {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie
    private val _serviceError = MutableLiveData<Exception>()
    val serviceError: LiveData<Exception> = _serviceError

    init {
        movieDBService.onError = { exception -> _serviceError.value = exception }
    }

    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            _movie.value = movieDBService.getMovieDetails(movieId)
        }
    }
}