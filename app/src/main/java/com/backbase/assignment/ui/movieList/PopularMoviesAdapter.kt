package com.backbase.assignment.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.databinding.MovieItemBinding
import com.backbase.assignment.databinding.NowPlayingViewBinding
import com.backbase.assignment.databinding.SectionItemBinding
import com.backbase.assignment.ui.data.Movie

class PopularMoviesAdapter(private val viewModel: MovieListViewModel) :
    PagingDataAdapter<Movie, PopularMoviesAdapter.ViewHolder>(REPO_COMPARATOR) {


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0, 2 -> ViewType.Section.ordinal
            1 -> ViewType.NowPlaying.ordinal
            else -> ViewType.MovieItem.ordinal
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ViewType.Section.ordinal -> {
                val text =
                    if (position == 0) holder.itemView.context.resources.getString(R.string.playing_now)
                    else holder.itemView.context.resources.getString(R.string.most_popular)
                holder.bindSection(text)
            }
            ViewType.NowPlaying.ordinal -> holder.bindNowPlayingItem(viewModel)
            else -> {
                val movie = getItem(position) ?: return
                holder.bindMovieItem(viewModel, movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            ViewType.Section.ordinal -> ViewHolder.section(parent)
            ViewType.NowPlaying.ordinal -> ViewHolder.nowPlaying(parent)
            else -> ViewHolder.movieItem(parent)
        }
    }
    class ViewHolder private constructor(private val itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindMovieItem(viewModel: MovieListViewModel, movie: Movie) {
            val movieItemBinding = itemBinding as MovieItemBinding
            movieItemBinding.root.setOnClickListener { viewModel.onMovieClick(movie.movieId) }
            movieItemBinding.movie = movie
            movieItemBinding.executePendingBindings()
        }

        fun bindSection(text: String) {
            val sectionItemBinding = itemBinding as SectionItemBinding
            sectionItemBinding.text = text
            sectionItemBinding.executePendingBindings()
        }

        fun bindNowPlayingItem(viewModel: MovieListViewModel) {
            val nowPlayingViewBinding = itemBinding as NowPlayingViewBinding
            nowPlayingViewBinding.viewModel = viewModel
            nowPlayingViewBinding.recyclerViewPlayingNow.adapter = NowPlayingMovieAdapter()
            nowPlayingViewBinding.executePendingBindings()
        }

        companion object {
            fun movieItem(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val movieItemBinding = MovieItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(movieItemBinding)
            }
            fun section(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val sectionItemBinding = SectionItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(sectionItemBinding)
            }
            fun nowPlaying(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val nowPlayingViewBinding = NowPlayingViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(nowPlayingViewBinding)
            }
        }
    }
    enum class ViewType {
        Section, NowPlaying, MovieItem
    }
}