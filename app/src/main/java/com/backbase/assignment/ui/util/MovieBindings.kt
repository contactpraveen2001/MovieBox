package com.backbase.assignment.ui.movieList

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.data.MoviePoster
import com.backbase.assignment.ui.util.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey


@BindingAdapter("app:setPosterList")
fun setPosterList(nowPlayingMovieRecycleView: RecyclerView, moviePosterList: List<MoviePoster>?) {
    moviePosterList?.let {
        (nowPlayingMovieRecycleView.adapter as NowPlayingMovieAdapter).moviePosterList = moviePosterList
        (nowPlayingMovieRecycleView.adapter as NowPlayingMovieAdapter).notifyDataSetChanged()
    }
}

@BindingAdapter("app:loadMoviePoster")
fun loadMoviePoster(view: ImageView, moviePoster: MoviePoster?) {
    if (moviePoster == null) return
    GlideApp.with(view.context)
        .load(moviePoster.posterUrl)
        .placeholder(R.drawable.image_loading)
        .signature(ObjectKey(moviePoster.posterId))
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(view)
}

@BindingAdapter("app:updateRatingColor")
fun updateRatingColor(ratingView: ProgressBar, progress: Int?) {
    if (progress == null) return
    val context = ratingView.context
    if (progress < 50) {
        ratingView.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorProgressBarLow))
        ratingView.progressBackgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.colorBackGroundProgressBarLow
            )
        )
    } else {
        ratingView.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorProgressBarHigh))
        ratingView.progressBackgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.colorBackGroundProgressBarHigh
            )
        )
    }
}