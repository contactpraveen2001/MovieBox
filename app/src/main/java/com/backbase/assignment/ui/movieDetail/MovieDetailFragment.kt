package com.backbase.assignment.ui.movieDetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.backbase.assignment.R
import com.backbase.assignment.databinding.GenreTextViewBinding
import com.backbase.assignment.databinding.MovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var movieDetailBinding: MovieDetailBinding
    private val movieDetailViewModel by viewModels<MovieDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieDetailBinding = MovieDetailBinding.inflate(layoutInflater, container, false).apply {
            viewModel = movieDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        movieDetailBinding.back.setOnClickListener { activity?.onBackPressed() }
        updateGenres()
        movieDetailViewModel.serviceError.observe(viewLifecycleOwner, { exception ->
            if (exception == null) return@observe
            Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
        })
        val movieId = arguments?.getString("movieId", "") ?: ""
        if (movieId.isNotEmpty()) movieDetailViewModel.getMovieDetail(movieId)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_image)
        return movieDetailBinding.root
    }

    private fun updateGenres() {
        movieDetailViewModel.movie.observe(viewLifecycleOwner, { movie ->
            if (movie?.genres.isNullOrEmpty()) return@observe
            for (genre in movie.genres) {
                val genreTextViewBinding =
                    GenreTextViewBinding.inflate(LayoutInflater.from(context))
                val textView = genreTextViewBinding.root
                textView.id = View.generateViewId()
                genreTextViewBinding.text = genre
                movieDetailBinding.movieDetail.addView(genreTextViewBinding.root)
                movieDetailBinding.genresContainer.addView(genreTextViewBinding.root)
            }
        })
    }
}