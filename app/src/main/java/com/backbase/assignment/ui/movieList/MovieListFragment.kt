package com.backbase.assignment.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.backbase.assignment.R
import com.backbase.assignment.databinding.MovieDetailBinding
import com.backbase.assignment.databinding.MovieListBinding
import com.backbase.assignment.ui.movieDetail.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val movieListViewModel by viewModels<MovieListViewModel>()
    private lateinit var movieListBinding: MovieListBinding
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieListBinding = MovieListBinding.inflate(inflater, container, false).apply {
            viewModel = movieListViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        popularMoviesAdapter = PopularMoviesAdapter(movieListViewModel)
        movieListBinding.recyclerViewPopular.adapter = popularMoviesAdapter
        movieListViewModel.onMovieClick.observe(viewLifecycleOwner, { movieId ->
            if (movieId.isNullOrEmpty()) return@observe
            showMovieDetail(movieId)
        })
        movieListViewModel.serviceError.observe(viewLifecycleOwner, { exception ->
            if (exception == null) return@observe
            Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
        })
        movieListViewModel.getPopularMovies().observe(viewLifecycleOwner, { data ->
            lifecycleScope.launch {
                popularMoviesAdapter.submitData(data)
            }
        })
        return movieListBinding.root
    }

    private fun showMovieDetail(movieId: String) {
        val movieDetailBinding = MovieDetailBinding.inflate(layoutInflater)
        val movieDetailFragment = MovieDetailFragment()
        movieDetailFragment.arguments = Bundle().apply { putString("movieId", movieId) }
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("movieDetailFragment")
        fragmentTransaction.add(R.id.nav_host_fragment, movieDetailFragment)
            .addSharedElement(movieDetailBinding.poster, "poster_big")
            .commit()
    }
}