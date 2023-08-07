package com.backbase.assignment.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.NowPlayingItemBinding
import com.backbase.assignment.ui.data.MoviePoster

class NowPlayingMovieAdapter : RecyclerView.Adapter<NowPlayingMovieAdapter.ViewHolder>() {
    var moviePosterList: List<MoviePoster> = listOf()

    override fun getItemCount() = moviePosterList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moviePoster = moviePosterList[position]
        holder.bind(moviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    class ViewHolder private constructor(private val nowPlayingItemBinding: NowPlayingItemBinding) :
        RecyclerView.ViewHolder(nowPlayingItemBinding.root) {
        fun bind(moviePoster: MoviePoster) {
            nowPlayingItemBinding.moviePoster = moviePoster
            nowPlayingItemBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val nowPlayingItemBinding = NowPlayingItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(nowPlayingItemBinding)
            }
        }
    }
}