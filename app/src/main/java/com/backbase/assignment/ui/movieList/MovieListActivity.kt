package com.backbase.assignment.ui.movieList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.backbase.assignment.R
import com.backbase.assignment.databinding.MovieItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        showMovieList()
    }

    private fun showMovieList() {
        val movieItemBinding = MovieItemBinding.inflate(layoutInflater)
        val movieListFragment = MovieListFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.nav_host_fragment, movieListFragment)
            .addSharedElement(movieItemBinding.poster, "poster_small")
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStackImmediate()
        else super.onBackPressed()
    }
}
